/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author Simon
 */
public class Parser 
{
    private Document document;
    private boolean isDTD = false;
    private boolean parseNext = true;
    
    public Parser(Document document) 
    {
        
        this.document = document;        
    }
    
    public File parse(File file) throws IOException
    {                
        boolean isReferenceSet = false;
        try (FileInputStream inputStream = new FileInputStream(file)) 
        {                                
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   
            File tempFile = new File("myTempFile.xml");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            int character = reader.read();
            String block = new String();
            while(character != -1 && parseNext)
            {
                if(String.valueOf((char)character).equals("<"))
                {
                    block += String.valueOf((char)character);
                    while(!String.valueOf((char)character).equals(">") && !String.valueOf((char)character).equals("["))
                    {                        
                        character = reader.read();
                        block += String.valueOf((char)character);                         
                    }
                    
                    if(block.contains("!DOCTYPE"))
                    {
                        isDTD = true;
                    }
                    
                    if(!block.contains("!DOCTYPE") && !block.contains("!ELEMENT") && !block.contains("!ATTLIST"))
                    {                        
                        if(!isReferenceSet && !block.contains("<?") && !block.contains("?>"))
                        {            
                            block = block.substring(0, block.indexOf(">"));
                            block += " xmlns=\"http://www.w3schools.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.w3schools.com output.xsd\">";
                            character = reader.read();
                            while(character != -1)
                            {
                                block += String.valueOf((char)character);  
                                character = reader.read();             
                            }
                            isReferenceSet = true;
                            parseNext = false;                         
                        }                                      
                        writer.write(block + System.getProperty("line.separator"));                                          
                    }                    
                    
                    if(block.contains("version") && block.contains("<?") && block.contains("?>"))
                    {
                        int start;
                        int end;
                        String subString1;
                        String subString2;
                        
                        subString1 = block.substring(block.indexOf("version"));
                        start = subString1.indexOf("\"");
                        subString2 = subString1.substring(start = subString1.indexOf("\""));                        
                        start = 1;
                        end = subString2.substring(1).indexOf("\"") + 1;                        
                        subString2 = subString2.substring(start, end);
                        subString2 = subString2.replaceAll("\\s","");
                                              
                        document.setVersion(subString2);                        
                    }

                    if(block.contains("encoding") && block.contains("<?") && block.contains("?>"))
                    {
                        int start;
                        int end;
                        String subString1;
                        String subString2;
                        
                        subString1 = block.substring(block.indexOf("encoding"));
                        start = subString1.indexOf("\"");
                        subString2 = subString1.substring(start = subString1.indexOf("\""));                        
                        start = 1;
                        end = subString2.substring(1).indexOf("\"") + 1;                        
                        subString2 = subString2.substring(start, end);
                        subString2 = subString2.replaceAll("\\s","");
            
                        document.setEncoding(subString2);
                    }
                    
                    if(!block.endsWith("[") && block.contains("!"))
                    {
                        parseBlock(block);
                    }                                        
                    block = "";
                }
                character = reader.read();
            }
            
            reader.close();
            writer.close();
            return tempFile;
        }       
    }               
    
    public void parseBlock(String block)
    {
        block = block.replaceAll("\\n","");
        block = block.replaceAll("\\s+"," ");
        
        if(block.contains("DOCTYPE"))
        {
            int start;
            int end;
            String subString = new String(block);
            start = subString.indexOf("\"");
            subString = subString.substring(start = subString.indexOf("\""));                        
            start = 1;
            end = subString.substring(1).indexOf("\"") + 1;                        
            subString = subString.substring(start, end);
            subString = subString.replaceAll("\\s","");
            
            File file = new File(subString); 
            Parser p1 = new Parser(document);
            try
            {            
                p1.parse(file);            
            }
            catch( Exception e )
            {

            }            
            
            return;
        }

        String[] parts = block.split(" ",3);        
        String type = parts[0];
        String parentName = parts[1];  
        String sequence = parts[2];     
        
        if(type.contains("ELEMENT"))
        {
            sequence = sequence.replaceAll(" ","");          

            if(document.getRootElement() == null)
            {
                document.addElement(null, new Elem(parentName));
            }        
            if(sequence.contains("#PCDATA"))
            {
                return;
            }
            document.addObject(parentName, parseSequence(sequence, parentName));
        }
        
        if(type.contains("ATTLIST"))
        {           
            parseAttributes(sequence, parentName);
        }          
          
    }
    
