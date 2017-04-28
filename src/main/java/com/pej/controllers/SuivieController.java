package com.pej.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pej.domains.*;
import com.pej.repository.*;
import com.pej.services.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
@Controller
public class SuivieController {
    @Autowired private SuivieRepository suivieRepository;
    @Autowired private AnneesRepository anneesRepository;
    @Autowired private FormateurRepository formateurRepository;
    @Autowired private EntrepriseRepository entrepriseRepository;
    @Autowired private PeriodeRepository periodeRepository;
    @Autowired private UsersRepository usersRepository;
    @Autowired private NotificationService notifyService;

    private Suivie unsuivie;
    @GetMapping("/pej/suivies")
    String index(Model model,@ModelAttribute("ObjSuivies") Suivie objSuivies) {
        System.out.println("Starting Index Ok");
        List<Suivie> suivies = (List<Suivie>) suivieRepository.findAll();
        model.addAttribute("objSuivies", objSuivies);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "suivies";
    }
    public List<LocalDateTime> getDateRange(LocalDateTime start, LocalDateTime end,Integer periode,String type) {
        List<LocalDateTime> ret = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:MM:ss");
        LocalDateTime tmpDebut = LocalDateTime.now();
        while (tmpDebut.isBefore(LocalDateTime.now().plusYears(1)) || tmpDebut.equals(LocalDateTime.now().plusYears(1))) {
             ret.add(tmpDebut);
            if(type.equals("Mois"))
            tmpDebut= tmpDebut.plusMonths(periode);
            else
                tmpDebut= tmpDebut.plusDays(periode);

        }
        return ret;
    }

    @GetMapping("/pej/suivies/add")
    public String addAnnee(@ModelAttribute("ObjSuivies") Suivie ObjSuivies, ModelMap model){
        model.addAttribute("ObjSuivies", ObjSuivies);
        model.addAttribute("entreprises", entrepriseRepository.findAll());
        model.addAttribute("formateurs", formateurRepository.findAll());
        model.addAttribute("annees", anneesRepository.findAll());
        model.addAttribute("periodes",periodeRepository.findAll() );
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmSuivies";
    }

    @GetMapping("/pej/generercalendrier/form")
    public String generercalendrierform(@ModelAttribute("ObjSuivies") Suivie ObjSuivies, ModelMap model){
        model.addAttribute("ObjSuivies", ObjSuivies);
        model.addAttribute("entreprises", entrepriseRepository.findAll());
        model.addAttribute("formateurs", formateurRepository.findAll());
        model.addAttribute("annees", anneesRepository.findAll());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmGenererCalendrier";
    }
    @GetMapping("/pej/genererlecalendrier")
    public String genererCalendar(@ModelAttribute(value="objSuivies")  Suivie objSuivies, BindingResult result,Model model) {
        System.out.println(toJson(suivieRepository.getByEFASuivies(objSuivies.getEntreprise().getIdentreprise(),objSuivies.getFormateur().getIdformateur(),objSuivies.getAnnees().getIdannees())));
        model.addAttribute("listesuivies", toJson(suivieRepository.getByEFASuivies(objSuivies.getEntreprise().getIdentreprise(),objSuivies.getFormateur().getIdformateur(),objSuivies.getAnnees().getIdannees())));
        //objSuivies =new Suivie();
        objSuivies.setEntreprise(objSuivies.getEntreprise());
        objSuivies.setFormateur(objSuivies.getFormateur());
        objSuivies.setAnnees(objSuivies.getAnnees());
        objSuivies.setPeriode(periodeRepository.findOne(1));
        model.addAttribute("objetUnSuivie",objSuivies);

            return "frmCalendrier";

    }

