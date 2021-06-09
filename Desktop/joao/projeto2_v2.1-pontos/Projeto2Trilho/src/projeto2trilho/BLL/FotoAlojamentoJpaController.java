/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import projeto2trilho.DAL.FotoAlojamento;
import projeto2trilho.exceptions.NonexistentEntityException;
import projeto2trilho.exceptions.PreexistingEntityException;

/**
 *
 * @author rafae
 */
public class FotoAlojamentoJpaController implements Serializable {

     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
    public FotoAlojamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

     public static void create(FotoAlojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(alo);
        em.getTransaction().commit();
    }

    public static List<FotoAlojamento> readAll(){
        List<FotoAlojamento> alo = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("FotoAlojamento.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            FotoAlojamento gui = ((FotoAlojamento)obj);
            alo.add(gui);
        }        
        
        return alo;
    } 
    public static FotoAlojamento read(int idAlojamento){
        FotoAlojamento alo = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("FotoAlojamento.findByFotoaloid");
        q1.setParameter("fotoaloid", idAlojamento);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            alo = ((FotoAlojamento)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return alo;
    }
     public static void delete(FotoAlojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(alo);
        em.getTransaction().commit();
    }
     
    public static void update(FotoAlojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(alo);
        em.getTransaction().commit();
       
    }

    public List<FotoAlojamento> findFotoAlojamentoEntities(int maxResults, int firstResult) {
        return findFotoAlojamentoEntities(false, maxResults, firstResult);
    }

    private List<FotoAlojamento> findFotoAlojamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FotoAlojamento.class));
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

    public FotoAlojamento findFotoAlojamento(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FotoAlojamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoAlojamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FotoAlojamento> rt = cq.from(FotoAlojamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
