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
import projeto2trilho.DAL.Alojamento;
import projeto2trilho.exceptions.NonexistentEntityException;
import projeto2trilho.exceptions.PreexistingEntityException;

/**
 *
 * @author rafae
 */
public class AlojamentoJpaController implements Serializable {
    
     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;

    public AlojamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

     public static void create(Alojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(alo);
        em.getTransaction().commit();
    }

    public static List<Alojamento> readAll(){
        List<Alojamento> alo = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Alojamento.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Alojamento gui = ((Alojamento)obj);
            alo.add(gui);
        }        
        
        return alo;
    } 
    public static Alojamento read(int idAlojamento){
        Alojamento alo = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Alojamento.findByAlojamentoId");
        q1.setParameter("alojamentoId", idAlojamento);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            alo = ((Alojamento)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return alo;
    }
     public static void delete(Alojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(alo);
        em.getTransaction().commit();
    }
     
    public static void update(Alojamento alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(alo);
        em.getTransaction().commit();
       
    }

    

    public List<Alojamento> findAlojamentoEntities() {
        return findAlojamentoEntities(true, -1, -1);
    }

    public List<Alojamento> findAlojamentoEntities(int maxResults, int firstResult) {
        return findAlojamentoEntities(false, maxResults, firstResult);
    }

    private List<Alojamento> findAlojamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alojamento.class));
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

    public Alojamento findAlojamento(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alojamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlojamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alojamento> rt = cq.from(Alojamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}