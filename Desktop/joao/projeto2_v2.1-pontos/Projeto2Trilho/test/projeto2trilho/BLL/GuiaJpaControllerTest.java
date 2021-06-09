/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projeto2trilho.DAL.Excursao;
import projeto2trilho.DAL.Guia;

/**
 *
 * @author Utilizador
 */
public class GuiaJpaControllerTest {
     private Guia guia = new Guia();
    public GuiaJpaControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        guia = GuiaJpaController.read(12);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class GuiaJpaController.
     */
//    @Test
//    public void testGetEntityManager() {
//        System.out.println("getEntityManager");
//        GuiaJpaController instance = null;
//        EntityManager expResult = null;
//        EntityManager result = instance.getEntityManager();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class GuiaJpaController.
//     */
//    @Test
//    public void testCreate() {
//        System.out.println("create");
//        Guia gui = null;
//        GuiaJpaController.create(gui);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readAll method, of class GuiaJpaController.
//     */
//    @Test
//    public void testReadAll() {
//        System.out.println("readAll");
//        List<Guia> expResult = null;
//        List<Guia> result = GuiaJpaController.readAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of read method, of class GuiaJpaController.
//     */
//    @Test
//    public void testRead() {
//        System.out.println("read");
//        int idGuia = 0;
//        Guia expResult = null;
//        Guia result = GuiaJpaController.read(idGuia);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class GuiaJpaController.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Guia gui = null;
//        GuiaJpaController.delete(gui);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class GuiaJpaController.
//     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Guia gui = null;
//        GuiaJpaController.update(gui);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findGuiaEntities method, of class GuiaJpaController.
//     */
//    @Test
//    public void testFindGuiaEntities_0args() {
//        System.out.println("findGuiaEntities");
//        GuiaJpaController instance = null;
//        List<Guia> expResult = null;
//        List<Guia> result = instance.findGuiaEntities();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findGuiaEntities method, of class GuiaJpaController.
//     */
//    @Test
//    public void testFindGuiaEntities_int_int() {
//        System.out.println("findGuiaEntities");
//        int maxResults = 0;
//        int firstResult = 0;
//        GuiaJpaController instance = null;
//        List<Guia> expResult = null;
//        List<Guia> result = instance.findGuiaEntities(maxResults, firstResult);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findGuia method, of class GuiaJpaController.
//     */
//    @Test
//    public void testFindGuia() {
//        System.out.println("findGuia");
//        BigDecimal id = null;
//        GuiaJpaController instance = null;
//        Guia expResult = null;
//        Guia result = instance.findGuia(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGuiaCount method, of class GuiaJpaController.
//     */
//    @Test
//    public void testGetGuiaCount() {
//        System.out.println("getGuiaCount");
//        GuiaJpaController instance = null;
//        int expResult = 0;
//        int result = instance.getGuiaCount();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    //-------------------------------------
    
    
     @Test
    public void testDeleteGuia() {
        System.out.println("EliminarGuia");
        
        // Guia guia = null;
        GuiaJpaController.delete(guia);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testReadExcursaoDoGuia() {
        System.out.println("readExcursaoDoGuia");
        int expResult = 1;
        List<Excursao> list = GuiaJpaController.readExcursaoDoGuia(guia);
        int result = list.size();
        assertEquals(expResult, result);
    }
    
    
    
    
}
