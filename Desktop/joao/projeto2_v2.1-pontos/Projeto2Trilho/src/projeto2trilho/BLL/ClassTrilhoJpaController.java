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
import projeto2trilho.DAL.ClassTrilho;
import projeto2trilho.DAL.Trilho;
import projeto2trilho.DAL.Utilizador;

/**
 *
 * @author rafae
 */
public class ClassTrilhoJpaController implements Serializable {

    public ClassTrilhoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClassTrilho classTrilho) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trilho trilhoId = classTrilho.getTrilhoId();
            if (trilhoId != null) {
                trilhoId = em.getReference(trilhoId.getClass(), trilhoId.getTrilhoId());
                classTrilho.setTrilhoId(trilhoId);
            }
            Utilizador utilizadorId = classTrilho.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                classTrilho.setUtilizadorId(utilizadorId);
            }
            em.persist(classTrilho);
            if (trilhoId != null) {
                trilhoId.getClassTrilhoList().add(classTrilho);
                trilhoId = em.merge(trilhoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getClassTrilhoList().add(classTrilho);
                utilizadorId = em.merge(utilizadorId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClassTrilho(classTrilho.getClassTrilhoId()) != null) {
                throw new PreexistingEntityException("ClassTrilho " + classTrilho + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClassTrilho classTrilho) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClassTrilho persistentClassTrilho = em.find(ClassTrilho.class, classTrilho.getClassTrilhoId());
            Trilho trilhoIdOld = persistentClassTrilho.getTrilhoId();
            Trilho trilhoIdNew = classTrilho.getTrilhoId();
            Utilizador utilizadorIdOld = persistentClassTrilho.getUtilizadorId();
            Utilizador utilizadorIdNew = classTrilho.getUtilizadorId();
            if (trilhoIdNew != null) {
                trilhoIdNew = em.getReference(trilhoIdNew.getClass(), trilhoIdNew.getTrilhoId());
                classTrilho.setTrilhoId(trilhoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                classTrilho.setUtilizadorId(utilizadorIdNew);
            }
            classTrilho = em.merge(classTrilho);
            if (trilhoIdOld != null && !trilhoIdOld.equals(trilhoIdNew)) {
                trilhoIdOld.getClassTrilhoList().remove(classTrilho);
                trilhoIdOld = em.merge(trilhoIdOld);
            }
            if (trilhoIdNew != null && !trilhoIdNew.equals(trilhoIdOld)) {
                trilhoIdNew.getClassTrilhoList().add(classTrilho);
                trilhoIdNew = em.merge(trilhoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getClassTrilhoList().remove(classTrilho);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getClassTrilhoList().add(classTrilho);
                utilizadorIdNew = em.merge(utilizadorIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = classTrilho.getClassTrilhoId();
                if (findClassTrilho(id) == null) {
                    throw new NonexistentEntityException("The classTrilho with id " + id + " no longer exists.");
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
            ClassTrilho classTrilho;
            try {
                classTrilho = em.getReference(ClassTrilho.class, id);
                classTrilho.getClassTrilhoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The classTrilho with id " + id + " no longer exists.", enfe);
            }
            Trilho trilhoId = classTrilho.getTrilhoId();
            if (trilhoId != null) {
                trilhoId.getClassTrilhoList().remove(classTrilho);
                trilhoId = em.merge(trilhoId);
            }
            Utilizador utilizadorId = classTrilho.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getClassTrilhoList().remove(classTrilho);
                utilizadorId = em.merge(utilizadorId);
            }
            em.remove(classTrilho);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClassTrilho> findClassTrilhoEntities() {
        return findClassTrilhoEntities(true, -1, -1);
    }

    public List<ClassTrilho> findClassTrilhoEntities(int maxResults, int firstResult) {
        return findClassTrilhoEntities(false, maxResults, firstResult);
    }

    private List<ClassTrilho> findClassTrilhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClassTrilho.class));
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

    public ClassTrilho findClassTrilho(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClassTrilho.class, id);
        } finally {
            em.close();
        }
    }

    public int getClassTrilhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClassTrilho> rt = cq.from(ClassTrilho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
}
