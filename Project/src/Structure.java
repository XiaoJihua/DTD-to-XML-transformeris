/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class Structure 
{
    private String identifier;
    private List<Object> structures = null;
    private String minOccurs;
    private String maxOccurs;
        
    public Structure(String identifier)
    {
        this.identifier = identifier;
        structures = new LinkedList<>();  
        minOccurs = "1";
        maxOccurs = "1";
    }
    
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    
    public String getIdentifier()
    {
        return identifier;
    }
    
    public List<Object> getObjects()
    {
        return Collections.unmodifiableList(structures);
    }
    
    public void addObject(Object object)
    {
        structures.add(object);
    }
    
    public String getMaxOccurs()
    {
        return maxOccurs;
    }
    public void setMaxOccurs(String maxOccurs)
    {
        this.maxOccurs = maxOccurs;
    }
    
    public String getMinOccurs()
    {
        return minOccurs;
    }
    public void setMinOccurs(String minOccurs)
    {
        this.minOccurs = minOccurs;
    }
    
    
    public String toXsd(Document document) 
    {
        String ret = new String();
        ret += "<xsd:" + identifier + " minOccurs=\"" + getMinOccurs() + "\" maxOccurs=\"" + getMaxOccurs() + "\">\n";
        for(Object o:structures)
        {
            if(o.getClass().equals(Elem.class))
            {                
                ret += ((Elem)o).toXsd(document);
            }
            else 
            {                
                ret += ((Structure)o).toXsd(document);   
            }
        }    
        ret += "</xsd:" + identifier + ">\n";
              
        return ret;
    }
}


