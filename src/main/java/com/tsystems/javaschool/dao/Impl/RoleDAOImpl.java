package com.tsystems.javaschool.dao.Impl;


import com.tsystems.javaschool.dao.RoleDAO;
import com.tsystems.javaschool.entities.Role;
import org.springframework.stereotype.Repository;

@Repository("RoleDAOImpl")
public class RoleDAOImpl extends GenericDaoImpl<Role> implements RoleDAO {

}
