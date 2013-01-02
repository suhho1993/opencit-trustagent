/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intel.mtwilson.as.controller;

import com.intel.mtwilson.as.controller.exceptions.NonexistentEntityException;
import com.intel.mtwilson.as.data.MwMleSource;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.intel.mtwilson.as.data.TblMle;
import java.util.List;
import javax.persistence.*;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author ssbangal
 */
public class MwMleSourceJpaController implements Serializable {
    private Logger log = LoggerFactory.getLogger(getClass());

    public MwMleSourceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MwMleSource mwMleSource) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblMle mleId = mwMleSource.getMleId();
            if (mleId != null) {
                mleId = em.getReference(mleId.getClass(), mleId.getId());
                mwMleSource.setMleId(mleId);
            }
            em.persist(mwMleSource);
            if (mleId != null) {
                mleId.getMwMleSourceCollection().add(mwMleSource);
                mleId = em.merge(mleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MwMleSource mwMleSource) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MwMleSource persistentMwMleSource = em.find(MwMleSource.class, mwMleSource.getId());
            TblMle mleIdOld = persistentMwMleSource.getMleId();
            TblMle mleIdNew = mwMleSource.getMleId();
            if (mleIdNew != null) {
                mleIdNew = em.getReference(mleIdNew.getClass(), mleIdNew.getId());
                mwMleSource.setMleId(mleIdNew);
            }
            mwMleSource = em.merge(mwMleSource);
            if (mleIdOld != null && !mleIdOld.equals(mleIdNew)) {
                mleIdOld.getMwMleSourceCollection().remove(mwMleSource);
                mleIdOld = em.merge(mleIdOld);
            }
            if (mleIdNew != null && !mleIdNew.equals(mleIdOld)) {
                mleIdNew.getMwMleSourceCollection().add(mwMleSource);
                mleIdNew = em.merge(mleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mwMleSource.getId();
                if (findMwMleSource(id) == null) {
                    throw new NonexistentEntityException("The mwMleSource with id " + id + " no longer exists.");
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
            MwMleSource mwMleSource;
            try {
                mwMleSource = em.getReference(MwMleSource.class, id);
                mwMleSource.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mwMleSource with id " + id + " no longer exists.", enfe);
            }
            TblMle mleId = mwMleSource.getMleId();
            if (mleId != null) {
                mleId.getMwMleSourceCollection().remove(mwMleSource);
                mleId = em.merge(mleId);
            }
            em.remove(mwMleSource);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MwMleSource> findMwMleSourceEntities() {
        return findMwMleSourceEntities(true, -1, -1);
    }

    public List<MwMleSource> findMwMleSourceEntities(int maxResults, int firstResult) {
        return findMwMleSourceEntities(false, maxResults, firstResult);
    }

    private List<MwMleSource> findMwMleSourceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MwMleSource.class));
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

    public MwMleSource findMwMleSource(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MwMleSource.class, id);
        } finally {
            em.close();
        }
    }

    public int getMwMleSourceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MwMleSource> rt = cq.from(MwMleSource.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public MwMleSource findByMleId(Integer id) {
        MwMleSource mleSourceObj = null;
        EntityManager em = getEntityManager();
        try {

            Query query = em.createNamedQuery("MwMleSource.findByMleID");
            query.setParameter("mleId", id);

            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            query.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);

            mleSourceObj = (MwMleSource) query.getSingleResult();
            return mleSourceObj;

        } catch(NoResultException e){
        	log.error(String.format("MLE information with identity %d not found in the DB.", id));
        	return null;
        } finally {
            em.close();
        }               
    }    
}
