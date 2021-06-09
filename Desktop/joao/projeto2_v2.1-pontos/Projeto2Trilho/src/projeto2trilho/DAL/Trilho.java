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
@Table(name = "TRILHO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trilho.findAll", query = "SELECT t FROM Trilho t")
    , @NamedQuery(name = "Trilho.findByTrilhoId", query = "SELECT t FROM Trilho t WHERE t.trilhoId = :trilhoId")
    , @NamedQuery(name = "Trilho.findByTrilhoNome", query = "SELECT t FROM Trilho t WHERE t.trilhoNome = :trilhoNome")
    , @NamedQuery(name = "Trilho.findByTrilhoCidadeInicio", query = "SELECT t FROM Trilho t WHERE t.trilhoCidadeInicio = :trilhoCidadeInicio")
    , @NamedQuery(name = "Trilho.findByTrilhoCidadeFim", query = "SELECT t FROM Trilho t WHERE t.trilhoCidadeFim = :trilhoCidadeFim")
    , @NamedQuery(name = "Trilho.findByTrilhoLatitudeInicio", query = "SELECT t FROM Trilho t WHERE t.trilhoLatitudeInicio = :trilhoLatitudeInicio")
    , @NamedQuery(name = "Trilho.findByTrilhoLongitudeInicio", query = "SELECT t FROM Trilho t WHERE t.trilhoLongitudeInicio = :trilhoLongitudeInicio")
    , @NamedQuery(name = "Trilho.findByTrilhoCpInicio", query = "SELECT t FROM Trilho t WHERE t.trilhoCpInicio = :trilhoCpInicio")
    , @NamedQuery(name = "Trilho.findByTrilhoLatitudeFim", query = "SELECT t FROM Trilho t WHERE t.trilhoLatitudeFim = :trilhoLatitudeFim")
    , @NamedQuery(name = "Trilho.findByTrilhoLongitudeFim", query = "SELECT t FROM Trilho t WHERE t.trilhoLongitudeFim = :trilhoLongitudeFim")
    , @NamedQuery(name = "Trilho.findByTrilhoCpFim", query = "SELECT t FROM Trilho t WHERE t.trilhoCpFim = :trilhoCpFim")
    , @NamedQuery(name = "Trilho.findByTrilhoDescricao", query = "SELECT t FROM Trilho t WHERE t.trilhoDescricao = :trilhoDescricao")
    , @NamedQuery(name = "Trilho.findByTrilhoNivel", query = "SELECT t FROM Trilho t WHERE t.trilhoNivel = :trilhoNivel")})
