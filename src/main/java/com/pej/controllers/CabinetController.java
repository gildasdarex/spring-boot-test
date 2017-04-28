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
import com.pej.domains.Cabinet;
import com.pej.repository.CabinetRepository;
import com.pej.services.NotificationService;

@Controller
public class CabinetController {
	@Autowired private CabinetRepository cabinetRepository;
	@Autowired private NotificationService notifyService;
	@GetMapping("/pej/cabinets")
    String index(Model model,@ModelAttribute("objCabinet") Cabinet objCabinet) {
    	System.out.println("Starting Index Ok");
    	List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();  	
    	model.addAttribute("cabinets", cabinets);
        return "cabinets";
    }
	
	@GetMapping("/pej/cabinets/add")
	public String editCabinet(@ModelAttribute("objCabinet") Cabinet objCabinet, ModelMap model){
		 
		 List<Cabinet> antennes = (List<Cabinet>) cabinetRepository.findAll();
		 model.addAttribute("antennes", antennes); 
		 model.addAttribute("objCabinet", objCabinet); 
		 return "frmCabinet";
	}
	
	@GetMapping("/pej/cabinet/{id}")
	public String modifierCabinet(@PathVariable Integer id, ModelMap model){

		 Cabinet objCabinet=cabinetRepository.findOne(id);
	
		 model.addAttribute("objCabinet", objCabinet); 
		 return "frmCabinet";
	}
	
    @PostMapping("/pej/cabinets")
    public String saveagents(@ModelAttribute(value="objCabinet")  Cabinet objCabinet, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
        	notifyService.addErrorMessage("Echec enregistrement.");
            return "frmCabinet";
        }
        if(objCabinet.getIdcabinet()!=null)
       System.out.println("id cabinet: "+objCabinet.getIntitule());
        else
        	System.out.println("objCabinet est null: ");
        
       if(objCabinet.getIdcabinet()!=null && objCabinet.getIdcabinet().intValue() >0 ){
    	   Cabinet cabinet =cabinetRepository.findOne(objCabinet.getIdcabinet());
    	   cabinet.setIntitule(objCabinet.getIntitule());
    	   cabinet.setResponsable(objCabinet.getResponsable());
    	   cabinet.setTelephone(objCabinet.getTelephone());
    	   cabinetRepository.save(cabinet);
    	   notifyService.addInfoMessage("Modificcation effectuée avec succès.");
           return "redirect:/pej/cabinets";
       }
       notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
       cabinetRepository.save(objCabinet);
       return "redirect:/pej/cabinets";
    }

}
