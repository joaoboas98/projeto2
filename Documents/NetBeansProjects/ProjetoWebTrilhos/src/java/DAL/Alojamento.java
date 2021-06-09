/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Utilizador
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
    , @NamedQuery(name = "Alojamento.findByPais", query = "SELECT a FROM Alojamento a WHERE a.pais = :pais")})
public class Alojamento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ALOJAMENTO_ID")
    private BigDecimal alojamentoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ALOJAMENTO_NOME")
    private String alojamentoNome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ALOJAMENTO__CIDADE")
    private String alojamentoCidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ALOJAMENTO_LATITUDE")
    private String alojamentoLatitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ALOJAMENTO_LONGITUDE")
    private String alojamentoLongitude;
    @Size(max = 300)
    @Column(name = "ALOJAMENTO_CP")
    private String alojamentoCp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "ALOJAMENTO_DESCRICAO")
    private String alojamentoDescricao;
    @Size(max = 100)
    @Column(name = "PAIS")
    private String pais;
    @ManyToMany(mappedBy = "alojamentoList")
    private List<Trilho> trilhoList;
    @OneToMany(mappedBy = "alojamentoId")
    private List<ClassAlo> classAloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alojamentoid")
    private List<FotoAlojamento> fotoAlojamentoList;

    public Alojamento() {
    }

    public Alojamento(BigDecimal alojamentoId) {
        this.alojamentoId = alojamentoId;
    }

    public Alojamento(BigDecimal alojamentoId, String alojamentoNome, String alojamentoCidade, String alojamentoLatitude, String alojamentoLongitude, String alojamentoDescricao) {
        this.alojamentoId = alojamentoId;
        this.alojamentoNome = alojamentoNome;
        this.alojamentoCidade = alojamentoCidade;
        this.alojamentoLatitude = alojamentoLatitude;
        this.alojamentoLongitude = alojamentoLongitude;
        this.alojamentoDescricao = alojamentoDescricao;
    }

    public BigDecimal getAlojamentoId() {
        return alojamentoId;
    }

    public void setAlojamentoId(BigDecimal alojamentoId) {
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @XmlTransient
    public List<Trilho> getTrilhoList() {
        return trilhoList;
    }

    public void setTrilhoList(List<Trilho> trilhoList) {
        this.trilhoList = trilhoList;
    }

    @XmlTransient
    public List<ClassAlo> getClassAloList() {
        return classAloList;
    }

    public void setClassAloList(List<ClassAlo> classAloList) {
        this.classAloList = classAloList;
    }

    @XmlTransient
    public List<FotoAlojamento> getFotoAlojamentoList() {
        return fotoAlojamentoList;
    }

    public void setFotoAlojamentoList(List<FotoAlojamento> fotoAlojamentoList) {
        this.fotoAlojamentoList = fotoAlojamentoList;
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
        return "DAL.Alojamento[ alojamentoId=" + alojamentoId + " ]";
    }
    
}
