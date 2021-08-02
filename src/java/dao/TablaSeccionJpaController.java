/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.SeccionMicrocurriculo;
import dto.Tabla;
import dto.TablaInfo;
import dto.TablaSeccion;
import dto.TablaSeccionPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sachikia
 */
public class TablaSeccionJpaController implements Serializable {

    public TablaSeccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TablaSeccion tablaSeccion) throws PreexistingEntityException, Exception {
        if (tablaSeccion.getTablaSeccionPK() == null) {
            tablaSeccion.setTablaSeccionPK(new TablaSeccionPK());
        }
        if (tablaSeccion.getTablaInfoList() == null) {
            tablaSeccion.setTablaInfoList(new ArrayList<TablaInfo>());
        }
        tablaSeccion.getTablaSeccionPK().setTablaId(tablaSeccion.getTabla().getId());
        tablaSeccion.getTablaSeccionPK().setSeccionMicrocurriculoId(tablaSeccion.getSeccionMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionMicrocurriculo seccionMicrocurriculo = tablaSeccion.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo = em.getReference(seccionMicrocurriculo.getClass(), seccionMicrocurriculo.getId());
                tablaSeccion.setSeccionMicrocurriculo(seccionMicrocurriculo);
            }
            Tabla tabla = tablaSeccion.getTabla();
            if (tabla != null) {
                tabla = em.getReference(tabla.getClass(), tabla.getId());
                tablaSeccion.setTabla(tabla);
            }
            List<TablaInfo> attachedTablaInfoList = new ArrayList<TablaInfo>();
            for (TablaInfo tablaInfoListTablaInfoToAttach : tablaSeccion.getTablaInfoList()) {
                tablaInfoListTablaInfoToAttach = em.getReference(tablaInfoListTablaInfoToAttach.getClass(), tablaInfoListTablaInfoToAttach.getTablaInfoPK());
                attachedTablaInfoList.add(tablaInfoListTablaInfoToAttach);
            }
            tablaSeccion.setTablaInfoList(attachedTablaInfoList);
            em.persist(tablaSeccion);
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getTablaSeccionList().add(tablaSeccion);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
            }
            if (tabla != null) {
                tabla.getTablaSeccionList().add(tablaSeccion);
                tabla = em.merge(tabla);
            }
            for (TablaInfo tablaInfoListTablaInfo : tablaSeccion.getTablaInfoList()) {
                TablaSeccion oldTablaSeccionOfTablaInfoListTablaInfo = tablaInfoListTablaInfo.getTablaSeccion();
                tablaInfoListTablaInfo.setTablaSeccion(tablaSeccion);
                tablaInfoListTablaInfo = em.merge(tablaInfoListTablaInfo);
                if (oldTablaSeccionOfTablaInfoListTablaInfo != null) {
                    oldTablaSeccionOfTablaInfoListTablaInfo.getTablaInfoList().remove(tablaInfoListTablaInfo);
                    oldTablaSeccionOfTablaInfoListTablaInfo = em.merge(oldTablaSeccionOfTablaInfoListTablaInfo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTablaSeccion(tablaSeccion.getTablaSeccionPK()) != null) {
                throw new PreexistingEntityException("TablaSeccion " + tablaSeccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TablaSeccion tablaSeccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        tablaSeccion.getTablaSeccionPK().setTablaId(tablaSeccion.getTabla().getId());
        tablaSeccion.getTablaSeccionPK().setSeccionMicrocurriculoId(tablaSeccion.getSeccionMicrocurriculo().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaSeccion persistentTablaSeccion = em.find(TablaSeccion.class, tablaSeccion.getTablaSeccionPK());
            SeccionMicrocurriculo seccionMicrocurriculoOld = persistentTablaSeccion.getSeccionMicrocurriculo();
            SeccionMicrocurriculo seccionMicrocurriculoNew = tablaSeccion.getSeccionMicrocurriculo();
            Tabla tablaOld = persistentTablaSeccion.getTabla();
            Tabla tablaNew = tablaSeccion.getTabla();
            List<TablaInfo> tablaInfoListOld = persistentTablaSeccion.getTablaInfoList();
            List<TablaInfo> tablaInfoListNew = tablaSeccion.getTablaInfoList();
            List<String> illegalOrphanMessages = null;
            for (TablaInfo tablaInfoListOldTablaInfo : tablaInfoListOld) {
                if (!tablaInfoListNew.contains(tablaInfoListOldTablaInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TablaInfo " + tablaInfoListOldTablaInfo + " since its tablaSeccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (seccionMicrocurriculoNew != null) {
                seccionMicrocurriculoNew = em.getReference(seccionMicrocurriculoNew.getClass(), seccionMicrocurriculoNew.getId());
                tablaSeccion.setSeccionMicrocurriculo(seccionMicrocurriculoNew);
            }
            if (tablaNew != null) {
                tablaNew = em.getReference(tablaNew.getClass(), tablaNew.getId());
                tablaSeccion.setTabla(tablaNew);
            }
            List<TablaInfo> attachedTablaInfoListNew = new ArrayList<TablaInfo>();
            for (TablaInfo tablaInfoListNewTablaInfoToAttach : tablaInfoListNew) {
                tablaInfoListNewTablaInfoToAttach = em.getReference(tablaInfoListNewTablaInfoToAttach.getClass(), tablaInfoListNewTablaInfoToAttach.getTablaInfoPK());
                attachedTablaInfoListNew.add(tablaInfoListNewTablaInfoToAttach);
            }
            tablaInfoListNew = attachedTablaInfoListNew;
            tablaSeccion.setTablaInfoList(tablaInfoListNew);
            tablaSeccion = em.merge(tablaSeccion);
            if (seccionMicrocurriculoOld != null && !seccionMicrocurriculoOld.equals(seccionMicrocurriculoNew)) {
                seccionMicrocurriculoOld.getTablaSeccionList().remove(tablaSeccion);
                seccionMicrocurriculoOld = em.merge(seccionMicrocurriculoOld);
            }
            if (seccionMicrocurriculoNew != null && !seccionMicrocurriculoNew.equals(seccionMicrocurriculoOld)) {
                seccionMicrocurriculoNew.getTablaSeccionList().add(tablaSeccion);
                seccionMicrocurriculoNew = em.merge(seccionMicrocurriculoNew);
            }
            if (tablaOld != null && !tablaOld.equals(tablaNew)) {
                tablaOld.getTablaSeccionList().remove(tablaSeccion);
                tablaOld = em.merge(tablaOld);
            }
            if (tablaNew != null && !tablaNew.equals(tablaOld)) {
                tablaNew.getTablaSeccionList().add(tablaSeccion);
                tablaNew = em.merge(tablaNew);
            }
            for (TablaInfo tablaInfoListNewTablaInfo : tablaInfoListNew) {
                if (!tablaInfoListOld.contains(tablaInfoListNewTablaInfo)) {
                    TablaSeccion oldTablaSeccionOfTablaInfoListNewTablaInfo = tablaInfoListNewTablaInfo.getTablaSeccion();
                    tablaInfoListNewTablaInfo.setTablaSeccion(tablaSeccion);
                    tablaInfoListNewTablaInfo = em.merge(tablaInfoListNewTablaInfo);
                    if (oldTablaSeccionOfTablaInfoListNewTablaInfo != null && !oldTablaSeccionOfTablaInfoListNewTablaInfo.equals(tablaSeccion)) {
                        oldTablaSeccionOfTablaInfoListNewTablaInfo.getTablaInfoList().remove(tablaInfoListNewTablaInfo);
                        oldTablaSeccionOfTablaInfoListNewTablaInfo = em.merge(oldTablaSeccionOfTablaInfoListNewTablaInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TablaSeccionPK id = tablaSeccion.getTablaSeccionPK();
                if (findTablaSeccion(id) == null) {
                    throw new NonexistentEntityException("The tablaSeccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TablaSeccionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TablaSeccion tablaSeccion;
            try {
                tablaSeccion = em.getReference(TablaSeccion.class, id);
                tablaSeccion.getTablaSeccionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tablaSeccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TablaInfo> tablaInfoListOrphanCheck = tablaSeccion.getTablaInfoList();
            for (TablaInfo tablaInfoListOrphanCheckTablaInfo : tablaInfoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TablaSeccion (" + tablaSeccion + ") cannot be destroyed since the TablaInfo " + tablaInfoListOrphanCheckTablaInfo + " in its tablaInfoList field has a non-nullable tablaSeccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SeccionMicrocurriculo seccionMicrocurriculo = tablaSeccion.getSeccionMicrocurriculo();
            if (seccionMicrocurriculo != null) {
                seccionMicrocurriculo.getTablaSeccionList().remove(tablaSeccion);
                seccionMicrocurriculo = em.merge(seccionMicrocurriculo);
            }
            Tabla tabla = tablaSeccion.getTabla();
            if (tabla != null) {
                tabla.getTablaSeccionList().remove(tablaSeccion);
                tabla = em.merge(tabla);
            }
            em.remove(tablaSeccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TablaSeccion> findTablaSeccionEntities() {
        return findTablaSeccionEntities(true, -1, -1);
    }

    public List<TablaSeccion> findTablaSeccionEntities(int maxResults, int firstResult) {
        return findTablaSeccionEntities(false, maxResults, firstResult);
    }

    private List<TablaSeccion> findTablaSeccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TablaSeccion.class));
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

    public TablaSeccion findTablaSeccion(TablaSeccionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TablaSeccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTablaSeccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TablaSeccion> rt = cq.from(TablaSeccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
