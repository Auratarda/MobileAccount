package com.tsystems.javaschool.domain.dao.Impl;


import com.tsystems.javaschool.domain.dao.RoleDAO;
import com.tsystems.javaschool.domain.entities.Role;
import org.springframework.stereotype.Repository;

@Repository("RoleDAOImpl")
public class RoleDAOImpl extends GenericDaoImpl<Role> implements RoleDAO {

}
