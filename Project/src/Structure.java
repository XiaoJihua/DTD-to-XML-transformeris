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
public class Structure 
{
    private String identifier;
    private List<Elem> elems = null;
    private int minOccurs;
    private int maxOccurs;
        
    public Structure(List<String>names, String identifier)
    {
        this.identifier = identifier;
        elems = new LinkedList<>();
        for(String i:names)
        {
            elems.add(new Elem(i));
        }
    }
    
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
    
    public String getIdentifier()
    {
        return identifier;
    }
    
    public List<Elem> getElements()
    {
        return elems;
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


