/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformeris;
import Parser.Parser;
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
        
        System.out.println("version:" + document.getVersion());
        System.out.println("encoding:" + document.getEncoding());
        System.out.println("root element:" + document.getRootElement().getName());
        
        for(Structure i:document.getElements(document.getRootElement().getName()))
        {
            for(Elem j:i.getElements())
            {
                System.out.println("elements in root " + i.getIdentifier()+ ": " + j.getName());                
            }
        }
        
        for(Attribute i:document.getAttributes(document.getRootElement().getName()))
        {
            System.out.println("attributes in root:\nname " + i.getName() + "\ntype " + i.getType() + "\noption " + i.getOption());                
        }
        
        for(Attribute i:document.getAttributes("element2"))
        {
            System.out.println("attributes in element2:\nname " + i.getName() + "\ntype " + i.getType() + "\noption " + i.getOption());                
        }
    }
}
