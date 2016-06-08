/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class Document {
    private String version;
    private String encoding;
    private Elem rootElement = null;
    private List<Elem> allElements = new LinkedList<>();
    
    public void addElement(String parentName, List<String> names, String type)
    {
        if(parentName == null)
        {
            rootElement = new Elem(names.get(0));
            allElements.add(rootElement);
        }
        else
        {
            Elem pom = findElement(parentName);
            Structure pomStruct = pom.addElement(names, type);
            allElements.addAll(pomStruct.getElements());
        }
    }
    
    public Elem findElement(String parentName)
    {
        for(Elem i:allElements)
        {
            if(i.getName().equals(parentName))
            {
                return i;
            }
        }
        return null;
    }
    
    public void addAttribute(String parentName, String name, String type, String option)
    {        
            Elem pom = findElement(parentName);
            pom.addAttribute(name, type, option); 
    }
    
    public List<Structure> getElements(String parentName)
    {
        Elem pom = findElement(parentName);
        return pom.getStructures();
    }
    
    public List<Attribute> getAttributes(String parentName)
    {
        Elem pom = findElement(parentName);
        return pom.getAttributes();
    }
    
    public Elem getRootElement()
    {
        return rootElement;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getEncoding()
    {
        return encoding;
    }
    
    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }
}
