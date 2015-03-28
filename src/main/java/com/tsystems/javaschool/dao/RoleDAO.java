package com.tsystems.javaschool.dao;

import com.tsystems.javaschool.entities.Role;

import java.util.List;

public interface RoleDAO  {
    Role create(Role t);

    Role read(Long id);

    Role update(Role t);

    void delete(Role t);

    List<Role> getAll();
}
