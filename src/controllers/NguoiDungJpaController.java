/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.NguoiDung;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Sach;

/**
 *
 * @author student
 */
public class NguoiDungJpaController implements Serializable {

    public NguoiDungJpaController() {
        this.emf = Persistence.createEntityManagerFactory("BookshelfAppPU");
    }
    
    public NguoiDungJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NguoiDung nguoiDung) throws PreexistingEntityException, Exception {
        if (nguoiDung.getNguoiDungCollection() == null) {
            nguoiDung.setNguoiDungCollection(new ArrayList<NguoiDung>());
        }
        if (nguoiDung.getNguoiDungCollection1() == null) {
            nguoiDung.setNguoiDungCollection1(new ArrayList<NguoiDung>());
        }
        if (nguoiDung.getSachCollection() == null) {
            nguoiDung.setSachCollection(new ArrayList<Sach>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<NguoiDung> attachedNguoiDungCollection = new ArrayList<NguoiDung>();
            for (NguoiDung nguoiDungCollectionNguoiDungToAttach : nguoiDung.getNguoiDungCollection()) {
                nguoiDungCollectionNguoiDungToAttach = em.getReference(nguoiDungCollectionNguoiDungToAttach.getClass(), nguoiDungCollectionNguoiDungToAttach.getTaiKhoan());
                attachedNguoiDungCollection.add(nguoiDungCollectionNguoiDungToAttach);
            }
            nguoiDung.setNguoiDungCollection(attachedNguoiDungCollection);
            Collection<NguoiDung> attachedNguoiDungCollection1 = new ArrayList<NguoiDung>();
            for (NguoiDung nguoiDungCollection1NguoiDungToAttach : nguoiDung.getNguoiDungCollection1()) {
                nguoiDungCollection1NguoiDungToAttach = em.getReference(nguoiDungCollection1NguoiDungToAttach.getClass(), nguoiDungCollection1NguoiDungToAttach.getTaiKhoan());
                attachedNguoiDungCollection1.add(nguoiDungCollection1NguoiDungToAttach);
            }
            nguoiDung.setNguoiDungCollection1(attachedNguoiDungCollection1);
            Collection<Sach> attachedSachCollection = new ArrayList<Sach>();
            for (Sach sachCollectionSachToAttach : nguoiDung.getSachCollection()) {
                sachCollectionSachToAttach = em.getReference(sachCollectionSachToAttach.getClass(), sachCollectionSachToAttach.getId());
                attachedSachCollection.add(sachCollectionSachToAttach);
            }
            nguoiDung.setSachCollection(attachedSachCollection);
            em.persist(nguoiDung);
            for (NguoiDung nguoiDungCollectionNguoiDung : nguoiDung.getNguoiDungCollection()) {
                nguoiDungCollectionNguoiDung.getNguoiDungCollection().add(nguoiDung);
                nguoiDungCollectionNguoiDung = em.merge(nguoiDungCollectionNguoiDung);
            }
            for (NguoiDung nguoiDungCollection1NguoiDung : nguoiDung.getNguoiDungCollection1()) {
                nguoiDungCollection1NguoiDung.getNguoiDungCollection().add(nguoiDung);
                nguoiDungCollection1NguoiDung = em.merge(nguoiDungCollection1NguoiDung);
            }
            for (Sach sachCollectionSach : nguoiDung.getSachCollection()) {
                NguoiDung oldNguoiDungOfSachCollectionSach = sachCollectionSach.getNguoiDung();
                sachCollectionSach.setNguoiDung(nguoiDung);
                sachCollectionSach = em.merge(sachCollectionSach);
                if (oldNguoiDungOfSachCollectionSach != null) {
                    oldNguoiDungOfSachCollectionSach.getSachCollection().remove(sachCollectionSach);
                    oldNguoiDungOfSachCollectionSach = em.merge(oldNguoiDungOfSachCollectionSach);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNguoiDung(nguoiDung.getTaiKhoan()) != null) {
                throw new PreexistingEntityException("NguoiDung " + nguoiDung + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NguoiDung nguoiDung) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NguoiDung persistentNguoiDung = em.find(NguoiDung.class, nguoiDung.getTaiKhoan());
            Collection<NguoiDung> nguoiDungCollectionOld = persistentNguoiDung.getNguoiDungCollection();
            Collection<NguoiDung> nguoiDungCollectionNew = nguoiDung.getNguoiDungCollection();
            Collection<NguoiDung> nguoiDungCollection1Old = persistentNguoiDung.getNguoiDungCollection1();
            Collection<NguoiDung> nguoiDungCollection1New = nguoiDung.getNguoiDungCollection1();
            Collection<Sach> sachCollectionOld = persistentNguoiDung.getSachCollection();
            Collection<Sach> sachCollectionNew = nguoiDung.getSachCollection();
            List<String> illegalOrphanMessages = null;
            for (Sach sachCollectionOldSach : sachCollectionOld) {
                if (!sachCollectionNew.contains(sachCollectionOldSach)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sach " + sachCollectionOldSach + " since its nguoiDung field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<NguoiDung> attachedNguoiDungCollectionNew = new ArrayList<NguoiDung>();
            for (NguoiDung nguoiDungCollectionNewNguoiDungToAttach : nguoiDungCollectionNew) {
                nguoiDungCollectionNewNguoiDungToAttach = em.getReference(nguoiDungCollectionNewNguoiDungToAttach.getClass(), nguoiDungCollectionNewNguoiDungToAttach.getTaiKhoan());
                attachedNguoiDungCollectionNew.add(nguoiDungCollectionNewNguoiDungToAttach);
            }
            nguoiDungCollectionNew = attachedNguoiDungCollectionNew;
            nguoiDung.setNguoiDungCollection(nguoiDungCollectionNew);
            Collection<NguoiDung> attachedNguoiDungCollection1New = new ArrayList<NguoiDung>();
            for (NguoiDung nguoiDungCollection1NewNguoiDungToAttach : nguoiDungCollection1New) {
                nguoiDungCollection1NewNguoiDungToAttach = em.getReference(nguoiDungCollection1NewNguoiDungToAttach.getClass(), nguoiDungCollection1NewNguoiDungToAttach.getTaiKhoan());
                attachedNguoiDungCollection1New.add(nguoiDungCollection1NewNguoiDungToAttach);
            }
            nguoiDungCollection1New = attachedNguoiDungCollection1New;
            nguoiDung.setNguoiDungCollection1(nguoiDungCollection1New);
            Collection<Sach> attachedSachCollectionNew = new ArrayList<Sach>();
            for (Sach sachCollectionNewSachToAttach : sachCollectionNew) {
                sachCollectionNewSachToAttach = em.getReference(sachCollectionNewSachToAttach.getClass(), sachCollectionNewSachToAttach.getId());
                attachedSachCollectionNew.add(sachCollectionNewSachToAttach);
            }
            sachCollectionNew = attachedSachCollectionNew;
            nguoiDung.setSachCollection(sachCollectionNew);
            nguoiDung = em.merge(nguoiDung);
            for (NguoiDung nguoiDungCollectionOldNguoiDung : nguoiDungCollectionOld) {
                if (!nguoiDungCollectionNew.contains(nguoiDungCollectionOldNguoiDung)) {
                    nguoiDungCollectionOldNguoiDung.getNguoiDungCollection().remove(nguoiDung);
                    nguoiDungCollectionOldNguoiDung = em.merge(nguoiDungCollectionOldNguoiDung);
                }
            }
            for (NguoiDung nguoiDungCollectionNewNguoiDung : nguoiDungCollectionNew) {
                if (!nguoiDungCollectionOld.contains(nguoiDungCollectionNewNguoiDung)) {
                    nguoiDungCollectionNewNguoiDung.getNguoiDungCollection().add(nguoiDung);
                    nguoiDungCollectionNewNguoiDung = em.merge(nguoiDungCollectionNewNguoiDung);
                }
            }
            for (NguoiDung nguoiDungCollection1OldNguoiDung : nguoiDungCollection1Old) {
                if (!nguoiDungCollection1New.contains(nguoiDungCollection1OldNguoiDung)) {
                    nguoiDungCollection1OldNguoiDung.getNguoiDungCollection().remove(nguoiDung);
                    nguoiDungCollection1OldNguoiDung = em.merge(nguoiDungCollection1OldNguoiDung);
                }
            }
            for (NguoiDung nguoiDungCollection1NewNguoiDung : nguoiDungCollection1New) {
                if (!nguoiDungCollection1Old.contains(nguoiDungCollection1NewNguoiDung)) {
                    nguoiDungCollection1NewNguoiDung.getNguoiDungCollection().add(nguoiDung);
                    nguoiDungCollection1NewNguoiDung = em.merge(nguoiDungCollection1NewNguoiDung);
                }
            }
            for (Sach sachCollectionNewSach : sachCollectionNew) {
                if (!sachCollectionOld.contains(sachCollectionNewSach)) {
                    NguoiDung oldNguoiDungOfSachCollectionNewSach = sachCollectionNewSach.getNguoiDung();
                    sachCollectionNewSach.setNguoiDung(nguoiDung);
                    sachCollectionNewSach = em.merge(sachCollectionNewSach);
                    if (oldNguoiDungOfSachCollectionNewSach != null && !oldNguoiDungOfSachCollectionNewSach.equals(nguoiDung)) {
                        oldNguoiDungOfSachCollectionNewSach.getSachCollection().remove(sachCollectionNewSach);
                        oldNguoiDungOfSachCollectionNewSach = em.merge(oldNguoiDungOfSachCollectionNewSach);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = nguoiDung.getTaiKhoan();
                if (findNguoiDung(id) == null) {
                    throw new NonexistentEntityException("The nguoiDung with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NguoiDung nguoiDung;
            try {
                nguoiDung = em.getReference(NguoiDung.class, id);
                nguoiDung.getTaiKhoan();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nguoiDung with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sach> sachCollectionOrphanCheck = nguoiDung.getSachCollection();
            for (Sach sachCollectionOrphanCheckSach : sachCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NguoiDung (" + nguoiDung + ") cannot be destroyed since the Sach " + sachCollectionOrphanCheckSach + " in its sachCollection field has a non-nullable nguoiDung field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<NguoiDung> nguoiDungCollection = nguoiDung.getNguoiDungCollection();
            for (NguoiDung nguoiDungCollectionNguoiDung : nguoiDungCollection) {
                nguoiDungCollectionNguoiDung.getNguoiDungCollection().remove(nguoiDung);
                nguoiDungCollectionNguoiDung = em.merge(nguoiDungCollectionNguoiDung);
            }
            Collection<NguoiDung> nguoiDungCollection1 = nguoiDung.getNguoiDungCollection1();
            for (NguoiDung nguoiDungCollection1NguoiDung : nguoiDungCollection1) {
                nguoiDungCollection1NguoiDung.getNguoiDungCollection().remove(nguoiDung);
                nguoiDungCollection1NguoiDung = em.merge(nguoiDungCollection1NguoiDung);
            }
            em.remove(nguoiDung);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NguoiDung> findNguoiDungEntities() {
        return findNguoiDungEntities(true, -1, -1);
    }

    public List<NguoiDung> findNguoiDungEntities(int maxResults, int firstResult) {
        return findNguoiDungEntities(false, maxResults, firstResult);
    }

    private List<NguoiDung> findNguoiDungEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NguoiDung.class));
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

    public NguoiDung findNguoiDung(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NguoiDung.class, id);
        } finally {
            em.close();
        }
    }

    public int getNguoiDungCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NguoiDung> rt = cq.from(NguoiDung.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
