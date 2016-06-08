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
    private String type;
    private String option;
    
    public Attribute(String name, String type, String option)
    {
        this.name = name;
        this.type = type;
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
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOption()
    {
        return option;
    }
    
    public void setOption(String option)
    {
        this.option = option;
    }
}
