package com.pej.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import com.pej.domains.Departement;
import com.pej.domains.Don;
import com.pej.domains.Quartier;
import com.pej.domains.Statutcandidat;
import com.pej.domains.Commune;
import com.pej.domains.Agent;
import com.pej.domains.Arrondissement;
import com.pej.domains.Candidat;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import com.pej.services.NotificationServiceImpl.NotificationMessageType;
import com.pej.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Controller
public class CandidatController {
	@Autowired private DepartementRepository departementRepository;
	@Autowired private CommuneRepository communeRepository;
	@Autowired private ArrondissementRepository arrondissementRepository;
	@Autowired private QuartierRepository quartierRepository;
	@Autowired private CandidatRepository candidatRepository;
	@Autowired private AgentRepository agentRepository;
	@Autowired private StatutcandidatRepository statutcandidatRepository;
	@Autowired private DonRepository donRepository;
	@Autowired private NotificationService notifyService;
	
	@GetMapping("/pej/candidats")
    String index(Model model,@ModelAttribute("objDepartement") Departement objDepartement) {  
		List<Candidat> candidats = (List<Candidat>) candidatRepository.findAll();
    	model.addAttribute("candidats", candidats);
    	System.out.println("Nbre candidats: "+candidats.size());
        return "candidats";
    }
	

	@RequestMapping(value="/pej/candidats/add", method=RequestMethod.GET)
    String addCandidat(@ModelAttribute("objCandidat") Candidat objCandidat, ModelMap model) {
    	List<Departement> departements = (List<Departement>) departementRepository.findAll();
    	model.addAttribute("departements", departements);
        return "frmCandidat";
    }
	
	
	
