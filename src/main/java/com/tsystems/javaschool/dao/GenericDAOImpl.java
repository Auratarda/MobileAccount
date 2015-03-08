package com.tsystems.javaschool.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

class GenericDAOImpl<T, PK extends Serializable>
        implements GenericDAO<T, PK> {

    private Class<T> entityClass;
    private EntityManager entityManager;

    public GenericDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T create(T t) {
        entityManager.getTransaction().begin();
        this.entityManager.persist(t);
        entityManager.getTransaction().commit();
        return t;
    }

    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    public T update(T t) {
        entityManager.getTransaction().begin();
        this.entityManager.merge(t);
        entityManager.getTransaction().commit();
        return t;
    }

    public void delete(T t) {
        entityManager.getTransaction().begin();
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
        entityManager.getTransaction().commit();
    }

    public List<T> getAll() {
        TypedQuery<T> query = entityManager.createQuery("from " + entityClass.getName(), entityClass);
        return query.getResultList();
    }
}