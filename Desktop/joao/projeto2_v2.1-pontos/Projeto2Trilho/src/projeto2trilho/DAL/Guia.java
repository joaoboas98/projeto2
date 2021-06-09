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
@Table(name = "GUIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Guia.findAll", query = "SELECT g FROM Guia g")
    , @NamedQuery(name = "Guia.findByGuiaId", query = "SELECT g FROM Guia g WHERE g.guiaId = :guiaId")
    , @NamedQuery(name = "Guia.findByGuiaNome", query = "SELECT g FROM Guia g WHERE g.guiaNome = :guiaNome")
    , @NamedQuery(name = "Guia.findByGuiaIdade", query = "SELECT g FROM Guia g WHERE g.guiaIdade = :guiaIdade")})
public class Guia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "GUIA_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger guiaId;
    @Basic(optional = false)
    @Column(name = "GUIA_NOME")
    private String guiaNome;
    @Basic(optional = false)
    @Column(name = "GUIA_IDADE")
    private BigInteger guiaIdade;
    @JoinTable(name = "GUIA_EXCURSAO", joinColumns = {
        @JoinColumn(name = "GUIA_ID", referencedColumnName = "GUIA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXCURSAO_ID", referencedColumnName = "EXCURSAO_ID")})
    @ManyToMany
    private List<Excursao> excursaoList;
    @OneToMany(mappedBy = "guiaId")
    private List<Excursao> excursaoList1;

    public Guia() {
    }

    public Guia(BigInteger guiaId) {
        this.guiaId = guiaId;
    }

    public Guia(BigInteger guiaId, String guiaNome, BigInteger guiaIdade) {
        this.guiaId = guiaId;
        this.guiaNome = guiaNome;
        this.guiaIdade = guiaIdade;
    }

    public BigInteger getGuiaId() {
        return guiaId;
    }

    public void setGuiaId(BigInteger guiaId) {
        this.guiaId = guiaId;
    }

    public String getGuiaNome() {
        return guiaNome;
    }

    public void setGuiaNome(String guiaNome) {
        this.guiaNome = guiaNome;
    }

    public BigInteger getGuiaIdade() {
        return guiaIdade;
    }

    public void setGuiaIdade(BigInteger guiaIdade) {
        this.guiaIdade = guiaIdade;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (guiaId != null ? guiaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guia)) {
            return false;
        }
        Guia other = (Guia) object;
        if ((this.guiaId == null && other.guiaId != null) || (this.guiaId != null && !this.guiaId.equals(other.guiaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.Guia[ guiaId=" + guiaId + " ]";
    }
    
}
