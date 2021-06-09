/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Utilizador
 */
@Entity
@Table(name = "FOTO_ALOJAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoAlojamento.findAll", query = "SELECT f FROM FotoAlojamento f")
    , @NamedQuery(name = "FotoAlojamento.findByFotoaloid", query = "SELECT f FROM FotoAlojamento f WHERE f.fotoaloid = :fotoaloid")})
public class FotoAlojamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FOTOALOID")
    private BigDecimal fotoaloid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "FOTOALO")
    private Serializable fotoalo;
    @JoinColumn(name = "ALOJAMENTOID", referencedColumnName = "ALOJAMENTO_ID")
    @ManyToOne(optional = false)
    private Alojamento alojamentoid;

    public FotoAlojamento() {
    }

    public FotoAlojamento(BigDecimal fotoaloid) {
        this.fotoaloid = fotoaloid;
    }

    public FotoAlojamento(BigDecimal fotoaloid, Serializable fotoalo) {
        this.fotoaloid = fotoaloid;
        this.fotoalo = fotoalo;
    }

    public BigDecimal getFotoaloid() {
        return fotoaloid;
    }

    public void setFotoaloid(BigDecimal fotoaloid) {
        this.fotoaloid = fotoaloid;
    }

    public Serializable getFotoalo() {
        return fotoalo;
    }

    public void setFotoalo(Serializable fotoalo) {
        this.fotoalo = fotoalo;
    }

    public Alojamento getAlojamentoid() {
        return alojamentoid;
    }

    public void setAlojamentoid(Alojamento alojamentoid) {
        this.alojamentoid = alojamentoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fotoaloid != null ? fotoaloid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FotoAlojamento)) {
            return false;
        }
        FotoAlojamento other = (FotoAlojamento) object;
        if ((this.fotoaloid == null && other.fotoaloid != null) || (this.fotoaloid != null && !this.fotoaloid.equals(other.fotoaloid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAL.FotoAlojamento[ fotoaloid=" + fotoaloid + " ]";
    }
    
}
