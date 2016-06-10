/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author Simon
 */
public class Attribute {
    private String name;
    private String option;
    
    public Attribute(String name, String option)
    {
        this.name = name;
        this.option = option;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getOption()
    {
        return option;
    }
    
    public void setOption(String option)
    {
        this.option = option;
    }
    
    public String toXsd() 
    {
        return "<xsd:attribute name=\"" + name + "\" type=\"xsd:string\" " + "use=\"" + (option.equals("REQUIRED") ? "required" : "optional" ) + "\"/>\n";
    }
}
