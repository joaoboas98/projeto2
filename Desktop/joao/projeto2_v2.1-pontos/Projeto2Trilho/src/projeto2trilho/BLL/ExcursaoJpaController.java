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
import projeto2trilho.DAL.Grupo;
import projeto2trilho.DAL.Guia;
import projeto2trilho.DAL.Trilho;
import projeto2trilho.DAL.Utilizador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.BLL.exceptions.PreexistingEntityException;
import projeto2trilho.DAL.Excursao;
import projeto2trilho.DAL.PagamentoExcursao;
import projeto2trilho.DAL.Trilho;
import projeto2trilho.DAL.Guia;

/**
 *
 * @author rafae
 */
public class ExcursaoJpaController implements Serializable {

      private static  EntityManagerFactory factory = null;
     private static final String PERSISTENCE_UNIT_NAME = "Projeto2TrilhoPU";
     private static EntityManager em = null;
     
    public ExcursaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     public static List<Excursao> readAll(){
        List<Excursao> excursao = new ArrayList<>();
        
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Excursao.findAll");
        List<Object> lstObj = q1.getResultList();
        
        for(Object obj : lstObj){
            Excursao gru = ((Excursao)obj);
            excursao.add(gru);
        }        
        
        return excursao;
    } 
    public static Excursao read(int idExcursao){
        Excursao gui = null;
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        Query q1 = em.createNamedQuery("Excursao.findByExcursaoId");
        q1.setParameter("excursaoId", idExcursao);
        Object obj = q1.getSingleResult();
        
        if(obj != null){
            gui = ((Excursao)obj);
            //em.merge(cli);
        }
        else
            return null;
        
        
        return gui;
    }
    
    
     public static void delete(Excursao gui){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.remove(gui);
        em.getTransaction().commit();
    }
     
    public static void update(Excursao gui){
        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();
        
        em.getTransaction().begin();
        em.merge(gui);
        em.getTransaction().commit();
       
    }
    
public static boolean readVerificar(int idUExcursao){
        boolean uti;

        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();

        Query q1 = em.createNamedQuery("Excursao.findByExcursaoId");
        q1.setParameter("excursaoId", idUExcursao);
        Object obj = q1.getSingleResult();

        if(obj != null){
            uti = true;

        }
        else
            uti=false;

        return uti;
    }
    

    public List<Excursao> findExcursaoEntities() {
        return findExcursaoEntities(true, -1, -1);
    }

    public List<Excursao> findExcursaoEntities(int maxResults, int firstResult) {
        return findExcursaoEntities(false, maxResults, firstResult);
    }

    private List<Excursao> findExcursaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Excursao.class));
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

    public Excursao findExcursao(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Excursao.class, id);
        } finally {
            em.close();
        }
    }

    public int getExcursaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Excursao> rt = cq.from(Excursao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public static boolean readExcursao1(int idUtilizador){
        boolean uti;

        if(factory == null)
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        if (em == null) em = factory.createEntityManager();

        Query q1 = em.createNamedQuery("Excursao.findByExcursaoId");
        q1.setParameter("utilizadorId", idUtilizador);
        Object obj = q1.getSingleResult();

        if(obj != null){
            uti = true;

        }
        else
            uti=false;

        return uti;
    }
     
     public static List<Guia> readGuiaExcursao (Excursao excursao){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        List<Guia> listaguias = new ArrayList<>();
        
        listaguias = excursao.getGuiaList();
        
        return listaguias;
    }
         public static boolean addGuiaExcursao(Excursao excursao, Guia guia){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Boolean existe = false;
        
        for(Guia g : ExcursaoJpaController.readGuiaExcursao(excursao)){
            if(g.getGuiaId().equals(guia.getGuiaId())){
                existe = true;
            }
        }
        
        if(existe){
            return false;
        } else{
            em.getTransaction().begin();
            excursao.getGuiaList().add(guia);
            em.merge(excursao);

            em.getTransaction().commit();
            return true;
        }

    }
           public static void removeGuiaExcursao(Excursao excursao, Guia guia){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        int existe =0;
        
        for(Guia g : excursao.getGuiaList()){
            if(g.equals(guia)){
                existe=1;
            }
        }
        
        if(existe == 1){
            em.getTransaction().begin();
            excursao.getGuiaList().remove(guia);
            em.merge(excursao);
            em.getTransaction().commit();
        } else {
            System.out.println("Guia nao esta associado a excursao");
        }

           }
}
