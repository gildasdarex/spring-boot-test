package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Presence;

public interface PresenceRepository extends CrudRepository<Presence, Integer> {
	@Query("select pr from Presence pr  where pr.candidat.idcandidat=?1 AND pr.formation.idformation=?2" )
	Presence findPresence(Integer idcandidat, Integer idformation);
	
	@Query("select pr from Presence pr where pr.formation.idformation=?1" )
	Iterable<Presence> getPresenceByFormation(Integer idformation);
}
