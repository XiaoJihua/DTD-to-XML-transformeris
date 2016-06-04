/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformeris;

import junit.framework.Test;
import junit.framework.TestCase;

/**
 *
 * @author Peavey
 */
public class TransformerisTest extends TestCase {
    
    public TransformerisTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of main method, of class Transformeris.
     */
    public void testMain() {
        System.out.println("main test");
        String[] args = null;
        Transformeris.main(args);
    }

    /**
     * Test of addition method, of class Transformeris.
     */
    public void testAddition() {
        System.out.println("addition test");
        int a = 1;
        int b = 2;
        Transformeris instance = new Transformeris();
        int expResult = 3;
        int result = instance.addition(a, b);
        assertEquals(expResult, result);
    }
    public void testFunc1() {
        System.out.println("func1 test");
        int x = 3;
        Transformeris instance = new Transformeris();
        int expResult = 33;
        int result = instance.func1(x);
        assertEquals(expResult, result);
    }
    
}
