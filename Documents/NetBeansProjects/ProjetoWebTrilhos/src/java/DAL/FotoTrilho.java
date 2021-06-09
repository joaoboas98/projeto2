/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Utilizador
 */
@Entity
@Table(name = "FOTO_TRILHO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoTrilho.findAll", query = "SELECT f FROM FotoTrilho f")
    , @NamedQuery(name = "FotoTrilho.findByFotoTrilhoId", query = "SELECT f FROM FotoTrilho f WHERE f.fotoTrilhoId = :fotoTrilhoId")
    , @NamedQuery(name = "FotoTrilho.findByFotoTrilho", query = "SELECT f FROM FotoTrilho f WHERE f.fotoTrilho = :fotoTrilho")
    , @NamedQuery(name = "FotoTrilho.findByFotoTrilhoData", query = "SELECT f FROM FotoTrilho f WHERE f.fotoTrilhoData = :fotoTrilhoData")
    , @NamedQuery(name = "FotoTrilho.findByFotoTrilhoDescricao", query = "SELECT f FROM FotoTrilho f WHERE f.fotoTrilhoDescricao = :fotoTrilhoDescricao")})
public class FotoTrilho implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FOTO_TRILHO_ID")
    private BigDecimal fotoTrilhoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOTO_TRILHO")
    private String fotoTrilho;
    @Column(name = "FOTO_TRILHO_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fotoTrilhoData;
    @Size(max = 50)
    @Column(name = "FOTO_TRILHO_DESCRICAO")
    private String fotoTrilhoDescricao;
    @JoinColumn(name = "TRILHO_ID", referencedColumnName = "TRILHO_ID")
    @ManyToOne
    private Trilho trilhoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public FotoTrilho() {
    }

    public FotoTrilho(BigDecimal fotoTrilhoId) {
        this.fotoTrilhoId = fotoTrilhoId;
    }

    public FotoTrilho(BigDecimal fotoTrilhoId, String fotoTrilho) {
        this.fotoTrilhoId = fotoTrilhoId;
        this.fotoTrilho = fotoTrilho;
    }

    public BigDecimal getFotoTrilhoId() {
        return fotoTrilhoId;
    }

    public void setFotoTrilhoId(BigDecimal fotoTrilhoId) {
        this.fotoTrilhoId = fotoTrilhoId;
    }

    public String getFotoTrilho() {
        return fotoTrilho;
    }

    public void setFotoTrilho(String fotoTrilho) {
        this.fotoTrilho = fotoTrilho;
    }

    public Date getFotoTrilhoData() {
        return fotoTrilhoData;
    }

    public void setFotoTrilhoData(Date fotoTrilhoData) {
        this.fotoTrilhoData = fotoTrilhoData;
    }

    public String getFotoTrilhoDescricao() {
        return fotoTrilhoDescricao;
    }

    public void setFotoTrilhoDescricao(String fotoTrilhoDescricao) {
        this.fotoTrilhoDescricao = fotoTrilhoDescricao;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fotoTrilhoId != null ? fotoTrilhoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FotoTrilho)) {
            return false;
        }
        FotoTrilho other = (FotoTrilho) object;
        if ((this.fotoTrilhoId == null && other.fotoTrilhoId != null) || (this.fotoTrilhoId != null && !this.fotoTrilhoId.equals(other.fotoTrilhoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAL.FotoTrilho[ fotoTrilhoId=" + fotoTrilhoId + " ]";
    }
    
}
