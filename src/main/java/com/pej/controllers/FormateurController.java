package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pej.domains.Cabinet;
import com.pej.domains.Formateur;
import com.pej.domains.Roles;
import com.pej.domains.UsersRole;
import com.pej.domains.Utilisateur;
import com.pej.repository.CabinetRepository;
import com.pej.repository.FormateurRepository;
import com.pej.repository.UserRoleRepository;
import com.pej.repository.UsersRepository;
import com.pej.services.NotificationService;
import com.pej.repository.RolesRepository;

@Controller
public class FormateurController {
	@Autowired private FormateurRepository formateurRepository;
	@Autowired private CabinetRepository cabinetRepository;
	  @Autowired private UserRoleRepository userRoleRepository;
	    @Autowired private RolesRepository roleRepository;
	    @Autowired private UsersRepository userRepository;
	    @Autowired private NotificationService notifyService;
	    @Autowired
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	    private Utilisateur usercourant;
	@GetMapping("/pej/formateurs")
    String index(Model model,@ModelAttribute("objFormateur") Formateur objFormateur) {
    	System.out.println("Starting Index Ok");
    	   try {
               List<Formateur> formateurs = (List<Formateur>) formateurRepository.findAll();
               model.addAttribute("formateurs", formateurs);
               Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
               if (principal instanceof UserDetails) {
                   String username = ((UserDetails) principal).getUsername();
                   model.addAttribute("username", username);
               } else {
                   String username = principal.toString();
                   model.addAttribute("username", username);
               }
           }catch (Exception ex){
               List<Formateur> formateurs =new ArrayList<>();
               model.addAttribute("formateurs", formateurs);
           }
        return "formateurs";
    }
	
	@GetMapping("/pej/formateurs/add")
	public String editFormateur(@ModelAttribute("objFormateur") Formateur objFormateur, ModelMap model){
		 model.addAttribute("objFormateur", objFormateur); 
		 List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
		 model.addAttribute("cabinets", cabinets); 
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        if (principal instanceof UserDetails) {
	            String username = ((UserDetails)principal).getUsername();
	            model.addAttribute("username", username);
	        } else {
	            String username = principal.toString();
	            model.addAttribute("username", username);
	     }
		 return "frmFormateur";
	}
	
	
	@GetMapping("/pej/formateurs/{id}")
	public String editFormateur(@PathVariable Integer id, ModelMap model){
		  try {
	            Formateur formateur = formateurRepository.findOne(id);
	            Utilisateur user = userRepository.findUserByfristlast(formateur.getNom(), formateur.getPrenom());
	            model.addAttribute("objFormateur", formateur);
	            model.addAttribute("objUtilisateur", user);
	            usercourant = user;
	            List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
	   		 model.addAttribute("cabinets", cabinets); 
	            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	            if (principal instanceof UserDetails) {
	                String username = ((UserDetails) principal).getUsername();
	                model.addAttribute("username", username);
	            } else {
	                String username = principal.toString();
	                model.addAttribute("username", username);
	            }
	        }
	        catch (Exception ex){
	            Formateur formateur=new Formateur();
	            Utilisateur user=new Utilisateur();
	            model.addAttribute("objFormateur", formateur);
	            model.addAttribute("objUtilisateur", user);
	            usercourant = user;
	        }
		 List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
		 model.addAttribute("cabinets", cabinets); 
		 return "frmFormateur";
	}
	
	
    @PostMapping("/pej/formateurs")
    public String saveagents(@ModelAttribute(value="objFormateur")  Formateur objFormateur, BindingResult result,Model model) {
    	System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmFormateur";
        }
        if(objFormateur!=null)
       System.out.println("Nom Formateur: "+objFormateur.getNom());
        else
        	System.out.println("objFormateur est null: ");
        
       if(objFormateur.getIdformateur()!=null && objFormateur.getIdformateur().intValue() >0 ){
    	   Formateur formateur =formateurRepository.findOne(objFormateur.getIdformateur());
    	   formateur.setNom(objFormateur.getNom());
    	   formateur.setPrenom(objFormateur.getPrenom());
    	   formateur.setCardid(objFormateur.getCardid());
    	   formateur.setDatenaissance(objFormateur.getDatenaissance());    	   
    	   formateur.setTelephone(objFormateur.getTelephone());
    	   formateur.setCabinet(objFormateur.getCabinet());
    	   Utilisateur utilisateur= userRepository.findOne(usercourant.getIdusers());
           utilisateur.setUsername(objFormateur.getUsername());
           utilisateur.setPassword(bCryptPasswordEncoder.encode(objFormateur.getPassword()));
    	   formateurRepository.save(formateur);
           return "redirect:/pej/formateurs";
       }
       formateurRepository.save(objFormateur);
       Utilisateur user=new Utilisateur();
       user.setFirstname(objFormateur.getNom());
       user.setLastname(objFormateur.getPrenom());
       user.setPassword(bCryptPasswordEncoder.encode(objFormateur.getPassword()));
       user.setUsername(objFormateur.getUsername());
       userRepository.save(user);
      Roles r=roleRepository.findByName("FORMATEUR");
       if(r!=null) {
           UsersRole userrole = new UsersRole();
           userrole.setRoles(r);
           userrole.setUtilisateur(user);
           userRoleRepository.save(userrole);
       }
       return "redirect:/pej/formateurs";
    }

}
