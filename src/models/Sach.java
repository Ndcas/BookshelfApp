/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author student
 */
@Entity
@Table(name = "Sach")
@NamedQueries({
    @NamedQuery(name = "Sach.findAll", query = "SELECT s FROM Sach s"),
    @NamedQuery(name = "Sach.findById", query = "SELECT s FROM Sach s WHERE s.id = :id"),
    @NamedQuery(name = "Sach.findByTen", query = "SELECT s FROM Sach s WHERE s.ten = :ten"),
    @NamedQuery(name = "Sach.findBySoTrangChuong", query = "SELECT s FROM Sach s WHERE s.soTrangChuong = :soTrangChuong"),
    @NamedQuery(name = "Sach.findByDaDoc", query = "SELECT s FROM Sach s WHERE s.daDoc = :daDoc"),
    @NamedQuery(name = "Sach.findByMoTa", query = "SELECT s FROM Sach s WHERE s.moTa = :moTa"),
    @NamedQuery(name = "Sach.findByGhiChu", query = "SELECT s FROM Sach s WHERE s.ghiChu = :ghiChu")})
public class Sach implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Ten")
    private String ten;
    @Basic(optional = false)
    @Column(name = "SoTrangChuong")
    private int soTrangChuong;
    @Basic(optional = false)
    @Column(name = "DaDoc")
    private int daDoc;
    @Column(name = "MoTa")
    private String moTa;
    @Basic(optional = false)
    @Lob
    @Column(name = "AnhBia")
    private byte[] anhBia;
    @Column(name = "GhiChu")
    private String ghiChu;
    @JoinColumn(name = "ChuyenMuc", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ChuyenMuc chuyenMuc;
    @JoinColumn(name = "NguoiDung", referencedColumnName = "TaiKhoan")
    @ManyToOne(optional = false)
    private NguoiDung nguoiDung;

    public Sach() {
    }

    public Sach(Integer id) {
        this.id = id;
    }

    public Sach(Integer id, String ten, int soTrangChuong, int daDoc, byte[] anhBia) {
        this.id = id;
        this.ten = ten;
        this.soTrangChuong = soTrangChuong;
        this.daDoc = daDoc;
        this.anhBia = anhBia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoTrangChuong() {
        return soTrangChuong;
    }

    public void setSoTrangChuong(int soTrangChuong) {
        this.soTrangChuong = soTrangChuong;
    }

    public int getDaDoc() {
        return daDoc;
    }

    public void setDaDoc(int daDoc) {
        this.daDoc = daDoc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public byte[] getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(byte[] anhBia) {
        this.anhBia = anhBia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public ChuyenMuc getChuyenMuc() {
        return chuyenMuc;
    }

    public void setChuyenMuc(ChuyenMuc chuyenMuc) {
        this.chuyenMuc = chuyenMuc;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
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
        if (!(object instanceof Sach)) {
            return false;
        }
        Sach other = (Sach) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Sach[ id=" + id + " ]";
    }
    
}
