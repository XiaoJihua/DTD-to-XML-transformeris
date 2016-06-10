/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformeris;
import Parser.Parser;
import java.io.PrintWriter;
import structures.Attribute;
import structures.Document;
import structures.Elem;
import structures.Structure;


/**
 *
 * @author Simon
 */
public class Transformeris {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nazovDokumentu = "xmlko";
        Document document = new Document();
        
        Parser p1 = new Parser(nazovDokumentu, document);
        p1.parse();
        
        try
        {     
            PrintWriter out = new PrintWriter("output.txt");
            out.print(document.toXsd());
            out.close();
        } 
        catch( Exception e )
        {
            
        }
    }
}
