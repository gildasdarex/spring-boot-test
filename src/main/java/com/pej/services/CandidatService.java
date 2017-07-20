package com.pej.services;

import com.pej.domains.*;
import com.pej.pojo.HelperEnum;
import com.pej.pojo.OdkCandidat;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class CandidatService {
    Logger logger = LogManager.getLogger(CandidatService.class);

    @Autowired private org.dozer.Mapper mapper;
    @Autowired private AgentRepository agentRepository;
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private StatutcandidatRepository statutcandidatRepository;



    public boolean saveFromFile(List<OdkCandidat> odkCandidats){

        List<Candidat> candidats = new ArrayList<>();
        for (OdkCandidat odkCandidat : odkCandidats) {
            Candidat candidat = mapper.map(odkCandidat, Candidat.class);
            Agent agent = agentRepository.getAgent(odkCandidat.getAe());
            Statutcandidat statutcandidat = null;
            if(odkCandidat.getStatut().equals("1") )
                statutcandidat = statutcandidatRepository.findOneByIntitule("BENEFICIAIRE DE FORMATION");
            else
                statutcandidat = statutcandidatRepository.findOneByIntitule("CANDIDAT");
            candidat.setAgent(agent);
            candidat.setStatutcandidat(statutcandidat);
            candidat.setSexe(HelperEnum.getSexe(odkCandidat.getIs_sexe()));
            candidats.add(candidat);
        }

        for (Candidat candidat : candidats) {
            candidatRepository.save(candidat);
        }

        return true;
    }



}
