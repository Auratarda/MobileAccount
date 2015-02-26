package com.tsystems.javaschool.dao;

import java.io.Serializable;

public interface GenericDAO<T, PK extends Serializable> {
    T create(T t);

    T read(PK id);

    T update(T t);

    void delete(T t);
}