package com.pej.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Departement;
import com.pej.domains.Don;
import com.pej.domains.DonCooperative;
import com.pej.repository.CooperativeRepository;
import com.pej.repository.DepartementRepository;
import com.pej.repository.DoncooperativeRepository;
import com.pej.services.NotificationService;

@Controller
public class CooperativeController {
	@Autowired private CooperativeRepository cooperativeRepository;
	@Autowired private DepartementRepository departementRepository;
	@Autowired private DoncooperativeRepository doncooperativeRepository;
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
			List<Departement> departements = (List<Departement>) departementRepository.findAll();
			model.addAttribute("departements", departements); 
			model.addAttribute("objCooperative", objCooperative); 
			 return "frmCooperative";
		}
		
		@GetMapping("/pej/cooperatives/{id}")
		public String modifierCooperative(@PathVariable Integer id, ModelMap model){
			List<Departement> departements = (List<Departement>) departementRepository.findAll();
			model.addAttribute("departements", departements); 
			Cooperative objCooperative=cooperativeRepository.findOne(id);
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
	    /*Récupérer la page d'accord de don à un groupe*/
		@RequestMapping(value = "/pej/cooperatives/don/{id}", method = RequestMethod.GET)
		public String  getDonPage (@PathVariable Integer id, ModelMap model) {
			Cooperative cooperative=cooperativeRepository.findOne(id);
			DonCooperative objDon=new DonCooperative();
			objDon.setCooperative(cooperative);
			List<DonCooperative> dons=(List<DonCooperative>)doncooperativeRepository.getDonByCopperative(id);
			//List<Don> dons =candidat.getDons().stream().collect(Collectors.toList());
			//System.out.println("Nbre de ligne: "+dons.size());
			model.addAttribute("dons", dons);
			model.addAttribute("cooperative", cooperative);
			model.addAttribute("objDon", objDon);
			return "FrmDonCooperative";
		}
		
		/*Enregistrer un don pour une coopérative*/	
		@PostMapping("/pej/cooperative/don")
	    public String saveDon(@Valid @ModelAttribute(value="objDon")  DonCooperative objDon, BindingResult result,Model model) {
	    	System.out.println("Starting Save Ok");
	        if (result.hasErrors()) {
	            return "FrmDonCooperative";
	        }
	        Cooperative cooperative=cooperativeRepository.findOne(objDon.getCooperative().getIdgroupe());
	        if(cooperative !=null){
	        	objDon.setCooperative(cooperative);
	        	 doncooperativeRepository.save(objDon);
	        }
	       
	        return "redirect:/pej/cooperatives";
	    }
		
		
		
}
