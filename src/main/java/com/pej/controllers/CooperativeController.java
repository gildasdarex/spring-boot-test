package com.pej.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.pej.domains.*;
import com.pej.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pej.services.NotificationService;

@Controller
public class CooperativeController {
    @Autowired private CooperativeRepository cooperativeRepository;
    @Autowired private CommuneRepository communeRepository;
    @Autowired private DoncooperativeRepository doncooperativeRepository;
    @Autowired private LotRepository lotRepository;
    @Autowired private NotificationService notifyService;

    @GetMapping("/pej/cooperatives")
    String index(Model model, @ModelAttribute("objCooperative") Cooperative objCooperative) {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        model.addAttribute("cooperatives", cooperatives);
        return "cooperatives";
    }

    @GetMapping("/pej/cooperatives/add")
    public String create(@ModelAttribute("objCooperative") Cooperative objCooperative, ModelMap model) {
        List<Commune> communes = new ArrayList<>();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();

        for (Lot lot : lots) {
            communes.add(communeRepository.findOne(Integer.parseInt(lot.getCommune())));
        }

        model.addAttribute("lots", lots);
        model.addAttribute("objCooperative", objCooperative);
        model.addAttribute("communes", communes);

        return "frmCooperative";
    }

    @GetMapping("/pej/cooperatives/{id}")
    public String edit(@PathVariable Integer id, ModelMap model) {
        List<Commune> communes = new ArrayList<>();
        List<Lot> lots = (List<Lot>) lotRepository.findAll();
        Cooperative objCooperative = cooperativeRepository.findOne(id);

        for (Lot lot : lots) {
            communes.add(communeRepository.findOne(Integer.parseInt(lot.getCommune())));
        }

        model.addAttribute("lots", lots);
        model.addAttribute("objCooperative", objCooperative);
        model.addAttribute("communes", communes);
        return "frmCooperative";
    }

    @PostMapping("/pej/cooperatives")
    public String save(@ModelAttribute(value = "objCooperative") Cooperative objCooperative, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors) {
            }
            notifyService.addErrorMessage("ECHEC DE LENREGISTREMENT ");
            return "frmCooperative";
        }


        if (objCooperative.getIdgroupe() != null && objCooperative.getIdgroupe().intValue() > 0) {
            Cooperative cooperative = cooperativeRepository.findOne(objCooperative.getIdgroupe());
            cooperative.setDescription(objCooperative.getDescription());
            cooperative.setLibgroupe(objCooperative.getLibgroupe());
        }

        cooperativeRepository.save(objCooperative);
        notifyService.addInfoMessage("Opération effectué avec succès");
        return "redirect:/pej/cooperatives";
    }


    /*Récupérer la page d'accord de don à un groupe*/
    @RequestMapping(value = "/pej/cooperatives/don/{id}", method = RequestMethod.GET)
    public String getDonPage(@PathVariable Integer id, ModelMap model) {
        List<DonCooperative> dons = (List<DonCooperative>) doncooperativeRepository.getDonByCopperative(id);

        Cooperative cooperative = cooperativeRepository.findOne(id);
        DonCooperative objDon = new DonCooperative();
        objDon.setCooperative(cooperative);

        model.addAttribute("dons", dons);
        model.addAttribute("cooperative", cooperative);
        model.addAttribute("objDon", objDon);

        return "FrmDonCooperative";
    }

    /*Enregistrer un don pour une coopérative*/
    @PostMapping("/pej/cooperative/don")
    public String saveDon(@Valid @ModelAttribute(value = "objDon") DonCooperative objDon, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/pej/cooperatives";
        }
        Cooperative cooperative = cooperativeRepository.findOne(objDon.getCooperative().getIdgroupe());
        if (cooperative != null) {
            objDon.setCooperative(cooperative);
            doncooperativeRepository.save(objDon);
        }

        return "redirect:/pej/cooperatives";
    }


}
