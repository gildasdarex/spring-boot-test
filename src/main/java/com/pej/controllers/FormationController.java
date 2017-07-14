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
    	System.out.println("Starting Index Ok");
    	List<Formation> formations = (List<Formation>) formationRepository.findAll();  
    	 
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
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmFormation";
        }
        if(objFormation!=null)
       System.out.println("Nom Formation: "+objFormation.getIntitule());
        else
        	System.out.println("objFormation est null: ");
        
       if(objFormation.getIdformation()!=null && objFormation.getIdformation().intValue() >0 ){
    	   Formation formation =formationRepository.findOne(objFormation.getIdformation());
    	   formation.setIntitule(objFormation.getIntitule());
    	   formation.setDateformation(objFormation.getDateformation());
    	   formation.setFormateur(objFormation.getFormateur());
    	   objFormation.setHeuredebut(objFormation.getHeuredebut());
    	   objFormation.setHeurefin(objFormation.getHeurefin());
    	   objFormation.setRemarque(objFormation.getRemarque());
	  
    	   formationRepository.save(formation);
           return "redirect:/pej/formations";
       }
       formationRepository.save(objFormation);
       return "redirect:/pej/formations";
    }

}
