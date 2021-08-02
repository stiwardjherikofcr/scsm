/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import dto.Cumplimiento;
import dto.Docente;
import dto.MateriaPeriodo;
import dto.MateriaPeriodoGrupo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Sachikia
 */
public class MateriaPeriodoGrupoJpaController implements Serializable {

    public MateriaPeriodoGrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MateriaPeriodoGrupo materiaPeriodoGrupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cumplimiento cumplimiento = materiaPeriodoGrupo.getCumplimiento();
            if (cumplimiento != null) {
                cumplimiento = em.getReference(cumplimiento.getClass(), cumplimiento.getMateriaPeriodoGrupoId());
                materiaPeriodoGrupo.setCumplimiento(cumplimiento);
            }
            Docente docenteCodigo = materiaPeriodoGrupo.getDocenteCodigo();
            if (docenteCodigo != null) {
                docenteCodigo = em.getReference(docenteCodigo.getClass(), docenteCodigo.getCodigo());
                materiaPeriodoGrupo.setDocenteCodigo(docenteCodigo);
            }
            MateriaPeriodo materiaPeriodoId = materiaPeriodoGrupo.getMateriaPeriodoId();
            if (materiaPeriodoId != null) {
                materiaPeriodoId = em.getReference(materiaPeriodoId.getClass(), materiaPeriodoId.getId());
                materiaPeriodoGrupo.setMateriaPeriodoId(materiaPeriodoId);
            }
            em.persist(materiaPeriodoGrupo);
            if (cumplimiento != null) {
                MateriaPeriodoGrupo oldMateriaPeriodoGrupoOfCumplimiento = cumplimiento.getMateriaPeriodoGrupo();
                if (oldMateriaPeriodoGrupoOfCumplimiento != null) {
                    oldMateriaPeriodoGrupoOfCumplimiento.setCumplimiento(null);
                    oldMateriaPeriodoGrupoOfCumplimiento = em.merge(oldMateriaPeriodoGrupoOfCumplimiento);
                }
                cumplimiento.setMateriaPeriodoGrupo(materiaPeriodoGrupo);
                cumplimiento = em.merge(cumplimiento);
            }
            if (docenteCodigo != null) {
                docenteCodigo.getMateriaPeriodoGrupoList().add(materiaPeriodoGrupo);
                docenteCodigo = em.merge(docenteCodigo);
            }
            if (materiaPeriodoId != null) {
                materiaPeriodoId.getMateriaPeriodoGrupoList().add(materiaPeriodoGrupo);
                materiaPeriodoId = em.merge(materiaPeriodoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MateriaPeriodoGrupo materiaPeriodoGrupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaPeriodoGrupo persistentMateriaPeriodoGrupo = em.find(MateriaPeriodoGrupo.class, materiaPeriodoGrupo.getId());
            Cumplimiento cumplimientoOld = persistentMateriaPeriodoGrupo.getCumplimiento();
            Cumplimiento cumplimientoNew = materiaPeriodoGrupo.getCumplimiento();
            Docente docenteCodigoOld = persistentMateriaPeriodoGrupo.getDocenteCodigo();
            Docente docenteCodigoNew = materiaPeriodoGrupo.getDocenteCodigo();
            MateriaPeriodo materiaPeriodoIdOld = persistentMateriaPeriodoGrupo.getMateriaPeriodoId();
            MateriaPeriodo materiaPeriodoIdNew = materiaPeriodoGrupo.getMateriaPeriodoId();
            List<String> illegalOrphanMessages = null;
            if (cumplimientoOld != null && !cumplimientoOld.equals(cumplimientoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cumplimiento " + cumplimientoOld + " since its materiaPeriodoGrupo field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cumplimientoNew != null) {
                cumplimientoNew = em.getReference(cumplimientoNew.getClass(), cumplimientoNew.getMateriaPeriodoGrupoId());
                materiaPeriodoGrupo.setCumplimiento(cumplimientoNew);
            }
            if (docenteCodigoNew != null) {
                docenteCodigoNew = em.getReference(docenteCodigoNew.getClass(), docenteCodigoNew.getCodigo());
                materiaPeriodoGrupo.setDocenteCodigo(docenteCodigoNew);
            }
            if (materiaPeriodoIdNew != null) {
                materiaPeriodoIdNew = em.getReference(materiaPeriodoIdNew.getClass(), materiaPeriodoIdNew.getId());
                materiaPeriodoGrupo.setMateriaPeriodoId(materiaPeriodoIdNew);
            }
            materiaPeriodoGrupo = em.merge(materiaPeriodoGrupo);
            if (cumplimientoNew != null && !cumplimientoNew.equals(cumplimientoOld)) {
                MateriaPeriodoGrupo oldMateriaPeriodoGrupoOfCumplimiento = cumplimientoNew.getMateriaPeriodoGrupo();
                if (oldMateriaPeriodoGrupoOfCumplimiento != null) {
                    oldMateriaPeriodoGrupoOfCumplimiento.setCumplimiento(null);
                    oldMateriaPeriodoGrupoOfCumplimiento = em.merge(oldMateriaPeriodoGrupoOfCumplimiento);
                }
                cumplimientoNew.setMateriaPeriodoGrupo(materiaPeriodoGrupo);
                cumplimientoNew = em.merge(cumplimientoNew);
            }
            if (docenteCodigoOld != null && !docenteCodigoOld.equals(docenteCodigoNew)) {
                docenteCodigoOld.getMateriaPeriodoGrupoList().remove(materiaPeriodoGrupo);
                docenteCodigoOld = em.merge(docenteCodigoOld);
            }
            if (docenteCodigoNew != null && !docenteCodigoNew.equals(docenteCodigoOld)) {
                docenteCodigoNew.getMateriaPeriodoGrupoList().add(materiaPeriodoGrupo);
                docenteCodigoNew = em.merge(docenteCodigoNew);
            }
            if (materiaPeriodoIdOld != null && !materiaPeriodoIdOld.equals(materiaPeriodoIdNew)) {
                materiaPeriodoIdOld.getMateriaPeriodoGrupoList().remove(materiaPeriodoGrupo);
                materiaPeriodoIdOld = em.merge(materiaPeriodoIdOld);
            }
            if (materiaPeriodoIdNew != null && !materiaPeriodoIdNew.equals(materiaPeriodoIdOld)) {
                materiaPeriodoIdNew.getMateriaPeriodoGrupoList().add(materiaPeriodoGrupo);
                materiaPeriodoIdNew = em.merge(materiaPeriodoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materiaPeriodoGrupo.getId();
                if (findMateriaPeriodoGrupo(id) == null) {
                    throw new NonexistentEntityException("The materiaPeriodoGrupo with id " + id + " no longer exists.");
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
            MateriaPeriodoGrupo materiaPeriodoGrupo;
            try {
                materiaPeriodoGrupo = em.getReference(MateriaPeriodoGrupo.class, id);
                materiaPeriodoGrupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materiaPeriodoGrupo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cumplimiento cumplimientoOrphanCheck = materiaPeriodoGrupo.getCumplimiento();
            if (cumplimientoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MateriaPeriodoGrupo (" + materiaPeriodoGrupo + ") cannot be destroyed since the Cumplimiento " + cumplimientoOrphanCheck + " in its cumplimiento field has a non-nullable materiaPeriodoGrupo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Docente docenteCodigo = materiaPeriodoGrupo.getDocenteCodigo();
            if (docenteCodigo != null) {
                docenteCodigo.getMateriaPeriodoGrupoList().remove(materiaPeriodoGrupo);
                docenteCodigo = em.merge(docenteCodigo);
            }
            MateriaPeriodo materiaPeriodoId = materiaPeriodoGrupo.getMateriaPeriodoId();
            if (materiaPeriodoId != null) {
                materiaPeriodoId.getMateriaPeriodoGrupoList().remove(materiaPeriodoGrupo);
                materiaPeriodoId = em.merge(materiaPeriodoId);
            }
            em.remove(materiaPeriodoGrupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MateriaPeriodoGrupo> findMateriaPeriodoGrupoEntities() {
        return findMateriaPeriodoGrupoEntities(true, -1, -1);
    }

    public List<MateriaPeriodoGrupo> findMateriaPeriodoGrupoEntities(int maxResults, int firstResult) {
        return findMateriaPeriodoGrupoEntities(false, maxResults, firstResult);
    }

    private List<MateriaPeriodoGrupo> findMateriaPeriodoGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MateriaPeriodoGrupo.class));
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

    public MateriaPeriodoGrupo findMateriaPeriodoGrupo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MateriaPeriodoGrupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaPeriodoGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MateriaPeriodoGrupo> rt = cq.from(MateriaPeriodoGrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
