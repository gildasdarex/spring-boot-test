package com.pej.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pej.domains.Arrondissement;
import com.pej.domains.Candidat;
import com.pej.domains.Formation;
import com.pej.domains.Formationbeneficaire;
import com.pej.repository.CandidatRepository;
import com.pej.repository.FormationBeneficiareRepository;
import com.pej.repository.FormationRepository;

@Controller
public class FormationBeneficiaireController {
@Autowired private CandidatRepository candidatRepository;
@Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
@Autowired private FormationRepository formationRepository;
	

	@RequestMapping(value = "/pej/formationbeneficiaires/formation/{id}", method = RequestMethod.GET)	
    String index(Model model,@PathVariable Integer id) {
    	System.out.println("Starting Index Ok");
    	List<Candidat> candidats = (List<Candidat>) candidatRepository.getNotInFormationCandidat(id);
    	Formation formation=formationRepository.findOne(id);
    	model.addAttribute("formation", formation);
    	model.addAttribute("candidats", candidats);
    	List<Candidat> candidatsformation = (List<Candidat>) candidatRepository.getInFormationCandidat(id);
    	model.addAttribute("candidatsformation", candidatsformation);
    	
    	System.out.println("candidats: "+candidats.size());
    	System.out.println("candidatsformation: "+candidatsformation.size());
        return "formationbeneficiaire";
    }
	
	@GetMapping("/pej/formationbeneficiaires/{idformation}/candidat/{idcandidat}")
	public String addFormationBeneficiaire(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
		System.out.println("Formation :"+idformation);
		System.out.println("Candidat "+idcandidat);
		Formation formation=formationRepository.findOne(idformation);
		Candidat candidat =candidatRepository.findOne(idcandidat);
		System.out.println("Candidat "+candidat.toString());
		Formationbeneficaire formationbeneficiaire= new Formationbeneficaire();
		formationbeneficiaire.setCandidat(candidat);
		formationbeneficiaire.setFormation(formation);
		if(formation!=null && candidat!=null)
		formationBeneficiareRepository.save(formationbeneficiaire);
		

		 return "redirect:"+"/pej/formationbeneficiaires/formation/"+idformation;
	}
	
	@GetMapping("/pej/formationbeneficiaires/rm/{idformation}/candidat/{idcandidat}")
	public String removeFormationBenenficiaire(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
		
		Formationbeneficaire formationbeneficiaire=formationBeneficiareRepository.findFb(idcandidat, idformation);
		if(formationbeneficiaire !=null)
			formationBeneficiareRepository.delete(formationbeneficiaire.getIdformationbeneficiaire());

		 return "redirect:"+"/pej/formationbeneficiaires/formation/"+idformation;
	}
}
