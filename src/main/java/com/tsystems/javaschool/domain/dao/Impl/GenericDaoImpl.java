package com.tsystems.javaschool.domain.dao.Impl;


import com.tsystems.javaschool.domain.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
/**
 * This class contains CRUD methods for all entities.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    protected Class entityClass;
    @PersistenceContext
    protected EntityManager em;

    public GenericDaoImpl(){
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class) genericSuperclass
                .getActualTypeArguments()[0];
    }

    /**
     * This method persists an entity to the database.
     */
    @Override
    public T create(T t) {
        em.persist(t);
        return t;
    }

    /**
     * This method updates an entity in the database.
     */
    @Override
    public T update(T t) {
        em.merge(t);
        return t;
    }

    /**
     * This method removes an entity from the database.
     */
    @Override
    public void remove(T t) {
        t = em.merge(t);
        em.remove(t);
    }

    /**
     * This method retrieves an entity from the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getById(Long id) {
        return (T) em.find(entityClass, id);
    }

    /**
     * This method retrieves all the entities of the same class from the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        TypedQuery<T> query = em.createQuery("from " + entityClass.getName(), entityClass);
        return query.getResultList();
    }
}