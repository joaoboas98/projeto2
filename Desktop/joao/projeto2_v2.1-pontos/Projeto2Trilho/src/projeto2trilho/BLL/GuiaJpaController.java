/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import projeto2trilho.DAL.Excursao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.BLL.exceptions.PreexistingEntityException;
import projeto2trilho.DAL.Guia;

/**
 *
 * @author rafae
 */
public class GuiaJpaController implements Serializable {
    
     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
     
    public GuiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    
     public static void create(Guia gui){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(gui);
        em.getTransaction().commit();
    }
    
    public static List<Guia> readAll(){
        List<Guia> guia = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Guia.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Guia gui = ((Guia)obj);
            guia.add(gui);
        }        
        
        return guia;
    } 
    public static Guia read(int idGuia){
        Guia gui = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Guia.findByGuiaId");
        q1.setParameter("guiaId", idGuia);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            gui = ((Guia)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return gui;
    }
    
    
     public static void delete(Guia gui){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(em.merge(gui));
        em.getTransaction().commit();
    }
     
    public static void update(Guia gui){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(gui);
        em.getTransaction().commit();
       
    }

    

    

    public List<Guia> findGuiaEntities() {
        return findGuiaEntities(true, -1, -1);
    }

    public List<Guia> findGuiaEntities(int maxResults, int firstResult) {
        return findGuiaEntities(false, maxResults, firstResult);
    }

    private List<Guia> findGuiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Guia.class));
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

    public Guia findGuia(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Guia.class, id);
        } finally {
            em.close();
        }
    }

    public int getGuiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Guia> rt = cq.from(Guia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public static List<Excursao> readExcursaoDoGuia (Guia guia){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        List<Excursao> listaexcursoes= new ArrayList<>();

        listaexcursoes = guia.getExcursaoList();

        return listaexcursoes;
    }
    
}
