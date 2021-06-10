/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAL.FotoPonto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DAL.Ponto;
import DAL.Utilizador;
import Model.exceptions.NonexistentEntityException;
import Model.exceptions.PreexistingEntityException;
import Model.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Utilizador
 */
public class FotoPontoJpaController implements Serializable {

    public FotoPontoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FotoPonto fotoPonto) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ponto pontoId = fotoPonto.getPontoId();
            if (pontoId != null) {
                pontoId = em.getReference(pontoId.getClass(), pontoId.getPontoId());
                fotoPonto.setPontoId(pontoId);
            }
            Utilizador utilizadorId = fotoPonto.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                fotoPonto.setUtilizadorId(utilizadorId);
            }
            em.persist(fotoPonto);
            if (pontoId != null) {
                pontoId.getFotoPontoList().add(fotoPonto);
                pontoId = em.merge(pontoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getFotoPontoList().add(fotoPonto);
                utilizadorId = em.merge(utilizadorId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFotoPonto(fotoPonto.getFotoPontoId()) != null) {
                throw new PreexistingEntityException("FotoPonto " + fotoPonto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FotoPonto fotoPonto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FotoPonto persistentFotoPonto = em.find(FotoPonto.class, fotoPonto.getFotoPontoId());
            Ponto pontoIdOld = persistentFotoPonto.getPontoId();
            Ponto pontoIdNew = fotoPonto.getPontoId();
            Utilizador utilizadorIdOld = persistentFotoPonto.getUtilizadorId();
            Utilizador utilizadorIdNew = fotoPonto.getUtilizadorId();
            if (pontoIdNew != null) {
                pontoIdNew = em.getReference(pontoIdNew.getClass(), pontoIdNew.getPontoId());
                fotoPonto.setPontoId(pontoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                fotoPonto.setUtilizadorId(utilizadorIdNew);
            }
            fotoPonto = em.merge(fotoPonto);
            if (pontoIdOld != null && !pontoIdOld.equals(pontoIdNew)) {
                pontoIdOld.getFotoPontoList().remove(fotoPonto);
                pontoIdOld = em.merge(pontoIdOld);
            }
            if (pontoIdNew != null && !pontoIdNew.equals(pontoIdOld)) {
                pontoIdNew.getFotoPontoList().add(fotoPonto);
                pontoIdNew = em.merge(pontoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getFotoPontoList().remove(fotoPonto);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getFotoPontoList().add(fotoPonto);
                utilizadorIdNew = em.merge(utilizadorIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = fotoPonto.getFotoPontoId();
                if (findFotoPonto(id) == null) {
                    throw new NonexistentEntityException("The fotoPonto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FotoPonto fotoPonto;
            try {
                fotoPonto = em.getReference(FotoPonto.class, id);
                fotoPonto.getFotoPontoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotoPonto with id " + id + " no longer exists.", enfe);
            }
            Ponto pontoId = fotoPonto.getPontoId();
            if (pontoId != null) {
                pontoId.getFotoPontoList().remove(fotoPonto);
                pontoId = em.merge(pontoId);
            }
            Utilizador utilizadorId = fotoPonto.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getFotoPontoList().remove(fotoPonto);
                utilizadorId = em.merge(utilizadorId);
            }
            em.remove(fotoPonto);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
