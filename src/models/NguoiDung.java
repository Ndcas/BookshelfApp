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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "NguoiDung")
@NamedQueries({
    @NamedQuery(name = "NguoiDung.findAll", query = "SELECT n FROM NguoiDung n"),
    @NamedQuery(name = "NguoiDung.findByTaiKhoan", query = "SELECT n FROM NguoiDung n WHERE n.taiKhoan = :taiKhoan"),
    @NamedQuery(name = "NguoiDung.findByMatKhau", query = "SELECT n FROM NguoiDung n WHERE n.matKhau = :matKhau"),
    @NamedQuery(name = "NguoiDung.findByTen", query = "SELECT n FROM NguoiDung n WHERE n.ten = :ten"),
    @NamedQuery(name = "NguoiDung.findByEmail", query = "SELECT n FROM NguoiDung n WHERE n.email = :email"),
    @NamedQuery(name = "NguoiDung.findByNguoiTheoDoi", query = "SELECT n FROM NguoiDung n WHERE n.nguoiTheoDoi = :nguoiTheoDoi"),
    @NamedQuery(name = "NguoiDung.findByDangTheoDoi", query = "SELECT n FROM NguoiDung n WHERE n.dangTheoDoi = :dangTheoDoi")})
public class NguoiDung implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TaiKhoan")
    private String taiKhoan;
    @Basic(optional = false)
    @Column(name = "MatKhau")
    private String matKhau;
    @Basic(optional = false)
    @Column(name = "Ten")
    private String ten;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "NguoiTheoDoi")
    private int nguoiTheoDoi;
    @Basic(optional = false)
    @Column(name = "DangTheoDoi")
    private int dangTheoDoi;
    @JoinTable(name = "TheoDoi", joinColumns = {
        @JoinColumn(name = "NguoiTheoDoi", referencedColumnName = "TaiKhoan")}, inverseJoinColumns = {
        @JoinColumn(name = "TheoDoi", referencedColumnName = "TaiKhoan")})
    @ManyToMany
    private Collection<NguoiDung> nguoiDungCollection;
    @ManyToMany(mappedBy = "nguoiDungCollection")
    private Collection<NguoiDung> nguoiDungCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nguoiDung")
    private Collection<Sach> sachCollection;

    public NguoiDung() {
    }

    public NguoiDung(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public NguoiDung(String taiKhoan, String matKhau, String ten, String email, int nguoiTheoDoi, int dangTheoDoi) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ten = ten;
        this.email = email;
        this.nguoiTheoDoi = nguoiTheoDoi;
        this.dangTheoDoi = dangTheoDoi;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNguoiTheoDoi() {
        return nguoiTheoDoi;
    }

    public void setNguoiTheoDoi(int nguoiTheoDoi) {
        this.nguoiTheoDoi = nguoiTheoDoi;
    }

    public int getDangTheoDoi() {
        return dangTheoDoi;
    }

    public void setDangTheoDoi(int dangTheoDoi) {
        this.dangTheoDoi = dangTheoDoi;
    }

    public Collection<NguoiDung> getNguoiDungCollection() {
        return nguoiDungCollection;
    }

    public void setNguoiDungCollection(Collection<NguoiDung> nguoiDungCollection) {
        this.nguoiDungCollection = nguoiDungCollection;
    }

    public Collection<NguoiDung> getNguoiDungCollection1() {
        return nguoiDungCollection1;
    }

    public void setNguoiDungCollection1(Collection<NguoiDung> nguoiDungCollection1) {
        this.nguoiDungCollection1 = nguoiDungCollection1;
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
        hash += (taiKhoan != null ? taiKhoan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NguoiDung)) {
            return false;
        }
        NguoiDung other = (NguoiDung) object;
        if ((this.taiKhoan == null && other.taiKhoan != null) || (this.taiKhoan != null && !this.taiKhoan.equals(other.taiKhoan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.NguoiDung[ taiKhoan=" + taiKhoan + " ]";
    }
    
}
