package com.pej.services;

import com.pej.domains.*;
import com.pej.pojo.CandidatPresence;
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
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
    @Autowired private FormationCooperativeRepository formationCooperativeRepository;
    @Autowired private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;



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
            candidat.setDocidentite(HelperEnum.getDocumentIdentite(odkCandidat.getCandidat_document_identite()));
            candidat.setActiviteprincipale(HelperEnum.getActivite(odkCandidat.getActivite_principale()));
            candidats.add(candidat);
        }

        for (Candidat candidat : candidats) {
            candidatRepository.save(candidat);
        }

        return true;
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
