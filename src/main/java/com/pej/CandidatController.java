package com.pej;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import com.pej.domains.Departement;
import com.pej.domains.Commune;
import com.pej.domains.Arrondissement;
import com.pej.repository.*;
import java.util.List;

import javax.validation.Valid;

@Controller
public class CandidatController {
	@Autowired private DepartementRepository departementRepository;
	
	@RequestMapping(value="/candidats", method=RequestMethod.GET)
    String index(Model model) {
    	List<Departement> departements = (List<Departement>) departementRepository.findAll();
    	model.addAttribute("departements", departements);
        return "candidat";
    }

}
