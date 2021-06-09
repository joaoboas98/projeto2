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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "ALOJAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alojamento.findAll", query = "SELECT a FROM Alojamento a")
    , @NamedQuery(name = "Alojamento.findByAlojamentoId", query = "SELECT a FROM Alojamento a WHERE a.alojamentoId = :alojamentoId")
    , @NamedQuery(name = "Alojamento.findByAlojamentoNome", query = "SELECT a FROM Alojamento a WHERE a.alojamentoNome = :alojamentoNome")
    , @NamedQuery(name = "Alojamento.findByAlojamentoCidade", query = "SELECT a FROM Alojamento a WHERE a.alojamentoCidade = :alojamentoCidade")
    , @NamedQuery(name = "Alojamento.findByAlojamentoLatitude", query = "SELECT a FROM Alojamento a WHERE a.alojamentoLatitude = :alojamentoLatitude")
    , @NamedQuery(name = "Alojamento.findByAlojamentoLongitude", query = "SELECT a FROM Alojamento a WHERE a.alojamentoLongitude = :alojamentoLongitude")
    , @NamedQuery(name = "Alojamento.findByAlojamentoCp", query = "SELECT a FROM Alojamento a WHERE a.alojamentoCp = :alojamentoCp")
    , @NamedQuery(name = "Alojamento.findByAlojamentoDescricao", query = "SELECT a FROM Alojamento a WHERE a.alojamentoDescricao = :alojamentoDescricao")
    , @NamedQuery(name = "Alojamento.findByAlojamentoPais", query = "SELECT a FROM Alojamento a WHERE a.alojamentoPais = :alojamentoPais")})
public class Alojamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ALOJAMENTO_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger alojamentoId;
    @Basic(optional = false)
    @Column(name = "ALOJAMENTO_NOME")
    private String alojamentoNome;
    @Column(name = "ALOJAMENTO__CIDADE")
    private String alojamentoCidade;
    @Column(name = "ALOJAMENTO_LATITUDE")
    private String alojamentoLatitude;
    @Column(name = "ALOJAMENTO_LONGITUDE")
    private String alojamentoLongitude;
    @Column(name = "ALOJAMENTO_CP")
    private String alojamentoCp;
    @Basic(optional = false)
    @Column(name = "ALOJAMENTO_DESCRICAO")
    private String alojamentoDescricao;
    @Column(name = "PAIS")
    private String alojamentoPais;

    public Alojamento() {
    }

    public Alojamento(BigInteger alojamentoId) {
        this.alojamentoId = alojamentoId;
    }

    public Alojamento(BigInteger alojamentoId, String alojamentoNome, String alojamentoDescricao) {
        this.alojamentoId = alojamentoId;
        this.alojamentoNome = alojamentoNome;
        this.alojamentoDescricao = alojamentoDescricao;
    }

    public BigInteger getAlojamentoId() {
        return alojamentoId;
    }

    public void setAlojamentoId(BigInteger alojamentoId) {
        this.alojamentoId = alojamentoId;
    }

    public String getAlojamentoNome() {
        return alojamentoNome;
    }

    public void setAlojamentoNome(String alojamentoNome) {
        this.alojamentoNome = alojamentoNome;
    }

    public String getAlojamentoCidade() {
        return alojamentoCidade;
    }

    public void setAlojamentoCidade(String alojamentoCidade) {
        this.alojamentoCidade = alojamentoCidade;
    }

    public String getAlojamentoLatitude() {
        return alojamentoLatitude;
    }

    public void setAlojamentoLatitude(String alojamentoLatitude) {
        this.alojamentoLatitude = alojamentoLatitude;
    }

    public String getAlojamentoLongitude() {
        return alojamentoLongitude;
    }

    public void setAlojamentoLongitude(String alojamentoLongitude) {
        this.alojamentoLongitude = alojamentoLongitude;
    }

    public String getAlojamentoCp() {
        return alojamentoCp;
    }

    public void setAlojamentoCp(String alojamentoCp) {
        this.alojamentoCp = alojamentoCp;
    }

    public String getAlojamentoDescricao() {
        return alojamentoDescricao;
    }

    public void setAlojamentoDescricao(String alojamentoDescricao) {
        this.alojamentoDescricao = alojamentoDescricao;
    }

    public String getAlojamentoPais() {
        return alojamentoPais;
    }

    public void setAlojamentoPais(String alojamentoPais) {
        this.alojamentoPais = alojamentoPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alojamentoId != null ? alojamentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alojamento)) {
            return false;
        }
        Alojamento other = (Alojamento) object;
        if ((this.alojamentoId == null && other.alojamentoId != null) || (this.alojamentoId != null && !this.alojamentoId.equals(other.alojamentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.Alojamento[ alojamentoId=" + alojamentoId + " ]";
    }
    
}
