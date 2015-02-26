package com.tsystems.javaschool.dao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class GenericDAOImpl<T, PK extends Serializable>
        implements GenericDAO<T, PK> {

    // TODO: it must be private
    // TODO: if you need an access create getter
    protected Class<T> entityClass;
    protected EntityManager entityManager;

    public GenericDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GenericDAOImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass
                .getActualTypeArguments()[0];
    }

    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    public T update(T t) {
        return this.entityManager.merge(t);
    }

    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }
}