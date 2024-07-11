package it.corso.dao;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {

}
