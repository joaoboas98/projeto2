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
import DAL.Utilizador;
import DAL.Excursao;
import DAL.Grupo;
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
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (grupo.getExcursaoList() == null) {
            grupo.setExcursaoList(new ArrayList<Excursao>());
        }
        if (grupo.getExcursaoList1() == null) {
            grupo.setExcursaoList1(new ArrayList<Excursao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilizador utilizadorId = grupo.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                grupo.setUtilizadorId(utilizadorId);
            }
            List<Excursao> attachedExcursaoList = new ArrayList<Excursao>();
            for (Excursao excursaoListExcursaoToAttach : grupo.getExcursaoList()) {
                excursaoListExcursaoToAttach = em.getReference(excursaoListExcursaoToAttach.getClass(), excursaoListExcursaoToAttach.getExcursaoId());
                attachedExcursaoList.add(excursaoListExcursaoToAttach);
            }
            grupo.setExcursaoList(attachedExcursaoList);
            List<Excursao> attachedExcursaoList1 = new ArrayList<Excursao>();
            for (Excursao excursaoList1ExcursaoToAttach : grupo.getExcursaoList1()) {
                excursaoList1ExcursaoToAttach = em.getReference(excursaoList1ExcursaoToAttach.getClass(), excursaoList1ExcursaoToAttach.getExcursaoId());
                attachedExcursaoList1.add(excursaoList1ExcursaoToAttach);
            }
            grupo.setExcursaoList1(attachedExcursaoList1);
            em.persist(grupo);
            if (utilizadorId != null) {
                utilizadorId.getGrupoList().add(grupo);
                utilizadorId = em.merge(utilizadorId);
            }
            for (Excursao excursaoListExcursao : grupo.getExcursaoList()) {
                excursaoListExcursao.getGrupoList().add(grupo);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            for (Excursao excursaoList1Excursao : grupo.getExcursaoList1()) {
                Grupo oldGrupoIdOfExcursaoList1Excursao = excursaoList1Excursao.getGrupoId();
                excursaoList1Excursao.setGrupoId(grupo);
                excursaoList1Excursao = em.merge(excursaoList1Excursao);
                if (oldGrupoIdOfExcursaoList1Excursao != null) {
                    oldGrupoIdOfExcursaoList1Excursao.getExcursaoList1().remove(excursaoList1Excursao);
                    oldGrupoIdOfExcursaoList1Excursao = em.merge(oldGrupoIdOfExcursaoList1Excursao);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGrupo(grupo.getGrupoId()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupoId());
            Utilizador utilizadorIdOld = persistentGrupo.getUtilizadorId();
            Utilizador utilizadorIdNew = grupo.getUtilizadorId();
            List<Excursao> excursaoListOld = persistentGrupo.getExcursaoList();
            List<Excursao> excursaoListNew = grupo.getExcursaoList();
            List<Excursao> excursaoList1Old = persistentGrupo.getExcursaoList1();
            List<Excursao> excursaoList1New = grupo.getExcursaoList1();
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                grupo.setUtilizadorId(utilizadorIdNew);
            }
            List<Excursao> attachedExcursaoListNew = new ArrayList<Excursao>();
            for (Excursao excursaoListNewExcursaoToAttach : excursaoListNew) {
                excursaoListNewExcursaoToAttach = em.getReference(excursaoListNewExcursaoToAttach.getClass(), excursaoListNewExcursaoToAttach.getExcursaoId());
                attachedExcursaoListNew.add(excursaoListNewExcursaoToAttach);
            }
            excursaoListNew = attachedExcursaoListNew;
            grupo.setExcursaoList(excursaoListNew);
            List<Excursao> attachedExcursaoList1New = new ArrayList<Excursao>();
            for (Excursao excursaoList1NewExcursaoToAttach : excursaoList1New) {
                excursaoList1NewExcursaoToAttach = em.getReference(excursaoList1NewExcursaoToAttach.getClass(), excursaoList1NewExcursaoToAttach.getExcursaoId());
                attachedExcursaoList1New.add(excursaoList1NewExcursaoToAttach);
            }
            excursaoList1New = attachedExcursaoList1New;
            grupo.setExcursaoList1(excursaoList1New);
            grupo = em.merge(grupo);
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getGrupoList().remove(grupo);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getGrupoList().add(grupo);
                utilizadorIdNew = em.merge(utilizadorIdNew);
            }
            for (Excursao excursaoListOldExcursao : excursaoListOld) {
                if (!excursaoListNew.contains(excursaoListOldExcursao)) {
                    excursaoListOldExcursao.getGrupoList().remove(grupo);
                    excursaoListOldExcursao = em.merge(excursaoListOldExcursao);
                }
            }
            for (Excursao excursaoListNewExcursao : excursaoListNew) {
                if (!excursaoListOld.contains(excursaoListNewExcursao)) {
                    excursaoListNewExcursao.getGrupoList().add(grupo);
                    excursaoListNewExcursao = em.merge(excursaoListNewExcursao);
                }
            }
            for (Excursao excursaoList1OldExcursao : excursaoList1Old) {
                if (!excursaoList1New.contains(excursaoList1OldExcursao)) {
                    excursaoList1OldExcursao.setGrupoId(null);
                    excursaoList1OldExcursao = em.merge(excursaoList1OldExcursao);
                }
            }
            for (Excursao excursaoList1NewExcursao : excursaoList1New) {
                if (!excursaoList1Old.contains(excursaoList1NewExcursao)) {
                    Grupo oldGrupoIdOfExcursaoList1NewExcursao = excursaoList1NewExcursao.getGrupoId();
                    excursaoList1NewExcursao.setGrupoId(grupo);
                    excursaoList1NewExcursao = em.merge(excursaoList1NewExcursao);
                    if (oldGrupoIdOfExcursaoList1NewExcursao != null && !oldGrupoIdOfExcursaoList1NewExcursao.equals(grupo)) {
                        oldGrupoIdOfExcursaoList1NewExcursao.getExcursaoList1().remove(excursaoList1NewExcursao);
                        oldGrupoIdOfExcursaoList1NewExcursao = em.merge(oldGrupoIdOfExcursaoList1NewExcursao);
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
                BigDecimal id = grupo.getGrupoId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            Utilizador utilizadorId = grupo.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getGrupoList().remove(grupo);
                utilizadorId = em.merge(utilizadorId);
            }
            List<Excursao> excursaoList = grupo.getExcursaoList();
            for (Excursao excursaoListExcursao : excursaoList) {
                excursaoListExcursao.getGrupoList().remove(grupo);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            List<Excursao> excursaoList1 = grupo.getExcursaoList1();
            for (Excursao excursaoList1Excursao : excursaoList1) {
                excursaoList1Excursao.setGrupoId(null);
                excursaoList1Excursao = em.merge(excursaoList1Excursao);
            }
            em.remove(grupo);
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

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
