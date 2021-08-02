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
import dto.MateriaPeriodoGrupo;
import dto.Contenido;
import dto.Cumplimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sachikia
 */
public class CumplimientoJpaController implements Serializable {

    public CumplimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cumplimiento cumplimiento) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        MateriaPeriodoGrupo materiaPeriodoGrupoOrphanCheck = cumplimiento.getMateriaPeriodoGrupo();
        if (materiaPeriodoGrupoOrphanCheck != null) {
            Cumplimiento oldCumplimientoOfMateriaPeriodoGrupo = materiaPeriodoGrupoOrphanCheck.getCumplimiento();
            if (oldCumplimientoOfMateriaPeriodoGrupo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The MateriaPeriodoGrupo " + materiaPeriodoGrupoOrphanCheck + " already has an item of type Cumplimiento whose materiaPeriodoGrupo column cannot be null. Please make another selection for the materiaPeriodoGrupo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaPeriodoGrupo materiaPeriodoGrupo = cumplimiento.getMateriaPeriodoGrupo();
            if (materiaPeriodoGrupo != null) {
                materiaPeriodoGrupo = em.getReference(materiaPeriodoGrupo.getClass(), materiaPeriodoGrupo.getId());
                cumplimiento.setMateriaPeriodoGrupo(materiaPeriodoGrupo);
            }
            Contenido contenidoId = cumplimiento.getContenidoId();
            if (contenidoId != null) {
                contenidoId = em.getReference(contenidoId.getClass(), contenidoId.getId());
                cumplimiento.setContenidoId(contenidoId);
            }
            em.persist(cumplimiento);
            if (materiaPeriodoGrupo != null) {
                materiaPeriodoGrupo.setCumplimiento(cumplimiento);
                materiaPeriodoGrupo = em.merge(materiaPeriodoGrupo);
            }
            if (contenidoId != null) {
                contenidoId.getCumplimientoList().add(cumplimiento);
                contenidoId = em.merge(contenidoId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCumplimiento(cumplimiento.getMateriaPeriodoGrupoId()) != null) {
                throw new PreexistingEntityException("Cumplimiento " + cumplimiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cumplimiento cumplimiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cumplimiento persistentCumplimiento = em.find(Cumplimiento.class, cumplimiento.getMateriaPeriodoGrupoId());
            MateriaPeriodoGrupo materiaPeriodoGrupoOld = persistentCumplimiento.getMateriaPeriodoGrupo();
            MateriaPeriodoGrupo materiaPeriodoGrupoNew = cumplimiento.getMateriaPeriodoGrupo();
            Contenido contenidoIdOld = persistentCumplimiento.getContenidoId();
            Contenido contenidoIdNew = cumplimiento.getContenidoId();
            List<String> illegalOrphanMessages = null;
            if (materiaPeriodoGrupoNew != null && !materiaPeriodoGrupoNew.equals(materiaPeriodoGrupoOld)) {
                Cumplimiento oldCumplimientoOfMateriaPeriodoGrupo = materiaPeriodoGrupoNew.getCumplimiento();
                if (oldCumplimientoOfMateriaPeriodoGrupo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The MateriaPeriodoGrupo " + materiaPeriodoGrupoNew + " already has an item of type Cumplimiento whose materiaPeriodoGrupo column cannot be null. Please make another selection for the materiaPeriodoGrupo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (materiaPeriodoGrupoNew != null) {
                materiaPeriodoGrupoNew = em.getReference(materiaPeriodoGrupoNew.getClass(), materiaPeriodoGrupoNew.getId());
                cumplimiento.setMateriaPeriodoGrupo(materiaPeriodoGrupoNew);
            }
            if (contenidoIdNew != null) {
                contenidoIdNew = em.getReference(contenidoIdNew.getClass(), contenidoIdNew.getId());
                cumplimiento.setContenidoId(contenidoIdNew);
            }
            cumplimiento = em.merge(cumplimiento);
            if (materiaPeriodoGrupoOld != null && !materiaPeriodoGrupoOld.equals(materiaPeriodoGrupoNew)) {
                materiaPeriodoGrupoOld.setCumplimiento(null);
                materiaPeriodoGrupoOld = em.merge(materiaPeriodoGrupoOld);
            }
            if (materiaPeriodoGrupoNew != null && !materiaPeriodoGrupoNew.equals(materiaPeriodoGrupoOld)) {
                materiaPeriodoGrupoNew.setCumplimiento(cumplimiento);
                materiaPeriodoGrupoNew = em.merge(materiaPeriodoGrupoNew);
            }
            if (contenidoIdOld != null && !contenidoIdOld.equals(contenidoIdNew)) {
                contenidoIdOld.getCumplimientoList().remove(cumplimiento);
                contenidoIdOld = em.merge(contenidoIdOld);
            }
            if (contenidoIdNew != null && !contenidoIdNew.equals(contenidoIdOld)) {
                contenidoIdNew.getCumplimientoList().add(cumplimiento);
                contenidoIdNew = em.merge(contenidoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cumplimiento.getMateriaPeriodoGrupoId();
                if (findCumplimiento(id) == null) {
                    throw new NonexistentEntityException("The cumplimiento with id " + id + " no longer exists.");
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
            Cumplimiento cumplimiento;
            try {
                cumplimiento = em.getReference(Cumplimiento.class, id);
                cumplimiento.getMateriaPeriodoGrupoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cumplimiento with id " + id + " no longer exists.", enfe);
            }
            MateriaPeriodoGrupo materiaPeriodoGrupo = cumplimiento.getMateriaPeriodoGrupo();
            if (materiaPeriodoGrupo != null) {
                materiaPeriodoGrupo.setCumplimiento(null);
                materiaPeriodoGrupo = em.merge(materiaPeriodoGrupo);
            }
            Contenido contenidoId = cumplimiento.getContenidoId();
            if (contenidoId != null) {
                contenidoId.getCumplimientoList().remove(cumplimiento);
                contenidoId = em.merge(contenidoId);
            }
            em.remove(cumplimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cumplimiento> findCumplimientoEntities() {
        return findCumplimientoEntities(true, -1, -1);
    }

    public List<Cumplimiento> findCumplimientoEntities(int maxResults, int firstResult) {
        return findCumplimientoEntities(false, maxResults, firstResult);
    }

    private List<Cumplimiento> findCumplimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cumplimiento.class));
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

    public Cumplimiento findCumplimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cumplimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCumplimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cumplimiento> rt = cq.from(Cumplimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
