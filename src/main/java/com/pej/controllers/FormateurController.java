package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import com.pej.services.FormateurService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import com.pej.domains.Cabinet;
import com.pej.domains.Formateur;
import com.pej.domains.Roles;
import com.pej.domains.UsersRole;
import com.pej.domains.Utilisateur;
import com.pej.repository.CabinetRepository;
import com.pej.repository.FormateurRepository;
import com.pej.repository.UserRoleRepository;
import com.pej.repository.UsersRepository;
import com.pej.services.NotificationService;
import com.pej.repository.RolesRepository;

@Controller
public class FormateurController {
    Logger logger = LogManager.getLogger(FormateurController.class);

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
    private NotificationService notifyService;
    @Autowired
    private FormateurService formateurService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Utilisateur usercourant;

    @GetMapping("/pej/formateurs")
    String index(Model model) {
        List<Formateur> formateurs = new ArrayList<>();
        try {
            formateurs = (List<Formateur>) formateurRepository.findAll();
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null)
                model.addAttribute("username", principal.getUsername());
        } catch (Exception ex) {
           logger.debug("error for /pej/formateurs request");
        }

        model.addAttribute("formateurs", formateurs);

        return "formateurs";
    }

    @GetMapping("/pej/formateurs/add")
    public String editFormateur(@ModelAttribute("objFormateur") Formateur objFormateur, ModelMap model) {
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal != null)
            model.addAttribute("username", principal.getUsername());
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objFormateur", objFormateur);

        return "frmFormateur";
    }


    @GetMapping("/pej/delete/formateurs/{id}")
    public String deleteFormateur(@PathVariable Integer id) {
        formateurService.deleteFormateur(id);
        return "redirect:/pej/formateurs";
    }


    @PostMapping("/pej/delete/multiple/formateurs")
    public String deleteAllFormateurs(@RequestParam("table_records") List<String> table_records) {
        for(String id : table_records){
            formateurService.deleteFormateur(Integer.parseInt(id));
        }

        return "redirect:/pej/formateurs";
    }


    @GetMapping("/pej/formateurs/{id}")
    public String editFormateur(@PathVariable Integer id, ModelMap model) {
        Formateur formateur = new Formateur();
        Utilisateur user = new Utilisateur();

        try {
            formateur = formateurRepository.findOne(id);
            user = userRepository.findUserByfristlast(formateur.getNom(), formateur.getPrenom());

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null)
                model.addAttribute("username", principal.getUsername());
        } catch (Exception ex) {
            logger.debug("error for /pej/formateurs/{id} request");
        }

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objFormateur", formateur);
        model.addAttribute("objUtilisateur", user);

        usercourant = user;

        return "editformateurs";
    }


    @GetMapping("/pej/profile")
    public String setProfile(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur user = userRepository.findByUsername(principal.getUsername());
        user.setPassword("");
        model.addAttribute("utilsateur", user);
        return "setprofile";
    }


    @PostMapping("/pej/newProfile")
    public String newProfile(@ModelAttribute(value = "utilisateur") Utilisateur utilisateur) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Formateur formateur = formateurRepository.findOneByUsername(principal.getUsername());

        String oldPass = principal.getPassword();

        if (utilisateur.getPassword().equals("") || utilisateur.getPassword() == null)
            utilisateur.setPassword(bCryptPasswordEncoder.encode(oldPass));
        else
            utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));

        formateur.setUsername(utilisateur.getUsername());


        userRepository.save(utilisateur);
        formateurRepository.save(formateur);

        return "redirect:/pej/logout";
    }


    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value = "objFormateur") Formateur objFormateur, BindingResult result) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                logger.debug("error to create new formateur " + error.toString());
            }
            return "frmFormateur";
        }

        if (objFormateur.getIdformateur() != null && objFormateur.getIdformateur().intValue() > 0){
            Formateur formateur = formateurRepository.findOne(objFormateur.getIdformateur());
            objFormateur.setUsername(formateur.getUsername());
            objFormateur.setDatenaissance(formateur.getDatenaissance());
        }

        formateurRepository.save(objFormateur);

        Utilisateur user = userRepository.findByUsername(objFormateur.getUsername());

        if(user == null){
            user = new Utilisateur();
            user.setUsername(objFormateur.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(objFormateur.getPassword()));
        }

        user.setFirstname(objFormateur.getNom());
        user.setLastname(objFormateur.getPrenom());
        user.setEmail(objFormateur.getEmail());

        userRepository.save(user);

        UsersRole usersRole = userRoleRepository.findByUtilisateurUsername(user.getUsername());

        if(usersRole == null){
            Roles roles = roleRepository.findByName("FORMATEUR");
            UsersRole userrole = new UsersRole();
            userrole.setRoles(roles);
            userrole.setUtilisateur(user);
            userRoleRepository.save(userrole);
        }

        return "redirect:/pej/formateurs";
    }

}
