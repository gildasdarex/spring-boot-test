package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.pej.domains.Antenne;
import com.pej.domains.Arrondissement;
import com.pej.domains.Commune;
import com.pej.domains.Departement;
import com.pej.domains.Permission;
import com.pej.domains.Quartier;
import com.pej.domains.Roles;
import com.pej.domains.Rolespermission;
import com.pej.domains.Utilisateur;
import com.pej.domains.UsersRole;
import com.pej.repository.AntenneRepository;
import com.pej.repository.ArrondissementRepository;
import com.pej.repository.CommuneRepository;
import com.pej.repository.DepartementRepository;
import com.pej.repository.PermissionRepository;
import com.pej.repository.PermissionRoleRepository;
import com.pej.repository.QuartierRepository;
import com.pej.repository.RolesRepository;
import com.pej.repository.UserRoleRepository;
import com.pej.repository.UsersRepository;


@Controller
public class UsersManagementController {
	@Autowired private UsersRepository usersRepository;
	@Autowired private RolesRepository rolesRepository;
	@Autowired private PermissionRepository permissionRepository;
	@Autowired private PermissionRoleRepository permissionRoleRepository;
	@Autowired private UserRoleRepository usersRoleRepository;
	@Autowired private AntenneRepository antenneRepository;
    


	@GetMapping("/pej/usermamagement")
    String index(Model model,@ModelAttribute("objUser") Utilisateur objUsers) {
    	System.out.println("Starting Index Ok");
    	List<Utilisateur> users = (List<Utilisateur>) usersRepository.findAll();
    	List<Roles> roles = (List<Roles>) rolesRepository.findAll();
    	List<Permission> permissions = (List<Permission>) permissionRepository.findAll();    	
    	List<UsersRole> usersRoles = (List<UsersRole>) usersRoleRepository.findAll();
    	List<Rolespermission> rolespermission = (List<Rolespermission>) permissionRoleRepository.findAll();
    	
    	model.addAttribute("users", users);
    	model.addAttribute("roles", roles);
    	model.addAttribute("permissions", permissions);
    	model.addAttribute("usersRoles", usersRoles);
    	model.addAttribute("rolespermission", rolespermission);
        return "usermanagement";
    }
	

	@GetMapping("/pej/roles")
	public String editRoles(@ModelAttribute("ObjRole") Roles ObjRole){
		 return "frmRole";
	}
	@GetMapping("/pej/accessdenied")
    public String accessDenied(){

        return "page404";
    }
	@GetMapping("/pej/roles/{id}")
	public String editRoles(@PathVariable Integer id, ModelMap model){
		 Roles objRole=rolesRepository.findOne(id);
		 model.addAttribute("ObjRole", objRole);
		 return "frmRole";
	}
	@GetMapping("/pej/permissions")
	public String editPermissions(@ModelAttribute("ObjPermission") Permission objPermission){
		 return "frmPermission";
	}
	@GetMapping("/pej/permissions/{id}")
	public String editPermissions(@PathVariable Integer id, ModelMap model){
		 Permission objPermission=permissionRepository.findOne(id);
		 model.addAttribute("ObjPermission", objPermission);
		 return "frmPermission";
	}
	
	
	@GetMapping("/pej/users")
	public String editUsers(@ModelAttribute("ObjUsers") Utilisateur ObjUsers, ModelMap model){
		List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();
		model.addAttribute("antennes", antennes);
		 return "frmUsers";
	}
	@GetMapping("/pej/users/{id}")
	public String editUsers(@PathVariable Integer id, ModelMap model){
		Utilisateur objUsers=usersRepository.findOne(id);
		 model.addAttribute("ObjUsers", objUsers);
		List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();
		model.addAttribute("antennes", antennes);
		 return "frmUsers";
	}
	
