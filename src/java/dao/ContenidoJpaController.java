/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Contenido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.SeccionMicrocurriculo;
import dto.Cumplimiento;
import java.util.ArrayList;
import java.util.List;
import dto.TablaInfo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sachikia
 */
public class ContenidoJpaController implements Serializable {

    public ContenidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contenido contenido) {
        if (contenido.getCumplimientoList() == null) {
            contenido.setCumplimientoList(new ArrayList<Cumplimiento>());
        }
        if (contenido.getTablaInfoList() == null) {
            contenido.setTablaInfoList(new ArrayList<TablaInfo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeccionMicrocurriculo seccionMicrocurriculoId = contenido.getSeccionMicrocurriculoId();
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId = em.getReference(seccionMicrocurriculoId.getClass(), seccionMicrocurriculoId.getId());
                contenido.setSeccionMicrocurriculoId(seccionMicrocurriculoId);
            }
            List<Cumplimiento> attachedCumplimientoList = new ArrayList<Cumplimiento>();
            for (Cumplimiento cumplimientoListCumplimientoToAttach : contenido.getCumplimientoList()) {
                cumplimientoListCumplimientoToAttach = em.getReference(cumplimientoListCumplimientoToAttach.getClass(), cumplimientoListCumplimientoToAttach.getMateriaPeriodoGrupoId());
                attachedCumplimientoList.add(cumplimientoListCumplimientoToAttach);
            }
            contenido.setCumplimientoList(attachedCumplimientoList);
            List<TablaInfo> attachedTablaInfoList = new ArrayList<TablaInfo>();
            for (TablaInfo tablaInfoListTablaInfoToAttach : contenido.getTablaInfoList()) {
                tablaInfoListTablaInfoToAttach = em.getReference(tablaInfoListTablaInfoToAttach.getClass(), tablaInfoListTablaInfoToAttach.getTablaInfoPK());
                attachedTablaInfoList.add(tablaInfoListTablaInfoToAttach);
            }
            contenido.setTablaInfoList(attachedTablaInfoList);
            em.persist(contenido);
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId.getContenidoList().add(contenido);
                seccionMicrocurriculoId = em.merge(seccionMicrocurriculoId);
            }
            for (Cumplimiento cumplimientoListCumplimiento : contenido.getCumplimientoList()) {
                Contenido oldContenidoIdOfCumplimientoListCumplimiento = cumplimientoListCumplimiento.getContenidoId();
                cumplimientoListCumplimiento.setContenidoId(contenido);
                cumplimientoListCumplimiento = em.merge(cumplimientoListCumplimiento);
                if (oldContenidoIdOfCumplimientoListCumplimiento != null) {
                    oldContenidoIdOfCumplimientoListCumplimiento.getCumplimientoList().remove(cumplimientoListCumplimiento);
                    oldContenidoIdOfCumplimientoListCumplimiento = em.merge(oldContenidoIdOfCumplimientoListCumplimiento);
                }
            }
            for (TablaInfo tablaInfoListTablaInfo : contenido.getTablaInfoList()) {
                Contenido oldContenidoIdOfTablaInfoListTablaInfo = tablaInfoListTablaInfo.getContenidoId();
                tablaInfoListTablaInfo.setContenidoId(contenido);
                tablaInfoListTablaInfo = em.merge(tablaInfoListTablaInfo);
                if (oldContenidoIdOfTablaInfoListTablaInfo != null) {
                    oldContenidoIdOfTablaInfoListTablaInfo.getTablaInfoList().remove(tablaInfoListTablaInfo);
                    oldContenidoIdOfTablaInfoListTablaInfo = em.merge(oldContenidoIdOfTablaInfoListTablaInfo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contenido contenido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contenido persistentContenido = em.find(Contenido.class, contenido.getId());
            SeccionMicrocurriculo seccionMicrocurriculoIdOld = persistentContenido.getSeccionMicrocurriculoId();
            SeccionMicrocurriculo seccionMicrocurriculoIdNew = contenido.getSeccionMicrocurriculoId();
            List<Cumplimiento> cumplimientoListOld = persistentContenido.getCumplimientoList();
            List<Cumplimiento> cumplimientoListNew = contenido.getCumplimientoList();
            List<TablaInfo> tablaInfoListOld = persistentContenido.getTablaInfoList();
            List<TablaInfo> tablaInfoListNew = contenido.getTablaInfoList();
            List<String> illegalOrphanMessages = null;
            for (Cumplimiento cumplimientoListOldCumplimiento : cumplimientoListOld) {
                if (!cumplimientoListNew.contains(cumplimientoListOldCumplimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cumplimiento " + cumplimientoListOldCumplimiento + " since its contenidoId field is not nullable.");
                }
            }
            for (TablaInfo tablaInfoListOldTablaInfo : tablaInfoListOld) {
                if (!tablaInfoListNew.contains(tablaInfoListOldTablaInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TablaInfo " + tablaInfoListOldTablaInfo + " since its contenidoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (seccionMicrocurriculoIdNew != null) {
                seccionMicrocurriculoIdNew = em.getReference(seccionMicrocurriculoIdNew.getClass(), seccionMicrocurriculoIdNew.getId());
                contenido.setSeccionMicrocurriculoId(seccionMicrocurriculoIdNew);
            }
            List<Cumplimiento> attachedCumplimientoListNew = new ArrayList<Cumplimiento>();
            for (Cumplimiento cumplimientoListNewCumplimientoToAttach : cumplimientoListNew) {
                cumplimientoListNewCumplimientoToAttach = em.getReference(cumplimientoListNewCumplimientoToAttach.getClass(), cumplimientoListNewCumplimientoToAttach.getMateriaPeriodoGrupoId());
                attachedCumplimientoListNew.add(cumplimientoListNewCumplimientoToAttach);
            }
            cumplimientoListNew = attachedCumplimientoListNew;
            contenido.setCumplimientoList(cumplimientoListNew);
            List<TablaInfo> attachedTablaInfoListNew = new ArrayList<TablaInfo>();
            for (TablaInfo tablaInfoListNewTablaInfoToAttach : tablaInfoListNew) {
                tablaInfoListNewTablaInfoToAttach = em.getReference(tablaInfoListNewTablaInfoToAttach.getClass(), tablaInfoListNewTablaInfoToAttach.getTablaInfoPK());
                attachedTablaInfoListNew.add(tablaInfoListNewTablaInfoToAttach);
            }
            tablaInfoListNew = attachedTablaInfoListNew;
            contenido.setTablaInfoList(tablaInfoListNew);
            contenido = em.merge(contenido);
            if (seccionMicrocurriculoIdOld != null && !seccionMicrocurriculoIdOld.equals(seccionMicrocurriculoIdNew)) {
                seccionMicrocurriculoIdOld.getContenidoList().remove(contenido);
                seccionMicrocurriculoIdOld = em.merge(seccionMicrocurriculoIdOld);
            }
            if (seccionMicrocurriculoIdNew != null && !seccionMicrocurriculoIdNew.equals(seccionMicrocurriculoIdOld)) {
                seccionMicrocurriculoIdNew.getContenidoList().add(contenido);
                seccionMicrocurriculoIdNew = em.merge(seccionMicrocurriculoIdNew);
            }
            for (Cumplimiento cumplimientoListNewCumplimiento : cumplimientoListNew) {
                if (!cumplimientoListOld.contains(cumplimientoListNewCumplimiento)) {
                    Contenido oldContenidoIdOfCumplimientoListNewCumplimiento = cumplimientoListNewCumplimiento.getContenidoId();
                    cumplimientoListNewCumplimiento.setContenidoId(contenido);
                    cumplimientoListNewCumplimiento = em.merge(cumplimientoListNewCumplimiento);
                    if (oldContenidoIdOfCumplimientoListNewCumplimiento != null && !oldContenidoIdOfCumplimientoListNewCumplimiento.equals(contenido)) {
                        oldContenidoIdOfCumplimientoListNewCumplimiento.getCumplimientoList().remove(cumplimientoListNewCumplimiento);
                        oldContenidoIdOfCumplimientoListNewCumplimiento = em.merge(oldContenidoIdOfCumplimientoListNewCumplimiento);
                    }
                }
            }
            for (TablaInfo tablaInfoListNewTablaInfo : tablaInfoListNew) {
                if (!tablaInfoListOld.contains(tablaInfoListNewTablaInfo)) {
                    Contenido oldContenidoIdOfTablaInfoListNewTablaInfo = tablaInfoListNewTablaInfo.getContenidoId();
                    tablaInfoListNewTablaInfo.setContenidoId(contenido);
                    tablaInfoListNewTablaInfo = em.merge(tablaInfoListNewTablaInfo);
                    if (oldContenidoIdOfTablaInfoListNewTablaInfo != null && !oldContenidoIdOfTablaInfoListNewTablaInfo.equals(contenido)) {
                        oldContenidoIdOfTablaInfoListNewTablaInfo.getTablaInfoList().remove(tablaInfoListNewTablaInfo);
                        oldContenidoIdOfTablaInfoListNewTablaInfo = em.merge(oldContenidoIdOfTablaInfoListNewTablaInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenido.getId();
                if (findContenido(id) == null) {
                    throw new NonexistentEntityException("The contenido with id " + id + " no longer exists.");
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
            Contenido contenido;
            try {
                contenido = em.getReference(Contenido.class, id);
                contenido.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cumplimiento> cumplimientoListOrphanCheck = contenido.getCumplimientoList();
            for (Cumplimiento cumplimientoListOrphanCheckCumplimiento : cumplimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contenido (" + contenido + ") cannot be destroyed since the Cumplimiento " + cumplimientoListOrphanCheckCumplimiento + " in its cumplimientoList field has a non-nullable contenidoId field.");
            }
            List<TablaInfo> tablaInfoListOrphanCheck = contenido.getTablaInfoList();
            for (TablaInfo tablaInfoListOrphanCheckTablaInfo : tablaInfoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contenido (" + contenido + ") cannot be destroyed since the TablaInfo " + tablaInfoListOrphanCheckTablaInfo + " in its tablaInfoList field has a non-nullable contenidoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            SeccionMicrocurriculo seccionMicrocurriculoId = contenido.getSeccionMicrocurriculoId();
            if (seccionMicrocurriculoId != null) {
                seccionMicrocurriculoId.getContenidoList().remove(contenido);
                seccionMicrocurriculoId = em.merge(seccionMicrocurriculoId);
            }
            em.remove(contenido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contenido> findContenidoEntities() {
        return findContenidoEntities(true, -1, -1);
    }

    public List<Contenido> findContenidoEntities(int maxResults, int firstResult) {
        return findContenidoEntities(false, maxResults, firstResult);
    }

    private List<Contenido> findContenidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contenido.class));
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

    public Contenido findContenido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contenido.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contenido> rt = cq.from(Contenido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
