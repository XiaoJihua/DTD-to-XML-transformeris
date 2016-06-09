/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import structures.Document;
import structures.Structure;

/**
 *
 * @author Simon
 */
public class Parser {

    private Document document;
    private String documentName;
    
    public Parser(String nazovDokumentu, Document document) {
        this.documentName = nazovDokumentu;
        this.document = document;
    }
    
    public void parse() throws FileNotFoundException
    {
        File XMLFile = new File(documentName);
        Scanner scanner = new Scanner(XMLFile);
        scanner.useDelimiter(">");
        String buffer = null;
        while(scanner.hasNext()) {
            buffer=scanner.next();
            if(buffer.contains("DOCTYPE"))
            {
                break;
            }
            //System.out.println("SQL statement: " + buffer);
        }
        List<String> elements = new LinkedList<>();
        
        String version = "1.0";
        document.setVersion(version);
        String encoding = "UTF-8";
        document.setEncoding(encoding);
        if(buffer.contains("[")){
            //System.out.println("DTD Primo v XML: " + buffer);
            
            String[] parts = buffer.split("\\[");
            String part1 = parts[0];
            String part2 = parts[1];
            String[] parts2 = part1.split(" ", 2);
            String jmenoKorenu = parts2[1];
            System.out.println("Jmeno korenu = " + jmenoKorenu);
            elements.add(jmenoKorenu);
            document.addElement(null, elements, "element");
            elements.clear();
            //System.out.println("Parts3-2");
            parseDTDline(part2);
            
            while(scanner.hasNext()) {
                buffer=scanner.next();
                /*if(buffer.contains("\\]")){
                    System.out.println("ZASTAVENI PRO : " + buffer);
                    break;
                }*/
                if(buffer.contains("]")){
                    //System.out.println("ZASTAVENI PRO : " + buffer);
                    break;
                }
                //System.out.println("POKRACOVANI PRO : " + buffer);
                parseDTDline(buffer);
            }      
            
            
            
            
            
        }else{
            String[] parts = buffer.split("\"", 3);
            //String DTDFilename = parts[1];
            String DTDFilename = "C:\\Users\\Tomáš\\Downloads\\DTD-to-XML-transformeris-master\\examples\\2.dtd";
            File DTDFile = new File(DTDFilename);
            System.out.println("\n\n\n\n\n\n\n\n\nDTD v souboru zvlast: " + DTDFilename);
            
            String[] parts2 = buffer.split(" ", 3);
            String jmenoKorenu = parts2[1];
            System.out.println("Jmeno korenu = " + jmenoKorenu);
            elements.add(jmenoKorenu);
            document.addElement(null, elements, "element");
            elements.clear();
            
            
            
            Scanner scannerDTD = new Scanner(DTDFile);
            scannerDTD.useDelimiter(">");
            /*
            TODO
                otevrit soubor a nahazet ho do parse DTDLine (zase pomoci scanneru)
                overit jestli parsuje jak by melo
            */
            while(scannerDTD.hasNext()) {
                buffer=scannerDTD.next();
                parseDTDline(buffer);
            }  
        }
        
        
        /*
        List<String> elements = new LinkedList<>();
        
        String version = "1.0";
        document.setVersion(version);
        String encoding = "UTF-8";
        document.setEncoding(encoding);
        
        String root1 = "retazce";
        elements.add(root1);
        document.addElement(null, elements, "element");
        elements.clear();
        
        String element1 = "element1";
        String element2 = "element2";
        String element3 = "element3";
        elements.add(element1);
        elements.add(element2);
        elements.add(element3);
        document.addElement(root1, elements, "element");
        elements.clear();
        
        
        String sequence1 = "sequence1";
        String sequence2 = "sequence2";
        elements.add(sequence1);
        elements.add(sequence2);
        document.addElement(root1, elements, "sequence");
        elements.clear();
        
        String choice1 = "choice1";
        String choice2 = "choice2";
        elements.add(choice1);
        elements.add(choice2);
        document.addElement(root1, elements, "choice");
        elements.clear();
       
        String element4 = "element4";
        elements.add(element4);
        document.addElement(root1, elements, "element");
        elements.clear();
        
        String element5 = "element5";
        String element6 = "element6";
        elements.add(element5);
        elements.add(element6);
        document.addElement(element4, elements, "element");
        elements.clear();
        
        String choice3 = "choice3";
        String choice4 = "choice4";
        elements.add(choice3);
        elements.add(choice4);
        document.addElement(element4, elements, "choice");
        elements.clear();
        
        String sequence3 = "sequence3";
        String sequence4 = "sequence4";
        elements.add(sequence3);
        elements.add(sequence4);
        document.addElement(element4, elements, "sequence");
        elements.clear();      
        
        String attribute1 = "attribute1";
        document.addAttribute(root1, attribute1, "ID", "REQUIRED");
        
        String attribute2 = "attribute2";
        document.addAttribute(element2, attribute2, "CDATA", "REQUIRED");
        String attribute3 = "attribute3";
        document.addAttribute(element2, attribute3, "ID", "REQUIRED");
        */
    }    

    private void parseDTDline(String line) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
            TODO
            -Najit zpusob rozparsovani potomku
            -specificky ktery *?+ patri kteremu potomku (popripade vsem)
            -Nahazet to do dokumentu
            
            
        */        
        List<String> elements = new LinkedList<>();
            //System.out.println("Kolik casti = " + parts.length);
        if(line.contains("ELEMENT")){
            String[] parts = line.split(" ", 3);
            if(parts.length<3){
                return;
            }
            String zacatek = parts[0];
            String jmenoUzlu = parts[1];
            String zbytek = parts[2];
            System.out.println("------------------------------------------------------");
            System.out.println("Zpracovani elementu");
            System.out.println("Jmeno Uzlu : "+jmenoUzlu);
            System.out.println("Zbytek     : "+zbytek);
            if(zbytek.charAt(zbytek.length()-1)!=')'){
                System.out.println("Vnejsi opakovani zpusobem " + zbytek.charAt(zbytek.length()-1) );
            }
            if(zbytek.contains("(#PCDATA)")){
                /*
                TODO
                elements.add("(#PCDATA)");
                document.addElement(jmenoUzlu, elements, "string");
                elements.clear();
                */
            }
        }
        if(line.contains("ATTLIST")){
            System.out.println("------------------------------------------------------");
            System.out.println("Neni attlist     : "+line);
            /*String[] parts = line.split(" ", 4);
            if(parts.length<4){
                return;
            }
            System.out.println("------------------------------------------------------");
            System.out.println("Zacatek    : "+parts[0]);
            System.out.println("Jmeno Uzlu : "+parts[1]);
            System.out.println("Jmeno parametru : "+parts[1]);
            System.out.println("Zbytek     : "+zbytek);*/
        }
    }
}

