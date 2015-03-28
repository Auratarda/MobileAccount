package com.tsystems.javaschool.dao.Impl;

import com.tsystems.javaschool.dao.GenericDAO;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;


abstract class GenericDAOImpl<T, PK extends Serializable>
        implements GenericDAO<T, PK> {

    protected Class<T> entityClass;
    @PersistenceContext
    protected EntityManager entityManager;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    @Transactional
    public T update(T t) {
        this.entityManager.merge(t);
        return t;
    }

    @Transactional
    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }

    public List<T> getAll() {
        TypedQuery<T> query = entityManager.createQuery("from " + entityClass.getName(), entityClass);
        return query.getResultList();
    }
}