    @GetMapping("/pej/calendrierformateur/{id}")
    public String CalendarFormateur(@PathVariable Integer id,@ModelAttribute(value="objSuivies")  Suivie objSuivies, BindingResult result,Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utilisateur user;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
           user= usersRepository.findByUsername(username);
        } else {
            String username = principal.toString();
            user= usersRepository.findByUsername(username);
        }
        System.out.println(formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()));
        System.out.println(toJson(suivieRepository.getBySuiviesCalendar(entrepriseRepository.findOne(id).getIdentreprise(),formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()).getIdformateur())));
        model.addAttribute("listesuivies", toJson(suivieRepository.getBySuiviesCalendar(entrepriseRepository.findOne(id).getIdentreprise(),formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()).getIdformateur())));
        //objSuivies =new Suivie();
        objSuivies.setEntreprise(entrepriseRepository.findOne(id));
        objSuivies.setFormateur(formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()));
       // objSuivies.setAnnees(objSuivies.getAnnees());
        objSuivies.setPeriode(periodeRepository.findOne(1));
        model.addAttribute("objetUnSuivie",objSuivies);

        return "frmCalendrier";

    }

    @RequestMapping(value = "/pej/supprimerunsuivie", method = RequestMethod.POST)
    @ResponseBody public String supprimerUnSuivie(@RequestParam String idsuivie) {
        Suivie objetsuivie= suivieRepository.findOne(Integer.parseInt(idsuivie));
        suivieRepository.delete(objetsuivie);
        return toJson(suivieRepository.findAll());
    }

    @RequestMapping(value = "/pej/changerstatutgeo", method = RequestMethod.POST)
    @ResponseBody public String changerStatutGeo(@RequestParam String idsuivie,@RequestParam String longitudeGeo,@RequestParam String latitudeGeo) {
        Suivie objetsuivie= suivieRepository.findOne(Integer.parseInt(idsuivie));

       if(objetsuivie.getEntreprise().getLongitude().equals(longitudeGeo) && objetsuivie.getEntreprise().getLatitude().equals(latitudeGeo)){
           objetsuivie.setStatut("oui");
           System.out.println("logitude "+latitudeGeo);
           suivieRepository.save(objetsuivie);
       }
       else{
           objetsuivie.setStatut("non");
           System.out.println("long="+latitudeGeo);
           suivieRepository.save(objetsuivie);
       }
        return toJson(suivieRepository.findAll());
    }

    @RequestMapping(value = "/pej/genererunsuivie", method = RequestMethod.POST)
    @ResponseBody public String creerUnSuivie(@RequestParam String idsuivie, @RequestParam String datedebut, @RequestParam String datefin,
                                              @RequestParam String statut, @RequestParam String entreprise, @RequestParam String formateur,
                                              @RequestParam String annees,@RequestParam String periode) {
       if(idsuivie!=""){
           Suivie objetsuivie= suivieRepository.findOne(Integer.parseInt(idsuivie));
           objetsuivie.setDatedebut(convertirDate(datedebut, "dd/MM/yy"));
           objetsuivie.setDatefin(convertirDate(datefin, "dd/MM/yy"));
           objetsuivie.setFormateur(formateurRepository.findOne(Integer.parseInt(formateur)));
           objetsuivie.setEntreprise(entrepriseRepository.findOne(Integer.parseInt(entreprise)));
           //objetsuivie.setAnnees(anneesRepository.findOne(Integer.parseInt(annees)));
           objetsuivie.setPeriode(periodeRepository.findOne(Integer.parseInt(periode)));
           objetsuivie.setStatut(statut);
           suivieRepository.save(objetsuivie);

           return toJson(suivieRepository.getByEFASuivies(objetsuivie.getEntreprise().getIdentreprise(), objetsuivie.getFormateur().getIdformateur(), objetsuivie.getAnnees().getIdannees()));
       }
       else {
           Suivie objetsuivie = new Suivie();
           objetsuivie.setIdsuivie(99);
           System.out.println(convertirDate(datedebut, "dd/MM/yy"));
           System.out.println(datedebut);
           objetsuivie.setDatedebut(convertirDate(datedebut, "dd/MM/yy"));
           objetsuivie.setDatefin(convertirDate(datefin, "dd/MM/yy"));
           objetsuivie.setFormateur(formateurRepository.findOne(Integer.parseInt(formateur)));
           objetsuivie.setEntreprise(entrepriseRepository.findOne(Integer.parseInt(entreprise)));
//           objetsuivie.setAnnees(anneesRepository.findOne(Integer.parseInt(annees)));
          // objetsuivie.setPeriode(periodeRepository.findOne(1));
           objetsuivie.setStatut(statut);
           suivieRepository.save(objetsuivie);

           return toJson(suivieRepository.getByEFASuivies(objetsuivie.getEntreprise().getIdentreprise(), objetsuivie.getFormateur().getIdformateur(), objetsuivie.getAnnees().getIdannees()));
       }
       }
    @GetMapping("/pej/suivies/{id}")
    public String editSuivie(@PathVariable Integer id, ModelMap model){
        Suivie suivie=suivieRepository.findOne(id);
        model.addAttribute("ObjSuivies", suivie);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmSuivies";
    }
    @PostMapping("/pej/suivies")
    public String savesuivie(@ModelAttribute(value="ObjSuivies")  Suivie objSuivies, BindingResult result,Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec Enrégistrement.");
            return "frmSuivies";
        }
        if(objSuivies!=null)
            System.out.println("Suivie: "+objSuivies.getStatut());
        else
            System.out.println("ObjSuivies est null: ");

        if(objSuivies.getIdsuivie()!=null && objSuivies.getIdsuivie().intValue() >0 ){
            Suivie suivie =suivieRepository.findOne(objSuivies.getIdsuivie());
            suivie.setStatut(objSuivies.getStatut());
            suivieRepository.save(suivie);
            return "redirect:/pej/suivies/add";
        }

        for(LocalDateTime unedate: getDateRange(convertirEnLocalDateTime(objSuivies.getDatedebut()),convertirEnLocalDateTime(objSuivies.getDatefin()),objSuivies.getPeriode().getNombre(),objSuivies.getPeriode().getType())){
            Suivie unesuivie=new Suivie();
            unesuivie.setStatut(objSuivies.getStatut());
            unesuivie.setFormateur(objSuivies.getFormateur());
            unesuivie.setEntreprise(objSuivies.getEntreprise());
            unesuivie.setPeriode(objSuivies.getPeriode());
            unesuivie.setAnnees(objSuivies.getAnnees());
            unesuivie.setDatedebut(convertirEnDate(unedate));
            unesuivie.setDatefin(convertirEnDate(unedate));
            suivieRepository.save(unesuivie);
            System.out.println("date ="+unedate);
        }

        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/suivies/add";
    }
    /**
     * Convertir une date java.time.LocalDateTime en java.util.Date.
     *
     * @param dateTime Date java.time.LocalDateTime à convertir.
     * @return
     */
    public static Date convertirEnDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    /**
     * Convertir une date java.util.Date en java.time.LocalDateTime.
     *
     * @param date Date java.util.Date à convertir.
     * @return
     */
    public  LocalDateTime convertirEnLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    @GetMapping("/pej/suivies/delete/{id}")
    public String deleteAnnees(@PathVariable Integer id, ModelMap model){

        Annees annes=anneesRepository.findOne(id);
        anneesRepository.delete(annes);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/annees/add";
    }
    public String toJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public static Date convertirDate(String dateString, String format) {
        Date date = null;
        try {
            SimpleDateFormat formatToString = new SimpleDateFormat(format);
            formatToString.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = formatToString.parse(dateString);
        } catch (ParseException ex) {
            //Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

}