	/*Récupérer la liste des commmune d'un département*/
	@RequestMapping(value = "/pej/candidats/commune/{id}", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Commune> findCommune(@PathVariable Integer id) {
		Departement departement=departementRepository.findOne(id);
	    //return (List<Commune>)communeRepository.getCommune(id);
		//return (List<Commune>)departement.getCommunes()();
		return new ArrayList<Commune>(departement.getCommunes());
	}
	
	/*Récupérer la liste des arrondissement d'une commune*/
	@RequestMapping(value = "/pej/candidats/arrondissement/{id}", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Arrondissement> findArrondissement(@PathVariable Integer id) {
		Commune commune=communeRepository.findOne(id);
		return new ArrayList<Arrondissement>(commune.getArrondissements());
	}
	
	/*Récupérer la liste des quartier  d'un arrondissement*/
	@RequestMapping(value = "/pej/candidats/quartier/{id}", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Quartier> findQuartier(@PathVariable Integer id) {
		Arrondissement arrondissement=arrondissementRepository.findOne(id);
		return new ArrayList<Quartier>(arrondissement.getQuartiers());
	}
	/*Récupérer la liste des quartier  d'un arrondissement*/
	@RequestMapping(value = "/pej/candidats/agent/{id}", method = RequestMethod.GET)
	public @ResponseBody Agent findAgent(@PathVariable String id) {
		Agent agent=agentRepository.getAgent(id);
		return agent;
	}
	@PostMapping("/pej/candidats")
    public String savecandidat(@Valid @ModelAttribute(value="objCandidat")  Candidat objCandidat, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmCandidat";
        }
       
        Agent agent=agentRepository.getAgent(objCandidat.getNumeroagent());
        if(objCandidat.getIdcandidat()!=null && objCandidat.getIdcandidat().intValue() >0 ){
        	try{
				Candidat candidat=candidatRepository.findOne(objCandidat.getIdcandidat());
				candidat.setAgent(agent);
				candidat.setNom(objCandidat.getNom());  
				candidat.setPrenom(objCandidat.getPrenom());
				candidat.setDatenaissance(objCandidat.getDatenaissance());
				candidat.setDocidentite(objCandidat.getDocidentite());
				candidat.setNiveau(candidat.getNiveau());
				candidat.setDiplome(objCandidat.getDiplome());
				candidat.setCodearrondissement(objCandidat.getCodearrondissement());
				candidat.setRefdocidentite(objCandidat.getRefdocidentite());
				candidat.setTelprincipal(objCandidat.getTelprincipal());
				candidat.setTelalternatif(objCandidat.getTelalternatif());
				candidat.setAge(objCandidat.getAge());
				candidat.setSexe(objCandidat.getSexe());
				candidat.setSituationmatrimoniale(objCandidat.getSituationmatrimoniale());
				candidat.setParentechefmenage(objCandidat.getParentechefmenage());
				candidat.setNbpersonnemenage(objCandidat.getNbpersonnemenage());
				candidat.setMenagebeneficiaire(objCandidat.getMenagebeneficiaire());
				candidat.setScolarise(objCandidat.getScolarise());
				candidat.setDernierniveauetude(objCandidat.getDernierniveauetude());
				candidat.setLecture(objCandidat.getLecture());
				candidat.setQualificationpersonelle(objCandidat.getQualificationpersonelle());
				candidat.setWorklastmonth(objCandidat.getWorklastmonth());
				candidat.setRecherchetravail(objCandidat.getRecherchetravail());
				candidat.setMotifnonrecherche(objCandidat.getMotifnonrecherche());
				candidat.setActiviteactuelle(objCandidat.getActiviteactuelle());
				candidat.setAsoncompte(objCandidat.getAsoncompte());
				        		
				candidat.setNbmoistravail(objCandidat.getNbmoistravail());
				candidat.setNbjoursmoyen(objCandidat.getNbjoursmoyen());
				candidat.setNbheuremoyen(objCandidat.getNbheuremoyen());
				candidat.setRevenumoyen(objCandidat.getRevenumoyen());
				candidat.setDomainesouhait(objCandidat.getDomainesouhait());
				candidat.setTravailgroupe(objCandidat.getTravailgroupe());
				candidat.setNumcarteagratter(objCandidat.getNumcarteagratter());
				candidat.setQuartier(objCandidat.getQuartier());
				candidat.setCommune(objCandidat.getCommune());
				candidat.setDepartement(objCandidat.getDepartement());
				candidat.setDateenregistrement(objCandidat.getDateenregistrement());
				candidat.setNumeroagent(objCandidat.getNumeroagent());
				candidat.setNumerofiche(objCandidat.getNumerofiche());
        		
        		candidatRepository.save(objCandidat);
        		notifyService.addInfoMessage("Candidat modififé avec succès.");
        	}
        	catch(Exception e){
        		notifyService.addErrorMessage("Echec lors de la modification du candidat. "+e.getMessage());
        	}
        }
        Statutcandidat st=statutcandidatRepository.findOne(1);
        if(st==null){
        	notifyService.addErrorMessage("Aucun statut par défaut configuré pour les candidats");
        	return "";
        }
        objCandidat.setStatutcandidat(st);
        objCandidat.setAgent(agent);
        System.out.print("Agent récupéré: "+agent.getNom());
        System.out.print("Saving candidat: "+objCandidat.toString());
        
        try{
    		candidatRepository.save(objCandidat);
    		notifyService.addInfoMessage("Candidat enregistré avec succès.");
    	}
    	catch(Exception e){
    		notifyService.addErrorMessage("Echec lors de l'enregistrement du candidat. "+e.getMessage());
    	}
        return "redirect:/pej/candidats";
    }
	
	/*Changer de statut pour un candidat en cliquant sur le bouton valider*/
	@RequestMapping(value = "/pej/candidats/valider/{id}", method = RequestMethod.GET)
	public @ResponseBody  Candidat  upgradeStatut (@PathVariable Integer id) {
		Candidat candidat=candidatRepository.findOne(id);
		Statutcandidat statut= statutcandidatRepository.findOne(candidat.getStatutcandidat().getId()+1);
		if(statut !=null){
			candidat.setStatutcandidat(statut);	
			candidatRepository.save(candidat);
		}

		return candidat;
	}
    
	/*Récupérer la page d'accord de don à un candidat*/
	@RequestMapping(value = "/pej/candidats/don/{id}", method = RequestMethod.GET)
	public String  getDonPage (@PathVariable Integer id, ModelMap model) {
		Candidat candidat=candidatRepository.findOne(id);
		Don objDon=new Don();
		objDon.setCandidat(candidat);
		List dons=(List<Don>)donRepository.getDonByCandidat(id);
		//List<Don> dons =candidat.getDons().stream().collect(Collectors.toList());
		System.out.println("Nbre de ligne: "+dons.size());
		model.addAttribute("dons", dons);
		model.addAttribute("objDon", objDon);
		return "frmDon";
	}
	
	/*Enregistrer un don pour un candidat*/	
	@PostMapping("/pej/candidats/don")
    public String saveDon(@Valid @ModelAttribute(value="objDon")  Don objDon, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmDon";
        }
        Candidat candidat=candidatRepository.findOne(objDon.getCandidat().getIdcandidat());
        if(candidat !=null){
        	objDon.setCandidat(candidat);
        	 donRepository.save(objDon);
        }
       
        return "redirect:/pej/candidats";
    }
	
	@GetMapping("/pej/candidats/{id}")
	public String updateCandidat(@PathVariable Integer id, ModelMap model){
		List<Departement> departements = (List<Departement>) departementRepository.findAll();
    	model.addAttribute("departements", departements);
		Candidat candidat=candidatRepository.findOne(id);
		model.addAttribute("objCandidat", candidat);
		 return "frmCandidat";
	}
}
