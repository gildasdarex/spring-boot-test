package com.pej.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pej.services.CustomUserDetail;
import com.pej.services.FormationService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.pej.domains.Formateur;
import com.pej.domains.Formation;
import com.pej.domains.Typeformation;
import com.pej.repository.FormateurRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.TypeformationRepository;

@Controller
public class FormationController {
    Logger logger = LogManager.getLogger(FormationController.class);

    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private TypeformationRepository typeformationRepository;
    @Autowired
    private FormationService formationService;

    @GetMapping("/pej/formations")
    String index(Model model, @ModelAttribute("objFormation") Formation objFormation) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();

        List<String> roleNames = new ArrayList<>();
        List<Formation> formations = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            roleNames.add(grantedAuthority.getAuthority().toString());
        }


        if (roleNames.size() == 1 && roleNames.contains("FORMATEUR")) {
            String username = principal.getUsername();
            Formateur formateur = formateurRepository.findOneByUsername(username);
            formations = (List<Formation>) formateur.getFormations();
        } else {
            formations = (List<Formation>) formationRepository.findAll();
        }

        model.addAttribute("formations", formations);
        return "formations";
    }

    @GetMapping("/pej/formations/add")
    public String editFormation(@ModelAttribute("objFormation") Formation objFormation, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);
        return "frmFormation";
    }


    @GetMapping("/pej/formations/add/{id}")
    public String editFormation(@PathVariable Integer id, ModelMap model) {
        List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
        List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();
        Formation objFormation = formationRepository.findOne(id);

        model.addAttribute("formateurs", formateurs);
        model.addAttribute("objFormation", objFormation);
        model.addAttribute("typeformations", typeformations);

        return "frmFormation";
    }

    @GetMapping("/pej/formations/rm/{id}")
    public String deleteFormation(@PathVariable Integer id) {
        formationService.deleteFormation(id);
        return "redirect:/pej/formations";
    }


    @PostMapping("/pej/formations/multiple/rm")
    public String deleteFormations(@RequestParam("table_records") List<String> table_records) {

        for(String id: table_records){
            formationService.deleteFormation(Integer.parseInt(id));
        }
        return "redirect:/pej/formations";
    }

    @PostMapping("/pej/formations")
    public String saveagents(@ModelAttribute(value = "objFormation") Formation objFormation, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
                System.out.println("error to create new formation " + error.toString());
            }
            return "frmFormation";
        }

        objFormation = formationRepository.save(objFormation);

        String formateurList = objFormation.getFormateurs();
        String[] formateurListSplit = formateurList.split(",");
        for (String str : formateurListSplit) {
            Formateur formateur = formateurRepository.findOne(Integer.parseInt(str));
            formateur.getFormations().add(objFormation);
            formateurRepository.save(formateur);
        }
        return "redirect:/pej/formations";
    }

}
