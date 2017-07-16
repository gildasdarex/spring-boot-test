package com.pej.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pej.services.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pej.domains.Formateur;
import com.pej.domains.Formation;
import com.pej.domains.Typeformation;
import com.pej.repository.FormateurRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.TypeformationRepository;

@Controller
public class FormationController {
@Autowired private FormationRepository formationRepository;
@Autowired private FormateurRepository formateurRepository;
@Autowired private TypeformationRepository typeformationRepository;
	
	@GetMapping("/pej/formations")
    String index(Model model,@ModelAttribute("objFormation") Formation objFormation) {
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> grantedAuthorities = principal.getAuthorities();
		System.out.println("------------" + grantedAuthorities.size());
		System.out.println("------------" + principal.getUsername());
		List<String> roleNames = new ArrayList<>();
		List<Formation> formations = new ArrayList<>();
		for(GrantedAuthority grantedAuthority : grantedAuthorities){
			System.out.println("role "+ grantedAuthority.getAuthority().toString());
			roleNames.add(grantedAuthority.getAuthority().toString());
		}

		System.out.println("------------" + roleNames.contains("FORMATEUR"));

		if(roleNames.size()== 1 && roleNames.contains("FORMATEUR")) {
			String username = principal.getUsername();
			System.out.println("in role" );
			Formateur formateur = formateurRepository.findOneByUsername(username);
			formations = (List<Formation>) formateur.getFormations();
		}
		else {
			formations = (List<Formation>) formationRepository.findAll();
		}
    	 
    	model.addAttribute("formations", formations);
        return "formations";
    }
	
	@GetMapping("/pej/formations/add")
	public String editFormation(@ModelAttribute("objFormation") Formation objFormation, ModelMap model){
		List<Formateur> formateurs= (List<Formateur>)formateurRepository.findAll();
		 model.addAttribute("formateurs",formateurs);
		 model.addAttribute("objFormation", objFormation); 
		 List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll(); 
		 model.addAttribute("typeformations", typeformations);
		 return "frmFormation";
	}
	@GetMapping("/pej/formations/add/{id}")
	public String editFormation(@PathVariable Integer id, ModelMap model){
		List<Formateur> formateurs= (List<Formateur>)formateurRepository.findAll();
		Formation objFormation=formationRepository.findOne(id);
		model.addAttribute("formateurs",formateurs);
		model.addAttribute("objFormation", objFormation); 
		List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll(); 
		model.addAttribute("typeformations", typeformations);
		return "frmFormation";
	}
	
    @PostMapping("/pej/formations")
    public String saveagents(@ModelAttribute(value="objFormation")  Formation objFormation, BindingResult result,Model model) {
        if (result.hasErrors()) {
            return "frmFormation";
        }


		//System.out.println("Nom Formation: "+objFormation.getFormateur());
       if(objFormation.getIdformation()!=null && objFormation.getIdformation().intValue() >0 ){
    	   Formation formation =formationRepository.findOne(objFormation.getIdformation());
    	   formation.setIntitule(objFormation.getIntitule());
    	   formation.setDateformation(objFormation.getDateformation());
    	   objFormation.setHeuredebut(objFormation.getHeuredebut());
    	   objFormation.setHeurefin(objFormation.getHeurefin());
    	   objFormation.setRemarque(objFormation.getRemarque());
	  
    	   formationRepository.save(formation);
           return "redirect:/pej/formations";
       }

		objFormation = formationRepository.save(objFormation);

		System.out.println("Nom Formation: "+objFormation.getFormateurs());
		String formateurList = objFormation.getFormateurs();
		String []formateurListSplit = formateurList.split(",");
		for(String str: formateurListSplit){
			Formateur formateur = formateurRepository.findOne(Integer.parseInt(str));
			formateur.getFormations().add(objFormation);
			formateurRepository.save(formateur);
		}
       return "redirect:/pej/formations";
    }

}
