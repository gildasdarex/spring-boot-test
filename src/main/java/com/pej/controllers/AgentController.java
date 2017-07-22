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
import com.pej.domains.Antenne;
import com.pej.repository.AgentRepository;
import com.pej.repository.AntenneRepository;
import com.pej.services.NotificationService;

@Controller
public class AgentController {
	@Autowired private AgentRepository agentRepository;
	@Autowired private AntenneRepository antenneRepository;
	@Autowired private NotificationService notifyService;

	@GetMapping("/pej/agents")
    String index(Model model,@ModelAttribute("objAgent") Agent objAgent) {
    	List<Agent> agents = (List<Agent>) agentRepository.findAll();

    	model.addAttribute("agents", agents);

        return "agents";
    }
	
	@GetMapping("/pej/agents/add")
	public String editAgent(@ModelAttribute("objAgent") Agent objAgent, ModelMap model){
		 List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();

		 model.addAttribute("antennes", antennes); 
		 model.addAttribute("objAgent", objAgent);

		 return "frmAgent";
	}
	
	@GetMapping("/pej/agents/{id}")
	public String modifierAgent(@PathVariable Integer id, ModelMap model){
		 List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();
		 Agent objAgent=agentRepository.findOne(id);

		 model.addAttribute("antennes", antennes); 
		 model.addAttribute("objAgent", objAgent);

		 return "frmAgent";
	}
	
    @PostMapping("/pej/agents")
    public String saveagents(@ModelAttribute(value="objAgent")  Agent objAgent, BindingResult result,Model model) {
        if (result.hasErrors()) {
        	notifyService.addErrorMessage("ECHEC DE L'ENREGISTREMENT.");
			return "redirect:/pej/agents";
        }

       notifyService.addInfoMessage("ENREGISTREMENT EFFECTUE AVEC SUCCESS.");
       agentRepository.save(objAgent);

       return "redirect:/pej/agents";
    }

}
