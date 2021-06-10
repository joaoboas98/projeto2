/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DAL.Excursao;
import DAL.PagamentoExcursao;
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
public class PagamentoExcursaoJpaController implements Serializable {

    public PagamentoExcursaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PagamentoExcursao pagamentoExcursao) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Excursao excursaoId = pagamentoExcursao.getExcursaoId();
            if (excursaoId != null) {
                excursaoId = em.getReference(excursaoId.getClass(), excursaoId.getExcursaoId());
                pagamentoExcursao.setExcursaoId(excursaoId);
            }
            Utilizador utilizadorId = pagamentoExcursao.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                pagamentoExcursao.setUtilizadorId(utilizadorId);
            }
            em.persist(pagamentoExcursao);
            if (excursaoId != null) {
                excursaoId.getPagamentoExcursaoList().add(pagamentoExcursao);
                excursaoId = em.merge(excursaoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getPagamentoExcursaoList().add(pagamentoExcursao);
                utilizadorId = em.merge(utilizadorId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPagamentoExcursao(pagamentoExcursao.getPagamentoExcursaoId()) != null) {
                throw new PreexistingEntityException("PagamentoExcursao " + pagamentoExcursao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PagamentoExcursao pagamentoExcursao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PagamentoExcursao persistentPagamentoExcursao = em.find(PagamentoExcursao.class, pagamentoExcursao.getPagamentoExcursaoId());
            Excursao excursaoIdOld = persistentPagamentoExcursao.getExcursaoId();
            Excursao excursaoIdNew = pagamentoExcursao.getExcursaoId();
            Utilizador utilizadorIdOld = persistentPagamentoExcursao.getUtilizadorId();
            Utilizador utilizadorIdNew = pagamentoExcursao.getUtilizadorId();
            if (excursaoIdNew != null) {
                excursaoIdNew = em.getReference(excursaoIdNew.getClass(), excursaoIdNew.getExcursaoId());
                pagamentoExcursao.setExcursaoId(excursaoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                pagamentoExcursao.setUtilizadorId(utilizadorIdNew);
            }
            pagamentoExcursao = em.merge(pagamentoExcursao);
            if (excursaoIdOld != null && !excursaoIdOld.equals(excursaoIdNew)) {
                excursaoIdOld.getPagamentoExcursaoList().remove(pagamentoExcursao);
                excursaoIdOld = em.merge(excursaoIdOld);
            }
            if (excursaoIdNew != null && !excursaoIdNew.equals(excursaoIdOld)) {
                excursaoIdNew.getPagamentoExcursaoList().add(pagamentoExcursao);
                excursaoIdNew = em.merge(excursaoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getPagamentoExcursaoList().remove(pagamentoExcursao);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getPagamentoExcursaoList().add(pagamentoExcursao);
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
                BigDecimal id = pagamentoExcursao.getPagamentoExcursaoId();
                if (findPagamentoExcursao(id) == null) {
                    throw new NonexistentEntityException("The pagamentoExcursao with id " + id + " no longer exists.");
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
            PagamentoExcursao pagamentoExcursao;
            try {
                pagamentoExcursao = em.getReference(PagamentoExcursao.class, id);
                pagamentoExcursao.getPagamentoExcursaoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagamentoExcursao with id " + id + " no longer exists.", enfe);
            }
            Excursao excursaoId = pagamentoExcursao.getExcursaoId();
            if (excursaoId != null) {
                excursaoId.getPagamentoExcursaoList().remove(pagamentoExcursao);
                excursaoId = em.merge(excursaoId);
            }
            Utilizador utilizadorId = pagamentoExcursao.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getPagamentoExcursaoList().remove(pagamentoExcursao);
                utilizadorId = em.merge(utilizadorId);
            }
            em.remove(pagamentoExcursao);
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

    public List<PagamentoExcursao> findPagamentoExcursaoEntities() {
        return findPagamentoExcursaoEntities(true, -1, -1);
    }

    public List<PagamentoExcursao> findPagamentoExcursaoEntities(int maxResults, int firstResult) {
        return findPagamentoExcursaoEntities(false, maxResults, firstResult);
    }

    private List<PagamentoExcursao> findPagamentoExcursaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PagamentoExcursao.class));
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

    public PagamentoExcursao findPagamentoExcursao(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PagamentoExcursao.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagamentoExcursaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PagamentoExcursao> rt = cq.from(PagamentoExcursao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
