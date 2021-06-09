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
import projeto2trilho.DAL.FotoTrilho;
import projeto2trilho.DAL.Trilho;
import projeto2trilho.DAL.Utilizador;

/**
 *
 * @author rafae
 */
public class FotoTrilhoJpaController implements Serializable {

    public FotoTrilhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FotoTrilho fotoTrilho) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilho trilhoId = fotoTrilho.getTrilhoId();
            if (trilhoId != null) {
                trilhoId = em.getReference(trilhoId.getClass(), trilhoId.getTrilhoId());
                fotoTrilho.setTrilhoId(trilhoId);
            }
            Utilizador utilizadorId = fotoTrilho.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                fotoTrilho.setUtilizadorId(utilizadorId);
            }
            em.persist(fotoTrilho);
            if (trilhoId != null) {
                trilhoId.getFotoTrilhoList().add(fotoTrilho);
                trilhoId = em.merge(trilhoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getFotoTrilhoList().add(fotoTrilho);
                utilizadorId = em.merge(utilizadorId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFotoTrilho(fotoTrilho.getFotoTrilhoId()) != null) {
                throw new PreexistingEntityException("FotoTrilho " + fotoTrilho + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FotoTrilho fotoTrilho) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FotoTrilho persistentFotoTrilho = em.find(FotoTrilho.class, fotoTrilho.getFotoTrilhoId());
            Trilho trilhoIdOld = persistentFotoTrilho.getTrilhoId();
            Trilho trilhoIdNew = fotoTrilho.getTrilhoId();
            Utilizador utilizadorIdOld = persistentFotoTrilho.getUtilizadorId();
            Utilizador utilizadorIdNew = fotoTrilho.getUtilizadorId();
            if (trilhoIdNew != null) {
                trilhoIdNew = em.getReference(trilhoIdNew.getClass(), trilhoIdNew.getTrilhoId());
                fotoTrilho.setTrilhoId(trilhoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                fotoTrilho.setUtilizadorId(utilizadorIdNew);
            }
            fotoTrilho = em.merge(fotoTrilho);
            if (trilhoIdOld != null && !trilhoIdOld.equals(trilhoIdNew)) {
                trilhoIdOld.getFotoTrilhoList().remove(fotoTrilho);
                trilhoIdOld = em.merge(trilhoIdOld);
            }
            if (trilhoIdNew != null && !trilhoIdNew.equals(trilhoIdOld)) {
                trilhoIdNew.getFotoTrilhoList().add(fotoTrilho);
                trilhoIdNew = em.merge(trilhoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getFotoTrilhoList().remove(fotoTrilho);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getFotoTrilhoList().add(fotoTrilho);
                utilizadorIdNew = em.merge(utilizadorIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = fotoTrilho.getFotoTrilhoId();
                if (findFotoTrilho(id) == null) {
                    throw new NonexistentEntityException("The fotoTrilho with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FotoTrilho fotoTrilho;
            try {
                fotoTrilho = em.getReference(FotoTrilho.class, id);
                fotoTrilho.getFotoTrilhoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fotoTrilho with id " + id + " no longer exists.", enfe);
            }
            Trilho trilhoId = fotoTrilho.getTrilhoId();
            if (trilhoId != null) {
                trilhoId.getFotoTrilhoList().remove(fotoTrilho);
                trilhoId = em.merge(trilhoId);
            }
            Utilizador utilizadorId = fotoTrilho.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getFotoTrilhoList().remove(fotoTrilho);
                utilizadorId = em.merge(utilizadorId);
            }
            em.remove(fotoTrilho);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FotoTrilho> findFotoTrilhoEntities() {
        return findFotoTrilhoEntities(true, -1, -1);
    }

    public List<FotoTrilho> findFotoTrilhoEntities(int maxResults, int firstResult) {
        return findFotoTrilhoEntities(false, maxResults, firstResult);
    }

    private List<FotoTrilho> findFotoTrilhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FotoTrilho.class));
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

    public FotoTrilho findFotoTrilho(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FotoTrilho.class, id);
        } finally {
            em.close();
        }
    }

    public int getFotoTrilhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FotoTrilho> rt = cq.from(FotoTrilho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
