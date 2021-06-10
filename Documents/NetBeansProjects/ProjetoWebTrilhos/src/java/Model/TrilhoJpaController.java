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
import DAL.Ponto;
import java.util.ArrayList;
import java.util.List;
import DAL.Alojamento;
import DAL.ClassTrilho;
import DAL.Excursao;
import DAL.FotoTrilho;
import DAL.Trilho;
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
public class TrilhoJpaController implements Serializable {

    public TrilhoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trilho trilho) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (trilho.getPontoList() == null) {
            trilho.setPontoList(new ArrayList<Ponto>());
        }
        if (trilho.getAlojamentoList() == null) {
            trilho.setAlojamentoList(new ArrayList<Alojamento>());
        }
        if (trilho.getClassTrilhoList() == null) {
            trilho.setClassTrilhoList(new ArrayList<ClassTrilho>());
        }
        if (trilho.getExcursaoList() == null) {
            trilho.setExcursaoList(new ArrayList<Excursao>());
        }
        if (trilho.getFotoTrilhoList() == null) {
            trilho.setFotoTrilhoList(new ArrayList<FotoTrilho>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Ponto> attachedPontoList = new ArrayList<Ponto>();
            for (Ponto pontoListPontoToAttach : trilho.getPontoList()) {
                pontoListPontoToAttach = em.getReference(pontoListPontoToAttach.getClass(), pontoListPontoToAttach.getPontoId());
                attachedPontoList.add(pontoListPontoToAttach);
            }
            trilho.setPontoList(attachedPontoList);
            List<Alojamento> attachedAlojamentoList = new ArrayList<Alojamento>();
            for (Alojamento alojamentoListAlojamentoToAttach : trilho.getAlojamentoList()) {
                alojamentoListAlojamentoToAttach = em.getReference(alojamentoListAlojamentoToAttach.getClass(), alojamentoListAlojamentoToAttach.getAlojamentoId());
                attachedAlojamentoList.add(alojamentoListAlojamentoToAttach);
            }
            trilho.setAlojamentoList(attachedAlojamentoList);
            List<ClassTrilho> attachedClassTrilhoList = new ArrayList<ClassTrilho>();
            for (ClassTrilho classTrilhoListClassTrilhoToAttach : trilho.getClassTrilhoList()) {
                classTrilhoListClassTrilhoToAttach = em.getReference(classTrilhoListClassTrilhoToAttach.getClass(), classTrilhoListClassTrilhoToAttach.getClassTrilhoId());
                attachedClassTrilhoList.add(classTrilhoListClassTrilhoToAttach);
            }
            trilho.setClassTrilhoList(attachedClassTrilhoList);
            List<Excursao> attachedExcursaoList = new ArrayList<Excursao>();
            for (Excursao excursaoListExcursaoToAttach : trilho.getExcursaoList()) {
                excursaoListExcursaoToAttach = em.getReference(excursaoListExcursaoToAttach.getClass(), excursaoListExcursaoToAttach.getExcursaoId());
                attachedExcursaoList.add(excursaoListExcursaoToAttach);
            }
            trilho.setExcursaoList(attachedExcursaoList);
            List<FotoTrilho> attachedFotoTrilhoList = new ArrayList<FotoTrilho>();
            for (FotoTrilho fotoTrilhoListFotoTrilhoToAttach : trilho.getFotoTrilhoList()) {
                fotoTrilhoListFotoTrilhoToAttach = em.getReference(fotoTrilhoListFotoTrilhoToAttach.getClass(), fotoTrilhoListFotoTrilhoToAttach.getFotoTrilhoId());
                attachedFotoTrilhoList.add(fotoTrilhoListFotoTrilhoToAttach);
            }
            trilho.setFotoTrilhoList(attachedFotoTrilhoList);
            em.persist(trilho);
            for (Ponto pontoListPonto : trilho.getPontoList()) {
                pontoListPonto.getTrilhoList().add(trilho);
                pontoListPonto = em.merge(pontoListPonto);
            }
            for (Alojamento alojamentoListAlojamento : trilho.getAlojamentoList()) {
                alojamentoListAlojamento.getTrilhoList().add(trilho);
                alojamentoListAlojamento = em.merge(alojamentoListAlojamento);
            }
            for (ClassTrilho classTrilhoListClassTrilho : trilho.getClassTrilhoList()) {
                Trilho oldTrilhoIdOfClassTrilhoListClassTrilho = classTrilhoListClassTrilho.getTrilhoId();
                classTrilhoListClassTrilho.setTrilhoId(trilho);
                classTrilhoListClassTrilho = em.merge(classTrilhoListClassTrilho);
                if (oldTrilhoIdOfClassTrilhoListClassTrilho != null) {
                    oldTrilhoIdOfClassTrilhoListClassTrilho.getClassTrilhoList().remove(classTrilhoListClassTrilho);
                    oldTrilhoIdOfClassTrilhoListClassTrilho = em.merge(oldTrilhoIdOfClassTrilhoListClassTrilho);
                }
            }
            for (Excursao excursaoListExcursao : trilho.getExcursaoList()) {
                Trilho oldTrilhoIdOfExcursaoListExcursao = excursaoListExcursao.getTrilhoId();
                excursaoListExcursao.setTrilhoId(trilho);
                excursaoListExcursao = em.merge(excursaoListExcursao);
                if (oldTrilhoIdOfExcursaoListExcursao != null) {
                    oldTrilhoIdOfExcursaoListExcursao.getExcursaoList().remove(excursaoListExcursao);
                    oldTrilhoIdOfExcursaoListExcursao = em.merge(oldTrilhoIdOfExcursaoListExcursao);
                }
            }
            for (FotoTrilho fotoTrilhoListFotoTrilho : trilho.getFotoTrilhoList()) {
                Trilho oldTrilhoIdOfFotoTrilhoListFotoTrilho = fotoTrilhoListFotoTrilho.getTrilhoId();
                fotoTrilhoListFotoTrilho.setTrilhoId(trilho);
                fotoTrilhoListFotoTrilho = em.merge(fotoTrilhoListFotoTrilho);
                if (oldTrilhoIdOfFotoTrilhoListFotoTrilho != null) {
                    oldTrilhoIdOfFotoTrilhoListFotoTrilho.getFotoTrilhoList().remove(fotoTrilhoListFotoTrilho);
                    oldTrilhoIdOfFotoTrilhoListFotoTrilho = em.merge(oldTrilhoIdOfFotoTrilhoListFotoTrilho);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTrilho(trilho.getTrilhoId()) != null) {
                throw new PreexistingEntityException("Trilho " + trilho + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trilho trilho) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Trilho persistentTrilho = em.find(Trilho.class, trilho.getTrilhoId());
            List<Ponto> pontoListOld = persistentTrilho.getPontoList();
            List<Ponto> pontoListNew = trilho.getPontoList();
            List<Alojamento> alojamentoListOld = persistentTrilho.getAlojamentoList();
            List<Alojamento> alojamentoListNew = trilho.getAlojamentoList();
            List<ClassTrilho> classTrilhoListOld = persistentTrilho.getClassTrilhoList();
            List<ClassTrilho> classTrilhoListNew = trilho.getClassTrilhoList();
            List<Excursao> excursaoListOld = persistentTrilho.getExcursaoList();
            List<Excursao> excursaoListNew = trilho.getExcursaoList();
            List<FotoTrilho> fotoTrilhoListOld = persistentTrilho.getFotoTrilhoList();
            List<FotoTrilho> fotoTrilhoListNew = trilho.getFotoTrilhoList();
            List<Ponto> attachedPontoListNew = new ArrayList<Ponto>();
            for (Ponto pontoListNewPontoToAttach : pontoListNew) {
                pontoListNewPontoToAttach = em.getReference(pontoListNewPontoToAttach.getClass(), pontoListNewPontoToAttach.getPontoId());
                attachedPontoListNew.add(pontoListNewPontoToAttach);
            }
            pontoListNew = attachedPontoListNew;
            trilho.setPontoList(pontoListNew);
            List<Alojamento> attachedAlojamentoListNew = new ArrayList<Alojamento>();
            for (Alojamento alojamentoListNewAlojamentoToAttach : alojamentoListNew) {
                alojamentoListNewAlojamentoToAttach = em.getReference(alojamentoListNewAlojamentoToAttach.getClass(), alojamentoListNewAlojamentoToAttach.getAlojamentoId());
                attachedAlojamentoListNew.add(alojamentoListNewAlojamentoToAttach);
            }
            alojamentoListNew = attachedAlojamentoListNew;
            trilho.setAlojamentoList(alojamentoListNew);
            List<ClassTrilho> attachedClassTrilhoListNew = new ArrayList<ClassTrilho>();
            for (ClassTrilho classTrilhoListNewClassTrilhoToAttach : classTrilhoListNew) {
                classTrilhoListNewClassTrilhoToAttach = em.getReference(classTrilhoListNewClassTrilhoToAttach.getClass(), classTrilhoListNewClassTrilhoToAttach.getClassTrilhoId());
                attachedClassTrilhoListNew.add(classTrilhoListNewClassTrilhoToAttach);
            }
            classTrilhoListNew = attachedClassTrilhoListNew;
            trilho.setClassTrilhoList(classTrilhoListNew);
            List<Excursao> attachedExcursaoListNew = new ArrayList<Excursao>();
            for (Excursao excursaoListNewExcursaoToAttach : excursaoListNew) {
                excursaoListNewExcursaoToAttach = em.getReference(excursaoListNewExcursaoToAttach.getClass(), excursaoListNewExcursaoToAttach.getExcursaoId());
                attachedExcursaoListNew.add(excursaoListNewExcursaoToAttach);
            }
            excursaoListNew = attachedExcursaoListNew;
            trilho.setExcursaoList(excursaoListNew);
            List<FotoTrilho> attachedFotoTrilhoListNew = new ArrayList<FotoTrilho>();
            for (FotoTrilho fotoTrilhoListNewFotoTrilhoToAttach : fotoTrilhoListNew) {
                fotoTrilhoListNewFotoTrilhoToAttach = em.getReference(fotoTrilhoListNewFotoTrilhoToAttach.getClass(), fotoTrilhoListNewFotoTrilhoToAttach.getFotoTrilhoId());
                attachedFotoTrilhoListNew.add(fotoTrilhoListNewFotoTrilhoToAttach);
            }
            fotoTrilhoListNew = attachedFotoTrilhoListNew;
            trilho.setFotoTrilhoList(fotoTrilhoListNew);
            trilho = em.merge(trilho);
            for (Ponto pontoListOldPonto : pontoListOld) {
                if (!pontoListNew.contains(pontoListOldPonto)) {
                    pontoListOldPonto.getTrilhoList().remove(trilho);
                    pontoListOldPonto = em.merge(pontoListOldPonto);
                }
            }
            for (Ponto pontoListNewPonto : pontoListNew) {
                if (!pontoListOld.contains(pontoListNewPonto)) {
                    pontoListNewPonto.getTrilhoList().add(trilho);
                    pontoListNewPonto = em.merge(pontoListNewPonto);
                }
            }
            for (Alojamento alojamentoListOldAlojamento : alojamentoListOld) {
                if (!alojamentoListNew.contains(alojamentoListOldAlojamento)) {
                    alojamentoListOldAlojamento.getTrilhoList().remove(trilho);
                    alojamentoListOldAlojamento = em.merge(alojamentoListOldAlojamento);
                }
            }
            for (Alojamento alojamentoListNewAlojamento : alojamentoListNew) {
                if (!alojamentoListOld.contains(alojamentoListNewAlojamento)) {
                    alojamentoListNewAlojamento.getTrilhoList().add(trilho);
                    alojamentoListNewAlojamento = em.merge(alojamentoListNewAlojamento);
                }
            }
            for (ClassTrilho classTrilhoListOldClassTrilho : classTrilhoListOld) {
                if (!classTrilhoListNew.contains(classTrilhoListOldClassTrilho)) {
                    classTrilhoListOldClassTrilho.setTrilhoId(null);
                    classTrilhoListOldClassTrilho = em.merge(classTrilhoListOldClassTrilho);
                }
            }
            for (ClassTrilho classTrilhoListNewClassTrilho : classTrilhoListNew) {
                if (!classTrilhoListOld.contains(classTrilhoListNewClassTrilho)) {
                    Trilho oldTrilhoIdOfClassTrilhoListNewClassTrilho = classTrilhoListNewClassTrilho.getTrilhoId();
                    classTrilhoListNewClassTrilho.setTrilhoId(trilho);
                    classTrilhoListNewClassTrilho = em.merge(classTrilhoListNewClassTrilho);
                    if (oldTrilhoIdOfClassTrilhoListNewClassTrilho != null && !oldTrilhoIdOfClassTrilhoListNewClassTrilho.equals(trilho)) {
                        oldTrilhoIdOfClassTrilhoListNewClassTrilho.getClassTrilhoList().remove(classTrilhoListNewClassTrilho);
                        oldTrilhoIdOfClassTrilhoListNewClassTrilho = em.merge(oldTrilhoIdOfClassTrilhoListNewClassTrilho);
                    }
                }
            }
            for (Excursao excursaoListOldExcursao : excursaoListOld) {
                if (!excursaoListNew.contains(excursaoListOldExcursao)) {
                    excursaoListOldExcursao.setTrilhoId(null);
                    excursaoListOldExcursao = em.merge(excursaoListOldExcursao);
                }
            }
            for (Excursao excursaoListNewExcursao : excursaoListNew) {
                if (!excursaoListOld.contains(excursaoListNewExcursao)) {
                    Trilho oldTrilhoIdOfExcursaoListNewExcursao = excursaoListNewExcursao.getTrilhoId();
                    excursaoListNewExcursao.setTrilhoId(trilho);
                    excursaoListNewExcursao = em.merge(excursaoListNewExcursao);
                    if (oldTrilhoIdOfExcursaoListNewExcursao != null && !oldTrilhoIdOfExcursaoListNewExcursao.equals(trilho)) {
                        oldTrilhoIdOfExcursaoListNewExcursao.getExcursaoList().remove(excursaoListNewExcursao);
                        oldTrilhoIdOfExcursaoListNewExcursao = em.merge(oldTrilhoIdOfExcursaoListNewExcursao);
                    }
                }
            }
            for (FotoTrilho fotoTrilhoListOldFotoTrilho : fotoTrilhoListOld) {
                if (!fotoTrilhoListNew.contains(fotoTrilhoListOldFotoTrilho)) {
                    fotoTrilhoListOldFotoTrilho.setTrilhoId(null);
                    fotoTrilhoListOldFotoTrilho = em.merge(fotoTrilhoListOldFotoTrilho);
                }
            }
            for (FotoTrilho fotoTrilhoListNewFotoTrilho : fotoTrilhoListNew) {
                if (!fotoTrilhoListOld.contains(fotoTrilhoListNewFotoTrilho)) {
                    Trilho oldTrilhoIdOfFotoTrilhoListNewFotoTrilho = fotoTrilhoListNewFotoTrilho.getTrilhoId();
                    fotoTrilhoListNewFotoTrilho.setTrilhoId(trilho);
                    fotoTrilhoListNewFotoTrilho = em.merge(fotoTrilhoListNewFotoTrilho);
                    if (oldTrilhoIdOfFotoTrilhoListNewFotoTrilho != null && !oldTrilhoIdOfFotoTrilhoListNewFotoTrilho.equals(trilho)) {
                        oldTrilhoIdOfFotoTrilhoListNewFotoTrilho.getFotoTrilhoList().remove(fotoTrilhoListNewFotoTrilho);
                        oldTrilhoIdOfFotoTrilhoListNewFotoTrilho = em.merge(oldTrilhoIdOfFotoTrilhoListNewFotoTrilho);
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
                BigDecimal id = trilho.getTrilhoId();
                if (findTrilho(id) == null) {
                    throw new NonexistentEntityException("The trilho with id " + id + " no longer exists.");
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
            Trilho trilho;
            try {
                trilho = em.getReference(Trilho.class, id);
                trilho.getTrilhoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trilho with id " + id + " no longer exists.", enfe);
            }
            List<Ponto> pontoList = trilho.getPontoList();
            for (Ponto pontoListPonto : pontoList) {
                pontoListPonto.getTrilhoList().remove(trilho);
                pontoListPonto = em.merge(pontoListPonto);
            }
            List<Alojamento> alojamentoList = trilho.getAlojamentoList();
            for (Alojamento alojamentoListAlojamento : alojamentoList) {
                alojamentoListAlojamento.getTrilhoList().remove(trilho);
                alojamentoListAlojamento = em.merge(alojamentoListAlojamento);
            }
            List<ClassTrilho> classTrilhoList = trilho.getClassTrilhoList();
            for (ClassTrilho classTrilhoListClassTrilho : classTrilhoList) {
                classTrilhoListClassTrilho.setTrilhoId(null);
                classTrilhoListClassTrilho = em.merge(classTrilhoListClassTrilho);
            }
            List<Excursao> excursaoList = trilho.getExcursaoList();
            for (Excursao excursaoListExcursao : excursaoList) {
                excursaoListExcursao.setTrilhoId(null);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            List<FotoTrilho> fotoTrilhoList = trilho.getFotoTrilhoList();
            for (FotoTrilho fotoTrilhoListFotoTrilho : fotoTrilhoList) {
                fotoTrilhoListFotoTrilho.setTrilhoId(null);
                fotoTrilhoListFotoTrilho = em.merge(fotoTrilhoListFotoTrilho);
            }
            em.remove(trilho);
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

    public List<Trilho> findTrilhoEntities() {
        return findTrilhoEntities(true, -1, -1);
    }

    public List<Trilho> findTrilhoEntities(int maxResults, int firstResult) {
        return findTrilhoEntities(false, maxResults, firstResult);
    }

    private List<Trilho> findTrilhoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trilho.class));
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

    public Trilho findTrilho(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trilho.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrilhoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trilho> rt = cq.from(Trilho.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
