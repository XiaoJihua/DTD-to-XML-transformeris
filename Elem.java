/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class Elem 
{   
    private String name = null;
    private List<Structure> structures = null;
    private List<Attribute> attributes = null;
    private int minOccurs = 0;
    private int maxOccurs = 0;
    
    public Elem(String name)
    {
        this.name = name;     
    }    
    
    public Structure addElement(List<String> names, String type)
    {
        if(structures == null)
        {
            structures = new LinkedList<>();
        }
        
        Structure pom = new Structure(names, type);
        structures.add(pom);
        return pom;
    }
    
    public void addAttribute(String name, String type, String option)
    {
        if(attributes == null)
        {
            attributes = new LinkedList<>();
        }
        
        Attribute pom = new Attribute(name, type, option);
        attributes.add(pom);
    }
    
    public String getName()
    {
        return name;
    }
    
    public List<Structure> getStructures()
    {
        return Collections.unmodifiableList(structures);
    }
    
    public List<Attribute> getAttributes()
    {
        return Collections.unmodifiableList(attributes);
    }
    
    public int getMaxOccurs()
    {
        return maxOccurs;
    }
    public void setMaxOccurs(int maxOccurs)
    {
        this.maxOccurs = maxOccurs;
    }
    
    public int getMinOccurs()
    {
        return minOccurs;
    }
    public void setMinOccurs(int minOccurs)
    {
        this.minOccurs = minOccurs;
    }
}
