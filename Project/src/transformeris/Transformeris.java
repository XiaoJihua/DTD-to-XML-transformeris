/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformeris;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import parser.Parser;
import structures.Document;

/**
 *
 * @author Simon
 */
public class Transformeris {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {        
        if (args.length == 0) {
            System.err.println("No xml file specified.");
            return;
        }
    
        String documentName = args[0];
        File file = new File(documentName);
        File referencedFile = new File("");
        Document document = new Document();
        
        Parser p1 = new Parser(document);
        try
        {            
            referencedFile = p1.parse(file);            
        }
        catch( Exception e )
        {
            
        } 
        
        if(p1.getIsDTD())
        {
            file.delete();
            referencedFile.renameTo(file);
        }
        else
        {
            System.out.println("ERROR: DTD is not included in file");
        }
        
        
        try
        {     
            PrintWriter out = new PrintWriter("output.xsd");
            out.print(document.toXsd());
            out.close();
        } 
        catch( Exception e )
        {
            
        }
    }
    
}