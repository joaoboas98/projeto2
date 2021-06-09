/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import projeto2trilho.DAL.FotoPonto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.DAL.Grupo;
import projeto2trilho.DAL.ClassTrilho;
import projeto2trilho.DAL.ClassAlo;
import projeto2trilho.DAL.Excursao;
import projeto2trilho.DAL.PagamentoExcursao;
import projeto2trilho.DAL.FotoTrilho;
import projeto2trilho.DAL.Utilizador;
import projeto2trilho.DAL.Excursao;


/**
 *
 * @author rafae
 */
 public class UtilizadorJpaController implements Serializable {
     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;

    public UtilizadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private static EntityManagerFactory emf = null;

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void create(Utilizador uti){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(uti);
        em.getTransaction().commit();
    }

    

    

    public  List<Utilizador> findUtilizadorEntities() {
        return findUtilizadorEntities(true, -1, -1);
    }

    public  List<Utilizador> findUtilizadorEntities(int maxResults, int firstResult) {
        return findUtilizadorEntities(false, maxResults, firstResult);
    }

    private static List<Utilizador> findUtilizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilizador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    public static List<Utilizador> readAll(){
        List<Utilizador> utilizador = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Utilizador.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Utilizador uti = ((Utilizador)obj);
            utilizador.add(uti);
        }        
        
        return utilizador;
    } 
    public static Utilizador read(int idUtilizador){
        Utilizador uti = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Utilizador.findByUtilizadorId");
        q1.setParameter("utilizadorId", idUtilizador);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            uti = ((Utilizador)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return uti;
    }
     public static void delete(Utilizador uti){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(uti);
        em.getTransaction().commit();
    }
     
    public static void update(Utilizador uti){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(uti);
        em.getTransaction().commit();
       
    }

    public  Utilizador findUtilizador(BigInteger id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilizador.class, id);
        } finally {
            em.close();
        }
    }
    
      
    public int getUtilizadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilizador> rt = cq.from(Utilizador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public static boolean readVerificar(int idUtilizador){
        boolean uti;

        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();

        Query q1 = em.createNamedQuery("Utilizador.findByUtilizadorId");
        q1.setParameter("utilizadorId", idUtilizador);
        Object obj = q1.getSingleResult();

        if(obj != null){
            uti = true;

        }
        else
            uti=false;

        return uti;
    }
    
    public static List<Excursao> readeExcursaoDoUtilizador (Utilizador utilizador){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        List<Excursao> excursaos = new ArrayList<>();
        
        excursaos = utilizador.getExcursaoList();
        
        return excursaos;
    }
    

     
   public static boolean verificarTipoUtilizador (int idUser){

        Utilizador c = read(idUser);

        if(c.getUtilizadorTipo().equals(BigInteger.ZERO)){
            return false;
        } else {
            return true;
        }

    }
   public static List<Grupo> readGruposDoUtilizador (Utilizador utilizador){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        List<Grupo> listaGrupos = new ArrayList<>();

        listaGrupos = utilizador.getGrupoList();

        return listaGrupos;
    }

 public static List<ClassAlo> readAvaliacoesDoUtilizador (Utilizador utilizador){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        List<ClassAlo> listaAvaliacoes = new ArrayList<>();

        listaAvaliacoes = utilizador.getClassAloList();

        return listaAvaliacoes;
    }
 
   public static boolean alterarTipoUtilizador (Utilizador utilizador){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        short vOuts = (short)1;
        if(utilizador.getUtilizadorTipo().equals(vOuts)){
            short vOut = (short)0;
            utilizador.setUtilizadorTipo(vOut);

            em.getTransaction().begin();
            em.merge(utilizador);
            em.getTransaction().commit();
            return true;
        } 
        else{
            short vOut = (short)1;
            utilizador.setUtilizadorTipo(vOut);
            em.getTransaction().begin();
            em.merge(utilizador);
            em.getTransaction().commit();
            return true;
        }
    }
    
}
