package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pej.domains.Formateur;
import com.pej.repository.FormateurRepository;

@Controller
public class FormateurController {
	@Autowired private FormateurRepository formateurRepository;
	
	@GetMapping("/pej/formateurs")
    String index(Model model,@ModelAttribute("objFormateur") Formateur objFormateur) {
    	System.out.println("Starting Index Ok");
    	List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();  	
    	model.addAttribute("formateurs", formateurs);
        return "formateurs";
    }
	
	@GetMapping("/pej/formateurs/add")
	public String editFormateur(@ModelAttribute("objFormateur") Formateur objFormateur, ModelMap model){
		 model.addAttribute("objFormateur", objFormateur); 
		 return "frmFormateur";
	}
	
	@GetMapping("/pej/formateurs/{id}")
	public String editFormateur(@PathVariable Integer id, ModelMap model){
		 Formateur objFormateur=formateurRepository.findOne(id);
		 model.addAttribute("objFormateur", objFormateur); 
		 return "frmFormateur";
	}
	
	
    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value="objFormateur")  Formateur objFormateur, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmFormateur";
        }
        if(objFormateur!=null)
       System.out.println("Nom Formateur: "+objFormateur.getNom());
        else
        	System.out.println("objFormateur est null: ");
        
       if(objFormateur.getIdformateur()!=null && objFormateur.getIdformateur().intValue() >0 ){
    	   Formateur formateur =formateurRepository.findOne(objFormateur.getIdformateur());
    	   formateur.setNom(objFormateur.getNom());
    	   formateur.setPrenom(objFormateur.getPrenom());
    	   formateur.setCardid(objFormateur.getCardid());
    	   formateur.setDatenaissance(objFormateur.getDatenaissance());    	   
    	   formateur.setTelephone(objFormateur.getTelephone());
    	   formateurRepository.save(formateur);
           return "redirect:/pej/formateurs";
       }
       formateurRepository.save(objFormateur);
       return "redirect:/pej/formateurs";
    }

}
