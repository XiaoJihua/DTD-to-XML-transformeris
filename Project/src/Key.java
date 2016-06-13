/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Simon
 */
public class Key extends Attribute {
    private String parent;

    public Key(String parentName, String name, String option, String fixed) {
        super(name, option, fixed);
        this.parent = parentName;
    }
    
    public String getParent()
    {
        return parent;
    }
    
    public void setParent(String parent)
    {
        this.parent = parent;
    }
}
