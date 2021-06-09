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
import projeto2trilho.DAL.Ponto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.BLL.exceptions.PreexistingEntityException;
import projeto2trilho.DAL.Alojamento;
import projeto2trilho.DAL.ClassTrilho;
import projeto2trilho.DAL.Excursao;
import projeto2trilho.DAL.FotoTrilho;
import projeto2trilho.DAL.Trilho;

/**
 *
 * @author rafae
 */
public class TrilhoJpaController implements Serializable {

     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
    public TrilhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public List<Trilho> findTrilhoEntities() {
        return findTrilhoEntities(true, -1, -1);
    }

    public List<Trilho> findTrilhoEntities(int maxResults, int firstResult) {
        return findTrilhoEntities(false, maxResults, firstResult);
    }
    
        public static List<Trilho> readAll(){
        List<Trilho> alo = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Trilho.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Trilho gui = ((Trilho)obj);
            alo.add(gui);
        }        
        
        return alo;
    } 
    public static Trilho read(int idTrilho){
        Trilho alo = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Trilho.findByTrilhoId");
        q1.setParameter("trilhoId", idTrilho);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            alo = ((Trilho)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return alo;
    }
     public static void delete(Trilho alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(alo);
        em.getTransaction().commit();
    }
     
    public static void update(Trilho alo){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(alo);
        em.getTransaction().commit();
       
    }
    private List<Trilho> findTrilhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trilho.class));
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

    public Trilho findTrilho(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trilho.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrilhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trilho> rt = cq.from(Trilho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
