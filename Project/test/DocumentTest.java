/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Peavey
 */
public class DocumentTest extends TestCase {

    private Elem elem;
    private Document instance;
    
    /**
     * Constructor, inherits from superclass
     * @param testName name pf test
     */
    public DocumentTest(String testName) {
        super(testName);        
    }
    
    /**
     * This method sets up variables before every test
     * @throws java.lang.Exception
     */ 
    @Override
    protected void setUp() throws Exception {
        elem = new Elem("rootElement");
        instance = new Document();
        instance.addElement(null, elem);
    }
    
    /**
     * This method cleans up after every test
     * @throws java.lang.Exception
     */ 
    @Override
    protected void tearDown() throws Exception {
        elem = null;
        instance = null;
    }
    
    /**
     * Ssed to add different elements to parent. Convenience method only.
     * @param s identifier of which element to add
     * @param parentElement where to put new element
     */
    private void fill(int s, String parentElement) {
        Elem newElem;
        switch (s){
            case 1: newElem = new Elem("element1");
                    break;
            case 2: newElem = new Elem("element2");
                      break;
            case 3: newElem = new Elem("element3");
                    break;
            default: newElem = new Elem("element4");
                     break;
        }
        instance.addElement(parentElement, newElem);
    }
    
    /**
     * Test of addElement method, of class Document.
     * Tests if elements are added correctly. Tries root element and sub-elements.
     */
    public void testAddElement() {
            
        System.out.println("Test: add element");
            
        System.out.println("    Subtest: add root element");
        assertEquals("Adding root element failed.", 0, instance.getElements("rootElement").size());
        
        fill(1, "rootElement");
        
        System.out.println("    Subtest: add child element");
        assertEquals("Adding child element failed.", 1, instance.getElements("rootElement").size());
        
        System.out.println("    Subtest: check child element");
        assertEquals("Stored element is incorrect.", "element1", instance.getElements("rootElement").get(0).getName());
        
        System.out.println("    Subtest: check element list");
        assertEquals("There shouldn't be anything in element list.", 0, instance.getElements("element1").size());
    }

    /**
     * Test of findElement method, of class Document.
     * Creates a few elements and tries to fetch them using FindElement method.
     */
    public void testFindElement() {
        
        System.out.println("Test: Find element");
        
        Elem expResult = new Elem("rootElement");
        Elem result = instance.findElement("rootElement");
        
        System.out.println("    Subtest: find root element");
        assertEquals(expResult.getName(), result.getName());
    
        fill(2, "rootElement");
        
        expResult = new Elem("element2");
        result = instance.findElement("element2");
        
        System.out.println("    Subtest: find child element");
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of addAttribute method, of class Document.
     * Creates a few elements, adds them some attributes and checks if they were added correctly.
     */
    public void testAddAttribute() {
        
        System.out.println("Test: add attribute");
        
        String name = "attribute1";
        String option = "required";
        String fixed = "";
        Attribute attribute = new Attribute(name, option, fixed);
        instance.addAttribute("rootElement", attribute);
        
        System.out.println("    Subtest: check root element's attribute");
        assertEquals("Attribute should exist.", "attribute1", instance.getAttributes("rootElement").get(0).getName());
        assertEquals("Attribute should exist.", "required", instance.getAttributes("rootElement").get(0).getOption());
        
        fill(1, "rootElement");
        fill(2, "element1");
        name = "attribute2";
        option = "optional";  
        attribute = new Attribute(name, option, fixed);
        instance.addAttribute("element2", attribute);
        
        System.out.println("    Subtest: check sub-root element's attribute");
        assertEquals("Attribute should exist.", "attribute2", instance.getAttributes("element2").get(0).getName());
        assertEquals("Attribute should exist.", "optional", instance.getAttributes("element2").get(0).getOption());
    }

    /**
     * Test of getRootElement method, of class Document.
     * Basic test for getting root element.
     */
    public void testGetRootElement() {
        
        System.out.println("Test: get root element");
        
        Elem expResult = new Elem("rootElement");
        Elem result = instance.getRootElement();
        assertEquals("Root element should be present", expResult.getName(), result.getName());
    }

    /**
     * Test of toXsd method, of class Document.
     * Creates root, under it 2 elements with one sub-element each.
     * Performs basic automatic checks
     * Prints output to System.out for manual check
     */
    public void testToXsd() {

        System.out.println("Test: toXsd");

        String name = "attribute1";
        String option = "required";
        String fixed = "";
        Attribute attribute = new Attribute(name, option, fixed);
        
        Structure struct1 = new Structure("sequence");
        Structure struct2 = new Structure("sequence");
        Structure struct3 = new Structure("sequence");
        
        Elem elem1 = new Elem("element1");
        Elem elem2 = new Elem("element2");
        Elem elem3 = new Elem("element3");
        Elem elem4 = new Elem("element4");
     
        struct1.addObject(elem1);
        struct1.addObject(elem2);
        instance.addElement("rootElement", elem1);
        instance.addElement("rootElement", elem2);
        instance.addElement("element1", elem3);
        instance.addElement("element2", elem4);
        
        instance.addObject("rootElement", struct1);
        
        struct2.addObject(elem3);
        instance.findElement("element1").addObject(struct2);
        struct3.addObject(elem4);
        instance.findElement("element2").addObject(struct3);
        
        instance.addAttribute("element3", attribute);
        instance.setEncoding("UTF-8");
        instance.setVersion("1.0");
        String result = instance.toXsd();
        System.out.println(result); //prints xsd to standard output
        assertTrue(result.contains("<xsd:element name=\"rootElement\""));
        assertTrue(result.contains("</xsd:element>"));
        assertTrue(result.contains("<xsd:element name=\"element1\""));
        assertTrue(result.contains("<xsd:element name=\"element4\""));
        assertTrue(result.contains("<xsd:attribute name=\"attribute1\""));
    }
}
