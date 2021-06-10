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
import DAL.Alojamento;
import DAL.FotoAlojamento;
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
public class FotoAlojamentoJpaController implements Serializable {

    public FotoAlojamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FotoAlojamento fotoAlojamento) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alojamento alojamentoid = fotoAlojamento.getAlojamentoid();
            if (alojamentoid != null) {
                alojamentoid = em.getReference(alojamentoid.getClass(), alojamentoid.getAlojamentoId());
                fotoAlojamento.setAlojamentoid(alojamentoid);
            }
            em.persist(fotoAlojamento);
            if (alojamentoid != null) {
                alojamentoid.getFotoAlojamentoList().add(fotoAlojamento);
                alojamentoid = em.merge(alojamentoid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFotoAlojamento(fotoAlojamento.getFotoaloid()) != null) {
                throw new PreexistingEntityException("FotoAlojamento " + fotoAlojamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FotoAlojamento fotoAlojamento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FotoAlojamento persistentFotoAlojamento = em.find(FotoAlojamento.class, fotoAlojamento.getFotoaloid());
            Alojamento alojamentoidOld = persistentFotoAlojamento.getAlojamentoid();
            Alojamento alojamentoidNew = fotoAlojamento.getAlojamentoid();
            if (alojamentoidNew != null) {
                alojamentoidNew = em.getReference(alojamentoidNew.getClass(), alojamentoidNew.getAlojamentoId());
                fotoAlojamento.setAlojamentoid(alojamentoidNew);
            }
            fotoAlojamento = em.merge(fotoAlojamento);
            if (alojamentoidOld != null && !alojamentoidOld.equals(alojamentoidNew)) {
                alojamentoidOld.getFotoAlojamentoList().remove(fotoAlojamento);
                alojamentoidOld = em.merge(alojamentoidOld);
            }
            if (alojamentoidNew != null && !alojamentoidNew.equals(alojamentoidOld)) {
                alojamentoidNew.getFotoAlojamentoList().add(fotoAlojamento);
                alojamentoidNew = em.merge(alojamentoidNew);
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
                BigDecimal id = fotoAlojamento.getFotoaloid();
                if (findFotoAlojamento(id) == null) {
                    throw new NonexistentEntityException("The fotoAlojamento with id " + id + " no longer exists.");
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
            FotoAlojamento fotoAlojamento;
            try {
                fotoAlojamento = em.getReference(FotoAlojamento.class, id);
                fotoAlojamento.getFotoaloid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotoAlojamento with id " + id + " no longer exists.", enfe);
            }
            Alojamento alojamentoid = fotoAlojamento.getAlojamentoid();
            if (alojamentoid != null) {
                alojamentoid.getFotoAlojamentoList().remove(fotoAlojamento);
                alojamentoid = em.merge(alojamentoid);
            }
            em.remove(fotoAlojamento);
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

    public List<FotoAlojamento> findFotoAlojamentoEntities() {
        return findFotoAlojamentoEntities(true, -1, -1);
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
