/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto2trilho.DAL;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import static org.eclipse.persistence.platform.database.oracle.plsql.OraclePLSQLTypes.Int;

/**
 *
 * @author rafae
 */
@Entity
@Table(name = "UTILIZADOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u")
    , @NamedQuery(name = "Utilizador.findByUtilizadorId", query = "SELECT u FROM Utilizador u WHERE u.utilizadorId = :utilizadorId")
    , @NamedQuery(name = "Utilizador.findByUtilizadorNome", query = "SELECT u FROM Utilizador u WHERE u.utilizadorNome = :utilizadorNome")
    , @NamedQuery(name = "Utilizador.findByUtilizadorTipo", query = "SELECT u FROM Utilizador u WHERE u.utilizadorTipo = :utilizadorTipo")
    , @NamedQuery(name = "Utilizador.findByUtilizadorMorada", query = "SELECT u FROM Utilizador u WHERE u.utilizadorMorada = :utilizadorMorada")
    , @NamedQuery(name = "Utilizador.findByUtilizadorIdade", query = "SELECT u FROM Utilizador u WHERE u.utilizadorIdade = :utilizadorIdade")
    , @NamedQuery(name = "Utilizador.findByUtilizadorPass", query = "SELECT u FROM Utilizador u WHERE u.utilizadorPass = :utilizadorPass")})
public  class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "UTILIZADOR_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger utilizadorId;
    @Basic(optional = false)
    @Column(name = "UTILIZADOR_NOME")
    private String utilizadorNome;
    @Column(name = "UTILIZADOR_TIPO")
    private Short utilizadorTipo;
    @Basic(optional = false)
    @Column(name = "UTILIZADOR_MORADA")
    private String utilizadorMorada;
    @Basic(optional = false)
    @Column(name = "UTILIZADOR_IDADE")
    private BigInteger utilizadorIdade;
    @Basic(optional = false)
    @Column(name = "UTILIZADOR_PASS")
    private String utilizadorPass;
    @OneToMany(mappedBy = "utilizadorId")
    private List<FotoPonto> fotoPontoList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<Grupo> grupoList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<ClassTrilho> classTrilhoList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<ClassAlo> classAloList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<Excursao> excursaoList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<PagamentoExcursao> pagamentoExcursaoList;
    @OneToMany(mappedBy = "utilizadorId")
    private List<FotoTrilho> fotoTrilhoList;

    public Utilizador() {
    }

    public Utilizador(BigInteger utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public Utilizador(BigInteger utilizadorId, String utilizadorNome, String utilizadorMorada, BigInteger utilizadorIdade, String utilizadorPass) {
        this.utilizadorId = utilizadorId;
        this.utilizadorNome = utilizadorNome;
        this.utilizadorMorada = utilizadorMorada;
        this.utilizadorIdade = utilizadorIdade;
        this.utilizadorPass = utilizadorPass;
    }

    public BigInteger getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(BigInteger utilizadorId) {
        this.utilizadorId = utilizadorId;
    }

    public String getUtilizadorNome() {
        return utilizadorNome;
    }

    public void setUtilizadorNome(String utilizadorNome) {
        this.utilizadorNome = utilizadorNome;
    }

    public Short getUtilizadorTipo() {
        return utilizadorTipo;
    }

    public void setUtilizadorTipo(Short utilizadorTipo) {
        this.utilizadorTipo = utilizadorTipo;
    }

    public String getUtilizadorMorada() {
        return utilizadorMorada;
    }

    public void setUtilizadorMorada(String utilizadorMorada) {
        this.utilizadorMorada = utilizadorMorada;
    }

    public BigInteger getUtilizadorIdade() {
        return utilizadorIdade;
    }

    public void setUtilizadorIdade(BigInteger utilizadorIdade) {
        this.utilizadorIdade = utilizadorIdade;
    }

    public String getUtilizadorPass() {
        return utilizadorPass;
    }

    public void setUtilizadorPass(String utilizadorPass) {
        this.utilizadorPass = utilizadorPass;
    }

    @XmlTransient
    public List<FotoPonto> getFotoPontoList() {
        return fotoPontoList;
    }

    public void setFotoPontoList(List<FotoPonto> fotoPontoList) {
        this.fotoPontoList = fotoPontoList;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<ClassTrilho> getClassTrilhoList() {
        return classTrilhoList;
    }

    public void setClassTrilhoList(List<ClassTrilho> classTrilhoList) {
        this.classTrilhoList = classTrilhoList;
    }

    @XmlTransient
    public List<ClassAlo> getClassAloList() {
        return classAloList;
    }

    public void setClassAloList(List<ClassAlo> classAloList) {
        this.classAloList = classAloList;
    }

    @XmlTransient
    public List<Excursao> getExcursaoList() {
        return excursaoList;
    }

    public void setExcursaoList(List<Excursao> excursaoList) {
        this.excursaoList = excursaoList;
    }

    @XmlTransient
    public List<PagamentoExcursao> getPagamentoExcursaoList() {
        return pagamentoExcursaoList;
    }

    public void setPagamentoExcursaoList(List<PagamentoExcursao> pagamentoExcursaoList) {
        this.pagamentoExcursaoList = pagamentoExcursaoList;
    }

    @XmlTransient
    public List<FotoTrilho> getFotoTrilhoList() {
        return fotoTrilhoList;
    }

    public void setFotoTrilhoList(List<FotoTrilho> fotoTrilhoList) {
        this.fotoTrilhoList = fotoTrilhoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utilizadorId != null ? utilizadorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.utilizadorId == null && other.utilizadorId != null) || (this.utilizadorId != null && !this.utilizadorId.equals(other.utilizadorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.Utilizador[ utilizadorId=" + utilizadorId + " ]";
    }
}

