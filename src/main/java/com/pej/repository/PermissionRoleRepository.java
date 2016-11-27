package com.pej.repository;

import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Rolespermission;;

public interface PermissionRoleRepository extends  CrudRepository <Rolespermission, Integer> {

}
