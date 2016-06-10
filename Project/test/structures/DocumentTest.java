/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Peavey
 */
public class DocumentTest extends TestCase {

    private List<String> names;
    private String type;
    private Document instance;
    
    public DocumentTest(String testName) {
        super(testName);        
    }
    
    @Override
    protected void setUp() throws Exception {
        names = new LinkedList<>();
        names.add("rootElement");
        type = "element";
        instance = new Document();
        instance.addElement(null, names, type);
    }
    
    @Override
    protected void tearDown() throws Exception {
        names = null;
        type = null;
        instance = null;
    }
    
    private void fill(char s, String parentElement) {
        names.clear();
        switch (s){
            case '1': names.add("element1");
                      break;
            case '2': names.add("element2");
                      break;
            case '3': names.add("element3");
                      break;
            default:  names.add("element4");
                      break;
        }
        instance.addElement(parentElement, names, type);
    }
    
    /**
     * Test of addElement method, of class Document.
     */
    public void testAddElement() {
            
        System.out.println("Test: add element");
            
        System.out.println("    Subtest: add root element");
        assertEquals("Adding root element failed.", 0, instance.getElements("rootElement").size());
        
        fill('1', "rootElement");
        
        System.out.println("    Subtest: add child element");
        assertEquals("Adding child element failed.", 1, instance.getElements("rootElement").size());
        
        System.out.println("    Subtest: check child element");
        assertEquals("Stored element is incorrect.", "element1", instance.getElements("rootElement").get(0).getElements().get(0).getName());
        
        System.out.println("    Subtest: check element list");
        assertEquals("There shouldn't be anything in element list.", 0, instance.getElements("element1").size());
    }

    /**
     * Test of findElement method, of class Document.
     */
    public void testFindElement() {
        
        System.out.println("Test: Find element");
        
        Elem expResult = new Elem("rootElement");
        Elem result = instance.findElement("rootElement");
        
        System.out.println("    Subtest: find root element");
        assertEquals(expResult.getName(), result.getName());
    
        fill('2', "rootElement");
        
        expResult = new Elem("element2");
        result = instance.findElement("element2");
        
        System.out.println("    Subtest: find child element");
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of addAttribute method, of class Document.
     */
    public void testAddAttribute() {
        
        System.out.println("Test: add attribute");
        
        String name = "attribute1";
        String option = "REQUIRED";        
        instance.addAttribute("rootElement", name, option);
        
        System.out.println("    Subtest: check root element's attribute");
        assertEquals("Attribute should exist.", "attribute1", instance.getAttributes("rootElement").get(0).getName());
        assertEquals("Attribute should exist.", "REQUIRED", instance.getAttributes("rootElement").get(0).getOption());
        
        fill('1', "rootElement");
        fill('2', "element1");
        name = "attribute2";
        option = "OPTIONAL";  
        instance.addAttribute("element2", name, option);
        
        System.out.println("    Subtest: check sub-root element's attribute");
        assertEquals("Attribute should exist.", "attribute2", instance.getAttributes("element2").get(0).getName());
        assertEquals("Attribute should exist.", "OPTIONAL", instance.getAttributes("element2").get(0).getOption());
    }

    /**
     * Test of getRootElement method, of class Document.
     */
    public void testGetRootElement() {
        
        System.out.println("Test: get root element");
        
        Elem expResult = new Elem("rootElement");
        Elem result = instance.getRootElement();
        assertEquals("Root element should be present", expResult.getName(), result.getName());
    }

    /**
     * Test of toXsd method, of class Document.
     */
    public void testToXsd() {

        System.out.println("Test: toXsd");

        fill('1', "rootElement");
        fill('2', "rootElement");
        fill('3', "element1");
        fill('4', "element2");
        instance.addAttribute("element3","attribute1","REQUIRED");
        instance.setEncoding("UTF-8");
        instance.setVersion("1.0");
        String result = instance.toXsd();
        assertTrue(result.contains("<xsd:element name=\"rootElement\""));
        assertTrue(result.contains("</xsd:element>"));
        assertTrue(result.contains("<xsd:element name=\"element1\""));
        assertTrue(result.contains("<xsd:element name=\"element4\""));
        assertTrue(result.contains("<xsd:attribute name=\"attribute1\""));
    }
}