	 @PostMapping("/pej/role")
	    public String saverole(@Valid @ModelAttribute(value="ObjRole")  Roles ObjRole, BindingResult result,Model model) {
	    	System.out.println("Starting Save Ok");
	        if (result.hasErrors()) {
	            return "frmRole";
	        }
	        if(ObjRole!=null)
	       System.out.println("Lib role: "+ObjRole.getName());
	        else
	        	System.out.println("ObjRole est null: ");
	        
	       if(ObjRole.getIdrole()!=null && ObjRole.getIdrole().intValue() >0 ){
	    	   Roles roles=rolesRepository.findOne(ObjRole.getIdrole().intValue());
	    	   roles.setName(ObjRole.getName());
	    	   rolesRepository.save(roles);
	           return "redirect:/pej/usermamagement";
	       }
	       rolesRepository.save(ObjRole);
	       return "redirect:/pej/usermamagement";
	    }
	
	 @PostMapping("/pej/permission")
	    public String savepermission(@Valid @ModelAttribute(value="ObjPermission")  Permission ObjPermission, BindingResult result,Model model) {
	    	System.out.println("Starting Save Ok");
	        if (result.hasErrors()) {
	            return "frmPermission";
	        }
	        if(ObjPermission!=null)
	       System.out.println("Lib permission: "+ObjPermission.getName());
	        else
	        	System.out.println("ObjRole est null: ");
	        
	       if(ObjPermission.getIdpermission()!=null && ObjPermission.getIdpermission().intValue() >0 ){
	    	   Permission permission=permissionRepository.findOne(ObjPermission.getIdpermission().intValue());
	    	   permission.setName(ObjPermission.getName());
	    	   permissionRepository.save(permission);
	           return "redirect:/pej/usermamagement";
	       }
	       permissionRepository.save(ObjPermission);
	       return "redirect:/pej/usermamagement";
	    }
	
	 @PostMapping("/pej/users")
	    public String saveusers(@Valid @ModelAttribute(value="ObjUsers")  Utilisateur ObjUsers, BindingResult result,Model model) {
	    	System.out.println("Starting Save Ok");
	        if (result.hasErrors()) {
	            return "frmUsers";
	        }
	        if(ObjUsers!=null)
	       System.out.println("Lib permission: "+ObjUsers.getFirstname());
	        else
	        	System.out.println("ObjUsers est null: ");
	        
	       if(ObjUsers.getIdusers()!=null && ObjUsers.getIdusers().intValue() >0 ){
	    	   Utilisateur users=usersRepository.findOne(ObjUsers.getIdusers().intValue());
	    	   users.setFirstname(ObjUsers.getFirstname());
	    	   users.setLastname(ObjUsers.getLastname());
	    	   users.setUsername(ObjUsers.getUsername());
	    	   users.setPassword(ObjUsers.getPassword());
	    	   //users.setDateModified(DATE.getCurrentDate());
	           return "redirect:/pej/usermamagement";
	       }
	       //ObjUsers.setDateModified(DATE.getCurrentDate());
	       ObjUsers.setIdusers(2);
	       ObjUsers.setEnabled(1);
	       System.out.println("Utilisateur: "+ObjUsers.toString());
	       usersRepository.save(ObjUsers);
	       
	       return "redirect:/pej/usermamagement";
	    }
	 	
		@GetMapping("/pej/users/role/{id}")
		public String usersAddRole(@PathVariable Integer id, ModelMap model){
			
			Utilisateur objUsers=usersRepository.findOne(id);
			 model.addAttribute("ObjUsers", objUsers);
			List<Roles> roles = (List<Roles>) usersRepository.getNotInRoleUser(id);
			List<Roles> inRoles = (List<Roles>) usersRepository.getInRoleUser(id);
			model.addAttribute("roles", roles);
			model.addAttribute("inRoles", inRoles);
			 return "frmUserRoles";
		}
		
		@GetMapping("/pej/permission/role/{id}")
		public String permissionAddRole(@PathVariable Integer id, ModelMap model){
			Utilisateur objUsers=usersRepository.findOne(id);
			 model.addAttribute("ObjUsers", objUsers);
			List<Antenne> antennes = (List<Antenne>) antenneRepository.findAll();
			model.addAttribute("antennes", antennes);
			 return "frmUsers";
		}

}
