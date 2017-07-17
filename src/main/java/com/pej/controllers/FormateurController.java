package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Utilisateur usercourant;

    @GetMapping("/pej/formateurs")
    String index(Model model, @ModelAttribute("objFormateur") Formateur objFormateur) {
        try {
            List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
            model.addAttribute("formateurs", formateurs);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                model.addAttribute("username", username);
            } else {
                String username = principal.toString();
                model.addAttribute("username", username);
            }
        } catch (Exception ex) {
            List<Formateur> formateurs = new ArrayList<>();
            model.addAttribute("formateurs", formateurs);
        }
        return "formateurs";
    }

    @GetMapping("/pej/formateurs/add")
    public String editFormateur(@ModelAttribute("objFormateur") Formateur objFormateur, ModelMap model) {
        model.addAttribute("objFormateur", objFormateur);
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        model.addAttribute("cabinets", cabinets);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmFormateur";
    }


    @GetMapping("/pej/delete/formateurs/{id}")
    public String deleteFormateur(@PathVariable Integer id, ModelMap model) {
        Formateur formateur = formateurRepository.findOne(id);
        formateurRepository.delete(formateur);
        return "redirect:/pej/formateurs";
    }


    @PostMapping("/pej/delete/multiple/formateurs")
    public String deleteAllFormateurs(@RequestParam("table_records") List<String> table_records) {
        for(String id : table_records){
            Formateur formateur = formateurRepository.findOne(Integer.parseInt(id));
            formateurRepository.delete(formateur);
        }

        return "redirect:/pej/formateurs";
    }


    @GetMapping("/pej/formateurs/{id}")
    public String editFormateur(@PathVariable Integer id, ModelMap model) {
        try {
            Formateur formateur = formateurRepository.findOne(id);
            Utilisateur user = userRepository.findUserByfristlast(formateur.getNom(), formateur.getPrenom());
            model.addAttribute("objFormateur", formateur);
            model.addAttribute("objUtilisateur", user);
            usercourant = user;
            List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
            model.addAttribute("cabinets", cabinets);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                model.addAttribute("username", username);
            } else {
                String username = principal.toString();
                model.addAttribute("username", username);
            }
        } catch (Exception ex) {
            Formateur formateur = new Formateur();
            Utilisateur user = new Utilisateur();
            model.addAttribute("objFormateur", formateur);
            model.addAttribute("objUtilisateur", user);
            usercourant = user;
        }
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        model.addAttribute("cabinets", cabinets);
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
    public String newProfile(@ModelAttribute(value = "utilisateur") Utilisateur utilisateur, BindingResult result, Model model) {
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
        //User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Utilisateur user= userRepository.findByUsername(principal.getUsername());
        //model.addAttribute("utilsateur", user);
        return "redirect:/pej/logout";
    }


    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value = "objFormateur") Formateur objFormateur, BindingResult result, Model model) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println(error.toString());
            }
            return "frmFormateur";
        }


        if (objFormateur.getIdformateur() != null && objFormateur.getIdformateur().intValue() > 0) {
            Formateur formateur = formateurRepository.findOne(objFormateur.getIdformateur());
            formateur.setNom(objFormateur.getNom());
            formateur.setPrenom(objFormateur.getPrenom());
            formateur.setCardid(objFormateur.getCardid());
            //formateur.setDatenaissance(objFormateur.getDatenaissance());
            formateur.setTelephone(objFormateur.getTelephone());
            formateur.setCabinet(objFormateur.getCabinet());
            formateur.setUsername(objFormateur.getUsername());
            formateurRepository.save(formateur);
            return "redirect:/pej/formateurs";
        }
        formateurRepository.save(objFormateur);
        Utilisateur user = new Utilisateur();
        user.setFirstname(objFormateur.getNom());
        user.setLastname(objFormateur.getPrenom());
        user.setPassword(bCryptPasswordEncoder.encode(objFormateur.getPassword()));
        user.setUsername(objFormateur.getUsername());
        userRepository.save(user);
        Roles r = roleRepository.findByName("FORMATEUR");
        if (r != null) {
            UsersRole userrole = new UsersRole();
            userrole.setRoles(r);
            userrole.setUtilisateur(user);
            userRoleRepository.save(userrole);
        } else
            System.out.println("role is null");
        return "redirect:/pej/formateurs";
    }

}
