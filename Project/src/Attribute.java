/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simon
 */
public class Attribute {
    private String name;    
    private String option;
    private String fixed;
    
    public Attribute(String name, String option, String fixed)
    {
        this.name = name;      
        this.option = option;
        this.fixed = fixed;
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
        return "<xsd:attribute name=\"" + name + "\" type=\"xsd:string\" " + 
               "use=\"" + option + "\"" + ((!fixed.isEmpty()) ? " fixed=" + fixed : "") + "/>\n";         
    }
}
