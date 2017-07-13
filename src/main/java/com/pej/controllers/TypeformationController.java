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
import com.pej.domains.Typeformation;
import com.pej.repository.TypeformationRepository;
import com.pej.services.NotificationService;

@Controller
public class TypeformationController {
	@Autowired private TypeformationRepository typeformationRepository;
	@Autowired private NotificationService notifyService;
	@GetMapping("/pej/typeformations")
    String index(Model model,@ModelAttribute("objTypeformation") Typeformation objTypeformation) {
    	System.out.println("Starting Index Ok");
    	List<Typeformation> typeformations = (List<Typeformation>) typeformationRepository.findAll();  	
    	model.addAttribute("typeformations", typeformations);
        return "typeformations";
    }
	
	@GetMapping("/pej/typeformations/add")
	public String editCabinet(@ModelAttribute("objTypeformation") Typeformation objTypeformation, ModelMap model){

		 model.addAttribute("objTypeformation", objTypeformation); 
		 return "frmTypeformation";
	}
	
	@GetMapping("/pej/typeformation/{id}")
	public String modifierCabinet(@PathVariable Integer id, ModelMap model){

		 Typeformation objTypeformation=typeformationRepository.findOne(id);
	
		 model.addAttribute("objTypeformation", objTypeformation); 
		 return "frmTypeformation";
	}
	
    @PostMapping("/pej/typeformations")
    public String saveagents(@ModelAttribute(value="objTypeformation")  Typeformation objTypeformation, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	notifyService.addErrorMessage("Echec enregistrement.");
            return "frmCabinet";
        }
        if(objTypeformation.getIdtypeformation()!=null)
       System.out.println("id typeformation: "+objTypeformation.getIntitule());
        else
        	System.out.println("objTypeformation est null: ");
        
       if(objTypeformation.getIdtypeformation()!=null && objTypeformation.getIdtypeformation().intValue() >0 ){
    	   Typeformation typeformation =typeformationRepository.findOne(objTypeformation.getIdtypeformation());
    	   typeformation.setIntitule(objTypeformation.getIntitule());
    	   typeformation.setNature(objTypeformation.getNature());
    	   typeformation.setDescription(objTypeformation.getDescription());;
    	   typeformationRepository.save(typeformation);
    	   notifyService.addInfoMessage("Modificcation effectuée avec succès.");
           return "redirect:/pej/typeformations";
       }
       notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
       typeformationRepository.save(objTypeformation);
       return "redirect:/pej/typeformations";
    }

}
