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
public class FormateurService {
    Logger logger = LogManager.getLogger(FormateurService.class);

    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolesRepository roleRepository;
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public boolean deleteFormateur(Integer id){
        Formateur formateur = formateurRepository.findOne(id);
        List<Formation> formations = formateur.getFormations();

        for(Formation formation: formations) {
            formationRepository.delete(formation);
        }

        formateurRepository.delete(formateur);

        return true;
    }


    @Transactional(propagation= Propagation.REQUIRED)
    public boolean createFormateur(Formateur formateur){
        try {
            formateur = formateurRepository.save(formateur);
            Utilisateur user = setFormateurUser(formateur);
            setFormateurRole(user);
        }
        catch (Exception ex){
            logger.debug("error when create new user " + ex.getMessage());
            return false ;
        }

        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean editFormateur(Formateur updateFormateur){
        try{
            Formateur formateur = formateurRepository.findOne(updateFormateur.getIdformateur());

            updateFormateur.setUsername(formateur.getUsername());
            updateFormateur.setDatenaissance(formateur.getDatenaissance());

            formateurRepository.save(updateFormateur);
        }
        catch (Exception ex){
            logger.debug("error when edit  user " + ex.getMessage());
            return false ;
        }

        return true;
    }



    private Utilisateur setFormateurUser(Formateur formateur){
        Utilisateur user = new Utilisateur();

        user.setUsername(formateur.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(formateur.getPassword()));
        user.setFirstname(formateur.getNom());
        user.setLastname(formateur.getPrenom());
        user.setEmail(formateur.getEmail());

        userRepository.save(user);

        return user;
    }


    private boolean setFormateurRole(Utilisateur user){
        Roles roles = roleRepository.findByName("FORMATEUR");
        UsersRole userrole = new UsersRole();
        userrole.setRoles(roles);
        userrole.setUtilisateur(user);
        userRoleRepository.save(userrole);

        return true;
    }




}
