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
@Table(name = "CLASS_TRILHO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClassTrilho.findAll", query = "SELECT c FROM ClassTrilho c")
    , @NamedQuery(name = "ClassTrilho.findByClassTrilhoId", query = "SELECT c FROM ClassTrilho c WHERE c.classTrilhoId = :classTrilhoId")
    , @NamedQuery(name = "ClassTrilho.findByClassTrilhoValor", query = "SELECT c FROM ClassTrilho c WHERE c.classTrilhoValor = :classTrilhoValor")
    , @NamedQuery(name = "ClassTrilho.findByClassTrilhoComentario", query = "SELECT c FROM ClassTrilho c WHERE c.classTrilhoComentario = :classTrilhoComentario")})
public class ClassTrilho implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CLASS_TRILHO_ID")
    private BigDecimal classTrilhoId;
    @Basic(optional = false)
    @Column(name = "CLASS_TRILHO_VALOR")
    private BigInteger classTrilhoValor;
    @Column(name = "CLASS_TRILHO_COMENTARIO")
    private String classTrilhoComentario;
    @JoinColumn(name = "TRILHO_ID", referencedColumnName = "TRILHO_ID")
    @ManyToOne
    private Trilho trilhoId;
    @JoinColumn(name = "UTILIZADOR_ID", referencedColumnName = "UTILIZADOR_ID")
    @ManyToOne
    private Utilizador utilizadorId;

    public ClassTrilho() {
    }

    public ClassTrilho(BigDecimal classTrilhoId) {
        this.classTrilhoId = classTrilhoId;
    }

    public ClassTrilho(BigDecimal classTrilhoId, BigInteger classTrilhoValor) {
        this.classTrilhoId = classTrilhoId;
        this.classTrilhoValor = classTrilhoValor;
    }

    public BigDecimal getClassTrilhoId() {
        return classTrilhoId;
    }

    public void setClassTrilhoId(BigDecimal classTrilhoId) {
        this.classTrilhoId = classTrilhoId;
    }

    public BigInteger getClassTrilhoValor() {
        return classTrilhoValor;
    }

    public void setClassTrilhoValor(BigInteger classTrilhoValor) {
        this.classTrilhoValor = classTrilhoValor;
    }

    public String getClassTrilhoComentario() {
        return classTrilhoComentario;
    }

    public void setClassTrilhoComentario(String classTrilhoComentario) {
        this.classTrilhoComentario = classTrilhoComentario;
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
        hash += (classTrilhoId != null ? classTrilhoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassTrilho)) {
            return false;
        }
        ClassTrilho other = (ClassTrilho) object;
        if ((this.classTrilhoId == null && other.classTrilhoId != null) || (this.classTrilhoId != null && !this.classTrilhoId.equals(other.classTrilhoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "projeto2trilho.DAL.ClassTrilho[ classTrilhoId=" + classTrilhoId + " ]";
    }
    
}
