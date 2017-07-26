package com.pej.services;

import com.pej.domains.*;
import com.pej.pojo.CandidatPresence;
import com.pej.pojo.HelperEnum;
import com.pej.pojo.OdkCandidat;
import com.pej.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormationCooperativeRepository formationCooperativeRepository;
    @Autowired private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
    @Autowired private  DoWorkService workService;




    public boolean saveFromFile(List<OdkCandidat> odkCandidats){

        workService.doWork(odkCandidats);

        return true;
    }

    public int getNextPage(int page){
        page = page + 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAllWithPAgination(pageable);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page-1;
    }

    public int getPreviousPage(int page){
        if(page ==0) return 0;
        page = page - 1;

        Pageable pageable =  new PageRequest( page, 100 );

        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAllWithPAgination(pageable);

        if(candidats !=null && candidats.size() != 0) return page;
        else return page+1;
    }


    public List<CandidatPresence> convertToCandidatPresence(List<Candidat> candidats, Formation formation){
        List<CandidatPresence> candidatPresences = new ArrayList<>();
        for(Candidat candidat: candidats){
            CandidatPresence candidatPresence = convertToCandidatPresence( candidat,  formation);
            candidatPresences.add(candidatPresence);
        }

        return candidatPresences;

    }


    public List<Candidat> getCandidatForFormation(Integer idformation){
        List<Candidat> candidats = new ArrayList<>();
        List<Formationcooperative> formationcooperatives = formationCooperativeRepository.findByFormationIdformation(idformation);

        for(Formationcooperative formationcooperative : formationcooperatives){
            Cooperative cooperative = formationcooperative.getCooperative();
            List<Beneficiairecooperative> beneficiairecooperatives = beneficiaireCooperativeRepository.findByCooperativeIdgroupe(cooperative.getIdgroupe());
            for (Beneficiairecooperative beneficiairecooperative : beneficiairecooperatives){
                candidats.add(beneficiairecooperative.getCandidat());
            }

        }

        return candidats;
    }


    public CandidatPresence convertToCandidatPresence(Candidat candidat, Formation formation){
        CandidatPresence candidatPresence = new CandidatPresence();
        List<Presence> presences = presenceRepository.findByCandidatIdcandidatAndFormationIdformation(
                candidat.getIdcandidat(),
                formation.getIdformation()
                );

        candidatPresence.setIdCandidat(candidat.getIdcandidat());
        candidatPresence.setFormation(formation);
        candidatPresence.setPresences(presences);
        candidatPresence.setIdentite(candidat.getIdentite());


        return candidatPresence;

    }



}
