package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Formationbeneficaire;
import com.pej.repository.CandidatRepository;
import com.pej.repository.BeneficiaireCooperativeRepository;
import com.pej.repository.CooperativeRepository;
import com.pej.services.NotificationService;

@Controller
public class BeneficiaireCooperativeController {
	@Autowired private CandidatRepository candidatRepository;
	@Autowired private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
	@Autowired private CooperativeRepository cooperativeRepository;
	@Autowired private NotificationService notifyService;		

		@RequestMapping(value = "/pej/beneficiairecooperative/cooperative/{id}", method = RequestMethod.GET)	
	    String index(Model model,@PathVariable Integer id) {
	    	System.out.println("Starting Index Ok");
	    	List<Candidat> candidats = (List<Candidat>) candidatRepository.getNotInCooperativeCandidat(id);
	    	Cooperative cooperative=cooperativeRepository.findOne(id);
	    	model.addAttribute("cooperative", cooperative);
	    	model.addAttribute("candidats", candidats);
	    	List<Candidat> candidatscooperative = (List<Candidat>) candidatRepository.getInCooperativeCandidat(id);
	    	model.addAttribute("candidatscooperative", candidatscooperative);
	    	
	    	System.out.println("candidats: "+candidats.size());
	    	System.out.println("candidatscooperative: "+candidatscooperative.size());
	        return "beneficiairecooperative";
	    }
		
		@GetMapping("/pej/beneficiairecooperative/{idgroupe}/candidat/{idcandidat}")
		public String addFormationBeneficiaire(@PathVariable Integer idgroupe,@PathVariable Integer idcandidat, ModelMap model){
			System.out.println("Cooperative :"+idgroupe);
			System.out.println("Candidat "+idcandidat);
			Cooperative cooperative=cooperativeRepository.findOne(idgroupe);
			Candidat candidat =candidatRepository.findOne(idcandidat);
			System.out.println("Candidat "+candidat.toString());
			Beneficiairecooperative beneficiairecooperative= new Beneficiairecooperative();
			beneficiairecooperative.setCandidat(candidat);
			beneficiairecooperative.setCooperative(cooperative);;
			if(cooperative!=null && candidat!=null){
				beneficiaireCooperativeRepository.save(beneficiairecooperative);
				notifyService.addSucceesgMessage("Candidat ajouter à la coopérative avec succès.");
			}
			else{
				notifyService.addWarningMessage("Echec enregistrement. Candidat ou coopérative non trouvé.");
			}
			
			

			 return "redirect:"+"/pej/beneficiairecooperative/cooperative/"+idgroupe;
		}
		
		@GetMapping("/pej/beneficiairecooperative/rm/{idgroupe}/candidat/{idcandidat}")
		public String removeFormationBenenficiaire(@PathVariable Integer idgroupe,@PathVariable Integer idcandidat, ModelMap model){
			
			Beneficiairecooperative beneficiairecooperative=beneficiaireCooperativeRepository.findBc(idcandidat, idgroupe);
			if(beneficiairecooperative !=null)
				beneficiaireCooperativeRepository.delete(beneficiairecooperative.getIdcandidatgroupe());

			 return "redirect:"+"/pej/beneficiairecooperative/cooperative/"+idgroupe;
		}
}
