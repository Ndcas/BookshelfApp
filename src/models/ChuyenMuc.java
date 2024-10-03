/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "ChuyenMuc")
@NamedQueries({
    @NamedQuery(name = "ChuyenMuc.findAll", query = "SELECT c FROM ChuyenMuc c"),
    @NamedQuery(name = "ChuyenMuc.findById", query = "SELECT c FROM ChuyenMuc c WHERE c.id = :id"),
    @NamedQuery(name = "ChuyenMuc.findByTenChuyenMuc", query = "SELECT c FROM ChuyenMuc c WHERE c.tenChuyenMuc = :tenChuyenMuc")})
public class ChuyenMuc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TenChuyenMuc")
    private String tenChuyenMuc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chuyenMuc")
    private Collection<Sach> sachCollection;

    public ChuyenMuc() {
    }

    public ChuyenMuc(Integer id) {
        this.id = id;
    }

    public ChuyenMuc(Integer id, String tenChuyenMuc) {
        this.id = id;
        this.tenChuyenMuc = tenChuyenMuc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenChuyenMuc() {
        return tenChuyenMuc;
    }

    public void setTenChuyenMuc(String tenChuyenMuc) {
        this.tenChuyenMuc = tenChuyenMuc;
    }

    public Collection<Sach> getSachCollection() {
        return sachCollection;
    }

    public void setSachCollection(Collection<Sach> sachCollection) {
        this.sachCollection = sachCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChuyenMuc)) {
            return false;
        }
        ChuyenMuc other = (ChuyenMuc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ChuyenMuc[ id=" + id + " ]";
    }
    
}
