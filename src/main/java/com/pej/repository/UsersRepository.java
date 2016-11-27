package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Roles;
import com.pej.domains.Utilisateur;

public interface UsersRepository extends  CrudRepository <Utilisateur, Integer>{
	@Query("select ut from Utilisateur ut where ut.idusers  not in (select ur.utilisateur.idusers from UsersRole ur  where ur.roles.idrole=?1)" )
	Iterable<Roles> getNotInRoleUser(Integer id);
	
	@Query("select ut from Utilisateur ut where ut.idusers  in (select ur.utilisateur.idusers from UsersRole ur  where ur.roles.idrole=?1)" )
	Iterable<Roles> getInRoleUser(Integer id);
}
