package com.pej.services;

import com.pej.domains.*;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class CooperativeService {
    Logger logger = LogManager.getLogger(CooperativeService.class);

    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private FormationCooperativeRepository formationcooperativeRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private CandidatRepository candidatRepository;


    public boolean addFormationToCooperative(Integer idformation, Integer idgroupe){
        Formation formation = formationRepository.findOne(idformation);
        Cooperative cooperative = cooperativeRepository.findOne(idgroupe);

        Formationcooperative formationcooperative = new Formationcooperative();
        formationcooperative.setCooperative(cooperative);
        formationcooperative.setFormation(formation);

        if (formation != null && cooperative != null) {
            formationcooperativeRepository.save(formationcooperative);
        }

        return true;
    }


    public boolean deleteFormationFromCooperative(Integer idformation, Integer idgroupe){
        Formationcooperative formationcooperative = formationcooperativeRepository.findFb(idgroupe, idformation);
        if (formationcooperative != null)
            formationcooperativeRepository.delete(formationcooperative.getIdformationcooperative());


        return true;
    }



}
