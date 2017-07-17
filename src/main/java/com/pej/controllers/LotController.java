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
import com.pej.domains.Commune;
import com.pej.domains.Cooperative;
import com.pej.domains.Lot;
import com.pej.repository.LotRepository;
import com.pej.repository.CabinetRepository;
import com.pej.repository.CommuneRepository;
import com.pej.repository.CooperativeRepository;
import com.pej.services.NotificationService;

@Controller
public class LotController {
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private CabinetRepository cabinetRepository;
    @Autowired
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private NotificationService notifyService;

    @GetMapping("/pej/lots")
    String index(Model model, @ModelAttribute("objLot") Lot objLot) {
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        model.addAttribute("lots", lots);
        return "lots";
    }

    @GetMapping("/pej/lots/add")
    public String editLot(@ModelAttribute("objLot") Lot objLot, ModelMap model) {

        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();

        model.addAttribute("cabinets", cabinets);
        model.addAttribute("communes", communes);
        model.addAttribute("objLot", objLot);

        return "frmLot";
    }

    @GetMapping("/pej/lots/{id}")
    public String modifierLot(@PathVariable Integer id, ModelMap model) {

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        Lot objLot = lotRepository.findOne(id);
        List<Cabinet> cabinets = (List<Cabinet>) cabinetRepository.findAll();

        model.addAttribute("communes", communes);
        model.addAttribute("cabinets", cabinets);
        model.addAttribute("objLot", objLot);

        return "frmLot";
    }

    @PostMapping("/pej/lots")
    public String savelots(@ModelAttribute(value = "objLot") Lot objLot, BindingResult result) {

        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec enregistrement.");
            return "frmLot";
        }
        lotRepository.save(objLot);
        notifyService.addInfoMessage("OPERATION EFFECTUEE AVEC SUCCESS.");
        return "redirect:/pej/lots";
    }

    @RequestMapping(value = "/pej/lots/cooperative/add/{id}", method = RequestMethod.GET)
    String index(Model model, @PathVariable Integer id) {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.getNotInLotCooperative();
        Lot lot = lotRepository.findOne(id);
        List<Cooperative> cooperativelots = (List<Cooperative>) cooperativeRepository.getInLotCooperative(id);

        model.addAttribute("cooperatives", cooperatives);
        model.addAttribute("lot", lot);
        model.addAttribute("cooperativelots", cooperativelots);

        return "lotcooperatives";
    }

    @RequestMapping(value = "/pej/lots/cooperative/setlot/{idcooperative}/{idlot}", method = RequestMethod.GET)
    String setLot(@PathVariable Integer idcooperative, @PathVariable Integer idlot) {
        Cooperative cooperative = cooperativeRepository.findOne(idcooperative);
        Lot lot = lotRepository.findOne(idlot);
        cooperative.setLot(lot);
        cooperativeRepository.save(cooperative);

        return "redirect:/pej/lots/cooperative/add/" + lot.getIdlot();
    }
}
