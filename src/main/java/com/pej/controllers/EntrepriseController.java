package com.pej.controllers;
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
import com.pej.services.NotificationService;

import java.util.ArrayList;
import java.util.List;
@Controller
public class EntrepriseController {
    @Autowired private EntrepriseRepository entrepriseRepository;
    @Autowired private DepartementRepository departementRepository;
    @Autowired private CommuneRepository communeRepository;
    @Autowired private ArrondissementRepository arrondissementRepository;
    @Autowired private QuartierRepository quartierRepository;
    @Autowired private CandidatRepository candidatRepository;
    @Autowired private FormateurRepository formateurRepository;
    @Autowired private UsersRepository usersRepository;
    @Autowired private EntreprisesFormateursRepository entreprisesFormateursRepository;
    @Autowired private NotificationService notifyService;
    private Entreprise objEntrepriseEdit;
    private EntrepriseFormateursForm objetEntrepriseFormateursForm;
    @GetMapping("/pej/entreprises")
    String index(Model model,@ModelAttribute("objEntreprise") Entreprise objEntreprise,@ModelAttribute("objEntrepriseFormateurs") EntrepriseFormateursForm objEntrepriseFormateurs) {
        System.out.println("Starting Index Ok");



        model.addAttribute("objEntrepriseFormateurs", objetEntrepriseFormateursForm);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            try {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);

                Utilisateur user=usersRepository.findByUsername(username);
                List<Entreprise> entreprises =  entreprisesFormateursRepository.getEntreprisesDuFormateur(formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()).getIdformateur());
                model.addAttribute("entreprises", entreprises);
                if(formateurRepository.findByFormateur(user.getFirstname(),user.getLastname())!=null){
                    model.addAttribute("type", "1");
                }
                else{
                    model.addAttribute("type", "0");
                }
            }catch (Exception ex){
                List<Entreprise> entreprises=new ArrayList<>();
                model.addAttribute("entreprises", entreprises);
            }

        } else {
            try {
            String username = principal.toString();
            model.addAttribute("username", username);
            Utilisateur user=usersRepository.findByUsername(username);
            List<Entreprise> entreprises =  entreprisesFormateursRepository.getEntreprisesDuFormateur(formateurRepository.findByFormateur(user.getFirstname(),user.getLastname()).getIdformateur());
            model.addAttribute("entreprises", entreprises);
            }catch (Exception ex){
                List<Entreprise> entreprises=new ArrayList<>();
                model.addAttribute("entreprises", entreprises);
            }
        }
        return "entreprises";
    }
    /*Récupérer la liste des commmune d'un département*/
    @RequestMapping(value = "/pej/entreprises/commune/{id}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Commune> findCommune(@PathVariable Integer id) {
        Departement departement=departementRepository.findOne(id);
        //return (List<Commune>)communeRepository.getCommune(id);
        //return (List<Commune>)departement.getCommunes()();
//        System.out.println(communeRepository.getCommune(id).get(0).getLibcommune());
        return communeRepository.getCommunes(id);
    }
    @GetMapping("/pej/entreprise/{id}")
    public String editEntreprise(@PathVariable Integer id, ModelMap model){
        Entreprise entreprise=entrepriseRepository.findOne(id);
        model.addAttribute("objEntreprise", entreprise);
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("candidats", candidatRepository.findAll());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmEntreprise";
    }

    /*Récupérer la liste des arrondissement d'une commune*/
    @RequestMapping(value = "/pej/entreprises/arrondissement/{id}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Arrondissement> findArrondissement(@PathVariable Integer id) {
        Commune commune=communeRepository.findOne(id);
        return new ArrayList<Arrondissement>(commune.getArrondissements());
    }
    /*Récupérer la liste des quartier  d'un arrondissement*/
    @RequestMapping(value = "/pej/entreprises/quartier/{id}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Quartier> findQuartier(@PathVariable Integer id) {
        Arrondissement arrondissement=arrondissementRepository.findOne(id);
        return new ArrayList<Quartier>(arrondissement.getQuartiers());
    }
    /*Suppression entreprise*/
    @RequestMapping(value = "/pej/entreprises/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody String suppressionEntreprise(@PathVariable Integer id) {
        Entreprise entreprise=entrepriseRepository.findOne(id);
        entrepriseRepository.delete(entreprise);
        return "success";
    }



    /*Mise à jour entreprise*/
    @RequestMapping(value = "/pej/entreprises/edition", method = RequestMethod.GET)
    public @ResponseBody String editionEntreprise(@ModelAttribute("objEntreprise") Entreprise objEntreprise, ModelMap model) {
       entrepriseRepository.save(objEntreprise);
        return "success";
    }
    /*Modal edition entreprise */
    @RequestMapping(value = "/pej/entreprises/{id}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<EntrepriseFormBean> editModalEntreprise(@PathVariable Integer id, ModelMap model) {
        model.addAttribute("objEntreprise", objEntrepriseEdit);
        Entreprise entreprise=entrepriseRepository.findOne(id);
        System.out.println(entreprise.getQuartier().getLibquartier());
        List<EntrepriseFormBean> liste=new ArrayList<>();
        EntrepriseFormBean bean=new EntrepriseFormBean();
        bean.setQuartierChoisie(entreprise.getQuartier());
        bean.setArrondissementChoisie(entreprise.getQuartier().getArrondissement());
       bean.setCommuneChoisie(entreprise.getQuartier().getArrondissement().getCommune());
        bean.setCandidatChoisie(entreprise.getCandidat());
        bean.setDepartementChoisie(entreprise.getQuartier().getArrondissement().getCommune().getDepartement());
        bean.setQuartiers((List<Quartier>) quartierRepository.findAll());
        bean.setArrondissements((List<Arrondissement>) arrondissementRepository.findAll());
        bean.setCommunes((List<Commune>) communeRepository.findAll());
        bean.setDepartements((List<Departement>) departementRepository.findAll());
        bean.setIdentreprise(entreprise.getIdentreprise());
        bean.setRaisonsociale(entreprise.getRaisonsocial());
        bean.setNumeroifu(entreprise.getNumeroifu());
        bean.setContact(entreprise.getContact());
        bean.setLatitude(entreprise.getLatitude());
        bean.setLongitude(entreprise.getLongitude());
        bean.setTypeentreprise(entreprise.getTypeentreprise());
        bean.setCandidats((List<Candidat>)candidatRepository.findAll());
        liste.add(bean);
        return new ArrayList<EntrepriseFormBean>(liste);
    }



    @GetMapping("/pej/entreprises/add")
    public String editEntreprise(@ModelAttribute("objEntreprise") Entreprise objEntreprise, ModelMap model){
        model.addAttribute("objEntreprise", objEntreprise);
        model.addAttribute("departements", departementRepository.findAll());
        model.addAttribute("candidats", candidatRepository.findAll());


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmEntreprise";
    }

    @GetMapping("/pej/entreprisesformateurs/{id}")
    public String associerEntreprisesformateurs(@PathVariable Integer id, @ModelAttribute("objEntrepriseFormateurs") EntrepriseFormateursForm objEntrepriseFormateurs, ModelMap model){
        model.addAttribute("objEntrepriseFormateurs", objEntrepriseFormateurs);
        model.addAttribute("entreprise", entrepriseRepository.findOne(id));
        objEntrepriseFormateurs.setEntreprise(entrepriseRepository.findOne(id));
        model.addAttribute("formateursEntreprise", formateurRepository.findAll());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmEntrepriseFormateurs";
    }

    @GetMapping("/pej/editionentreprisesformateurs/{id}")
    public String deassocierEntreprisesformateurs(@PathVariable Integer id,@ModelAttribute("objEditionEntrepriseFormateurs") EntrepriseFormateursForm objEditionEntrepriseFormateurs, ModelMap model){
        model.addAttribute("objEditionEntrepriseFormateurs", objEditionEntrepriseFormateurs);
        model.addAttribute("entreprise", entrepriseRepository.findOne(id));
        model.addAttribute("formateurs",entreprisesFormateursRepository.getEntreprisesFormateurs(id));
        objEditionEntrepriseFormateurs.setEntreprise(entrepriseRepository.findOne(id));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            model.addAttribute("username", username);
        } else {
            String username = principal.toString();
            model.addAttribute("username", username);
        }
        return "frmEditionEntrepriseFormateurs";
    }
    @PostMapping("/pej/editionentreprisesformateurs")
    public String editionentreprisesformateurs(@ModelAttribute(value="objEditionEntrepriseFormateurs")  EntrepriseFormateursForm objEditionEntrepriseFormateurs, BindingResult result,Model model) {
        System.out.println("Starting Edition Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec Modification.");
            return "frmEditionEntrepriseFormateurs";
        }
        if(objEditionEntrepriseFormateurs!=null)
            System.out.println("Raison entreprise: "+objEditionEntrepriseFormateurs.getEntreprise().getRaisonsocial());
        else
            System.out.println("objEntrepriseFormateurs est null: ");
      //  List<Formateur> listeformateur=entreprisesFormateursRepository.getEntreprisesFormateurs(objEditionEntrepriseFormateurs.getEntreprise().getIdentreprise());
        //listeformateur.removeAll(objEditionEntrepriseFormateurs.getFormateurs());

        for(Formateur objet:objEditionEntrepriseFormateurs.getFormateurs()) {
            entreprisesFormateursRepository.delete(entreprisesFormateursRepository.getByEntreprisesFormateurs(objEditionEntrepriseFormateurs.getEntreprise().getIdentreprise(),objet.getIdformateur()));
            System.out.println("formateur "+objet);
        }


        notifyService.addInfoMessage("Modification effectuée avec succès.");
        //entrepriseRepository.save(objEntreprise);
        return "redirect:/pej/entreprises";
    }

    /*Récupérer la liste des formateurs*/
    @RequestMapping(value = "/pej/editionentreprisesformateurs/formateur/{id}", method = RequestMethod.GET)
    public @ResponseBody ArrayList<Formateur> findFormateurs(@PathVariable Integer id) {

        //return (List<Commune>)communeRepository.getCommune(id);
        //return (List<Commune>)departement.getCommunes()();
//        System.out.println(communeRepository.getCommune(id).get(0).getLibcommune());
        return (ArrayList<Formateur>) entreprisesFormateursRepository.getEntreprisesFormateurs(id);
    }
    @PostMapping("/pej/entreprisesformateurs")
    public String saveentreprisesformateurs(@ModelAttribute(value="objEntrepriseFormateurs")  EntrepriseFormateursForm objEntrepriseFormateurs, BindingResult result,Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec enregistrement.");
            return "frmEntrepriseFormateurs";
        }
        if(objEntrepriseFormateurs!=null)
            System.out.println("Raison entreprise: "+objEntrepriseFormateurs.getEntreprise().getRaisonsocial());
        else
            System.out.println("objEntrepriseFormateurs est null: ");


        for(Formateur objet:objEntrepriseFormateurs.getFormateurs()) {
            EntrepriseFormateur entrepriseformateur=new EntrepriseFormateur();
            entrepriseformateur.setEntreprise(objEntrepriseFormateurs.getEntreprise());
            entrepriseformateur.setFormateur(objet);
            entreprisesFormateursRepository.save(entrepriseformateur);
           System.out.println("formateur "+objet);
       }


        notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
        //entrepriseRepository.save(objEntreprise);
        return "redirect:/pej/entreprises";
    }


    @PostMapping("/pej/entreprises")
    public String saveentreprises(@ModelAttribute(value="objEntreprise")  Entreprise objEntreprise, BindingResult result,Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            notifyService.addErrorMessage("Echec enregistrement.");
            return "frmEntreprise";
        }
        if(objEntreprise!=null)
            System.out.println("Raison entreprise: "+objEntreprise.getRaisonsocial());
        else
            System.out.println("objEntreprise est null: ");

        if(objEntreprise.getIdentreprise()!=null && objEntreprise.getIdentreprise().intValue() >0 ){
            Entreprise entreprise =entrepriseRepository.findOne(objEntreprise.getIdentreprise());
            entreprise.setRaisonsocial(objEntreprise.getRaisonsocial());
            entreprise.setNumeroifu(objEntreprise.getNumeroifu());
            entreprise.setContact(objEntreprise.getContact());
            entreprise.setQuartier(objEntreprise.getQuartier());
            entreprise.setLatitude(objEntreprise.getLatitude());
            entreprise.setLongitude(objEntreprise.getLongitude());
            entreprise.setCandidat(objEntreprise.getCandidat());
            entrepriseRepository.save(entreprise);
            notifyService.addInfoMessage("Modificcation effectuée avec succès.");
            return "redirect:/pej/entreprises";
        }
        notifyService.addInfoMessage("Enregistrement effectuée avec succès.");
        entrepriseRepository.save(objEntreprise);
        return "redirect:/pej/entreprises";
    }

}
