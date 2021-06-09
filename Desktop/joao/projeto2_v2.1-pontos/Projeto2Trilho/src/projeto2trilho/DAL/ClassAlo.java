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
@Table(name = "CLASS_ALO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClassAlo.findAll", query = "SELECT c FROM ClassAlo c")
    , @NamedQuery(name = "ClassAlo.findByClassAloId", query = "SELECT c FROM ClassAlo c WHERE c.classAloId = :classAloId")
    , @NamedQuery(name = "ClassAlo.findByClassAloValor", query = "SELECT c FROM ClassAlo c WHERE c.classAloValor = :classAloValor")
    , @NamedQuery(name = "ClassAlo.findByClassAloComentario", query = "SELECT c FROM ClassAlo c WHERE c.classAloComentario = :classAloComentario")})
public class ClassAlo implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CLASS_ALO_ID")
    
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigDecimal classAloId;
    @Basic(optional = false)
    @Column(name = "CLASS_ALO_VALOR")
    private BigInteger classAloValor;
    @Column(name = "CLASS_ALO_COMENTARIO")
    private String classAloComentario;
    @JoinColumn(name = "ALOJAMENTO_ID", referencedColumnName = "ALOJAMENTO_ID")
    @ManyToOne
    private Alojamento alojamentoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public ClassAlo() {
    }

    public ClassAlo(BigDecimal classAloId) {
        this.classAloId = classAloId;
    }

    public ClassAlo(BigDecimal classAloId, BigInteger classAloValor) {
        this.classAloId = classAloId;
        this.classAloValor = classAloValor;
    }

    public BigDecimal getClassAloId() {
        return classAloId;
    }

    public void setClassAloId(BigDecimal classAloId) {
        this.classAloId = classAloId;
    }

    public BigInteger getClassAloValor() {
        return classAloValor;
    }

    public void setClassAloValor(BigInteger classAloValor) {
        this.classAloValor = classAloValor;
    }

    public String getClassAloComentario() {
        return classAloComentario;
    }

    public void setClassAloComentario(String classAloComentario) {
        this.classAloComentario = classAloComentario;
    }

    public Alojamento getAlojamentoId() {
        return alojamentoId;
    }

    public void setAlojamentoId(Alojamento alojamentoId) {
        this.alojamentoId = alojamentoId;
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
        hash += (classAloId != null ? classAloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassAlo)) {
            return false;
        }
        ClassAlo other = (ClassAlo) object;
        if ((this.classAloId == null && other.classAloId != null) || (this.classAloId != null && !this.classAloId.equals(other.classAloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.ClassAlo[ classAloId=" + classAloId + " ]";
    }
    
}
