/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "GRUPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByGrupoId", query = "SELECT g FROM Grupo g WHERE g.grupoId = :grupoId")
    , @NamedQuery(name = "Grupo.findByGrupoNome", query = "SELECT g FROM Grupo g WHERE g.grupoNome = :grupoNome")
    , @NamedQuery(name = "Grupo.findByGrupoNumero", query = "SELECT g FROM Grupo g WHERE g.grupoNumero = :grupoNumero")})
public class Grupo implements Serializable {

    @JoinTable(name = "GRUPO_EXCURSAO", joinColumns = {
        @JoinColumn(name = "GRUPO_ID", referencedColumnName = "GRUPO_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXCURSAO_ID", referencedColumnName = "EXCURSAO_ID")})
    @ManyToMany
    private List<Excursao> excursaoList;
    @OneToMany(mappedBy = "grupoId")
    private List<Excursao> excursaoList1;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "GRUPO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal grupoId;
    @Column(name = "GRUPO_NOME")
    private String grupoNome;
    @Column(name = "GRUPO_NUMERO")
    private BigInteger grupoNumero;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public Grupo() {
    }

    public Grupo(BigDecimal grupoId) {
        this.grupoId = grupoId;
    }

    public BigDecimal getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(BigDecimal grupoId) {
        this.grupoId = grupoId;
    }

    public String getGrupoNome() {
        return grupoNome;
    }

    public void setGrupoNome(String grupoNome) {
        this.grupoNome = grupoNome;
    }

    public BigInteger getGrupoNumero() {
        return grupoNumero;
    }

    public void setGrupoNumero(BigInteger grupoNumero) {
        this.grupoNumero = grupoNumero;
    }

    public Utilizador getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Utilizador utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupoId != null ? grupoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupoId == null && other.grupoId != null) || (this.grupoId != null && !this.grupoId.equals(other.grupoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.Grupo[ grupoId=" + grupoId + " ]";
    }

    @XmlTransient
    public List<Excursao> getExcursaoList() {
        return excursaoList;
    }

    public void setExcursaoList(List<Excursao> excursaoList) {
        this.excursaoList = excursaoList;
    }

    @XmlTransient
    public List<Excursao> getExcursaoList1() {
        return excursaoList1;
    }

    public void setExcursaoList1(List<Excursao> excursaoList1) {
        this.excursaoList1 = excursaoList1;
    }
    
}
