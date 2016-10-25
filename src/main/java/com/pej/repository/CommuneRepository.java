package com.pej.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pej.domains.Commune;

@Repository
public interface CommuneRepository  extends CrudRepository<Commune, String>{

}
