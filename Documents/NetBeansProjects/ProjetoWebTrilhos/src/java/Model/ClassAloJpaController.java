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
import DAL.ClassAlo;
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
public class ClassAloJpaController implements Serializable {

    public ClassAloJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClassAlo classAlo) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alojamento alojamentoId = classAlo.getAlojamentoId();
            if (alojamentoId != null) {
                alojamentoId = em.getReference(alojamentoId.getClass(), alojamentoId.getAlojamentoId());
                classAlo.setAlojamentoId(alojamentoId);
            }
            Utilizador utilizadorId = classAlo.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                classAlo.setUtilizadorId(utilizadorId);
            }
            em.persist(classAlo);
            if (alojamentoId != null) {
                alojamentoId.getClassAloList().add(classAlo);
                alojamentoId = em.merge(alojamentoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getClassAloList().add(classAlo);
                utilizadorId = em.merge(utilizadorId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClassAlo(classAlo.getClassAloId()) != null) {
                throw new PreexistingEntityException("ClassAlo " + classAlo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClassAlo classAlo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClassAlo persistentClassAlo = em.find(ClassAlo.class, classAlo.getClassAloId());
            Alojamento alojamentoIdOld = persistentClassAlo.getAlojamentoId();
            Alojamento alojamentoIdNew = classAlo.getAlojamentoId();
            Utilizador utilizadorIdOld = persistentClassAlo.getUtilizadorId();
            Utilizador utilizadorIdNew = classAlo.getUtilizadorId();
            if (alojamentoIdNew != null) {
                alojamentoIdNew = em.getReference(alojamentoIdNew.getClass(), alojamentoIdNew.getAlojamentoId());
                classAlo.setAlojamentoId(alojamentoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                classAlo.setUtilizadorId(utilizadorIdNew);
            }
            classAlo = em.merge(classAlo);
            if (alojamentoIdOld != null && !alojamentoIdOld.equals(alojamentoIdNew)) {
                alojamentoIdOld.getClassAloList().remove(classAlo);
                alojamentoIdOld = em.merge(alojamentoIdOld);
            }
            if (alojamentoIdNew != null && !alojamentoIdNew.equals(alojamentoIdOld)) {
                alojamentoIdNew.getClassAloList().add(classAlo);
                alojamentoIdNew = em.merge(alojamentoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getClassAloList().remove(classAlo);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getClassAloList().add(classAlo);
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
                BigDecimal id = classAlo.getClassAloId();
                if (findClassAlo(id) == null) {
                    throw new NonexistentEntityException("The classAlo with id " + id + " no longer exists.");
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
            ClassAlo classAlo;
            try {
                classAlo = em.getReference(ClassAlo.class, id);
                classAlo.getClassAloId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The classAlo with id " + id + " no longer exists.", enfe);
            }
            Alojamento alojamentoId = classAlo.getAlojamentoId();
            if (alojamentoId != null) {
                alojamentoId.getClassAloList().remove(classAlo);
                alojamentoId = em.merge(alojamentoId);
            }
            Utilizador utilizadorId = classAlo.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getClassAloList().remove(classAlo);
                utilizadorId = em.merge(utilizadorId);
            }
            em.remove(classAlo);
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
