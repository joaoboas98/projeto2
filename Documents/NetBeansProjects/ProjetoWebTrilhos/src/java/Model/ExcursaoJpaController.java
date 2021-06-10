/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAL.Excursao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DAL.Grupo;
import DAL.Guia;
import DAL.Trilho;
import DAL.Utilizador;
import java.util.ArrayList;
import java.util.List;
import DAL.PagamentoExcursao;
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
public class ExcursaoJpaController implements Serializable {

    public ExcursaoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Excursao excursao) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (excursao.getGrupoList() == null) {
            excursao.setGrupoList(new ArrayList<Grupo>());
        }
        if (excursao.getGuiaList() == null) {
            excursao.setGuiaList(new ArrayList<Guia>());
        }
        if (excursao.getPagamentoExcursaoList() == null) {
            excursao.setPagamentoExcursaoList(new ArrayList<PagamentoExcursao>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Grupo grupoId = excursao.getGrupoId();
            if (grupoId != null) {
                grupoId = em.getReference(grupoId.getClass(), grupoId.getGrupoId());
                excursao.setGrupoId(grupoId);
            }
            Guia guiaId = excursao.getGuiaId();
            if (guiaId != null) {
                guiaId = em.getReference(guiaId.getClass(), guiaId.getGuiaId());
                excursao.setGuiaId(guiaId);
            }
            Trilho trilhoId = excursao.getTrilhoId();
            if (trilhoId != null) {
                trilhoId = em.getReference(trilhoId.getClass(), trilhoId.getTrilhoId());
                excursao.setTrilhoId(trilhoId);
            }
            Utilizador utilizadorId = excursao.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId = em.getReference(utilizadorId.getClass(), utilizadorId.getUtilizadorId());
                excursao.setUtilizadorId(utilizadorId);
            }
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : excursao.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getGrupoId());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            excursao.setGrupoList(attachedGrupoList);
            List<Guia> attachedGuiaList = new ArrayList<Guia>();
            for (Guia guiaListGuiaToAttach : excursao.getGuiaList()) {
                guiaListGuiaToAttach = em.getReference(guiaListGuiaToAttach.getClass(), guiaListGuiaToAttach.getGuiaId());
                attachedGuiaList.add(guiaListGuiaToAttach);
            }
            excursao.setGuiaList(attachedGuiaList);
            List<PagamentoExcursao> attachedPagamentoExcursaoList = new ArrayList<PagamentoExcursao>();
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursaoToAttach : excursao.getPagamentoExcursaoList()) {
                pagamentoExcursaoListPagamentoExcursaoToAttach = em.getReference(pagamentoExcursaoListPagamentoExcursaoToAttach.getClass(), pagamentoExcursaoListPagamentoExcursaoToAttach.getPagamentoExcursaoId());
                attachedPagamentoExcursaoList.add(pagamentoExcursaoListPagamentoExcursaoToAttach);
            }
            excursao.setPagamentoExcursaoList(attachedPagamentoExcursaoList);
            em.persist(excursao);
            if (grupoId != null) {
                grupoId.getExcursaoList().add(excursao);
                grupoId = em.merge(grupoId);
            }
            if (guiaId != null) {
                guiaId.getExcursaoList().add(excursao);
                guiaId = em.merge(guiaId);
            }
            if (trilhoId != null) {
                trilhoId.getExcursaoList().add(excursao);
                trilhoId = em.merge(trilhoId);
            }
            if (utilizadorId != null) {
                utilizadorId.getExcursaoList().add(excursao);
                utilizadorId = em.merge(utilizadorId);
            }
            for (Grupo grupoListGrupo : excursao.getGrupoList()) {
                grupoListGrupo.getExcursaoList().add(excursao);
                grupoListGrupo = em.merge(grupoListGrupo);
            }
            for (Guia guiaListGuia : excursao.getGuiaList()) {
                guiaListGuia.getExcursaoList().add(excursao);
                guiaListGuia = em.merge(guiaListGuia);
            }
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursao : excursao.getPagamentoExcursaoList()) {
                Excursao oldExcursaoIdOfPagamentoExcursaoListPagamentoExcursao = pagamentoExcursaoListPagamentoExcursao.getExcursaoId();
                pagamentoExcursaoListPagamentoExcursao.setExcursaoId(excursao);
                pagamentoExcursaoListPagamentoExcursao = em.merge(pagamentoExcursaoListPagamentoExcursao);
                if (oldExcursaoIdOfPagamentoExcursaoListPagamentoExcursao != null) {
                    oldExcursaoIdOfPagamentoExcursaoListPagamentoExcursao.getPagamentoExcursaoList().remove(pagamentoExcursaoListPagamentoExcursao);
                    oldExcursaoIdOfPagamentoExcursaoListPagamentoExcursao = em.merge(oldExcursaoIdOfPagamentoExcursaoListPagamentoExcursao);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findExcursao(excursao.getExcursaoId()) != null) {
                throw new PreexistingEntityException("Excursao " + excursao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Excursao excursao) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Excursao persistentExcursao = em.find(Excursao.class, excursao.getExcursaoId());
            Grupo grupoIdOld = persistentExcursao.getGrupoId();
            Grupo grupoIdNew = excursao.getGrupoId();
            Guia guiaIdOld = persistentExcursao.getGuiaId();
            Guia guiaIdNew = excursao.getGuiaId();
            Trilho trilhoIdOld = persistentExcursao.getTrilhoId();
            Trilho trilhoIdNew = excursao.getTrilhoId();
            Utilizador utilizadorIdOld = persistentExcursao.getUtilizadorId();
            Utilizador utilizadorIdNew = excursao.getUtilizadorId();
            List<Grupo> grupoListOld = persistentExcursao.getGrupoList();
            List<Grupo> grupoListNew = excursao.getGrupoList();
            List<Guia> guiaListOld = persistentExcursao.getGuiaList();
            List<Guia> guiaListNew = excursao.getGuiaList();
            List<PagamentoExcursao> pagamentoExcursaoListOld = persistentExcursao.getPagamentoExcursaoList();
            List<PagamentoExcursao> pagamentoExcursaoListNew = excursao.getPagamentoExcursaoList();
            if (grupoIdNew != null) {
                grupoIdNew = em.getReference(grupoIdNew.getClass(), grupoIdNew.getGrupoId());
                excursao.setGrupoId(grupoIdNew);
            }
            if (guiaIdNew != null) {
                guiaIdNew = em.getReference(guiaIdNew.getClass(), guiaIdNew.getGuiaId());
                excursao.setGuiaId(guiaIdNew);
            }
            if (trilhoIdNew != null) {
                trilhoIdNew = em.getReference(trilhoIdNew.getClass(), trilhoIdNew.getTrilhoId());
                excursao.setTrilhoId(trilhoIdNew);
            }
            if (utilizadorIdNew != null) {
                utilizadorIdNew = em.getReference(utilizadorIdNew.getClass(), utilizadorIdNew.getUtilizadorId());
                excursao.setUtilizadorId(utilizadorIdNew);
            }
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getGrupoId());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            excursao.setGrupoList(grupoListNew);
            List<Guia> attachedGuiaListNew = new ArrayList<Guia>();
            for (Guia guiaListNewGuiaToAttach : guiaListNew) {
                guiaListNewGuiaToAttach = em.getReference(guiaListNewGuiaToAttach.getClass(), guiaListNewGuiaToAttach.getGuiaId());
                attachedGuiaListNew.add(guiaListNewGuiaToAttach);
            }
            guiaListNew = attachedGuiaListNew;
            excursao.setGuiaList(guiaListNew);
            List<PagamentoExcursao> attachedPagamentoExcursaoListNew = new ArrayList<PagamentoExcursao>();
            for (PagamentoExcursao pagamentoExcursaoListNewPagamentoExcursaoToAttach : pagamentoExcursaoListNew) {
                pagamentoExcursaoListNewPagamentoExcursaoToAttach = em.getReference(pagamentoExcursaoListNewPagamentoExcursaoToAttach.getClass(), pagamentoExcursaoListNewPagamentoExcursaoToAttach.getPagamentoExcursaoId());
                attachedPagamentoExcursaoListNew.add(pagamentoExcursaoListNewPagamentoExcursaoToAttach);
            }
            pagamentoExcursaoListNew = attachedPagamentoExcursaoListNew;
            excursao.setPagamentoExcursaoList(pagamentoExcursaoListNew);
            excursao = em.merge(excursao);
            if (grupoIdOld != null && !grupoIdOld.equals(grupoIdNew)) {
                grupoIdOld.getExcursaoList().remove(excursao);
                grupoIdOld = em.merge(grupoIdOld);
            }
            if (grupoIdNew != null && !grupoIdNew.equals(grupoIdOld)) {
                grupoIdNew.getExcursaoList().add(excursao);
                grupoIdNew = em.merge(grupoIdNew);
            }
            if (guiaIdOld != null && !guiaIdOld.equals(guiaIdNew)) {
                guiaIdOld.getExcursaoList().remove(excursao);
                guiaIdOld = em.merge(guiaIdOld);
            }
            if (guiaIdNew != null && !guiaIdNew.equals(guiaIdOld)) {
                guiaIdNew.getExcursaoList().add(excursao);
                guiaIdNew = em.merge(guiaIdNew);
            }
            if (trilhoIdOld != null && !trilhoIdOld.equals(trilhoIdNew)) {
                trilhoIdOld.getExcursaoList().remove(excursao);
                trilhoIdOld = em.merge(trilhoIdOld);
            }
            if (trilhoIdNew != null && !trilhoIdNew.equals(trilhoIdOld)) {
                trilhoIdNew.getExcursaoList().add(excursao);
                trilhoIdNew = em.merge(trilhoIdNew);
            }
            if (utilizadorIdOld != null && !utilizadorIdOld.equals(utilizadorIdNew)) {
                utilizadorIdOld.getExcursaoList().remove(excursao);
                utilizadorIdOld = em.merge(utilizadorIdOld);
            }
            if (utilizadorIdNew != null && !utilizadorIdNew.equals(utilizadorIdOld)) {
                utilizadorIdNew.getExcursaoList().add(excursao);
                utilizadorIdNew = em.merge(utilizadorIdNew);
            }
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    grupoListOldGrupo.getExcursaoList().remove(excursao);
                    grupoListOldGrupo = em.merge(grupoListOldGrupo);
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    grupoListNewGrupo.getExcursaoList().add(excursao);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                }
            }
            for (Guia guiaListOldGuia : guiaListOld) {
                if (!guiaListNew.contains(guiaListOldGuia)) {
                    guiaListOldGuia.getExcursaoList().remove(excursao);
                    guiaListOldGuia = em.merge(guiaListOldGuia);
                }
            }
            for (Guia guiaListNewGuia : guiaListNew) {
                if (!guiaListOld.contains(guiaListNewGuia)) {
                    guiaListNewGuia.getExcursaoList().add(excursao);
                    guiaListNewGuia = em.merge(guiaListNewGuia);
                }
            }
            for (PagamentoExcursao pagamentoExcursaoListOldPagamentoExcursao : pagamentoExcursaoListOld) {
                if (!pagamentoExcursaoListNew.contains(pagamentoExcursaoListOldPagamentoExcursao)) {
                    pagamentoExcursaoListOldPagamentoExcursao.setExcursaoId(null);
                    pagamentoExcursaoListOldPagamentoExcursao = em.merge(pagamentoExcursaoListOldPagamentoExcursao);
                }
            }
            for (PagamentoExcursao pagamentoExcursaoListNewPagamentoExcursao : pagamentoExcursaoListNew) {
                if (!pagamentoExcursaoListOld.contains(pagamentoExcursaoListNewPagamentoExcursao)) {
                    Excursao oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao = pagamentoExcursaoListNewPagamentoExcursao.getExcursaoId();
                    pagamentoExcursaoListNewPagamentoExcursao.setExcursaoId(excursao);
                    pagamentoExcursaoListNewPagamentoExcursao = em.merge(pagamentoExcursaoListNewPagamentoExcursao);
                    if (oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao != null && !oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao.equals(excursao)) {
                        oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao.getPagamentoExcursaoList().remove(pagamentoExcursaoListNewPagamentoExcursao);
                        oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao = em.merge(oldExcursaoIdOfPagamentoExcursaoListNewPagamentoExcursao);
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
                BigDecimal id = excursao.getExcursaoId();
                if (findExcursao(id) == null) {
                    throw new NonexistentEntityException("The excursao with id " + id + " no longer exists.");
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
            Excursao excursao;
            try {
                excursao = em.getReference(Excursao.class, id);
                excursao.getExcursaoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The excursao with id " + id + " no longer exists.", enfe);
            }
            Grupo grupoId = excursao.getGrupoId();
            if (grupoId != null) {
                grupoId.getExcursaoList().remove(excursao);
                grupoId = em.merge(grupoId);
            }
            Guia guiaId = excursao.getGuiaId();
            if (guiaId != null) {
                guiaId.getExcursaoList().remove(excursao);
                guiaId = em.merge(guiaId);
            }
            Trilho trilhoId = excursao.getTrilhoId();
            if (trilhoId != null) {
                trilhoId.getExcursaoList().remove(excursao);
                trilhoId = em.merge(trilhoId);
            }
            Utilizador utilizadorId = excursao.getUtilizadorId();
            if (utilizadorId != null) {
                utilizadorId.getExcursaoList().remove(excursao);
                utilizadorId = em.merge(utilizadorId);
            }
            List<Grupo> grupoList = excursao.getGrupoList();
            for (Grupo grupoListGrupo : grupoList) {
                grupoListGrupo.getExcursaoList().remove(excursao);
                grupoListGrupo = em.merge(grupoListGrupo);
            }
            List<Guia> guiaList = excursao.getGuiaList();
            for (Guia guiaListGuia : guiaList) {
                guiaListGuia.getExcursaoList().remove(excursao);
                guiaListGuia = em.merge(guiaListGuia);
            }
            List<PagamentoExcursao> pagamentoExcursaoList = excursao.getPagamentoExcursaoList();
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursao : pagamentoExcursaoList) {
                pagamentoExcursaoListPagamentoExcursao.setExcursaoId(null);
                pagamentoExcursaoListPagamentoExcursao = em.merge(pagamentoExcursaoListPagamentoExcursao);
            }
            em.remove(excursao);
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

    public List<Excursao> findExcursaoEntities() {
        return findExcursaoEntities(true, -1, -1);
    }

    public List<Excursao> findExcursaoEntities(int maxResults, int firstResult) {
        return findExcursaoEntities(false, maxResults, firstResult);
    }

    private List<Excursao> findExcursaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Excursao.class));
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

    public Excursao findExcursao(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Excursao.class, id);
        } finally {
            em.close();
        }
    }

    public int getExcursaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Excursao> rt = cq.from(Excursao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
