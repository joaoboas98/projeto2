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
import DAL.Guia;
import Model.exceptions.NonexistentEntityException;
import Model.exceptions.PreexistingEntityException;
import Model.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Utilizador
 */
public class GuiaJpaController implements Serializable {

    public GuiaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Guia guia) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (guia.getExcursaoList() == null) {
            guia.setExcursaoList(new ArrayList<Excursao>());
        }
        if (guia.getExcursaoList1() == null) {
            guia.setExcursaoList1(new ArrayList<Excursao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Excursao> attachedExcursaoList = new ArrayList<Excursao>();
            for (Excursao excursaoListExcursaoToAttach : guia.getExcursaoList()) {
                excursaoListExcursaoToAttach = em.getReference(excursaoListExcursaoToAttach.getClass(), excursaoListExcursaoToAttach.getExcursaoId());
                attachedExcursaoList.add(excursaoListExcursaoToAttach);
            }
            guia.setExcursaoList(attachedExcursaoList);
            List<Excursao> attachedExcursaoList1 = new ArrayList<Excursao>();
            for (Excursao excursaoList1ExcursaoToAttach : guia.getExcursaoList1()) {
                excursaoList1ExcursaoToAttach = em.getReference(excursaoList1ExcursaoToAttach.getClass(), excursaoList1ExcursaoToAttach.getExcursaoId());
                attachedExcursaoList1.add(excursaoList1ExcursaoToAttach);
            }
            guia.setExcursaoList1(attachedExcursaoList1);
            em.persist(guia);
            for (Excursao excursaoListExcursao : guia.getExcursaoList()) {
                excursaoListExcursao.getGuiaList().add(guia);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            for (Excursao excursaoList1Excursao : guia.getExcursaoList1()) {
                Guia oldGuiaIdOfExcursaoList1Excursao = excursaoList1Excursao.getGuiaId();
                excursaoList1Excursao.setGuiaId(guia);
                excursaoList1Excursao = em.merge(excursaoList1Excursao);
                if (oldGuiaIdOfExcursaoList1Excursao != null) {
                    oldGuiaIdOfExcursaoList1Excursao.getExcursaoList1().remove(excursaoList1Excursao);
                    oldGuiaIdOfExcursaoList1Excursao = em.merge(oldGuiaIdOfExcursaoList1Excursao);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGuia(guia.getGuiaId()) != null) {
                throw new PreexistingEntityException("Guia " + guia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Guia guia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Guia persistentGuia = em.find(Guia.class, guia.getGuiaId());
            List<Excursao> excursaoListOld = persistentGuia.getExcursaoList();
            List<Excursao> excursaoListNew = guia.getExcursaoList();
            List<Excursao> excursaoList1Old = persistentGuia.getExcursaoList1();
            List<Excursao> excursaoList1New = guia.getExcursaoList1();
            List<Excursao> attachedExcursaoListNew = new ArrayList<Excursao>();
            for (Excursao excursaoListNewExcursaoToAttach : excursaoListNew) {
                excursaoListNewExcursaoToAttach = em.getReference(excursaoListNewExcursaoToAttach.getClass(), excursaoListNewExcursaoToAttach.getExcursaoId());
                attachedExcursaoListNew.add(excursaoListNewExcursaoToAttach);
            }
            excursaoListNew = attachedExcursaoListNew;
            guia.setExcursaoList(excursaoListNew);
            List<Excursao> attachedExcursaoList1New = new ArrayList<Excursao>();
            for (Excursao excursaoList1NewExcursaoToAttach : excursaoList1New) {
                excursaoList1NewExcursaoToAttach = em.getReference(excursaoList1NewExcursaoToAttach.getClass(), excursaoList1NewExcursaoToAttach.getExcursaoId());
                attachedExcursaoList1New.add(excursaoList1NewExcursaoToAttach);
            }
            excursaoList1New = attachedExcursaoList1New;
            guia.setExcursaoList1(excursaoList1New);
            guia = em.merge(guia);
            for (Excursao excursaoListOldExcursao : excursaoListOld) {
                if (!excursaoListNew.contains(excursaoListOldExcursao)) {
                    excursaoListOldExcursao.getGuiaList().remove(guia);
                    excursaoListOldExcursao = em.merge(excursaoListOldExcursao);
                }
            }
            for (Excursao excursaoListNewExcursao : excursaoListNew) {
                if (!excursaoListOld.contains(excursaoListNewExcursao)) {
                    excursaoListNewExcursao.getGuiaList().add(guia);
                    excursaoListNewExcursao = em.merge(excursaoListNewExcursao);
                }
            }
            for (Excursao excursaoList1OldExcursao : excursaoList1Old) {
                if (!excursaoList1New.contains(excursaoList1OldExcursao)) {
                    excursaoList1OldExcursao.setGuiaId(null);
                    excursaoList1OldExcursao = em.merge(excursaoList1OldExcursao);
                }
            }
            for (Excursao excursaoList1NewExcursao : excursaoList1New) {
                if (!excursaoList1Old.contains(excursaoList1NewExcursao)) {
                    Guia oldGuiaIdOfExcursaoList1NewExcursao = excursaoList1NewExcursao.getGuiaId();
                    excursaoList1NewExcursao.setGuiaId(guia);
                    excursaoList1NewExcursao = em.merge(excursaoList1NewExcursao);
                    if (oldGuiaIdOfExcursaoList1NewExcursao != null && !oldGuiaIdOfExcursaoList1NewExcursao.equals(guia)) {
                        oldGuiaIdOfExcursaoList1NewExcursao.getExcursaoList1().remove(excursaoList1NewExcursao);
                        oldGuiaIdOfExcursaoList1NewExcursao = em.merge(oldGuiaIdOfExcursaoList1NewExcursao);
                    }
                }
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
                BigDecimal id = guia.getGuiaId();
                if (findGuia(id) == null) {
                    throw new NonexistentEntityException("The guia with id " + id + " no longer exists.");
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
            Guia guia;
            try {
                guia = em.getReference(Guia.class, id);
                guia.getGuiaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The guia with id " + id + " no longer exists.", enfe);
            }
            List<Excursao> excursaoList = guia.getExcursaoList();
            for (Excursao excursaoListExcursao : excursaoList) {
                excursaoListExcursao.getGuiaList().remove(guia);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            List<Excursao> excursaoList1 = guia.getExcursaoList1();
            for (Excursao excursaoList1Excursao : excursaoList1) {
                excursaoList1Excursao.setGuiaId(null);
                excursaoList1Excursao = em.merge(excursaoList1Excursao);
            }
            em.remove(guia);
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

    public List<Guia> findGuiaEntities() {
        return findGuiaEntities(true, -1, -1);
    }

    public List<Guia> findGuiaEntities(int maxResults, int firstResult) {
        return findGuiaEntities(false, maxResults, firstResult);
    }

    private List<Guia> findGuiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Guia.class));
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

    public Guia findGuia(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Guia.class, id);
        } finally {
            em.close();
        }
    }

    public int getGuiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Guia> rt = cq.from(Guia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
