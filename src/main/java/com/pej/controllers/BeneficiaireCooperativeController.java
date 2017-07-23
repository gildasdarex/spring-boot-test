package com.pej.controllers;

import java.util.List;

import com.pej.services.BeneficiaireCooperativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.pej.domains.Beneficiairecooperative;
import com.pej.domains.Candidat;
import com.pej.domains.Cooperative;
import com.pej.domains.Formationbeneficaire;
import com.pej.repository.CandidatRepository;
import com.pej.repository.BeneficiaireCooperativeRepository;
import com.pej.repository.CooperativeRepository;
import com.pej.services.NotificationService;

@Controller
public class BeneficiaireCooperativeController {
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private BeneficiaireCooperativeRepository beneficiaireCooperativeRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private NotificationService notifyService;
    @Autowired
    private BeneficiaireCooperativeService beneficiaireCooperativeService;

    @RequestMapping(value = "/pej/beneficiairecooperative/cooperative/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id) {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.getFreeCandidats();
        Cooperative cooperative = cooperativeRepository.findOne(id);
        List<Candidat> candidatscooperative = (List<Candidat>) candidatRepository.getInCooperativeCandidat(id);

        model.addAttribute("candidatscooperative", candidatscooperative);
        model.addAttribute("cooperative", cooperative);
        model.addAttribute("candidats", candidats);

        return "beneficiairecooperative";
    }

    @GetMapping("/pej/beneficiairecooperative/{idgroupe}/candidat/{idcandidat}")
    public String addFormationBeneficiaire(@PathVariable Integer idgroupe, @PathVariable Integer idcandidat) {

        boolean success = beneficiaireCooperativeService.addBeneficiaire(idgroupe,idcandidat);
        if (success) {
            notifyService.addSucceesgMessage("CANDIDATA AJOUTE A LA COOPERATIVE AVEC SUCCESS.");
        } else {
            notifyService.addWarningMessage("ECHEC ENREGISTREMENT. CANDIDAT OU COOPERATIVE NON TROUVE");
        }
        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }


    @PostMapping("/pej/beneficiairecooperative/multiple/{idgroupe}")
    public String addMultipleFormationBeneficiaire(@PathVariable Integer idgroupe,@RequestParam("table_records") List<String> table_records) {
        for(String id :table_records){
            beneficiaireCooperativeService.addBeneficiaire(idgroupe, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }


    @GetMapping("/pej/beneficiairecooperative/rm/{idgroupe}/candidat/{idcandidat}")
    public String removeFormationBenenficiaire(@PathVariable Integer idgroupe, @PathVariable Integer idcandidat) {

        beneficiaireCooperativeService.removeCandidatFromCooperative(idgroupe, idcandidat);
        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }

    @PostMapping("/pej/beneficiairecooperative/remove/{idgroupe}")
    public String removeMultipleFormationBeneficiaire(@PathVariable Integer idgroupe,@RequestParam("table_records") List<String> table_records) {
        for(String id :table_records){
            beneficiaireCooperativeService.removeCandidatFromCooperative(idgroupe, Integer.parseInt(id));
        }

        return "redirect:" + "/pej/beneficiairecooperative/cooperative/" + idgroupe;
    }

}
