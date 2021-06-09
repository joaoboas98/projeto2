/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Utilizador
 */
@Entity
@Table(name = "EXCURSAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Excursao.findAll", query = "SELECT e FROM Excursao e")
    , @NamedQuery(name = "Excursao.findByExcursaoId", query = "SELECT e FROM Excursao e WHERE e.excursaoId = :excursaoId")
    , @NamedQuery(name = "Excursao.findByDataInicio", query = "SELECT e FROM Excursao e WHERE e.dataInicio = :dataInicio")
    , @NamedQuery(name = "Excursao.findByDataFim", query = "SELECT e FROM Excursao e WHERE e.dataFim = :dataFim")})
public class Excursao implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCURSAO_ID")
    private BigDecimal excursaoId;
    @Column(name = "DATA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;
    @Column(name = "DATA_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;
    @ManyToMany(mappedBy = "excursaoList")
    private List<Grupo> grupoList;
    @ManyToMany(mappedBy = "excursaoList")
    private List<Guia> guiaList;
    @JoinColumn(name = "GRUPO_ID", referencedColumnName = "GRUPO_ID")
    @ManyToOne
    private Grupo grupoId;
    @JoinColumn(name = "GUIA_ID", referencedColumnName = "GUIA_ID")
    @ManyToOne
    private Guia guiaId;
    @JoinColumn(name = "TRILHO_ID", referencedColumnName = "TRILHO_ID")
    @ManyToOne
    private Trilho trilhoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;
    @OneToMany(mappedBy = "excursaoId")
    private List<PagamentoExcursao> pagamentoExcursaoList;

    public Excursao() {
    }

    public Excursao(BigDecimal excursaoId) {
        this.excursaoId = excursaoId;
    }

    public BigDecimal getExcursaoId() {
        return excursaoId;
    }

    public void setExcursaoId(BigDecimal excursaoId) {
        this.excursaoId = excursaoId;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<Guia> getGuiaList() {
        return guiaList;
    }

    public void setGuiaList(List<Guia> guiaList) {
        this.guiaList = guiaList;
    }

    public Grupo getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Grupo grupoId) {
        this.grupoId = grupoId;
    }

    public Guia getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(Guia guiaId) {
        this.guiaId = guiaId;
    }

    public Trilho getTrilhoId() {
        return trilhoId;
    }

    public void setTrilhoId(Trilho trilhoId) {
        this.trilhoId = trilhoId;
    }

    public Utilizador getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Utilizador utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    @XmlTransient
    public List<PagamentoExcursao> getPagamentoExcursaoList() {
        return pagamentoExcursaoList;
    }

    public void setPagamentoExcursaoList(List<PagamentoExcursao> pagamentoExcursaoList) {
        this.pagamentoExcursaoList = pagamentoExcursaoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (excursaoId != null ? excursaoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Excursao)) {
            return false;
        }
        Excursao other = (Excursao) object;
        if ((this.excursaoId == null && other.excursaoId != null) || (this.excursaoId != null && !this.excursaoId.equals(other.excursaoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAL.Excursao[ excursaoId=" + excursaoId + " ]";
    }
    
}
