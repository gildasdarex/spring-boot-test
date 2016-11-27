package com.pej.controllers;
import java.util.List;

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
import com.pej.domains.Agent;
import com.pej.repository.AgentRepository;
import com.pej.services.NotificationService;

@Controller
public class AgentController {
	@Autowired private AgentRepository agentRepository;
	@Autowired private NotificationService notifyService;
	@GetMapping("/pej/agents")
    String index(Model model,@ModelAttribute("objAgent") Agent objAgent) {
    	System.out.println("Starting Index Ok");
    	List<Agent> agents = (List<Agent>) agentRepository.findAll();  	
    	model.addAttribute("agents", agents);
        return "agents";
    }
	
	@GetMapping("/pej/agents/add")
	public String editCommune(@ModelAttribute("objAgent") Agent objAgent, ModelMap model){
		 model.addAttribute("objAgent", objAgent); 
		 return "frmAgent";
	}
	
    @PostMapping("/pej/agents")
    public String saveagents(@ModelAttribute(value="objAgent")  Agent objAgent, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	notifyService.addErrorMessage("Echec enregistrement.");
            return "frmCandidat";
        }
        if(objAgent!=null)
       System.out.println("Nom candidat: "+objAgent.getNom());
        else
        	System.out.println("objAgent est null: ");
        
       if(objAgent.getIdagent()!=null && objAgent.getIdagent().intValue() >0 ){
    	   Agent agent =agentRepository.findOne(objAgent.getIdagent());
    	   agent.setNom(objAgent.getNom());
    	   agent.setPrenom(objAgent.getPrenom());
    	   agent.setNumeroagent(objAgent.getNumeroagent());
    	   agent.setDatenaissance(objAgent.getDatenaissance());
    	   agent.setSexe(objAgent.getSexe());
    	   agent.setTelephone(objAgent.getTelephone());
    	   agentRepository.save(agent);
    	   notifyService.addInfoMessage("Modificcation effectuée avec succès.");
           return "redirect:/pej/decoupement";
       }
       notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
       agentRepository.save(objAgent);
       return "redirect:/pej/agents";
    }

}