public class Trilho implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TRILHO_ID")
    private BigInteger trilhoId;
    @Basic(optional = false)
    @Column(name = "TRILHO_NOME")
    private String trilhoNome;
    @Column(name = "TRILHO_CIDADE_INICIO")
    private String trilhoCidadeInicio;
    @Column(name = "TRILHO_CIDADE_FIM")
    private String trilhoCidadeFim;
    @Column(name = "TRILHO_LATITUDE_INICIO")
    private String trilhoLatitudeInicio;
    @Column(name = "TRILHO_LONGITUDE_INICIO")
    private String trilhoLongitudeInicio;
    @Column(name = "TRILHO_CP_INICIO")
    private String trilhoCpInicio;
    @Column(name = "TRILHO_LATITUDE_FIM")
    private String trilhoLatitudeFim;
    @Column(name = "TRILHO_LONGITUDE_FIM")
    private String trilhoLongitudeFim;
    @Column(name = "TRILHO_CP_FIM")
    private String trilhoCpFim;
    @Basic(optional = false)
    @Column(name = "TRILHO_DESCRICAO")
    private String trilhoDescricao;
    @Basic(optional = false)
    @Column(name = "TRILHO_NIVEL")
    private BigInteger trilhoNivel;
    @JoinTable(name = "TRILHO_PONTO", joinColumns = {
        @JoinColumn(name = "TRILHO_ID", referencedColumnName = "TRILHO_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "PONTO_ID", referencedColumnName = "PONTO_ID")})
    @ManyToMany
    private List<Ponto> pontoList;
    @JoinTable(name = "TRILHO_ALOJAMENTO", joinColumns = {
        @JoinColumn(name = "TRILHO_ID", referencedColumnName = "TRILHO_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ALOJAMENTO_ID", referencedColumnName = "ALOJAMENTO_ID")})
    @ManyToMany
    private List<Alojamento> alojamentoList;
    @OneToMany(mappedBy = "trilhoId")
    private List<ClassTrilho> classTrilhoList;
    @OneToMany(mappedBy = "trilhoId")
    private List<Excursao> excursaoList;
    @OneToMany(mappedBy = "trilhoId")
    private List<FotoTrilho> fotoTrilhoList;

    public Trilho() {
    }

    public Trilho(BigInteger trilhoId) {
        this.trilhoId = trilhoId;
    }

    public Trilho(BigInteger trilhoId, String trilhoNome, String trilhoDescricao, BigInteger trilhoNivel) {
        this.trilhoId = trilhoId;
        this.trilhoNome = trilhoNome;
        this.trilhoDescricao = trilhoDescricao;
        this.trilhoNivel = trilhoNivel;
    }

    public BigInteger getTrilhoId() {
        return trilhoId;
    }

    public void setTrilhoId(BigInteger trilhoId) {
        this.trilhoId = trilhoId;
    }

    public String getTrilhoNome() {
        return trilhoNome;
    }

    public void setTrilhoNome(String trilhoNome) {
        this.trilhoNome = trilhoNome;
    }

    public String getTrilhoCidadeInicio() {
        return trilhoCidadeInicio;
    }

    public void setTrilhoCidadeInicio(String trilhoCidadeInicio) {
        this.trilhoCidadeInicio = trilhoCidadeInicio;
    }

    public String getTrilhoCidadeFim() {
        return trilhoCidadeFim;
    }

    public void setTrilhoCidadeFim(String trilhoCidadeFim) {
        this.trilhoCidadeFim = trilhoCidadeFim;
    }

    public String getTrilhoLatitudeInicio() {
        return trilhoLatitudeInicio;
    }

    public void setTrilhoLatitudeInicio(String trilhoLatitudeInicio) {
        this.trilhoLatitudeInicio = trilhoLatitudeInicio;
    }

    public String getTrilhoLongitudeInicio() {
        return trilhoLongitudeInicio;
    }

    public void setTrilhoLongitudeInicio(String trilhoLongitudeInicio) {
        this.trilhoLongitudeInicio = trilhoLongitudeInicio;
    }

    public String getTrilhoCpInicio() {
        return trilhoCpInicio;
    }

    public void setTrilhoCpInicio(String trilhoCpInicio) {
        this.trilhoCpInicio = trilhoCpInicio;
    }

    public String getTrilhoLatitudeFim() {
        return trilhoLatitudeFim;
    }

    public void setTrilhoLatitudeFim(String trilhoLatitudeFim) {
        this.trilhoLatitudeFim = trilhoLatitudeFim;
    }

    public String getTrilhoLongitudeFim() {
        return trilhoLongitudeFim;
    }

    public void setTrilhoLongitudeFim(String trilhoLongitudeFim) {
        this.trilhoLongitudeFim = trilhoLongitudeFim;
    }

    public String getTrilhoCpFim() {
        return trilhoCpFim;
    }

    public void setTrilhoCpFim(String trilhoCpFim) {
        this.trilhoCpFim = trilhoCpFim;
    }

    public String getTrilhoDescricao() {
        return trilhoDescricao;
    }

    public void setTrilhoDescricao(String trilhoDescricao) {
        this.trilhoDescricao = trilhoDescricao;
    }

    public BigInteger getTrilhoNivel() {
        return trilhoNivel;
    }

    public void setTrilhoNivel(BigInteger trilhoNivel) {
        this.trilhoNivel = trilhoNivel;
    }

    @XmlTransient
    public List<Ponto> getPontoList() {
        return pontoList;
    }

    public void setPontoList(List<Ponto> pontoList) {
        this.pontoList = pontoList;
    }

    @XmlTransient
    public List<Alojamento> getAlojamentoList() {
        return alojamentoList;
    }

    public void setAlojamentoList(List<Alojamento> alojamentoList) {
        this.alojamentoList = alojamentoList;
    }

    @XmlTransient
    public List<ClassTrilho> getClassTrilhoList() {
        return classTrilhoList;
    }

    public void setClassTrilhoList(List<ClassTrilho> classTrilhoList) {
        this.classTrilhoList = classTrilhoList;
    }

    @XmlTransient
    public List<Excursao> getExcursaoList() {
        return excursaoList;
    }

    public void setExcursaoList(List<Excursao> excursaoList) {
        this.excursaoList = excursaoList;
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
        hash += (trilhoId != null ? trilhoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trilho)) {
            return false;
        }
        Trilho other = (Trilho) object;
        if ((this.trilhoId == null && other.trilhoId != null) || (this.trilhoId != null && !this.trilhoId.equals(other.trilhoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.Trilho[ trilhoId=" + trilhoId + " ]";
    }
    
}
