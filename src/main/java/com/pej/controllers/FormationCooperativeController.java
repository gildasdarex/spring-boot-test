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

import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Formation;
import com.pej.domains.Formationcooperative;
import com.pej.domains.Presence;
import com.pej.repository.CooperativeRepository;
import com.pej.repository.CandidatRepository;
import com.pej.repository.FormationBeneficiareRepository;
import com.pej.repository.FormationCooperativeRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.PresenceRepository;

@Controller
public class FormationCooperativeController {
	@Autowired private CooperativeRepository cooperativeRepository;
	@Autowired private FormationCooperativeRepository formationcooperativeRepository;
	@Autowired private FormationRepository formationRepository;
	@Autowired private PresenceRepository presenceRepository;
	@Autowired private CandidatRepository candidatRepository;
		

		@RequestMapping(value = "/pej/formationgroupe/formation/{id}", method = RequestMethod.GET)	
	    String index(Model model,@PathVariable Integer id) {
	    	System.out.println("Starting formation cooperative Index Ok");
	    	List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.getNotInFormationCooperative(id);
	    	Formation formation=formationRepository.findOne(id);
	    	model.addAttribute("formation", formation);
	    	model.addAttribute("cooperatives", cooperatives);
	    	List<Cooperative> cooperativeformation = (List<Cooperative>) cooperativeRepository.getInFormationCooperative(id);
	    	model.addAttribute("cooperativeformation", cooperativeformation);
	    	
	    	System.out.println("cooperatives: "+cooperatives.size());
	    	System.out.println("candidatsformation: "+cooperativeformation.size());
	        return "formationcooperatives";
	    }
		
		@GetMapping("/pej/formationgroupe/{idformation}/cooperative/{idgroupe}")
		public String addFormationPresence(@PathVariable Integer idformation,@PathVariable Integer idgroupe, ModelMap model){
			System.out.println("Formation :"+idformation);
			System.out.println("Cooperative "+idgroupe);
			Formation formation=formationRepository.findOne(idformation);
			Cooperative cooperative =cooperativeRepository.findOne(idgroupe);
			
			Formationcooperative formationcooperative= new Formationcooperative();
			formationcooperative.setCooperative(cooperative);
			formationcooperative.setFormation(formation);
			List<Candidat> inCooperative=(List<Candidat>) candidatRepository.getInFormationCandidat(idformation);
			if(formation!=null && cooperative!=null){
				formationcooperativeRepository.save(formationcooperative);				
			}
			

			 return "redirect:"+"/pej/formationgroupe/formation/"+idformation;
		}
		
		@GetMapping("/pej/formationgroupe/rm/{idformation}/cooperative/{idcandidat}")
		public String removeFormationBenenficiaire(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
			
			Formationcooperative formationcooperative=formationcooperativeRepository.findFb(idcandidat, idformation);
			if(formationcooperative !=null)
				formationcooperativeRepository.delete(formationcooperative.getIdformationcooperative());

			 return "redirect:"+"/pej/formationbeneficiaires/formation/"+idformation;
		}
		
	
	}
