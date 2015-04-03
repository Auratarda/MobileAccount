package com.tsystems.javaschool.domain.dao;

import java.util.List;

/*
* Generic interface with CRUD-methods.
*/

public interface GenericDao<T> {

    /*
    * Method writes an entity to the database
    */
    T create(T t);

    /*
    * Method writes the changes of the entity
    * to the database
    */
    T update(T t);

    /*
    * Method removes an entity from the database
    */
    void remove (T t);

    /*
    * Method reads an entity from the database
    * by entity Id
    */
    T getById(Long id);

    /*
    * Method reads all specified entities from database
    */
    List<T> getAll();
}