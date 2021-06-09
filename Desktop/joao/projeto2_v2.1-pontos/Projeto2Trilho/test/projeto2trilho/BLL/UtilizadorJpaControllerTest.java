/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import projeto2trilho.DAL.Utilizador;
import projeto2trilho.DAL.Grupo; 
import projeto2trilho.BLL.UtilizadorJpaControllerTest;
import projeto2trilho.BLL.PagamentoExcursaoJpaController;
import projeto2trilho.DAL.Excursao;
import projeto2trilho.DAL.ClassAlo;


/**
 *
 * @author Utilizador
 */
public class UtilizadorJpaControllerTest {
    private projeto2trilho.DAL.Utilizador utilizador = new projeto2trilho.DAL.Utilizador();

    private projeto2trilho.DAL.Excursao excursao = new  projeto2trilho.DAL.Excursao();
    public UtilizadorJpaControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        utilizador  = UtilizadorJpaController.read(21);

    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class UtilizadorJpaController.
     */
//    @Test
//    public void testGetEntityManager() {
//        System.out.println("getEntityManager");
//        EntityManager expResult = null;
//        EntityManager result = UtilizadorJpaController.getEntityManager();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of create method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testCreate() {
//        System.out.println("create");
//        Utilizador uti = null;
//        UtilizadorJpaController.create(uti);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findUtilizadorEntities method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testFindUtilizadorEntities_0args() {
//        System.out.println("findUtilizadorEntities");
//        UtilizadorJpaController instance = null;
//        List<Utilizador> expResult = null;
//        List<Utilizador> result = instance.findUtilizadorEntities();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findUtilizadorEntities method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testFindUtilizadorEntities_int_int() {
//        System.out.println("findUtilizadorEntities");
//        int maxResults = 0;
//        int firstResult = 0;
//        UtilizadorJpaController instance = null;
//        List<Utilizador> expResult = null;
//        List<Utilizador> result = instance.findUtilizadorEntities(maxResults, firstResult);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readAll method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testReadAll() {
//        System.out.println("readAll");
//        List<Utilizador> expResult = null;
//        List<Utilizador> result = UtilizadorJpaController.readAll();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of read method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testRead() {
//        System.out.println("read");
//        int idUtilizador = 0;
//        Utilizador expResult = null;
//        Utilizador result = UtilizadorJpaController.read(idUtilizador);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Utilizador uti = null;
//        UtilizadorJpaController.delete(uti);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Utilizador uti = null;
//        UtilizadorJpaController.update(uti);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findUtilizador method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testFindUtilizador() {
//        System.out.println("findUtilizador");
//        BigInteger id = null;
//        UtilizadorJpaController instance = null;
//        Utilizador expResult = null;
//        Utilizador result = instance.findUtilizador(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUtilizadorCount method, of class UtilizadorJpaController.
//     */
//    @Test
//    public void testGetUtilizadorCount() {
//        System.out.println("getUtilizadorCount");
//        UtilizadorJpaController instance = null;
//        int expResult = 0;
//        int result = instance.getUtilizadorCount();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    /**
     * Excursao create utuilizador
     */
    
//    @Test
//    public void testCreate() {
//        System.out.println("create");
//        Utilizador uti = new Utilizador();
//        uti.setUtilizadorNome("Jota1");
//        short value = (short) 1;
//        uti.setUtilizadorTipo(value);
//        uti.setUtilizadorMorada("rua das boucas");
//        BigInteger idade = BigInteger.valueOf(22);
//        uti.setUtilizadorIdade(idade);
//        uti.setUtilizadorPass("12345");
//        UtilizadorJpaController.create(uti);
//
//        Utilizador expResult = uti;
//        System.out.println("2-id cli" + uti.getUtilizadorId());
//
//        Utilizador result = UtilizadorJpaController.read(uti.getUtilizadorId().intValue());
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//        assertEquals(expResult,result);
//    }
//    
//    
     /**
     * Test read all utilizadores
     */
    
//    @Test
//    public void testReadAll() {
//    System.out.println("getAllUtilizador");
//
//    int expResult = 26;
//
//    List<Utilizador> result = UtilizadorJpaController.readAll();
//
//    assertEquals(expResult, result.size());
//    }
    

//    
//    /**
//     * Ler verificar utilizador
//     */
//
// @Test
//    public void verificarUtilizador() {
//    System.out.println("verificarUtilizador");
//
//    boolean expResult = true;
//
//    boolean result = UtilizadorJpaController.readVerificar(utilizador.getUtilizadorId().intValue());
//
//    assertEquals(expResult, result);
//    }
    
    /**
     * Ler avaliações do utilizador
     */
    
// @Test
//    public void readAvaliacoesDoUtilizador() {
//    System.out.println("readAvaliacoesDoUtilizador");
//
//    int expResult = 1;
//
//    List<ClassAlo> lista = UtilizadorJpaController.readAvaliacoesDoUtilizador(utilizador);
//    int result = lista.size();
//
//    assertEquals(expResult, result);
//    }
    
//     /**
//     * Ler grupos do Utilizador
//     */
//    
//    @Test
//    public void readGruposDoUtilizador() {
//    System.out.println("readGruposDoUtilizador");
//
//    int expResult = 1;
//
//    List<Grupo> lista = UtilizadorJpaController.readGruposDoUtilizador(utilizador);
//    int result = lista.size();
//
//    assertEquals(expResult, result);
//    }
//    
//    
     /**
     * Test verificar tipo de  utilizador
     */
//    @Test
//    public void verificarTipoUtilizador() {
//    System.out.println("verificarUtilizador");
//
//    boolean expResult = true;
//
//    boolean result = UtilizadorJpaController.verificarTipoUtilizador(utilizador.getUtilizadorId().intValue());
//
//    assertEquals(expResult, result);
//    }
    
// 
//    
//   
//    /**
//     * Alterar Tipo de Utilizador
//     */
//    
//    @Test
//    public void alterarTipoUtilizador() {
//    System.out.println("alterarTipoUtilizador"); 
//    boolean expResult = true;
//    boolean result = UtilizadorJpaController.alterarTipoUtilizador(utilizador);
//    assertEquals(expResult, result);
//    }
//    
//     /**
//     * Excursao update utuilizador
//     */
//    
// @Test 
// public void testUpdategrupo(){
//     System.out.println("Update Utilizador"); 
//     Grupo grupo = new Grupo();
//     grupo.setUtilizadorId(utilizador);
//     grupo.getUtilizadorId().setUtilizadorMorada("utilizadorMorada");
//     
//   UtilizadorJpaController.update(utilizador);
//   
//   String expResult = "utilizadorMorada";
//   String result = grupo.getUtilizadorId().getUtilizadorMorada();
//   
//   assertEquals(expResult, result);
//     
// }
//    
//      /**
//     * Excursao utilizador
//     */


//    @Test
//    public void testReadExcursaoDoUtilizador() {
//        System.out.println("readExcursaoDoUtilizador");
//        int expResult = 2;
//        List<Excursao> list = projeto2trilho.BLL.UtilizadorJpaController.readeExcursaoDoUtilizador(utilizador);
//        int result = list.size();
//        assertEquals(expResult, result);
//    }
    
    
    
}
