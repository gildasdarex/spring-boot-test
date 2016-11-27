package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pej.domains.Cooperative;
import com.pej.domains.Departement;
import com.pej.repository.CooperativeRepository;
import com.pej.repository.DepartementRepository;
import com.pej.services.NotificationService;

@Controller
public class CooperativeController {
	@Autowired private CooperativeRepository cooperativeRepository;
	@Autowired private DepartementRepository departementRepository;
	@Autowired private NotificationService notifyService;

		@GetMapping("/pej/cooperatives")
	    String index(Model model,@ModelAttribute("objCooperative") Cooperative objCooperative) {
	    	System.out.println("Starting Index Ok");
	    	List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();  	
	    	model.addAttribute("cooperatives", cooperatives);
	        return "cooperatives";
	    }
		
		@GetMapping("/pej/cooperatives/add")
		public String editCommune(@ModelAttribute("objCooperative") Cooperative objCooperative, ModelMap model){
			 model.addAttribute("objCooperative", objCooperative); 
			 return "frmCooperative";
		}
		
	    @PostMapping("/pej/cooperatives")
	    public String saveagents(@ModelAttribute(value="objCooperative")  Cooperative objCooperative, BindingResult result,Model model) {
	    	System.out.println("Starting Save Ok");
	        if (result.hasErrors()) {
	        	notifyService.addErrorMessage("Echec enregistrement");
	            return "frmCooperative";
	        }
	        if(objCooperative!=null)
	       System.out.println("Nom Cooperative: "+objCooperative.getLibgroupe());
	        else
	        	System.out.println("objAgent est null: ");
	        
	       if(objCooperative.getIdgroupe()!=null && objCooperative.getIdgroupe().intValue() >0 ){
	    	   Cooperative cooperative =cooperativeRepository.findOne(objCooperative.getIdgroupe());
	    	   cooperative.setDescription(objCooperative.getDescription());
	    	   cooperative.setLibgroupe(objCooperative.getLibgroupe());
	    	   cooperativeRepository.save(cooperative);
	           return "redirect:/pej/decoupement";
	       }
	       cooperativeRepository.save(objCooperative);
	       notifyService.addInfoMessage("Enregistrement effectué avec succès");
	       return "redirect:/pej/cooperatives";
	    }


}
