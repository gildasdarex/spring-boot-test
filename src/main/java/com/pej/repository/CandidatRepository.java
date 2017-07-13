package com.pej.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pej.domains.Formationbeneficaire;
import com.pej.domains.Candidat;

public interface CandidatRepository extends CrudRepository<Candidat, Integer>{
	
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getNotInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1)" )
	Iterable<Candidat> getInFormationCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  not in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getNotInCooperativeCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Beneficiairecooperative fb where fb.cooperative.idgroupe=?1)" )
	Iterable<Candidat> getInCooperativeCandidat(Integer id);
	
	
	@Query("select ca from Candidat ca where ca.statutcandidat.id= ?1 and ca.age >= 18 and ca.age <=35  ")
	Iterable<Candidat> getEligibleCandidat(Integer statut);
	
	@Query("select ca from Candidat ca where ca.statutcandidat.id < 2 and ca.age >= 18 and ca.age <= 35  ")
	Iterable<Candidat> getEligibleCandidat();
	
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?1)" )
	Iterable<Candidat> getInPresenceCandidat(Integer id);
	
	@Query("select ca from Candidat ca where ca.idcandidat  in (select fb.candidat.idcandidat from Formationbeneficaire fb where fb.formation.idformation=?1) and ca.idcandidat  not in (select pr.candidat.idcandidat from Presence pr where pr.formation.idformation=?1)" )
	Iterable<Candidat> getInAbsenceCandidat(Integer id);
	


}
