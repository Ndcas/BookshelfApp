/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import models.Sach;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.ChuyenMuc;

/**
 *
 * @author student
 */
public class ChuyenMucJpaController implements Serializable {

    public ChuyenMucJpaController() {
        this.emf = Persistence.createEntityManagerFactory("BookshelfAppPU");
    }
    
    public ChuyenMucJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ChuyenMuc chuyenMuc) {
        if (chuyenMuc.getSachCollection() == null) {
            chuyenMuc.setSachCollection(new ArrayList<Sach>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Sach> attachedSachCollection = new ArrayList<Sach>();
            for (Sach sachCollectionSachToAttach : chuyenMuc.getSachCollection()) {
                sachCollectionSachToAttach = em.getReference(sachCollectionSachToAttach.getClass(), sachCollectionSachToAttach.getId());
                attachedSachCollection.add(sachCollectionSachToAttach);
            }
            chuyenMuc.setSachCollection(attachedSachCollection);
            em.persist(chuyenMuc);
            for (Sach sachCollectionSach : chuyenMuc.getSachCollection()) {
                ChuyenMuc oldChuyenMucOfSachCollectionSach = sachCollectionSach.getChuyenMuc();
                sachCollectionSach.setChuyenMuc(chuyenMuc);
                sachCollectionSach = em.merge(sachCollectionSach);
                if (oldChuyenMucOfSachCollectionSach != null) {
                    oldChuyenMucOfSachCollectionSach.getSachCollection().remove(sachCollectionSach);
                    oldChuyenMucOfSachCollectionSach = em.merge(oldChuyenMucOfSachCollectionSach);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ChuyenMuc chuyenMuc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChuyenMuc persistentChuyenMuc = em.find(ChuyenMuc.class, chuyenMuc.getId());
            Collection<Sach> sachCollectionOld = persistentChuyenMuc.getSachCollection();
            Collection<Sach> sachCollectionNew = chuyenMuc.getSachCollection();
            List<String> illegalOrphanMessages = null;
            for (Sach sachCollectionOldSach : sachCollectionOld) {
                if (!sachCollectionNew.contains(sachCollectionOldSach)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sach " + sachCollectionOldSach + " since its chuyenMuc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Sach> attachedSachCollectionNew = new ArrayList<Sach>();
            for (Sach sachCollectionNewSachToAttach : sachCollectionNew) {
                sachCollectionNewSachToAttach = em.getReference(sachCollectionNewSachToAttach.getClass(), sachCollectionNewSachToAttach.getId());
                attachedSachCollectionNew.add(sachCollectionNewSachToAttach);
            }
            sachCollectionNew = attachedSachCollectionNew;
            chuyenMuc.setSachCollection(sachCollectionNew);
            chuyenMuc = em.merge(chuyenMuc);
            for (Sach sachCollectionNewSach : sachCollectionNew) {
                if (!sachCollectionOld.contains(sachCollectionNewSach)) {
                    ChuyenMuc oldChuyenMucOfSachCollectionNewSach = sachCollectionNewSach.getChuyenMuc();
                    sachCollectionNewSach.setChuyenMuc(chuyenMuc);
                    sachCollectionNewSach = em.merge(sachCollectionNewSach);
                    if (oldChuyenMucOfSachCollectionNewSach != null && !oldChuyenMucOfSachCollectionNewSach.equals(chuyenMuc)) {
                        oldChuyenMucOfSachCollectionNewSach.getSachCollection().remove(sachCollectionNewSach);
                        oldChuyenMucOfSachCollectionNewSach = em.merge(oldChuyenMucOfSachCollectionNewSach);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = chuyenMuc.getId();
                if (findChuyenMuc(id) == null) {
                    throw new NonexistentEntityException("The chuyenMuc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ChuyenMuc chuyenMuc;
            try {
                chuyenMuc = em.getReference(ChuyenMuc.class, id);
                chuyenMuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The chuyenMuc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Sach> sachCollectionOrphanCheck = chuyenMuc.getSachCollection();
            for (Sach sachCollectionOrphanCheckSach : sachCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ChuyenMuc (" + chuyenMuc + ") cannot be destroyed since the Sach " + sachCollectionOrphanCheckSach + " in its sachCollection field has a non-nullable chuyenMuc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(chuyenMuc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ChuyenMuc> findChuyenMucEntities() {
        return findChuyenMucEntities(true, -1, -1);
    }

    public List<ChuyenMuc> findChuyenMucEntities(int maxResults, int firstResult) {
        return findChuyenMucEntities(false, maxResults, firstResult);
    }

    private List<ChuyenMuc> findChuyenMucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ChuyenMuc.class));
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

    public ChuyenMuc findChuyenMuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ChuyenMuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getChuyenMucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ChuyenMuc> rt = cq.from(ChuyenMuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
