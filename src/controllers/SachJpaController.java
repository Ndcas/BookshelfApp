/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.ChuyenMuc;
import models.NguoiDung;
import models.Sach;

/**
 *
 * @author student
 */
public class SachJpaController implements Serializable {
    
    public SachJpaController() {
        this.emf = Persistence.createEntityManagerFactory("BookshelfAppPU");
    }

    public SachJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sach sach) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChuyenMuc chuyenMuc = sach.getChuyenMuc();
            if (chuyenMuc != null) {
                chuyenMuc = em.getReference(chuyenMuc.getClass(), chuyenMuc.getId());
                sach.setChuyenMuc(chuyenMuc);
            }
            NguoiDung nguoiDung = sach.getNguoiDung();
            if (nguoiDung != null) {
                nguoiDung = em.getReference(nguoiDung.getClass(), nguoiDung.getTaiKhoan());
                sach.setNguoiDung(nguoiDung);
            }
            em.persist(sach);
            if (chuyenMuc != null) {
                chuyenMuc.getSachCollection().add(sach);
                chuyenMuc = em.merge(chuyenMuc);
            }
            if (nguoiDung != null) {
                nguoiDung.getSachCollection().add(sach);
                nguoiDung = em.merge(nguoiDung);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sach sach) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sach persistentSach = em.find(Sach.class, sach.getId());
            ChuyenMuc chuyenMucOld = persistentSach.getChuyenMuc();
            ChuyenMuc chuyenMucNew = sach.getChuyenMuc();
            NguoiDung nguoiDungOld = persistentSach.getNguoiDung();
            NguoiDung nguoiDungNew = sach.getNguoiDung();
            if (chuyenMucNew != null) {
                chuyenMucNew = em.getReference(chuyenMucNew.getClass(), chuyenMucNew.getId());
                sach.setChuyenMuc(chuyenMucNew);
            }
            if (nguoiDungNew != null) {
                nguoiDungNew = em.getReference(nguoiDungNew.getClass(), nguoiDungNew.getTaiKhoan());
                sach.setNguoiDung(nguoiDungNew);
            }
            sach = em.merge(sach);
            if (chuyenMucOld != null && !chuyenMucOld.equals(chuyenMucNew)) {
                chuyenMucOld.getSachCollection().remove(sach);
                chuyenMucOld = em.merge(chuyenMucOld);
            }
            if (chuyenMucNew != null && !chuyenMucNew.equals(chuyenMucOld)) {
                chuyenMucNew.getSachCollection().add(sach);
                chuyenMucNew = em.merge(chuyenMucNew);
            }
            if (nguoiDungOld != null && !nguoiDungOld.equals(nguoiDungNew)) {
                nguoiDungOld.getSachCollection().remove(sach);
                nguoiDungOld = em.merge(nguoiDungOld);
            }
            if (nguoiDungNew != null && !nguoiDungNew.equals(nguoiDungOld)) {
                nguoiDungNew.getSachCollection().add(sach);
                nguoiDungNew = em.merge(nguoiDungNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sach.getId();
                if (findSach(id) == null) {
                    throw new NonexistentEntityException("The sach with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sach sach;
            try {
                sach = em.getReference(Sach.class, id);
                sach.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sach with id " + id + " no longer exists.", enfe);
            }
            ChuyenMuc chuyenMuc = sach.getChuyenMuc();
            if (chuyenMuc != null) {
                chuyenMuc.getSachCollection().remove(sach);
                chuyenMuc = em.merge(chuyenMuc);
            }
            NguoiDung nguoiDung = sach.getNguoiDung();
            if (nguoiDung != null) {
                nguoiDung.getSachCollection().remove(sach);
                nguoiDung = em.merge(nguoiDung);
            }
            em.remove(sach);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sach> findSachEntities() {
        return findSachEntities(true, -1, -1);
    }

    public List<Sach> findSachEntities(int maxResults, int firstResult) {
        return findSachEntities(false, maxResults, firstResult);
    }

    private List<Sach> findSachEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sach.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Sach findSach(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sach.class, id);
        } finally {
            em.close();
        }
    }

    public int getSachCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sach> rt = cq.from(Sach.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
