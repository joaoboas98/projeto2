/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.DAL;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "FOTO_PONTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FotoPonto.findAll", query = "SELECT f FROM FotoPonto f")
    , @NamedQuery(name = "FotoPonto.findByFotoPontoId", query = "SELECT f FROM FotoPonto f WHERE f.fotoPontoId = :fotoPontoId")
    , @NamedQuery(name = "FotoPonto.findByFotoPonto", query = "SELECT f FROM FotoPonto f WHERE f.fotoPonto = :fotoPonto")
    , @NamedQuery(name = "FotoPonto.findByFotoPontoData", query = "SELECT f FROM FotoPonto f WHERE f.fotoPontoData = :fotoPontoData")
    , @NamedQuery(name = "FotoPonto.findByFotoPontoDescricao", query = "SELECT f FROM FotoPonto f WHERE f.fotoPontoDescricao = :fotoPontoDescricao")})
public class FotoPonto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "FOTO_PONTO_ID")
    private BigDecimal fotoPontoId;
    @Basic(optional = false)
    @Column(name = "FOTO_PONTO")
    private String fotoPonto;
    @Basic(optional = false)
    @Column(name = "FOTO_PONTO_DATA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fotoPontoData;
    @Column(name = "FOTO_PONTO_DESCRICAO")
    private String fotoPontoDescricao;
    @JoinColumn(name = "PONTO_ID", referencedColumnName = "PONTO_ID")
    @ManyToOne
    private Ponto pontoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public FotoPonto() {
    }

    public FotoPonto(BigDecimal fotoPontoId) {
        this.fotoPontoId = fotoPontoId;
    }

    public FotoPonto(BigDecimal fotoPontoId, String fotoPonto, Date fotoPontoData) {
        this.fotoPontoId = fotoPontoId;
        this.fotoPonto = fotoPonto;
        this.fotoPontoData = fotoPontoData;
    }

    public BigDecimal getFotoPontoId() {
        return fotoPontoId;
    }

    public void setFotoPontoId(BigDecimal fotoPontoId) {
        this.fotoPontoId = fotoPontoId;
    }

    public String getFotoPonto() {
        return fotoPonto;
    }

    public void setFotoPonto(String fotoPonto) {
        this.fotoPonto = fotoPonto;
    }

    public Date getFotoPontoData() {
        return fotoPontoData;
    }

    public void setFotoPontoData(Date fotoPontoData) {
        this.fotoPontoData = fotoPontoData;
    }

    public String getFotoPontoDescricao() {
        return fotoPontoDescricao;
    }

    public void setFotoPontoDescricao(String fotoPontoDescricao) {
        this.fotoPontoDescricao = fotoPontoDescricao;
    }

    public Ponto getPontoId() {
        return pontoId;
    }

    public void setPontoId(Ponto pontoId) {
        this.pontoId = pontoId;
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
        hash += (fotoPontoId != null ? fotoPontoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FotoPonto)) {
            return false;
        }
        FotoPonto other = (FotoPonto) object;
        if ((this.fotoPontoId == null && other.fotoPontoId != null) || (this.fotoPontoId != null && !this.fotoPontoId.equals(other.fotoPontoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.FotoPonto[ fotoPontoId=" + fotoPontoId + " ]";
    }
    
}
