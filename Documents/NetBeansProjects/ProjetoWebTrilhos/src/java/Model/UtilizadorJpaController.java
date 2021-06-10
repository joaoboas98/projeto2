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
import DAL.FotoPonto;
import java.util.ArrayList;
import java.util.List;
import DAL.Grupo;
import DAL.ClassTrilho;
import DAL.ClassAlo;
import DAL.Excursao;
import DAL.PagamentoExcursao;
import DAL.FotoTrilho;
import DAL.Utilizador;
import Model.exceptions.NonexistentEntityException;
import Model.exceptions.PreexistingEntityException;
import Model.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author Utilizador
 */
public class UtilizadorJpaController implements Serializable {
  private static final String PERSISTENCE_UNIT_NAME = "ProjetoWebTrilhosPU";
    private static EntityManagerFactory factory;
    public UtilizadorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Utilizador utilizador) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (utilizador.getFotoPontoList() == null) {
            utilizador.setFotoPontoList(new ArrayList<FotoPonto>());
        }
        if (utilizador.getGrupoList() == null) {
            utilizador.setGrupoList(new ArrayList<Grupo>());
        }
        if (utilizador.getClassTrilhoList() == null) {
            utilizador.setClassTrilhoList(new ArrayList<ClassTrilho>());
        }
        if (utilizador.getClassAloList() == null) {
            utilizador.setClassAloList(new ArrayList<ClassAlo>());
        }
        if (utilizador.getExcursaoList() == null) {
            utilizador.setExcursaoList(new ArrayList<Excursao>());
        }
        if (utilizador.getPagamentoExcursaoList() == null) {
            utilizador.setPagamentoExcursaoList(new ArrayList<PagamentoExcursao>());
        }
        if (utilizador.getFotoTrilhoList() == null) {
            utilizador.setFotoTrilhoList(new ArrayList<FotoTrilho>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<FotoPonto> attachedFotoPontoList = new ArrayList<FotoPonto>();
            for (FotoPonto fotoPontoListFotoPontoToAttach : utilizador.getFotoPontoList()) {
                fotoPontoListFotoPontoToAttach = em.getReference(fotoPontoListFotoPontoToAttach.getClass(), fotoPontoListFotoPontoToAttach.getFotoPontoId());
                attachedFotoPontoList.add(fotoPontoListFotoPontoToAttach);
            }
            utilizador.setFotoPontoList(attachedFotoPontoList);
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : utilizador.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getGrupoId());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            utilizador.setGrupoList(attachedGrupoList);
            List<ClassTrilho> attachedClassTrilhoList = new ArrayList<ClassTrilho>();
            for (ClassTrilho classTrilhoListClassTrilhoToAttach : utilizador.getClassTrilhoList()) {
                classTrilhoListClassTrilhoToAttach = em.getReference(classTrilhoListClassTrilhoToAttach.getClass(), classTrilhoListClassTrilhoToAttach.getClassTrilhoId());
                attachedClassTrilhoList.add(classTrilhoListClassTrilhoToAttach);
            }
            utilizador.setClassTrilhoList(attachedClassTrilhoList);
            List<ClassAlo> attachedClassAloList = new ArrayList<ClassAlo>();
            for (ClassAlo classAloListClassAloToAttach : utilizador.getClassAloList()) {
                classAloListClassAloToAttach = em.getReference(classAloListClassAloToAttach.getClass(), classAloListClassAloToAttach.getClassAloId());
                attachedClassAloList.add(classAloListClassAloToAttach);
            }
            utilizador.setClassAloList(attachedClassAloList);
            List<Excursao> attachedExcursaoList = new ArrayList<Excursao>();
            for (Excursao excursaoListExcursaoToAttach : utilizador.getExcursaoList()) {
                excursaoListExcursaoToAttach = em.getReference(excursaoListExcursaoToAttach.getClass(), excursaoListExcursaoToAttach.getExcursaoId());
                attachedExcursaoList.add(excursaoListExcursaoToAttach);
            }
            utilizador.setExcursaoList(attachedExcursaoList);
            List<PagamentoExcursao> attachedPagamentoExcursaoList = new ArrayList<PagamentoExcursao>();
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursaoToAttach : utilizador.getPagamentoExcursaoList()) {
                pagamentoExcursaoListPagamentoExcursaoToAttach = em.getReference(pagamentoExcursaoListPagamentoExcursaoToAttach.getClass(), pagamentoExcursaoListPagamentoExcursaoToAttach.getPagamentoExcursaoId());
                attachedPagamentoExcursaoList.add(pagamentoExcursaoListPagamentoExcursaoToAttach);
            }
            utilizador.setPagamentoExcursaoList(attachedPagamentoExcursaoList);
            List<FotoTrilho> attachedFotoTrilhoList = new ArrayList<FotoTrilho>();
            for (FotoTrilho fotoTrilhoListFotoTrilhoToAttach : utilizador.getFotoTrilhoList()) {
                fotoTrilhoListFotoTrilhoToAttach = em.getReference(fotoTrilhoListFotoTrilhoToAttach.getClass(), fotoTrilhoListFotoTrilhoToAttach.getFotoTrilhoId());
                attachedFotoTrilhoList.add(fotoTrilhoListFotoTrilhoToAttach);
            }
            utilizador.setFotoTrilhoList(attachedFotoTrilhoList);
            em.persist(utilizador);
            for (FotoPonto fotoPontoListFotoPonto : utilizador.getFotoPontoList()) {
                Utilizador oldUtilizadorIdOfFotoPontoListFotoPonto = fotoPontoListFotoPonto.getUtilizadorId();
                fotoPontoListFotoPonto.setUtilizadorId(utilizador);
                fotoPontoListFotoPonto = em.merge(fotoPontoListFotoPonto);
                if (oldUtilizadorIdOfFotoPontoListFotoPonto != null) {
                    oldUtilizadorIdOfFotoPontoListFotoPonto.getFotoPontoList().remove(fotoPontoListFotoPonto);
                    oldUtilizadorIdOfFotoPontoListFotoPonto = em.merge(oldUtilizadorIdOfFotoPontoListFotoPonto);
                }
            }
            for (Grupo grupoListGrupo : utilizador.getGrupoList()) {
                Utilizador oldUtilizadorIdOfGrupoListGrupo = grupoListGrupo.getUtilizadorId();
                grupoListGrupo.setUtilizadorId(utilizador);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldUtilizadorIdOfGrupoListGrupo != null) {
                    oldUtilizadorIdOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldUtilizadorIdOfGrupoListGrupo = em.merge(oldUtilizadorIdOfGrupoListGrupo);
                }
            }
            for (ClassTrilho classTrilhoListClassTrilho : utilizador.getClassTrilhoList()) {
                Utilizador oldUtilizadorIdOfClassTrilhoListClassTrilho = classTrilhoListClassTrilho.getUtilizadorId();
                classTrilhoListClassTrilho.setUtilizadorId(utilizador);
                classTrilhoListClassTrilho = em.merge(classTrilhoListClassTrilho);
                if (oldUtilizadorIdOfClassTrilhoListClassTrilho != null) {
                    oldUtilizadorIdOfClassTrilhoListClassTrilho.getClassTrilhoList().remove(classTrilhoListClassTrilho);
                    oldUtilizadorIdOfClassTrilhoListClassTrilho = em.merge(oldUtilizadorIdOfClassTrilhoListClassTrilho);
                }
            }
            for (ClassAlo classAloListClassAlo : utilizador.getClassAloList()) {
                Utilizador oldUtilizadorIdOfClassAloListClassAlo = classAloListClassAlo.getUtilizadorId();
                classAloListClassAlo.setUtilizadorId(utilizador);
                classAloListClassAlo = em.merge(classAloListClassAlo);
                if (oldUtilizadorIdOfClassAloListClassAlo != null) {
                    oldUtilizadorIdOfClassAloListClassAlo.getClassAloList().remove(classAloListClassAlo);
                    oldUtilizadorIdOfClassAloListClassAlo = em.merge(oldUtilizadorIdOfClassAloListClassAlo);
                }
            }
            for (Excursao excursaoListExcursao : utilizador.getExcursaoList()) {
                Utilizador oldUtilizadorIdOfExcursaoListExcursao = excursaoListExcursao.getUtilizadorId();
                excursaoListExcursao.setUtilizadorId(utilizador);
                excursaoListExcursao = em.merge(excursaoListExcursao);
                if (oldUtilizadorIdOfExcursaoListExcursao != null) {
                    oldUtilizadorIdOfExcursaoListExcursao.getExcursaoList().remove(excursaoListExcursao);
                    oldUtilizadorIdOfExcursaoListExcursao = em.merge(oldUtilizadorIdOfExcursaoListExcursao);
                }
            }
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursao : utilizador.getPagamentoExcursaoList()) {
                Utilizador oldUtilizadorIdOfPagamentoExcursaoListPagamentoExcursao = pagamentoExcursaoListPagamentoExcursao.getUtilizadorId();
                pagamentoExcursaoListPagamentoExcursao.setUtilizadorId(utilizador);
                pagamentoExcursaoListPagamentoExcursao = em.merge(pagamentoExcursaoListPagamentoExcursao);
                if (oldUtilizadorIdOfPagamentoExcursaoListPagamentoExcursao != null) {
                    oldUtilizadorIdOfPagamentoExcursaoListPagamentoExcursao.getPagamentoExcursaoList().remove(pagamentoExcursaoListPagamentoExcursao);
                    oldUtilizadorIdOfPagamentoExcursaoListPagamentoExcursao = em.merge(oldUtilizadorIdOfPagamentoExcursaoListPagamentoExcursao);
                }
            }
            for (FotoTrilho fotoTrilhoListFotoTrilho : utilizador.getFotoTrilhoList()) {
                Utilizador oldUtilizadorIdOfFotoTrilhoListFotoTrilho = fotoTrilhoListFotoTrilho.getUtilizadorId();
                fotoTrilhoListFotoTrilho.setUtilizadorId(utilizador);
                fotoTrilhoListFotoTrilho = em.merge(fotoTrilhoListFotoTrilho);
                if (oldUtilizadorIdOfFotoTrilhoListFotoTrilho != null) {
                    oldUtilizadorIdOfFotoTrilhoListFotoTrilho.getFotoTrilhoList().remove(fotoTrilhoListFotoTrilho);
                    oldUtilizadorIdOfFotoTrilhoListFotoTrilho = em.merge(oldUtilizadorIdOfFotoTrilhoListFotoTrilho);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUtilizador(utilizador.getUtilizadorId()) != null) {
                throw new PreexistingEntityException("Utilizador " + utilizador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Utilizador utilizador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Utilizador persistentUtilizador = em.find(Utilizador.class, utilizador.getUtilizadorId());
            List<FotoPonto> fotoPontoListOld = persistentUtilizador.getFotoPontoList();
            List<FotoPonto> fotoPontoListNew = utilizador.getFotoPontoList();
            List<Grupo> grupoListOld = persistentUtilizador.getGrupoList();
            List<Grupo> grupoListNew = utilizador.getGrupoList();
            List<ClassTrilho> classTrilhoListOld = persistentUtilizador.getClassTrilhoList();
            List<ClassTrilho> classTrilhoListNew = utilizador.getClassTrilhoList();
            List<ClassAlo> classAloListOld = persistentUtilizador.getClassAloList();
            List<ClassAlo> classAloListNew = utilizador.getClassAloList();
            List<Excursao> excursaoListOld = persistentUtilizador.getExcursaoList();
            List<Excursao> excursaoListNew = utilizador.getExcursaoList();
            List<PagamentoExcursao> pagamentoExcursaoListOld = persistentUtilizador.getPagamentoExcursaoList();
            List<PagamentoExcursao> pagamentoExcursaoListNew = utilizador.getPagamentoExcursaoList();
            List<FotoTrilho> fotoTrilhoListOld = persistentUtilizador.getFotoTrilhoList();
            List<FotoTrilho> fotoTrilhoListNew = utilizador.getFotoTrilhoList();
            List<FotoPonto> attachedFotoPontoListNew = new ArrayList<FotoPonto>();
            for (FotoPonto fotoPontoListNewFotoPontoToAttach : fotoPontoListNew) {
                fotoPontoListNewFotoPontoToAttach = em.getReference(fotoPontoListNewFotoPontoToAttach.getClass(), fotoPontoListNewFotoPontoToAttach.getFotoPontoId());
                attachedFotoPontoListNew.add(fotoPontoListNewFotoPontoToAttach);
            }
            fotoPontoListNew = attachedFotoPontoListNew;
            utilizador.setFotoPontoList(fotoPontoListNew);
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getGrupoId());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            utilizador.setGrupoList(grupoListNew);
            List<ClassTrilho> attachedClassTrilhoListNew = new ArrayList<ClassTrilho>();
            for (ClassTrilho classTrilhoListNewClassTrilhoToAttach : classTrilhoListNew) {
                classTrilhoListNewClassTrilhoToAttach = em.getReference(classTrilhoListNewClassTrilhoToAttach.getClass(), classTrilhoListNewClassTrilhoToAttach.getClassTrilhoId());
                attachedClassTrilhoListNew.add(classTrilhoListNewClassTrilhoToAttach);
            }
            classTrilhoListNew = attachedClassTrilhoListNew;
            utilizador.setClassTrilhoList(classTrilhoListNew);
            List<ClassAlo> attachedClassAloListNew = new ArrayList<ClassAlo>();
            for (ClassAlo classAloListNewClassAloToAttach : classAloListNew) {
                classAloListNewClassAloToAttach = em.getReference(classAloListNewClassAloToAttach.getClass(), classAloListNewClassAloToAttach.getClassAloId());
                attachedClassAloListNew.add(classAloListNewClassAloToAttach);
            }
            classAloListNew = attachedClassAloListNew;
            utilizador.setClassAloList(classAloListNew);
            List<Excursao> attachedExcursaoListNew = new ArrayList<Excursao>();
            for (Excursao excursaoListNewExcursaoToAttach : excursaoListNew) {
                excursaoListNewExcursaoToAttach = em.getReference(excursaoListNewExcursaoToAttach.getClass(), excursaoListNewExcursaoToAttach.getExcursaoId());
                attachedExcursaoListNew.add(excursaoListNewExcursaoToAttach);
            }
            excursaoListNew = attachedExcursaoListNew;
            utilizador.setExcursaoList(excursaoListNew);
            List<PagamentoExcursao> attachedPagamentoExcursaoListNew = new ArrayList<PagamentoExcursao>();
            for (PagamentoExcursao pagamentoExcursaoListNewPagamentoExcursaoToAttach : pagamentoExcursaoListNew) {
                pagamentoExcursaoListNewPagamentoExcursaoToAttach = em.getReference(pagamentoExcursaoListNewPagamentoExcursaoToAttach.getClass(), pagamentoExcursaoListNewPagamentoExcursaoToAttach.getPagamentoExcursaoId());
                attachedPagamentoExcursaoListNew.add(pagamentoExcursaoListNewPagamentoExcursaoToAttach);
            }
            pagamentoExcursaoListNew = attachedPagamentoExcursaoListNew;
            utilizador.setPagamentoExcursaoList(pagamentoExcursaoListNew);
            List<FotoTrilho> attachedFotoTrilhoListNew = new ArrayList<FotoTrilho>();
            for (FotoTrilho fotoTrilhoListNewFotoTrilhoToAttach : fotoTrilhoListNew) {
                fotoTrilhoListNewFotoTrilhoToAttach = em.getReference(fotoTrilhoListNewFotoTrilhoToAttach.getClass(), fotoTrilhoListNewFotoTrilhoToAttach.getFotoTrilhoId());
                attachedFotoTrilhoListNew.add(fotoTrilhoListNewFotoTrilhoToAttach);
            }
            fotoTrilhoListNew = attachedFotoTrilhoListNew;
            utilizador.setFotoTrilhoList(fotoTrilhoListNew);
            utilizador = em.merge(utilizador);
            for (FotoPonto fotoPontoListOldFotoPonto : fotoPontoListOld) {
                if (!fotoPontoListNew.contains(fotoPontoListOldFotoPonto)) {
                    fotoPontoListOldFotoPonto.setUtilizadorId(null);
                    fotoPontoListOldFotoPonto = em.merge(fotoPontoListOldFotoPonto);
                }
            }
            for (FotoPonto fotoPontoListNewFotoPonto : fotoPontoListNew) {
                if (!fotoPontoListOld.contains(fotoPontoListNewFotoPonto)) {
                    Utilizador oldUtilizadorIdOfFotoPontoListNewFotoPonto = fotoPontoListNewFotoPonto.getUtilizadorId();
                    fotoPontoListNewFotoPonto.setUtilizadorId(utilizador);
                    fotoPontoListNewFotoPonto = em.merge(fotoPontoListNewFotoPonto);
                    if (oldUtilizadorIdOfFotoPontoListNewFotoPonto != null && !oldUtilizadorIdOfFotoPontoListNewFotoPonto.equals(utilizador)) {
                        oldUtilizadorIdOfFotoPontoListNewFotoPonto.getFotoPontoList().remove(fotoPontoListNewFotoPonto);
                        oldUtilizadorIdOfFotoPontoListNewFotoPonto = em.merge(oldUtilizadorIdOfFotoPontoListNewFotoPonto);
                    }
                }
            }
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    grupoListOldGrupo.setUtilizadorId(null);
                    grupoListOldGrupo = em.merge(grupoListOldGrupo);
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Utilizador oldUtilizadorIdOfGrupoListNewGrupo = grupoListNewGrupo.getUtilizadorId();
                    grupoListNewGrupo.setUtilizadorId(utilizador);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldUtilizadorIdOfGrupoListNewGrupo != null && !oldUtilizadorIdOfGrupoListNewGrupo.equals(utilizador)) {
                        oldUtilizadorIdOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldUtilizadorIdOfGrupoListNewGrupo = em.merge(oldUtilizadorIdOfGrupoListNewGrupo);
                    }
                }
            }
            for (ClassTrilho classTrilhoListOldClassTrilho : classTrilhoListOld) {
                if (!classTrilhoListNew.contains(classTrilhoListOldClassTrilho)) {
                    classTrilhoListOldClassTrilho.setUtilizadorId(null);
                    classTrilhoListOldClassTrilho = em.merge(classTrilhoListOldClassTrilho);
                }
            }
            for (ClassTrilho classTrilhoListNewClassTrilho : classTrilhoListNew) {
                if (!classTrilhoListOld.contains(classTrilhoListNewClassTrilho)) {
                    Utilizador oldUtilizadorIdOfClassTrilhoListNewClassTrilho = classTrilhoListNewClassTrilho.getUtilizadorId();
                    classTrilhoListNewClassTrilho.setUtilizadorId(utilizador);
                    classTrilhoListNewClassTrilho = em.merge(classTrilhoListNewClassTrilho);
                    if (oldUtilizadorIdOfClassTrilhoListNewClassTrilho != null && !oldUtilizadorIdOfClassTrilhoListNewClassTrilho.equals(utilizador)) {
                        oldUtilizadorIdOfClassTrilhoListNewClassTrilho.getClassTrilhoList().remove(classTrilhoListNewClassTrilho);
                        oldUtilizadorIdOfClassTrilhoListNewClassTrilho = em.merge(oldUtilizadorIdOfClassTrilhoListNewClassTrilho);
                    }
                }
            }
            for (ClassAlo classAloListOldClassAlo : classAloListOld) {
                if (!classAloListNew.contains(classAloListOldClassAlo)) {
                    classAloListOldClassAlo.setUtilizadorId(null);
                    classAloListOldClassAlo = em.merge(classAloListOldClassAlo);
                }
            }
            for (ClassAlo classAloListNewClassAlo : classAloListNew) {
                if (!classAloListOld.contains(classAloListNewClassAlo)) {
                    Utilizador oldUtilizadorIdOfClassAloListNewClassAlo = classAloListNewClassAlo.getUtilizadorId();
                    classAloListNewClassAlo.setUtilizadorId(utilizador);
                    classAloListNewClassAlo = em.merge(classAloListNewClassAlo);
                    if (oldUtilizadorIdOfClassAloListNewClassAlo != null && !oldUtilizadorIdOfClassAloListNewClassAlo.equals(utilizador)) {
                        oldUtilizadorIdOfClassAloListNewClassAlo.getClassAloList().remove(classAloListNewClassAlo);
                        oldUtilizadorIdOfClassAloListNewClassAlo = em.merge(oldUtilizadorIdOfClassAloListNewClassAlo);
                    }
                }
            }
            for (Excursao excursaoListOldExcursao : excursaoListOld) {
                if (!excursaoListNew.contains(excursaoListOldExcursao)) {
                    excursaoListOldExcursao.setUtilizadorId(null);
                    excursaoListOldExcursao = em.merge(excursaoListOldExcursao);
                }
            }
            for (Excursao excursaoListNewExcursao : excursaoListNew) {
                if (!excursaoListOld.contains(excursaoListNewExcursao)) {
                    Utilizador oldUtilizadorIdOfExcursaoListNewExcursao = excursaoListNewExcursao.getUtilizadorId();
                    excursaoListNewExcursao.setUtilizadorId(utilizador);
                    excursaoListNewExcursao = em.merge(excursaoListNewExcursao);
                    if (oldUtilizadorIdOfExcursaoListNewExcursao != null && !oldUtilizadorIdOfExcursaoListNewExcursao.equals(utilizador)) {
                        oldUtilizadorIdOfExcursaoListNewExcursao.getExcursaoList().remove(excursaoListNewExcursao);
                        oldUtilizadorIdOfExcursaoListNewExcursao = em.merge(oldUtilizadorIdOfExcursaoListNewExcursao);
                    }
                }
            }
            for (PagamentoExcursao pagamentoExcursaoListOldPagamentoExcursao : pagamentoExcursaoListOld) {
                if (!pagamentoExcursaoListNew.contains(pagamentoExcursaoListOldPagamentoExcursao)) {
                    pagamentoExcursaoListOldPagamentoExcursao.setUtilizadorId(null);
                    pagamentoExcursaoListOldPagamentoExcursao = em.merge(pagamentoExcursaoListOldPagamentoExcursao);
                }
            }
            for (PagamentoExcursao pagamentoExcursaoListNewPagamentoExcursao : pagamentoExcursaoListNew) {
                if (!pagamentoExcursaoListOld.contains(pagamentoExcursaoListNewPagamentoExcursao)) {
                    Utilizador oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao = pagamentoExcursaoListNewPagamentoExcursao.getUtilizadorId();
                    pagamentoExcursaoListNewPagamentoExcursao.setUtilizadorId(utilizador);
                    pagamentoExcursaoListNewPagamentoExcursao = em.merge(pagamentoExcursaoListNewPagamentoExcursao);
                    if (oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao != null && !oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao.equals(utilizador)) {
                        oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao.getPagamentoExcursaoList().remove(pagamentoExcursaoListNewPagamentoExcursao);
                        oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao = em.merge(oldUtilizadorIdOfPagamentoExcursaoListNewPagamentoExcursao);
                    }
                }
            }
            for (FotoTrilho fotoTrilhoListOldFotoTrilho : fotoTrilhoListOld) {
                if (!fotoTrilhoListNew.contains(fotoTrilhoListOldFotoTrilho)) {
                    fotoTrilhoListOldFotoTrilho.setUtilizadorId(null);
                    fotoTrilhoListOldFotoTrilho = em.merge(fotoTrilhoListOldFotoTrilho);
                }
            }
            for (FotoTrilho fotoTrilhoListNewFotoTrilho : fotoTrilhoListNew) {
                if (!fotoTrilhoListOld.contains(fotoTrilhoListNewFotoTrilho)) {
                    Utilizador oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho = fotoTrilhoListNewFotoTrilho.getUtilizadorId();
                    fotoTrilhoListNewFotoTrilho.setUtilizadorId(utilizador);
                    fotoTrilhoListNewFotoTrilho = em.merge(fotoTrilhoListNewFotoTrilho);
                    if (oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho != null && !oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho.equals(utilizador)) {
                        oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho.getFotoTrilhoList().remove(fotoTrilhoListNewFotoTrilho);
                        oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho = em.merge(oldUtilizadorIdOfFotoTrilhoListNewFotoTrilho);
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
                BigDecimal id = utilizador.getUtilizadorId();
                if (findUtilizador(id) == null) {
                    throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.");
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
            Utilizador utilizador;
            try {
                utilizador = em.getReference(Utilizador.class, id);
                utilizador.getUtilizadorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utilizador with id " + id + " no longer exists.", enfe);
            }
            List<FotoPonto> fotoPontoList = utilizador.getFotoPontoList();
            for (FotoPonto fotoPontoListFotoPonto : fotoPontoList) {
                fotoPontoListFotoPonto.setUtilizadorId(null);
                fotoPontoListFotoPonto = em.merge(fotoPontoListFotoPonto);
            }
            List<Grupo> grupoList = utilizador.getGrupoList();
            for (Grupo grupoListGrupo : grupoList) {
                grupoListGrupo.setUtilizadorId(null);
                grupoListGrupo = em.merge(grupoListGrupo);
            }
            List<ClassTrilho> classTrilhoList = utilizador.getClassTrilhoList();
            for (ClassTrilho classTrilhoListClassTrilho : classTrilhoList) {
                classTrilhoListClassTrilho.setUtilizadorId(null);
                classTrilhoListClassTrilho = em.merge(classTrilhoListClassTrilho);
            }
            List<ClassAlo> classAloList = utilizador.getClassAloList();
            for (ClassAlo classAloListClassAlo : classAloList) {
                classAloListClassAlo.setUtilizadorId(null);
                classAloListClassAlo = em.merge(classAloListClassAlo);
            }
            List<Excursao> excursaoList = utilizador.getExcursaoList();
            for (Excursao excursaoListExcursao : excursaoList) {
                excursaoListExcursao.setUtilizadorId(null);
                excursaoListExcursao = em.merge(excursaoListExcursao);
            }
            List<PagamentoExcursao> pagamentoExcursaoList = utilizador.getPagamentoExcursaoList();
            for (PagamentoExcursao pagamentoExcursaoListPagamentoExcursao : pagamentoExcursaoList) {
                pagamentoExcursaoListPagamentoExcursao.setUtilizadorId(null);
                pagamentoExcursaoListPagamentoExcursao = em.merge(pagamentoExcursaoListPagamentoExcursao);
            }
            List<FotoTrilho> fotoTrilhoList = utilizador.getFotoTrilhoList();
            for (FotoTrilho fotoTrilhoListFotoTrilho : fotoTrilhoList) {
                fotoTrilhoListFotoTrilho.setUtilizadorId(null);
                fotoTrilhoListFotoTrilho = em.merge(fotoTrilhoListFotoTrilho);
            }
            em.remove(utilizador);
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

    public List<Utilizador> findUtilizadorEntities() {
        return findUtilizadorEntities(true, -1, -1);
    }

    public List<Utilizador> findUtilizadorEntities(int maxResults, int firstResult) {
        return findUtilizadorEntities(false, maxResults, firstResult);
    }

    private List<Utilizador> findUtilizadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utilizador.class));
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

    public Utilizador findUtilizador(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utilizador.class, id);
        } finally {
            em.close();
        }
        
    }
      public static List<DAL.Utilizador> readAllUtilizadores() {
        factory = Persistence.createEntityManagerFactory("DAL_projeto2");
        EntityManager em = factory.createEntityManager();
        List<DAL.Utilizador> listaUtilizadores = new ArrayList();
        Query q = em.createNamedQuery("Utilizador.findAll");
        Iterator var4 = q.getResultList().iterator();

        while(var4.hasNext()) {
            Object uti = var4.next();
            listaUtilizadores.add((DAL.Utilizador)uti);
        }

        return listaUtilizadores;
    }

     public static DAL.Utilizador login(String email, String password) {
        DAL.Utilizador utilizador = new DAL.Utilizador();
        Boolean exite = false;
        Iterator var5 = readAllUtilizadores().iterator();

        while(var5.hasNext()) {
            DAL.Utilizador u = (DAL.Utilizador)var5.next();
            if (u.equals(email) && u.getUtilizadorPass().equals(password)) {
                utilizador = u;
                exite = true;
            }
        }

        if (exite) {
            return utilizador;
        } else {
            return null;
        }
    }


    public int getUtilizadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utilizador> rt = cq.from(Utilizador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
