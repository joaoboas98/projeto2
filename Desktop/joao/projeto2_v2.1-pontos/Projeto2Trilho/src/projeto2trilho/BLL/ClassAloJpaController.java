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
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.BLL.exceptions.PreexistingEntityException;
import projeto2trilho.DAL.ClassAlo;
import projeto2trilho.DAL.Utilizador;

/**
 *
 * @author rafae
 */
public class ClassAloJpaController implements Serializable {

    
    private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
    public ClassAloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void create(ClassAlo alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(alo);
        em.getTransaction().commit();
    }

    public static List<ClassAlo> readAll(){
        List<ClassAlo> alo = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("ClassAlo.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            ClassAlo gui = ((ClassAlo)obj);
            alo.add(gui);
        }        
        
        return alo;
    } 
    public static ClassAlo read(int idAlojamento){
        ClassAlo alo = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("ClassAlo.findByClassAloId");
        q1.setParameter("classAloId", idAlojamento);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            alo = ((ClassAlo)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return alo;
    }
     public static void delete(ClassAlo alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(alo);
        em.getTransaction().commit();
    }
     
    public static void update(ClassAlo alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(alo);
        em.getTransaction().commit();
       
    }

    public List<ClassAlo> findClassAloEntities() {
        return findClassAloEntities(true, -1, -1);
    }

    public List<ClassAlo> findClassAloEntities(int maxResults, int firstResult) {
        return findClassAloEntities(false, maxResults, firstResult);
    }

    private List<ClassAlo> findClassAloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClassAlo.class));
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

    public ClassAlo findClassAlo(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClassAlo.class, id);
        } finally {
            em.close();
        }
    }

    public int getClassAloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClassAlo> rt = cq.from(ClassAlo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
