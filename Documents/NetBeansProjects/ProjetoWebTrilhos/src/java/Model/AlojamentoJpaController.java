/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAL.Alojamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DAL.Trilho;
import java.util.ArrayList;
import java.util.List;
import DAL.ClassAlo;
import DAL.FotoAlojamento;
import Model.exceptions.IllegalOrphanException;
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
public class AlojamentoJpaController implements Serializable {

    public AlojamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alojamento alojamento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (alojamento.getTrilhoList() == null) {
            alojamento.setTrilhoList(new ArrayList<Trilho>());
        }
        if (alojamento.getClassAloList() == null) {
            alojamento.setClassAloList(new ArrayList<ClassAlo>());
        }
        if (alojamento.getFotoAlojamentoList() == null) {
            alojamento.setFotoAlojamentoList(new ArrayList<FotoAlojamento>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Trilho> attachedTrilhoList = new ArrayList<Trilho>();
            for (Trilho trilhoListTrilhoToAttach : alojamento.getTrilhoList()) {
                trilhoListTrilhoToAttach = em.getReference(trilhoListTrilhoToAttach.getClass(), trilhoListTrilhoToAttach.getTrilhoId());
                attachedTrilhoList.add(trilhoListTrilhoToAttach);
            }
            alojamento.setTrilhoList(attachedTrilhoList);
            List<ClassAlo> attachedClassAloList = new ArrayList<ClassAlo>();
            for (ClassAlo classAloListClassAloToAttach : alojamento.getClassAloList()) {
                classAloListClassAloToAttach = em.getReference(classAloListClassAloToAttach.getClass(), classAloListClassAloToAttach.getClassAloId());
                attachedClassAloList.add(classAloListClassAloToAttach);
            }
            alojamento.setClassAloList(attachedClassAloList);
            List<FotoAlojamento> attachedFotoAlojamentoList = new ArrayList<FotoAlojamento>();
            for (FotoAlojamento fotoAlojamentoListFotoAlojamentoToAttach : alojamento.getFotoAlojamentoList()) {
                fotoAlojamentoListFotoAlojamentoToAttach = em.getReference(fotoAlojamentoListFotoAlojamentoToAttach.getClass(), fotoAlojamentoListFotoAlojamentoToAttach.getFotoaloid());
                attachedFotoAlojamentoList.add(fotoAlojamentoListFotoAlojamentoToAttach);
            }
            alojamento.setFotoAlojamentoList(attachedFotoAlojamentoList);
            em.persist(alojamento);
            for (Trilho trilhoListTrilho : alojamento.getTrilhoList()) {
                trilhoListTrilho.getAlojamentoList().add(alojamento);
                trilhoListTrilho = em.merge(trilhoListTrilho);
            }
            for (ClassAlo classAloListClassAlo : alojamento.getClassAloList()) {
                Alojamento oldAlojamentoIdOfClassAloListClassAlo = classAloListClassAlo.getAlojamentoId();
                classAloListClassAlo.setAlojamentoId(alojamento);
                classAloListClassAlo = em.merge(classAloListClassAlo);
                if (oldAlojamentoIdOfClassAloListClassAlo != null) {
                    oldAlojamentoIdOfClassAloListClassAlo.getClassAloList().remove(classAloListClassAlo);
                    oldAlojamentoIdOfClassAloListClassAlo = em.merge(oldAlojamentoIdOfClassAloListClassAlo);
                }
            }
            for (FotoAlojamento fotoAlojamentoListFotoAlojamento : alojamento.getFotoAlojamentoList()) {
                Alojamento oldAlojamentoidOfFotoAlojamentoListFotoAlojamento = fotoAlojamentoListFotoAlojamento.getAlojamentoid();
                fotoAlojamentoListFotoAlojamento.setAlojamentoid(alojamento);
                fotoAlojamentoListFotoAlojamento = em.merge(fotoAlojamentoListFotoAlojamento);
                if (oldAlojamentoidOfFotoAlojamentoListFotoAlojamento != null) {
                    oldAlojamentoidOfFotoAlojamentoListFotoAlojamento.getFotoAlojamentoList().remove(fotoAlojamentoListFotoAlojamento);
                    oldAlojamentoidOfFotoAlojamentoListFotoAlojamento = em.merge(oldAlojamentoidOfFotoAlojamentoListFotoAlojamento);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAlojamento(alojamento.getAlojamentoId()) != null) {
                throw new PreexistingEntityException("Alojamento " + alojamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alojamento alojamento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alojamento persistentAlojamento = em.find(Alojamento.class, alojamento.getAlojamentoId());
            List<Trilho> trilhoListOld = persistentAlojamento.getTrilhoList();
            List<Trilho> trilhoListNew = alojamento.getTrilhoList();
            List<ClassAlo> classAloListOld = persistentAlojamento.getClassAloList();
            List<ClassAlo> classAloListNew = alojamento.getClassAloList();
            List<FotoAlojamento> fotoAlojamentoListOld = persistentAlojamento.getFotoAlojamentoList();
            List<FotoAlojamento> fotoAlojamentoListNew = alojamento.getFotoAlojamentoList();
            List<String> illegalOrphanMessages = null;
            for (FotoAlojamento fotoAlojamentoListOldFotoAlojamento : fotoAlojamentoListOld) {
                if (!fotoAlojamentoListNew.contains(fotoAlojamentoListOldFotoAlojamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FotoAlojamento " + fotoAlojamentoListOldFotoAlojamento + " since its alojamentoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trilho> attachedTrilhoListNew = new ArrayList<Trilho>();
            for (Trilho trilhoListNewTrilhoToAttach : trilhoListNew) {
                trilhoListNewTrilhoToAttach = em.getReference(trilhoListNewTrilhoToAttach.getClass(), trilhoListNewTrilhoToAttach.getTrilhoId());
                attachedTrilhoListNew.add(trilhoListNewTrilhoToAttach);
            }
            trilhoListNew = attachedTrilhoListNew;
            alojamento.setTrilhoList(trilhoListNew);
            List<ClassAlo> attachedClassAloListNew = new ArrayList<ClassAlo>();
            for (ClassAlo classAloListNewClassAloToAttach : classAloListNew) {
                classAloListNewClassAloToAttach = em.getReference(classAloListNewClassAloToAttach.getClass(), classAloListNewClassAloToAttach.getClassAloId());
                attachedClassAloListNew.add(classAloListNewClassAloToAttach);
            }
            classAloListNew = attachedClassAloListNew;
            alojamento.setClassAloList(classAloListNew);
            List<FotoAlojamento> attachedFotoAlojamentoListNew = new ArrayList<FotoAlojamento>();
            for (FotoAlojamento fotoAlojamentoListNewFotoAlojamentoToAttach : fotoAlojamentoListNew) {
                fotoAlojamentoListNewFotoAlojamentoToAttach = em.getReference(fotoAlojamentoListNewFotoAlojamentoToAttach.getClass(), fotoAlojamentoListNewFotoAlojamentoToAttach.getFotoaloid());
                attachedFotoAlojamentoListNew.add(fotoAlojamentoListNewFotoAlojamentoToAttach);
            }
            fotoAlojamentoListNew = attachedFotoAlojamentoListNew;
            alojamento.setFotoAlojamentoList(fotoAlojamentoListNew);
            alojamento = em.merge(alojamento);
            for (Trilho trilhoListOldTrilho : trilhoListOld) {
                if (!trilhoListNew.contains(trilhoListOldTrilho)) {
                    trilhoListOldTrilho.getAlojamentoList().remove(alojamento);
                    trilhoListOldTrilho = em.merge(trilhoListOldTrilho);
                }
            }
            for (Trilho trilhoListNewTrilho : trilhoListNew) {
                if (!trilhoListOld.contains(trilhoListNewTrilho)) {
                    trilhoListNewTrilho.getAlojamentoList().add(alojamento);
                    trilhoListNewTrilho = em.merge(trilhoListNewTrilho);
                }
            }
            for (ClassAlo classAloListOldClassAlo : classAloListOld) {
                if (!classAloListNew.contains(classAloListOldClassAlo)) {
                    classAloListOldClassAlo.setAlojamentoId(null);
                    classAloListOldClassAlo = em.merge(classAloListOldClassAlo);
                }
            }
            for (ClassAlo classAloListNewClassAlo : classAloListNew) {
                if (!classAloListOld.contains(classAloListNewClassAlo)) {
                    Alojamento oldAlojamentoIdOfClassAloListNewClassAlo = classAloListNewClassAlo.getAlojamentoId();
                    classAloListNewClassAlo.setAlojamentoId(alojamento);
                    classAloListNewClassAlo = em.merge(classAloListNewClassAlo);
                    if (oldAlojamentoIdOfClassAloListNewClassAlo != null && !oldAlojamentoIdOfClassAloListNewClassAlo.equals(alojamento)) {
                        oldAlojamentoIdOfClassAloListNewClassAlo.getClassAloList().remove(classAloListNewClassAlo);
                        oldAlojamentoIdOfClassAloListNewClassAlo = em.merge(oldAlojamentoIdOfClassAloListNewClassAlo);
                    }
                }
            }
            for (FotoAlojamento fotoAlojamentoListNewFotoAlojamento : fotoAlojamentoListNew) {
                if (!fotoAlojamentoListOld.contains(fotoAlojamentoListNewFotoAlojamento)) {
                    Alojamento oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento = fotoAlojamentoListNewFotoAlojamento.getAlojamentoid();
                    fotoAlojamentoListNewFotoAlojamento.setAlojamentoid(alojamento);
                    fotoAlojamentoListNewFotoAlojamento = em.merge(fotoAlojamentoListNewFotoAlojamento);
                    if (oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento != null && !oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento.equals(alojamento)) {
                        oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento.getFotoAlojamentoList().remove(fotoAlojamentoListNewFotoAlojamento);
                        oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento = em.merge(oldAlojamentoidOfFotoAlojamentoListNewFotoAlojamento);
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
                BigDecimal id = alojamento.getAlojamentoId();
                if (findAlojamento(id) == null) {
                    throw new NonexistentEntityException("The alojamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Alojamento alojamento;
            try {
                alojamento = em.getReference(Alojamento.class, id);
                alojamento.getAlojamentoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alojamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FotoAlojamento> fotoAlojamentoListOrphanCheck = alojamento.getFotoAlojamentoList();
            for (FotoAlojamento fotoAlojamentoListOrphanCheckFotoAlojamento : fotoAlojamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Alojamento (" + alojamento + ") cannot be destroyed since the FotoAlojamento " + fotoAlojamentoListOrphanCheckFotoAlojamento + " in its fotoAlojamentoList field has a non-nullable alojamentoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Trilho> trilhoList = alojamento.getTrilhoList();
            for (Trilho trilhoListTrilho : trilhoList) {
                trilhoListTrilho.getAlojamentoList().remove(alojamento);
                trilhoListTrilho = em.merge(trilhoListTrilho);
            }
            List<ClassAlo> classAloList = alojamento.getClassAloList();
            for (ClassAlo classAloListClassAlo : classAloList) {
                classAloListClassAlo.setAlojamentoId(null);
                classAloListClassAlo = em.merge(classAloListClassAlo);
            }
            em.remove(alojamento);
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

    public List<Alojamento> findAlojamentoEntities() {
        return findAlojamentoEntities(true, -1, -1);
    }

    public List<Alojamento> findAlojamentoEntities(int maxResults, int firstResult) {
        return findAlojamentoEntities(false, maxResults, firstResult);
    }

    private List<Alojamento> findAlojamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alojamento.class));
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

    public Alojamento findAlojamento(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alojamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlojamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alojamento> rt = cq.from(Alojamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
