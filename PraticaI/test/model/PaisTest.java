/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class PaisTest {
    
    public PaisTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPaiCodigo method, of class Pais.
     */
    @Test
    public void testGetPaiCodigo() {
        System.out.println("getPaiCodigo");
        Pais instance = new Pais();
        int expResult = 0;
        int result = instance.getPaiCodigo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiCodigo method, of class Pais.
     */
    @Test
    public void testSetPaiCodigo() {
        System.out.println("setPaiCodigo");
        int PaiCodigo = 0;
        Pais instance = new Pais();
        instance.setPaiCodigo(PaiCodigo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiAlfa2 method, of class Pais.
     */
    @Test
    public void testGetPaiAlfa2() {
        System.out.println("getPaiAlfa2");
        Pais instance = new Pais();
        String expResult = "";
        String result = instance.getPaiAlfa2();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiAlfa2 method, of class Pais.
     */
    @Test
    public void testSetPaiAlfa2() {
        System.out.println("setPaiAlfa2");
        String PaiAlfa2 = "";
        Pais instance = new Pais();
        instance.setPaiAlfa2(PaiAlfa2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiAlfa3 method, of class Pais.
     */
    @Test
    public void testGetPaiAlfa3() {
        System.out.println("getPaiAlfa3");
        Pais instance = new Pais();
        String expResult = "";
        String result = instance.getPaiAlfa3();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiAlfa3 method, of class Pais.
     */
    @Test
    public void testSetPaiAlfa3() {
        System.out.println("setPaiAlfa3");
        String PaiAlfa3 = "";
        Pais instance = new Pais();
        instance.setPaiAlfa3(PaiAlfa3);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiBacenIbge method, of class Pais.
     */
    @Test
    public void testGetPaiBacenIbge() {
        System.out.println("getPaiBacenIbge");
        Pais instance = new Pais();
        int expResult = 0;
        int result = instance.getPaiBacenIbge();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiBacenIbge method, of class Pais.
     */
    @Test
    public void testSetPaiBacenIbge() {
        System.out.println("setPaiBacenIbge");
        int PaiBacenIbge = 0;
        Pais instance = new Pais();
        instance.setPaiBacenIbge(PaiBacenIbge);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiISO3166 method, of class Pais.
     */
    @Test
    public void testGetPaiISO3166() {
        System.out.println("getPaiISO3166");
        Pais instance = new Pais();
        int expResult = 0;
        int result = instance.getPaiISO3166();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiISO3166 method, of class Pais.
     */
    @Test
    public void testSetPaiISO3166() {
        System.out.println("setPaiISO3166");
        int PaiISO3166 = 0;
        Pais instance = new Pais();
        instance.setPaiISO3166(PaiISO3166);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiNome method, of class Pais.
     */
    @Test
    public void testGetPaiNome() {
        System.out.println("getPaiNome");
        Pais instance = new Pais();
        String expResult = "";
        String result = instance.getPaiNome();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiNome method, of class Pais.
     */
    @Test
    public void testSetPaiNome() {
        System.out.println("setPaiNome");
        String PaiNome = "";
        Pais instance = new Pais();
        instance.setPaiNome(PaiNome);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPaiDtaDelecao method, of class Pais.
     */
    @Test
    public void testGetPaiDtaDelecao() {
        System.out.println("getPaiDtaDelecao");
        Pais instance = new Pais();
        Timestamp expResult = null;
        Timestamp result = instance.getPaiDtaDelecao();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPaiDtaDelecao method, of class Pais.
     */
    @Test
    public void testSetPaiDtaDelecao() {
        System.out.println("setPaiDtaDelecao");
        Timestamp PaiDtaDelecao = null;
        Pais instance = new Pais();
        instance.setPaiDtaDelecao(PaiDtaDelecao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlag method, of class Pais.
     */
    @Test
    public void testGetFlag() {
        System.out.println("getFlag");
        Pais instance = new Pais();
        String expResult = "";
        String result = instance.getFlag();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFlag method, of class Pais.
     */
    @Test
    public void testSetFlag() {
        System.out.println("setFlag");
        String flag = "";
        Pais instance = new Pais();
        instance.setFlag(flag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class Pais.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        int PaiCodigo = 0;
        Pais instance = new Pais();
        boolean expResult = false;
        boolean result = instance.load(PaiCodigo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class Pais.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Pais instance = new Pais();
        boolean expResult = false;
        boolean result = instance.save();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of insert method, of class Pais.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        Pais instance = new Pais();
        boolean expResult = false;
        boolean result = instance.insert();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class Pais.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Pais instance = new Pais();
        boolean expResult = false;
        boolean result = instance.update();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class Pais.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        ArrayList<Pais> expResult = null;
        ArrayList<Pais> result = Pais.getAll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
