/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "FOTO_ALOJAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoAlojamento.findAll", query = "SELECT f FROM FotoAlojamento f")
    , @NamedQuery(name = "FotoAlojamento.findByFotoaloid", query = "SELECT f FROM FotoAlojamento f WHERE f.fotoaloid = :fotoaloid")
    , @NamedQuery(name = "FotoAlojamento.findByAlojamentoid", query = "SELECT f FROM FotoAlojamento f WHERE f.alojamentoid = :alojamentoid")})
public class FotoAlojamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "FOTOALOID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger fotoaloid;
    @Basic(optional = false)
    @Lob
    @Column(name = "FOTOALO")
    private byte[] fotoalo;
    @Basic(optional = false)
    @Column(name = "ALOJAMENTOID")
    private BigInteger alojamentoid;

    public FotoAlojamento() {
    }

    public FotoAlojamento(BigInteger fotoaloid) {
        this.fotoaloid = fotoaloid;
    }

    public FotoAlojamento(BigInteger fotoaloid,byte[] fotoalo, BigInteger alojamentoid) {
        this.fotoaloid = fotoaloid;
        this.fotoalo = fotoalo;
        this.alojamentoid = alojamentoid;
    }

    public BigInteger getFotoaloid() {
        return fotoaloid;
    }

    public void setFotoaloid(BigInteger fotoaloid) {
        this.fotoaloid = fotoaloid;
    }

    public byte[] getFotoalo() {
        return fotoalo;
    }

    public void setFotoalo(byte[] fotoalo) {
        this.fotoalo = fotoalo;
    }

    public BigInteger getAlojamentoid() {
        return alojamentoid;
    }

    public void setAlojamentoid(BigInteger alojamentoid) {
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
        return "projeto2trilho.DAL.FotoAlojamento[ fotoaloid=" + fotoaloid + " ]";
    }
    
}
