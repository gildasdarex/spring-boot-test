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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by darextossa on 7/17/17.
 */

@Service()
public class FormationService {
    Logger logger = LogManager.getLogger(FormationService.class);

    @Autowired private FormationRepository formationRepository;
    @Autowired private FormationCooperativeRepository formationCooperativeRepository;
    @Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
    @Autowired private PresenceRepository presenceRepository;
    @Autowired private FormateurRepository formateurRepository;


    public boolean deleteFormation(Integer id){

       Formation formation = formationRepository.findOne(id);
       Set<Formationbeneficaire> formationbeneficaires = formation.getFormationbeneficaires();
       Set<Formationcooperative> formationcooperatives = formation.getFormationcooperatives();
       Set<Presence> presences = formation.getPresences();
       List<Formateur> formateurs = formation.getFormateurSet();

       for(Formateur formateur : formateurs){
           formateur.getFormations().remove(formation);
           formateurRepository.save(formateur);
       }


       formationBeneficiareRepository.delete(formationbeneficaires);
       formationCooperativeRepository.delete(formationcooperatives);
       presenceRepository.delete(presences);
       formationRepository.delete(formation);

       return true;
    }


    public void saveFormation(Formation formation){
        Integer idFormationBeforePersist = formation.getIdformation();

        if (idFormationBeforePersist != null && idFormationBeforePersist.intValue() > 0)
            editFormation(formation);
        else{
            List<Integer> idFormateurs = formation.getFormateurSet().stream()
                                           .map(Formateur::getIdformateur)
                                           .collect(Collectors.toList());

            formation = formationRepository.save(formation);
            for(Integer id : idFormateurs){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().add(formation);
                formateurRepository.save(formateur);
            }
        }
    }


    private void editFormation(Formation formation){
        Formation oldFormation = formationRepository.findOne(formation.getIdformation());
        List<Integer> oldIdFormateurs = oldFormation.getFormateurSet().stream()
                .map(Formateur::getIdformateur)
                .collect(Collectors.toList());

        List<Integer> newIdFormateurs = formation.getFormateurSet().stream()
                .map(Formateur::getIdformateur)
                .collect(Collectors.toList());


        for(Integer id : oldIdFormateurs){
            if(! newIdFormateurs.contains(id)){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().remove(formation);
                formateurRepository.save(formateur);
            }
        }

        for(Integer id : newIdFormateurs){
            if(! oldIdFormateurs.contains(id)){
                Formateur formateur = formateurRepository.findOne(id);
                formateur.getFormations().add(formation);
                formateurRepository.save(formateur);
            }
        }

        formationRepository.save(formation);

    }



}
