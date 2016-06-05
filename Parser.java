/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import java.util.LinkedList;
import java.util.List;
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
    
    public void parse()
    {
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
        
    }    
}