    public Structure parseSequence(String sequence, String parentName)
    {  
        String parts = sequence.substring(sequence.indexOf("(") + 1, sequence.lastIndexOf(")"));     
        Structure seq = new Structure("sequence");
        Structure choice = null;
        
       
        if(sequence.lastIndexOf(")")!= sequence.length() - 1)
        {
            switch(sequence.charAt(sequence.lastIndexOf(")") + 1))
            {            
                case '*':
                    seq.setMinOccurs("0");
                    seq.setMaxOccurs("unbounded");
                    break;
                case '+':
                    seq.setMinOccurs("1");
                    seq.setMaxOccurs("unbounded");
                    break;
                case '?':
                    seq.setMinOccurs("0");
                    seq.setMaxOccurs("1");
                    break;
                default:                   
                    break;
            }
        }
       
        int position = 0;
        int i = 0;        
        boolean activeChoice = false;        
        
        while(i < parts.length())
        {    
            switch (parts.charAt(i))
            {
                case ',':
                    String substr = parts.substring(position, i); 
                    
                    if(activeChoice)
                    {
                        if(substr.contains("("))
                        {
                            choice.addObject(parseSequence(substr, parentName));
                            seq.addObject(choice);
                            choice = null;
                            activeChoice = false;
                        }
                        else
                        {
                            choice.addObject(createElement(substr, parentName));
                            seq.addObject(choice);
                            choice = null;
                            activeChoice = false;
                        }   
                    }
                    else
                    {
                        if(substr.contains("("))
                        {
                            seq.addObject(parseSequence(substr, parentName));
                        }
                        else
                        {
                            seq.addObject(createElement(substr, parentName));
                        }                                
                    }                      
               
                    position = i+1;
                    i++;
                    break;                
                case '(':                    
                    i += findPairToBracket(parts.substring(i));                    
                    break;
                case '|':  
                    if(!activeChoice)
                    {
                        activeChoice = true;   
                        choice = new Structure("choice");
                    }

                    String substr2 = parts.substring(position, i);  
                    
                    if(substr2.contains("("))
                    {
                        Structure str3 = parseSequence(substr2, parentName);                        
                        choice.addObject(str3);              
                    }
                    else
                    {                            
                        choice.addObject(createElement(substr2, parentName));                            
                    }                            
                    position = i+1;                          
                    i++;
                    break;
                default:
                    if(i == (parts.length() - 1))
                    {
                        String substr3 = parts.substring(position, i + 1);                         
                        if(activeChoice)
                        {
                            if(substr3.contains("("))
                            {
                                choice.addObject(parseSequence(substr3, parentName));
                                seq.addObject(choice);
                                choice = null;
                                activeChoice = false;
                            }
                            else
                            {
                                choice.addObject(createElement(substr3, parentName));
                                seq.addObject(choice);
                                choice = null;
                                activeChoice = false;
                            }   
                        }
                        else
                        {
                            if(substr3.contains("("))
                            {
                                seq.addObject(parseSequence(substr3, parentName));
                            }
                            else
                            {
                                seq.addObject(createElement(substr3, parentName));
                            }                                
                        }                                                
                    }
                    i++;
                    break;
            }           
        }        
        return seq;
    }
    public Elem createElement(String substr, String parentName)
    {                
        Elem element1;
        if(substr.contains("+") | substr.contains("*") | substr.contains("?"))
        {
            element1 = new Elem(substr.substring(0, substr.length() - 1));

            switch(substr.charAt(substr.length() - 1))
            {
                case '*':
                    element1.setMinOccurs("0");
                    element1.setMaxOccurs("unbounded");
                    break;
                case '+':
                    element1.setMinOccurs("1");
                    element1.setMaxOccurs("unbounded");
                    break;
                case '?':
                    element1.setMinOccurs("0");
                    element1.setMaxOccurs("1");
                    break;
                default:
                    break;
            }                        
        } 
        else
        {
            element1 = new Elem(substr);                        
        }  
        document.addElement(parentName, element1);
        return element1;
    }
    
    public int findPairToBracket(String sequence)
    {
        int numberOfBrackets = 1;
        int position = 0;
        while(numberOfBrackets != 0)
        {            
            position++;
            switch(sequence.charAt(position))
            {
                case '(':
                    numberOfBrackets++;
                    break;
                case ')':
                    numberOfBrackets--;
                    break;
                default: break;
            }            
        }
        return position;
    }
    
    public void parseAttributes(String sequence, String parentName)
    {   
        String name = "";
        String type = "";
        String use = "";
        String fixedValue = "";        
       
        String[] parts = sequence.split(" ");
        int counter = 0;
        for(String i:parts)
        {     
            switch(counter)
            {
                case 0:
                    name = i;
                    break;
                case 1:
                    type = i;
                    break;
                case 2:
                    use = i;
                    if(!use.contains("#FIXED"))
                    {
                        if(type.equals("ID"))
                        {
                            document.addKey(parentName, name, "");
                        }
                        
                        if(use.contains("#REQUIRED"))
                        {
                            document.addAttribute(parentName, new Attribute(name, "required", ""));
                        }
                        else
                        {
                            document.addAttribute(parentName, new Attribute(name, "optional", "")); 
                        }                        
                        counter = -1;
                    }
                    break;
                case 3:
                    fixedValue = i;
                     if(type.equals("ID"))
                        {
                            document.addKey(parentName, name, fixedValue);
                        }
                        
                        document.addAttribute(parentName, new Attribute(name, "required", fixedValue));
                        
                                            
                    counter = -1;
                    break;
                default:
                    break;
            } 
            counter++;   
        }           

    }
    
    public boolean getIsDTD()
    {
        return isDTD;
    }
}
