/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "PAGAMENTO_EXCURSAO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagamentoExcursao.findAll", query = "SELECT p FROM PagamentoExcursao p")
    , @NamedQuery(name = "PagamentoExcursao.findByPagamentoExcursaoId", query = "SELECT p FROM PagamentoExcursao p WHERE p.pagamentoExcursaoId = :pagamentoExcursaoId")
    , @NamedQuery(name = "PagamentoExcursao.findByPagamentoExcursao", query = "SELECT p FROM PagamentoExcursao p WHERE p.pagamentoExcursao = :pagamentoExcursao")})
public class PagamentoExcursao implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PAGAMENTO_EXCURSAO_ID")
    private BigDecimal pagamentoExcursaoId;
    @Column(name = "PAGAMENTO_EXCURSAO")
    private Double pagamentoExcursao;
    @JoinColumn(name = "EXCURSAO_ID", referencedColumnName = "EXCURSAO_ID")
    @ManyToOne
    private Excursao excursaoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public PagamentoExcursao() {
    }

    public PagamentoExcursao(BigDecimal pagamentoExcursaoId) {
        this.pagamentoExcursaoId = pagamentoExcursaoId;
    }

    public BigDecimal getPagamentoExcursaoId() {
        return pagamentoExcursaoId;
    }

    public void setPagamentoExcursaoId(BigDecimal pagamentoExcursaoId) {
        this.pagamentoExcursaoId = pagamentoExcursaoId;
    }

    public Double getPagamentoExcursao() {
        return pagamentoExcursao;
    }

    public void setPagamentoExcursao(Double pagamentoExcursao) {
        this.pagamentoExcursao = pagamentoExcursao;
    }

    public Excursao getExcursaoId() {
        return excursaoId;
    }

    public void setExcursaoId(Excursao excursaoId) {
        this.excursaoId = excursaoId;
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
        hash += (pagamentoExcursaoId != null ? pagamentoExcursaoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagamentoExcursao)) {
            return false;
        }
        PagamentoExcursao other = (PagamentoExcursao) object;
        if ((this.pagamentoExcursaoId == null && other.pagamentoExcursaoId != null) || (this.pagamentoExcursaoId != null && !this.pagamentoExcursaoId.equals(other.pagamentoExcursaoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.PagamentoExcursao[ pagamentoExcursaoId=" + pagamentoExcursaoId + " ]";
    }
    
}
