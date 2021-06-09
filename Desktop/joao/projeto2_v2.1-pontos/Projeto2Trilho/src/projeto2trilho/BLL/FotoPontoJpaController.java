/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import projeto2trilho.BLL.exceptions.NonexistentEntityException;
import projeto2trilho.BLL.exceptions.PreexistingEntityException;
import projeto2trilho.DAL.FotoPonto;
import projeto2trilho.DAL.Ponto;
import projeto2trilho.DAL.Utilizador;

/**
 *
 * @author rafae
 */
public class FotoPontoJpaController implements Serializable {

    public FotoPontoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    

   

   

    public List<FotoPonto> findFotoPontoEntities() {
        return findFotoPontoEntities(true, -1, -1);
    }

    public List<FotoPonto> findFotoPontoEntities(int maxResults, int firstResult) {
        return findFotoPontoEntities(false, maxResults, firstResult);
    }

    private List<FotoPonto> findFotoPontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FotoPonto.class));
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

    public FotoPonto findFotoPonto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FotoPonto.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoPontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FotoPonto> rt = cq.from(FotoPonto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
