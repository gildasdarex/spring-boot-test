package com.pej;

import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pej.domains.Departement;
import com.pej.domains.Commune;
import com.pej.domains.Arrondissement;
import com.pej.repository.*;
import java.util.List;


@Controller
class HomeController  extends WebMvcConfigurerAdapter {
	@Autowired private DepartementRepository departementRepository;
	@Autowired private CommuneRepository communeRepository;
	@Autowired private ArrondissementRepository arrondissementRepository;
    


	@GetMapping("/pej/decoupement")
    String index(Model model,@ModelAttribute("objDepartement") Departement objDepartement) {
    	System.out.println("Starting Index Ok");
    	List<Departement> departements = (List<Departement>) departementRepository.findAll();
    	List<Commune> communes = (List<Commune>) communeRepository.findAll();
    	List<Arrondissement> aronndissements = (List<Arrondissement>) arrondissementRepository.findAll();
    	model.addAttribute("departements", departements);
    	model.addAttribute("communes", communes);
    	model.addAttribute("arrondissements", aronndissements);
    	//model.addAttribute("objDepartement", new Departement());
        return "home";
    }
	
	@GetMapping("/pej/departement")
	public String editDepartement(@ModelAttribute("objDepartement") Departement objDepartement){
		 return "departement";
	}

	@GetMapping("/pej/departement/{id}")
	public String updateDepartement(@PathVariable Integer id, ModelMap model){
		Departement departement=departementRepository.findOne(id);
		model.addAttribute("objDepartement", departement);
		 return "departement";
	}

    @PostMapping("/pej/departement")
    public String savedepartement(@Valid @ModelAttribute(value="objDepartement")  Departement objDepartement, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "departement";
        }
        if(objDepartement!=null)
       System.out.println("Lib département: "+objDepartement.getCodedepartement());
        else
        	System.out.println("Objdépartement est null: ");
        
       if(objDepartement.getCodedepartement()>0){
    	   Departement departement=departementRepository.findOne(objDepartement.getCodedepartement());
    	   departement.setLibdeparteement(objDepartement.getLibdeparteement());
    	   departement.setDescription(objDepartement.getDescription());
    	   departementRepository.save(departement);
           return "redirect:/pej/decoupement";
       }
       departementRepository.save(objDepartement);
       return "redirect:/pej/decoupement";
    }
}