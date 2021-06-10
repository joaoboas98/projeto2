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
import DAL.Trilho;
import java.util.ArrayList;
import java.util.List;
import DAL.FotoPonto;
import DAL.Ponto;
import Model.exceptions.NonexistentEntityException;
import Model.exceptions.PreexistingEntityException;
import Model.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Utilizador
 */
public class PontoJpaController implements Serializable {

    public PontoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ponto ponto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (ponto.getTrilhoList() == null) {
            ponto.setTrilhoList(new ArrayList<Trilho>());
        }
        if (ponto.getFotoPontoList() == null) {
            ponto.setFotoPontoList(new ArrayList<FotoPonto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trilho> attachedTrilhoList = new ArrayList<Trilho>();
            for (Trilho trilhoListTrilhoToAttach : ponto.getTrilhoList()) {
                trilhoListTrilhoToAttach = em.getReference(trilhoListTrilhoToAttach.getClass(), trilhoListTrilhoToAttach.getTrilhoId());
                attachedTrilhoList.add(trilhoListTrilhoToAttach);
            }
            ponto.setTrilhoList(attachedTrilhoList);
            List<FotoPonto> attachedFotoPontoList = new ArrayList<FotoPonto>();
            for (FotoPonto fotoPontoListFotoPontoToAttach : ponto.getFotoPontoList()) {
                fotoPontoListFotoPontoToAttach = em.getReference(fotoPontoListFotoPontoToAttach.getClass(), fotoPontoListFotoPontoToAttach.getFotoPontoId());
                attachedFotoPontoList.add(fotoPontoListFotoPontoToAttach);
            }
            ponto.setFotoPontoList(attachedFotoPontoList);
            em.persist(ponto);
            for (Trilho trilhoListTrilho : ponto.getTrilhoList()) {
                trilhoListTrilho.getPontoList().add(ponto);
                trilhoListTrilho = em.merge(trilhoListTrilho);
            }
            for (FotoPonto fotoPontoListFotoPonto : ponto.getFotoPontoList()) {
                Ponto oldPontoIdOfFotoPontoListFotoPonto = fotoPontoListFotoPonto.getPontoId();
                fotoPontoListFotoPonto.setPontoId(ponto);
                fotoPontoListFotoPonto = em.merge(fotoPontoListFotoPonto);
                if (oldPontoIdOfFotoPontoListFotoPonto != null) {
                    oldPontoIdOfFotoPontoListFotoPonto.getFotoPontoList().remove(fotoPontoListFotoPonto);
                    oldPontoIdOfFotoPontoListFotoPonto = em.merge(oldPontoIdOfFotoPontoListFotoPonto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPonto(ponto.getPontoId()) != null) {
                throw new PreexistingEntityException("Ponto " + ponto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ponto ponto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ponto persistentPonto = em.find(Ponto.class, ponto.getPontoId());
            List<Trilho> trilhoListOld = persistentPonto.getTrilhoList();
            List<Trilho> trilhoListNew = ponto.getTrilhoList();
            List<FotoPonto> fotoPontoListOld = persistentPonto.getFotoPontoList();
            List<FotoPonto> fotoPontoListNew = ponto.getFotoPontoList();
            List<Trilho> attachedTrilhoListNew = new ArrayList<Trilho>();
            for (Trilho trilhoListNewTrilhoToAttach : trilhoListNew) {
                trilhoListNewTrilhoToAttach = em.getReference(trilhoListNewTrilhoToAttach.getClass(), trilhoListNewTrilhoToAttach.getTrilhoId());
                attachedTrilhoListNew.add(trilhoListNewTrilhoToAttach);
            }
            trilhoListNew = attachedTrilhoListNew;
            ponto.setTrilhoList(trilhoListNew);
            List<FotoPonto> attachedFotoPontoListNew = new ArrayList<FotoPonto>();
            for (FotoPonto fotoPontoListNewFotoPontoToAttach : fotoPontoListNew) {
                fotoPontoListNewFotoPontoToAttach = em.getReference(fotoPontoListNewFotoPontoToAttach.getClass(), fotoPontoListNewFotoPontoToAttach.getFotoPontoId());
                attachedFotoPontoListNew.add(fotoPontoListNewFotoPontoToAttach);
            }
            fotoPontoListNew = attachedFotoPontoListNew;
            ponto.setFotoPontoList(fotoPontoListNew);
            ponto = em.merge(ponto);
            for (Trilho trilhoListOldTrilho : trilhoListOld) {
                if (!trilhoListNew.contains(trilhoListOldTrilho)) {
                    trilhoListOldTrilho.getPontoList().remove(ponto);
                    trilhoListOldTrilho = em.merge(trilhoListOldTrilho);
                }
            }
            for (Trilho trilhoListNewTrilho : trilhoListNew) {
                if (!trilhoListOld.contains(trilhoListNewTrilho)) {
                    trilhoListNewTrilho.getPontoList().add(ponto);
                    trilhoListNewTrilho = em.merge(trilhoListNewTrilho);
                }
            }
            for (FotoPonto fotoPontoListOldFotoPonto : fotoPontoListOld) {
                if (!fotoPontoListNew.contains(fotoPontoListOldFotoPonto)) {
                    fotoPontoListOldFotoPonto.setPontoId(null);
                    fotoPontoListOldFotoPonto = em.merge(fotoPontoListOldFotoPonto);
                }
            }
            for (FotoPonto fotoPontoListNewFotoPonto : fotoPontoListNew) {
                if (!fotoPontoListOld.contains(fotoPontoListNewFotoPonto)) {
                    Ponto oldPontoIdOfFotoPontoListNewFotoPonto = fotoPontoListNewFotoPonto.getPontoId();
                    fotoPontoListNewFotoPonto.setPontoId(ponto);
                    fotoPontoListNewFotoPonto = em.merge(fotoPontoListNewFotoPonto);
                    if (oldPontoIdOfFotoPontoListNewFotoPonto != null && !oldPontoIdOfFotoPontoListNewFotoPonto.equals(ponto)) {
                        oldPontoIdOfFotoPontoListNewFotoPonto.getFotoPontoList().remove(fotoPontoListNewFotoPonto);
                        oldPontoIdOfFotoPontoListNewFotoPonto = em.merge(oldPontoIdOfFotoPontoListNewFotoPonto);
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
                BigDecimal id = ponto.getPontoId();
                if (findPonto(id) == null) {
                    throw new NonexistentEntityException("The ponto with id " + id + " no longer exists.");
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
            Ponto ponto;
            try {
                ponto = em.getReference(Ponto.class, id);
                ponto.getPontoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ponto with id " + id + " no longer exists.", enfe);
            }
            List<Trilho> trilhoList = ponto.getTrilhoList();
            for (Trilho trilhoListTrilho : trilhoList) {
                trilhoListTrilho.getPontoList().remove(ponto);
                trilhoListTrilho = em.merge(trilhoListTrilho);
            }
            List<FotoPonto> fotoPontoList = ponto.getFotoPontoList();
            for (FotoPonto fotoPontoListFotoPonto : fotoPontoList) {
                fotoPontoListFotoPonto.setPontoId(null);
                fotoPontoListFotoPonto = em.merge(fotoPontoListFotoPonto);
            }
            em.remove(ponto);
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

    public List<Ponto> findPontoEntities() {
        return findPontoEntities(true, -1, -1);
    }

    public List<Ponto> findPontoEntities(int maxResults, int firstResult) {
        return findPontoEntities(false, maxResults, firstResult);
    }

    private List<Ponto> findPontoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ponto.class));
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

    public Ponto findPonto(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ponto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPontoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ponto> rt = cq.from(Ponto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
