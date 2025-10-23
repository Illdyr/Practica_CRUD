package services.dao;


import util.JPAConexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import services.interfaces.ICRUD;

import java.util.List;


public class MyDao implements ICRUD
{


    @Override
    public <T> List<T> GetAll(String nameQuery, Class<T> clazz)
    {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            TypedQuery<T> query = em.createNamedQuery(nameQuery, clazz);
            return query.getResultList();
        }catch (Exception ex){ex.printStackTrace();}
        finally{em.close();}
        return null;
    }

    @Override
    public <T> void insert(T entity)
    {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            em.getTransaction().rollback();
        }
        finally{em.close();}

    }

    @Override
    public <T> void update(T entity)
    {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }catch (Exception ex){ex.printStackTrace();
        em.getTransaction().rollback();}
        finally{em.close();}

    }

    @Override
    public <T> void delete(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            em.getTransaction().begin();
            // Verifica si la entidad está administrada por este EntityManager
            T managed = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }



    @Override
    public <T> T findById(Integer id, Class<T> clazz)
    {
        EntityManager em = JPAConexion.getEntityManager();
        try{
            T entity = em.find(clazz, id);
            return entity;
        }catch (Exception ex){ex.printStackTrace();
        em.getTransaction().rollback();}
        finally{em.close();}
        return null;

    }
}
