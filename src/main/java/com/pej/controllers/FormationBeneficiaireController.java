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
import com.pej.domains.Formateur;
import com.pej.domains.Formation;
import com.pej.domains.Formationbeneficaire;
import com.pej.domains.Formationformateur;
import com.pej.domains.Presence;
import com.pej.repository.CandidatRepository;
import com.pej.repository.FormateurRepository;
import com.pej.repository.FormationBeneficiareRepository;
import com.pej.repository.FormationRepository;
import com.pej.repository.PresenceRepository;

@Controller
public class FormationBeneficiaireController {
@Autowired private CandidatRepository candidatRepository;
@Autowired private FormationBeneficiareRepository formationBeneficiareRepository;
@Autowired private FormationRepository formationRepository;
@Autowired private PresenceRepository presenceRepository;
@Autowired private FormateurRepository formateurRepository;
	

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
	@RequestMapping(value = "/pej/formationbeneficiaires/presence/{id}", method = RequestMethod.GET)	
    String presence(Model model,@PathVariable Integer id) {
    	System.out.println("Starting Index Ok");
    	List<Candidat> candidats = (List<Candidat>) candidatRepository.getInAbsenceCandidat(id);
    	Formation formation=formationRepository.findOne(id);
    	model.addAttribute("formation", formation);
    	model.addAttribute("candidats", candidats);
    	/*List<Candidat> candidatsformation = (List<Candidat>) candidatRepository.getInPresenceCandidat(id);
    	model.addAttribute("candidatsformation", candidatsformation);*/
    	List<Presence> presences=(List<Presence>) presenceRepository.getPresenceByFormation(id);
    	model.addAttribute("presences", presences);
    	//System.out.println("presences: "+presences.get(0).getCandidat().getNom());
    	System.out.println("candidats: "+candidats.size());
        return "presence";
    }
	@GetMapping("/pej/formationbeneficiaires/{idformation}/presence/{idcandidat}")
	public String addFormationBeneficiaire(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
		System.out.println("Formation :"+idformation);
		System.out.println("Candidat "+idcandidat);
		Formation formation=formationRepository.findOne(idformation);
		Candidat candidat =candidatRepository.findOne(idcandidat);
		System.out.println("Candidat "+candidat.toString());
		Presence presence=new Presence();
		presence.setCandidat(candidat);
		presence.setFormation(formation);
		if(formation!=null && candidat!=null){
			presence.setNbpresence(1);
			presenceRepository.save(presence);
		}
		 return "redirect:"+"/pej/formationbeneficiaires/presence/"+idformation;
		
		
		
		
	}
	
	@GetMapping("/pej/formationbeneficiaires/{idformation}/candidat/{idcandidat}")
	public String addFormationPresence(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
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
	
	@GetMapping("/pej/formationbeneficiaires/rm/{idformation}/cooperative/{idcandidat}")
	public String removeFormationBenenficiaire(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
		
		Formationbeneficaire formationbeneficiaire=formationBeneficiareRepository.findFb(idcandidat, idformation);
		if(formationbeneficiaire !=null)
			formationBeneficiareRepository.delete(formationbeneficiaire.getIdformationbeneficiaire());

		 return "redirect:"+"/pej/formationbeneficiaires/formation/"+idformation;
	}
	
	@GetMapping("/pej/formationbeneficiaires/rm/{idformation}/presence/{idcandidat}")
	public String removePresenceFormation(@PathVariable Integer idformation,@PathVariable Integer idcandidat, ModelMap model){
		
		Presence presence=presenceRepository.findPresence(idcandidat, idformation);
		if(presence !=null){
			if(presence.getNbpresence()> 1){
				presence.setNbpresence(presence.getNbpresence()-1);
				presenceRepository.save(presence);
			}
			else{
			System.out.println("Présence trouvé");
			presenceRepository.delete(presence.getIdpresence());
			}
		}
		else{
			System.out.println("Présence non trouvé");
		}

		 return "redirect:"+"/pej/formationbeneficiaires/presence/"+idformation;
	}
	
	@GetMapping("/pej/presence/add/{idpresence}")
	public String addPresence(@PathVariable Integer idpresence){
		System.out.println("Présence :"+idpresence);
		Presence presence =presenceRepository.findOne(idpresence);
		presence.setNbpresence(presence.getNbpresence()+1);
		presenceRepository.save(presence);
		return "redirect:"+"/pej/formationbeneficiaires/presence/"+presence.getFormation().getIdformation();
	}
	
	
}
