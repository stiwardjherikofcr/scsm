/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.TablaSeccion;
import dto.Contenido;
import dto.TablaInfo;
import dto.TablaInfoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sachikia
 */
public class TablaInfoJpaController implements Serializable {

    public TablaInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TablaInfo tablaInfo) throws PreexistingEntityException, Exception {
        if (tablaInfo.getTablaInfoPK() == null) {
            tablaInfo.setTablaInfoPK(new TablaInfoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaSeccion tablaSeccion = tablaInfo.getTablaSeccion();
            if (tablaSeccion != null) {
                tablaSeccion = em.getReference(tablaSeccion.getClass(), tablaSeccion.getTablaSeccionPK());
                tablaInfo.setTablaSeccion(tablaSeccion);
            }
            Contenido contenidoId = tablaInfo.getContenidoId();
            if (contenidoId != null) {
                contenidoId = em.getReference(contenidoId.getClass(), contenidoId.getId());
                tablaInfo.setContenidoId(contenidoId);
            }
            em.persist(tablaInfo);
            if (tablaSeccion != null) {
                tablaSeccion.getTablaInfoList().add(tablaInfo);
                tablaSeccion = em.merge(tablaSeccion);
            }
            if (contenidoId != null) {
                contenidoId.getTablaInfoList().add(tablaInfo);
                contenidoId = em.merge(contenidoId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTablaInfo(tablaInfo.getTablaInfoPK()) != null) {
                throw new PreexistingEntityException("TablaInfo " + tablaInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TablaInfo tablaInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaInfo persistentTablaInfo = em.find(TablaInfo.class, tablaInfo.getTablaInfoPK());
            TablaSeccion tablaSeccionOld = persistentTablaInfo.getTablaSeccion();
            TablaSeccion tablaSeccionNew = tablaInfo.getTablaSeccion();
            Contenido contenidoIdOld = persistentTablaInfo.getContenidoId();
            Contenido contenidoIdNew = tablaInfo.getContenidoId();
            if (tablaSeccionNew != null) {
                tablaSeccionNew = em.getReference(tablaSeccionNew.getClass(), tablaSeccionNew.getTablaSeccionPK());
                tablaInfo.setTablaSeccion(tablaSeccionNew);
            }
            if (contenidoIdNew != null) {
                contenidoIdNew = em.getReference(contenidoIdNew.getClass(), contenidoIdNew.getId());
                tablaInfo.setContenidoId(contenidoIdNew);
            }
            tablaInfo = em.merge(tablaInfo);
            if (tablaSeccionOld != null && !tablaSeccionOld.equals(tablaSeccionNew)) {
                tablaSeccionOld.getTablaInfoList().remove(tablaInfo);
                tablaSeccionOld = em.merge(tablaSeccionOld);
            }
            if (tablaSeccionNew != null && !tablaSeccionNew.equals(tablaSeccionOld)) {
                tablaSeccionNew.getTablaInfoList().add(tablaInfo);
                tablaSeccionNew = em.merge(tablaSeccionNew);
            }
            if (contenidoIdOld != null && !contenidoIdOld.equals(contenidoIdNew)) {
                contenidoIdOld.getTablaInfoList().remove(tablaInfo);
                contenidoIdOld = em.merge(contenidoIdOld);
            }
            if (contenidoIdNew != null && !contenidoIdNew.equals(contenidoIdOld)) {
                contenidoIdNew.getTablaInfoList().add(tablaInfo);
                contenidoIdNew = em.merge(contenidoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TablaInfoPK id = tablaInfo.getTablaInfoPK();
                if (findTablaInfo(id) == null) {
                    throw new NonexistentEntityException("The tablaInfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TablaInfoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaInfo tablaInfo;
            try {
                tablaInfo = em.getReference(TablaInfo.class, id);
                tablaInfo.getTablaInfoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tablaInfo with id " + id + " no longer exists.", enfe);
            }
            TablaSeccion tablaSeccion = tablaInfo.getTablaSeccion();
            if (tablaSeccion != null) {
                tablaSeccion.getTablaInfoList().remove(tablaInfo);
                tablaSeccion = em.merge(tablaSeccion);
            }
            Contenido contenidoId = tablaInfo.getContenidoId();
            if (contenidoId != null) {
                contenidoId.getTablaInfoList().remove(tablaInfo);
                contenidoId = em.merge(contenidoId);
            }
            em.remove(tablaInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TablaInfo> findTablaInfoEntities() {
        return findTablaInfoEntities(true, -1, -1);
    }

    public List<TablaInfo> findTablaInfoEntities(int maxResults, int firstResult) {
        return findTablaInfoEntities(false, maxResults, firstResult);
    }

    private List<TablaInfo> findTablaInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TablaInfo.class));
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

    public TablaInfo findTablaInfo(TablaInfoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TablaInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTablaInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TablaInfo> rt = cq.from(TablaInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
