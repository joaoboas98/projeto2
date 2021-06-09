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

/**
 *
 * @author Utilizador
 */
public class ExcursaoJpaControllerTest {
    private projeto2trilho.DAL.Excursao Ex1 = new projeto2trilho.DAL.Excursao();
     private projeto2trilho.DAL.Excursao Ex2 = new projeto2trilho.DAL.Excursao();
    private projeto2trilho.DAL.Guia guia1 = new projeto2trilho.DAL.Guia();
    
    public ExcursaoJpaControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        Ex1 = projeto2trilho.BLL.ExcursaoJpaController.read(1);
        Ex2 = projeto2trilho.BLL.ExcursaoJpaController.read(2);
        guia1 = projeto2trilho.BLL.GuiaJpaController.read(12);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testGetEntityManager() {
//        System.out.println("getEntityManager");
//        ExcursaoJpaController instance = null;
//        EntityManager expResult = null;
//        EntityManager result = instance.getEntityManager();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of readAll method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testReadAll() {
//        System.out.println("readAll");
//        List<Excursao> expResult = null;
//        List<Excursao> result = ExcursaoJpaController.readAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of read method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testRead() {
//        System.out.println("read");
//        int idExcursao = 0;
//        Excursao expResult = null;
//        Excursao result = ExcursaoJpaController.read(idExcursao);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of delete method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Excursao gui = null;
//        ExcursaoJpaController.delete(gui);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of update method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Excursao gui = null;
//        ExcursaoJpaController.update(gui);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findExcursaoEntities method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testFindExcursaoEntities_0args() {
//        System.out.println("findExcursaoEntities");
//        ExcursaoJpaController instance = null;
//        List<Excursao> expResult = null;
//        List<Excursao> result = instance.findExcursaoEntities();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findExcursaoEntities method, of class ExcursaoJpaController.
//     */
//    @Test
//    public void testFindExcursaoEntities_int_int() {
//        System.out.println("findExcursaoEntities");
//        int maxResults = 0;
//        int firstResult = 0;
//        ExcursaoJpaController instance = null;
//        List<Excursao> expResult = null;
//        List<Excursao> result = instance.findExcursaoEntities(maxResults, firstResult);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of findExcursao method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testFindExcursao() {
//        System.out.println("findExcursao");
//        BigDecimal id = null;
//        ExcursaoJpaController instance = null;
//        Excursao expResult = null;
//        Excursao result = instance.findExcursao(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getExcursaoCount method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testGetExcursaoCount() {
//        System.out.println("getExcursaoCount");
//        ExcursaoJpaController instance = null;
//        int expResult = 0;
//        int result = instance.getExcursaoCount();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of readExcursao1 method, of class ExcursaoJpaController.
     */
//    @Test
//    public void testReadExcursao1() {
//        System.out.println("readExcursao1");
//        int idUtilizador = 0;
//        boolean expResult = false;
//        boolean result = ExcursaoJpaController.readExcursao1(idUtilizador);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
//     @Test
//    public void testGuiaExcursao() {
//        System.out.println("adicionado Guia a uma excursao");
//        boolean expResult = true;
//        boolean result = projeto2trilho.BLL.ExcursaoJpaController.addGuiaExcursao(Ex1, guia1 );
//        assertEquals(expResult, result);        
//    }
    
//     @Test
//    public void testGuiaExcursao2() {
//        System.out.println("adicionado Guia a uma excursao 1");
//        boolean expResult = false;
//        boolean result = projeto2trilho.BLL.ExcursaoJpaController.addGuiaExcursao(Ex2, guia1);
//        assertEquals(expResult, result);        
//    }
       @Test
    public void testRemoveGuiaExcursao() {
        System.out.println("remove Guia a Excursao");
        ExcursaoJpaController.removeGuiaExcursao(Ex1, guia1);
        boolean result = Ex1.getGuiaList().contains(guia1);
        
        assertFalse(result);
    }
    
}
