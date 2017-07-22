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

    @Autowired private FormateurRepository formateurRepository;
    @Autowired private CabinetRepository cabinetRepository;
    @Autowired private UsersRepository userRepository;
    @Autowired private FormateurService formateurService;
    @Autowired private NotificationService notifyService;

    private Utilisateur usercourant;

    @GetMapping("/pej/formateurs")
    String index(Model model, @RequestParam(value = "idcabinet", required = false, defaultValue = "") String idcabinet) {
        List<Formateur> formateurs = new ArrayList<>();
        try {
            if(idcabinet.equals("")){
                formateurs = (List<Formateur>) formateurRepository.findAll();
            }else{
                formateurs = formateurRepository.findByCabinetIdcabinet(Integer.parseInt(idcabinet));
            }
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




    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value = "objFormateur") Formateur objFormateur, BindingResult result) {

        boolean success = true;

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                logger.debug("error to create new formateur " + error.toString());
            }
            return "frmFormateur";
        }

        if (objFormateur.getIdformateur() != null && objFormateur.getIdformateur().intValue() > 0)
            success = formateurService.editFormateur(objFormateur);
        else
            success = formateurService.createFormateur(objFormateur);

        if(!success)
            notifyService.addErrorMessage("ECHEC DE L'ENREGSTREMENT. VERFIER S'IL N'EXISTE PAS D'ENREGISTREMENT AVEC LE MEME USERNAME OU EMAIL   ");

        return "redirect:/pej/formateurs";
    }

}
