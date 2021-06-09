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
import projeto2trilho.DAL.Ponto;
import projeto2trilho.exceptions.NonexistentEntityException;
import projeto2trilho.exceptions.PreexistingEntityException;

/**
 *
 * @author rafae
 */
public class PontoJpaController implements Serializable {

     private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
    public PontoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public static void create(Ponto po){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(po);
        em.getTransaction().commit();
    }

   

   public static List<Ponto> readAll(){
        List<Ponto> po = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Ponto.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Ponto gui = ((Ponto)obj);
            po.add(gui);
        }        
        
        return po;
    } 
    public static Ponto read(int idPonto){
        Ponto po = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Ponto.findByPontoId");
        q1.setParameter("pontoId", idPonto);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            po = ((Ponto)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return po;
    }
     public static void delete(Ponto po){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(po);
        em.getTransaction().commit();
    }
     
    public static void update(Ponto po){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(po);
        em.getTransaction().commit();
       
    }

    

    


    
}