package com.pej.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.pej.config.Config;
import com.pej.config.TestConfig;
import com.pej.domains.Departement;

@ContextConfiguration(classes={Config.class, TestConfig.class})
@ActiveProfiles("test")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class DepartementRepositoryTest {
	@Autowired private DepartementRepository departementRepository;

    @Autowired private JdbcTemplate jdbcTemplate; 
    @PersistenceContext private EntityManager em;

    //------------------------------------------------- find all
    
    @Test
    public void testFindAll() {
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        
        assertThat(departements.size(), equalTo(4));
    }
   
}
