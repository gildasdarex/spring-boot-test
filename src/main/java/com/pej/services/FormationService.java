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



}
