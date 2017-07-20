package com.pej.controllers;

import com.pej.pojo.OdkCandidat;
import com.pej.services.CandidatService;
import com.poiji.internal.Poiji;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.apache.camel.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.pej.domains.Departement;
import com.pej.domains.Don;
import com.pej.domains.Dossiers;
import com.pej.domains.FileBucket;
import com.pej.domains.Parametres;
import com.pej.domains.Quartier;
import com.pej.domains.Statutcandidat;
import com.pej.filtres.FCandidat;
import com.pej.domains.Commune;
import com.pej.domains.Agent;
import com.pej.domains.Arrondissement;
import com.pej.domains.Candidat;
import com.pej.domains.Fichefinancement;
import com.pej.domains.Fournisseur;
import com.pej.domains.Materiel;
import com.pej.repository.*;
import com.pej.services.NotificationService;
import com.pej.utils.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;


@Controller
public class CandidatController {
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private ArrondissementRepository arrondissementRepository;
    @Autowired
    private QuartierRepository quartierRepository;
    @Autowired
    private CandidatRepository candidatRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private StatutcandidatRepository statutcandidatRepository;
    @Autowired
    private DonRepository donRepository;
    @Autowired
    private NotificationService notifyService;
    @Autowired
    private CandidatJpaRepo candidatJpaRepo;
    @Autowired
    private ParametresRepository parametresRepository;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private DossierRepository dossierRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private FicheFinancementRepository ficheFinancementRepository;
    @Autowired
    private org.dozer.Mapper mapper;
    @Autowired
    private CandidatService candidatService;


    Fichefinancement fiche = new Fichefinancement();
    @Value("${tmp.location}")
    String tempLocation;

    @GetMapping("/pej/candidats")
    String index(Model model, @ModelAttribute("objDepartement") Departement objDepartement) {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAll();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                model.addAttribute("username", username);
            } else {
                String username = principal.toString();
                model.addAttribute("username", username);
            }
        } catch (Exception e) {
            return "redirect:/pej/login";
        }

        model.addAttribute("candidats", candidats);
        FCandidat objCritere = new FCandidat();
        model.addAttribute("objCritere", objCritere);
        model.addAttribute("communes", communes);
        model.addAttribute("statuts", statuts);
        return "candidats";
    }

    /*Récupérer la liste des candidats au format json*/
    @RequestMapping(value = "/pej/candidatjson", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Candidat> findCandidatJson() throws IOException {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.findAll();
        Candidats listCnd = new Candidats(candidats);
        JSONUtils.createAndWriteToFile("C:/FFOutput/candidats.json", listCnd, Candidats.class);
        return candidats;
    }

    @RequestMapping(value = "/pej/candidats/add", method = RequestMethod.GET)
    String addCandidat(@ModelAttribute("objCandidat") Candidat objCandidat, ModelMap model) {
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        List<Arrondissement> arrondissements = (List<Arrondissement>) arrondissementRepository.findAll();
        List<Quartier> quartiers = (List<Quartier>) quartierRepository.findAll();

        model.addAttribute("departements", departements);
        model.addAttribute("communes", communes);
        model.addAttribute("arrondissements", arrondissements);
        model.addAttribute("quartiers", quartiers);

        return "frmCandidat";
    }


    @RequestMapping(value = "/pej/candidats/commune/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Commune> findCommune(@PathVariable Integer id) {
        Departement departement = departementRepository.findOne(id);
        return new ArrayList<Commune>(departement.getCommunes());
    }

    /*Récupérer la liste des arrondissement d'une commune*/
    @RequestMapping(value = "/pej/candidats/arrondissement/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Arrondissement> findArrondissement(@PathVariable Integer id) {
        Commune commune = communeRepository.findOne(id);
        return new ArrayList<Arrondissement>(commune.getArrondissements());
    }

    /*Récupérer la liste des quartier  d'un arrondissement*/
    @RequestMapping(value = "/pej/candidats/quartier/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ArrayList<Quartier> findQuartier(@PathVariable Integer id) {
        Arrondissement arrondissement = arrondissementRepository.findOne(id);
        return new ArrayList<Quartier>(arrondissement.getQuartiers());
    }

    /*Récupérer la liste des quartier  d'un arrondissement*/
    @RequestMapping(value = "/pej/candidats/agent/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Agent findAgent(@PathVariable String id) {
        Agent agent = agentRepository.getAgent(id);
        return agent;
    }

    @PostMapping("/pej/candidats")
    public String savecandidat(@Valid @ModelAttribute(value = "objCandidat")
                                       Candidat objCandidat,
                               BindingResult result, Model model) {


        Agent agent = agentRepository.getAgent(objCandidat.getNumeroagent());
        if (objCandidat.getIdcandidat() != null && objCandidat.getIdcandidat().intValue() > 0) {
            try {
                Candidat candidat = candidatRepository.findOne(objCandidat.getIdcandidat());
                candidat.setAgent(agent);
                candidat.setNom(objCandidat.getNom());
                candidat.setPrenom(objCandidat.getPrenom());
                candidat.setDatenaissance(objCandidat.getDatenaissance());
                candidat.setDocidentite(objCandidat.getDocidentite());
                candidat.setNiveau(candidat.getNiveau());
                candidat.setDiplome(objCandidat.getDiplome());
                candidat.setCodearrondissement(objCandidat.getCodearrondissement());
                candidat.setRefdocidentite(objCandidat.getRefdocidentite());
                candidat.setTelprincipal(objCandidat.getTelprincipal());
                candidat.setTelalternatif(objCandidat.getTelalternatif());
                candidat.setAge(objCandidat.getAge());
                candidat.setSexe(objCandidat.getSexe());
                candidat.setSituationmatrimoniale(objCandidat.getSituationmatrimoniale());
                candidat.setParentechefmenage(objCandidat.getParentechefmenage());
                candidat.setNbpersonnemenage(objCandidat.getNbpersonnemenage());
                candidat.setMenagebeneficiaire(objCandidat.getMenagebeneficiaire());
                candidat.setScolarise(objCandidat.getScolarise());
                candidat.setDernierniveauetude(objCandidat.getDernierniveauetude());
                candidat.setLecture(objCandidat.getLecture());
                candidat.setQualificationpersonelle(objCandidat.getQualificationpersonelle());
                candidat.setWorklastmonth(objCandidat.getWorklastmonth());
                candidat.setRecherchetravail(objCandidat.getRecherchetravail());
                candidat.setMotifnonrecherche(objCandidat.getMotifnonrecherche());
                candidat.setActiviteactuelle(objCandidat.getActiviteactuelle());
                candidat.setAsoncompte(objCandidat.getAsoncompte());

                candidat.setNbmoistravail(objCandidat.getNbmoistravail());
                candidat.setNbjoursmoyen(objCandidat.getNbjoursmoyen());
                candidat.setNbheuremoyen(objCandidat.getNbheuremoyen());
                candidat.setRevenumoyen(objCandidat.getRevenumoyen());
                candidat.setDomainesouhait(objCandidat.getDomainesouhait());
                candidat.setTravailgroupe(objCandidat.getTravailgroupe());
                candidat.setNumcarteagratter(objCandidat.getNumcarteagratter());
                candidat.setQuartier(objCandidat.getQuartier());
                candidat.setCommune(objCandidat.getCommune());
                candidat.setDepartement(objCandidat.getDepartement());
                candidat.setDateenregistrement(objCandidat.getDateenregistrement());
                candidat.setNumeroagent(objCandidat.getNumeroagent());
                candidat.setNumerofiche(objCandidat.getNumerofiche());

                candidatRepository.save(objCandidat);
                notifyService.addInfoMessage("Candidat modififé avec succès.");
            } catch (Exception e) {
                notifyService.addErrorMessage("Echec lors de la modification du candidat. " + e.getMessage());
            }
        }
        Statutcandidat st = statutcandidatRepository.findOne(1);
        if (st == null) {
            notifyService.addErrorMessage("Aucun statut par défaut configuré pour les candidats");
            return "";
        }
        objCandidat.setStatutcandidat(st);
        objCandidat.setAgent(agent);
        System.out.print("Agent récupéré: " + agent.getNom());
        System.out.print("Saving candidat: " + objCandidat.toString());

        try {
            candidatRepository.save(objCandidat);
            notifyService.addInfoMessage("Candidat enregistré avec succès.");
        } catch (Exception e) {
            notifyService.addErrorMessage("Echec lors de l'enregistrement du candidat. " + e.getMessage());
        }
        return "redirect:/pej/candidats";
    }

    /*Changer de statut pour un candidat en cliquant sur le bouton valider*/
    @RequestMapping(value = "/pej/candidats/valider/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Candidat upgradeStatut(@PathVariable Integer id) {
        Candidat candidat = candidatRepository.findOne(id);
        Statutcandidat statut = statutcandidatRepository.findOne(candidat.getStatutcandidat().getId() + 1);
        if (statut != null) {
            candidat.setStatutcandidat(statut);
            candidatRepository.save(candidat);
        }

        return candidat;
    }


    /*Upgrade en max des candidats*/
    @RequestMapping(value = "/pej/candidats/validermax/{id}", method = RequestMethod.GET)
    public String upgradeStatuts(@PathVariable String id) {
        String[] ids = id.split("@");
        List<Candidat> candidatToUpgrade = new ArrayList<Candidat>();
        for (int i = 0; i < ids.length; i++) {
            Candidat candidat = candidatRepository.findOne(Integer.parseInt(ids[i]));
            if (candidat == null) continue;
            Statutcandidat statut = statutcandidatRepository.findOne(candidat.getStatutcandidat().getId() + 1);
            if (statut != null) {
                candidat.setStatutcandidat(statut);
                candidatRepository.save(candidat);
                candidatToUpgrade.add(candidat);
            }
        }
        //return candidatToUpgrade;
        return "redirect:/pej/candidats";
    }

    /*Récupérer la page d'accord de don à un candidat*/
    @RequestMapping(value = "/pej/candidats/don/{id}", method = RequestMethod.GET)
    public String getDonPage(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        Don objDon = new Don();
        objDon.setCandidat(candidat);
        List dons = (List<Don>) donRepository.getDonByCandidat(id);
        //List<Don> dons =candidat.getDons().stream().collect(Collectors.toList());
        System.out.println("Nbre de ligne: " + dons.size());
        model.addAttribute("dons", dons);
        model.addAttribute("objDon", objDon);
        return "frmDon";
    }

    /*Enregistrer un don pour un candidat*/
    @PostMapping("/pej/candidats/don")
    public String saveDon(@Valid @ModelAttribute(value = "objDon") Don objDon, BindingResult result, Model model) {
        System.out.println("Starting Save Ok");
        if (result.hasErrors()) {
            return "frmDon";
        }
        Candidat candidat = candidatRepository.findOne(objDon.getCandidat().getIdcandidat());
        if (candidat != null) {
            objDon.setCandidat(candidat);
            donRepository.save(objDon);
        }

        return "redirect:/pej/candidats";
    }

    @GetMapping("/pej/candidats/{id}")

    public String updateCandidat(@PathVariable Integer id, ModelMap model) {
        List<Departement> departements = (List<Departement>) departementRepository.findAll();
        model.addAttribute("departements", departements);
        Candidat candidat = candidatRepository.findOne(id);
        model.addAttribute("objCandidat", candidat);
        return "frmCandidat";
    }


    @GetMapping("/pej/candidats/upload")
    public String uploadFile(ModelMap model) {
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "frmUploadCandidat";
    }

    @RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
    public String getSingleUploadPage(ModelMap model) {
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
        return "singleFileUploader";
    }

    @RequestMapping(value = "/pej/candidats/upload", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {

        MultipartFile multipartFile = fileBucket.getFile();

        File convFile = new File(tempLocation + "/" + multipartFile.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        List<OdkCandidat> odkCandidats = Poiji.fromExcel(convFile, OdkCandidat.class);
//        List<Candidat> candidats = new ArrayList<>();
//        for (OdkCandidat odkCandidat : odkCandidats) {
//            candidats.add(mapper.map(odkCandidat, Candidat.class));
//        }
//        System.out.println(odkCandidats.size());
//        System.out.println(candidats.size());
//
//        Class<?> clazz = odkCandidats.get(0).getClass();
//
//        for(Field field : clazz.getDeclaredFields()) {
//            //you can also use .toGenericString() instead of .getName(). This will
//            //give you the type information as well.
//
//            try {
//                System.out.println("##########  " + field.getName() + " #########  "+field.get(odkCandidats.get(0)) );
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (Candidat candidat : candidats) {
//            candidatRepository.save(candidat);
//        }
        candidatService.saveFromFile(odkCandidats);
        return "redirect:/pej/formations";

    }

    @GetMapping("/pej/candidats/tirage")
    String tirage(Model model, @ModelAttribute("objDepartement") Departement objDepartement) {
        List<Candidat> candidats = (List<Candidat>) candidatRepository.getEligibleCandidat(2);
        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        model.addAttribute("communes", communes);
        model.addAttribute("candidats", candidats);

        Tirage objTirage = new Tirage();
        model.addAttribute("objTirage", objTirage);
        System.out.println("Nbre candidats: " + candidats.size());
        return "tirages";
    }


    @PostMapping("/pej/candidats/tirage")
    String tirer(Model model, @ModelAttribute("objTirage") Tirage objTirage) {
        Sort.Direction ordre = (objTirage.getOrdre().equalsIgnoreCase("Croissant")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable request = new PageRequest(0, objTirage.getNombre(), ordre, "numcarteagratter");

        List<Candidat> candidats = (List<Candidat>) candidatJpaRepo.getEligibleCandidat(request, objTirage.getCommune().getCodecommune()).getContent();
        //List<Candidat> candidats = (List<Candidat>)candidatJpaRepo.findAll(request).getContent();

        List<Commune> communes = (List<Commune>) communeRepository.findAll();
        model.addAttribute("communes", communes);
        model.addAttribute("candidats", candidats);

        Statutcandidat statutcandidat = statutcandidatRepository.findOne(3);
        for (int i = 0; i < candidats.size(); i++) {
            candidats.get(i).setStatutcandidat(statutcandidat);
            candidatRepository.save(candidats.get(i));
        }

        model.addAttribute("objTirage", objTirage);
        System.out.println("Nbre candidats: " + candidats.size());

        return "tirages";
    }

    private void saveImage(String filename, MultipartFile image)
            throws RuntimeException, IOException {
        try {
            File file = new File(servletContext.getRealPath("/") + "/"
                    + filename);

            FileUtils.writeByteArrayToFile(file, image.getBytes());
            System.out.println("Go to the location:  " + file.toString()
                    + " on your computer and verify that the image has been stored.");
        } catch (IOException e) {
            throw e;
        }
    }

    @GetMapping("/pej/candidats/picture/{id}")
    public String uploadPicture(@PathVariable Integer id, ModelMap model) {
        FileBucket fileModel = new FileBucket();
        fileModel.setId(id);
        model.addAttribute("fileBucket", fileModel);
        return "frmUploadPicture";
    }


    @RequestMapping(value = "/pej/candidats/picture", method = RequestMethod.POST)
    public String uploadPicture(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
        Parametres parametres = parametresRepository.findOne(1);
        System.out.println("servletContext.getContextPath() " + servletContext.getRealPath("/"));
        System.out.println("Fetching file");


        try {
            //MultipartFile multipartFile = fileBucket.getFile();
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();
            fileName = fileName + "." + FilenameUtils.getExtension(fileBucket.getFile().getOriginalFilename());
            byte[] imageByteArray = new byte[fileBucket.getFile().getBytes().length];

            String fileLocation = new File("src\\main\\resources\\static\\images").getAbsolutePath() + "\\" + fileName;
            FileOutputStream fos = new FileOutputStream(fileLocation);
            fos.write(fileBucket.getFile().getBytes());
            fos.close();
            System.out.println("servletContext.getContext " + servletContext.getContext("/"));

            //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
            //System.out.println("fileLocation "+fileLocation);
            //FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fileLocation));
            Candidat candidat = candidatRepository.findOne(fileBucket.getId());
            candidat.setPhotolink(fileName);
            candidatRepository.save(candidat);
            notifyService.addSucceesgMessage("Photo enregistrée avec succès.");
            System.out.println("End copy file");

        } catch (Exception e) {
            notifyService.addErrorMessage("Une erreur est survenue lors du chargement de la photo.");
            System.out.println("Erreur " + e.getMessage());
            // TODO: handle exception
        }


        return "redirect:/pej/candidats";

    }

    @RequestMapping(value = "/pej/candidats/eligible", method = RequestMethod.GET)
    private String getEligibles() {
        List<Candidat> candidatToUpgrade = (List<Candidat>) candidatRepository.getEligibleCandidat();
        Statutcandidat statut = statutcandidatRepository.findOne(2);
        for (int i = 0; i < candidatToUpgrade.size(); i++) {
            candidatToUpgrade.get(i).setStatutcandidat(statut);
            ;
            candidatRepository.save(candidatToUpgrade.get(i));
        }
        notifyService.addSucceesgMessage("Candidats éligibles sélectionnés.");
        return "redirect:/pej/candidats";
    }

    @GetMapping("/pej/candidats/dossier/{id}")
    public String dossier(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        FileBucket fileModel = new FileBucket();
        fileModel.setId(id);
        model.addAttribute("candidat", candidat);
        model.addAttribute("fileBucket", fileModel);
        List<Dossiers> monDossier = (List<Dossiers>) dossierRepository.getDossierCandidat(id);
        model.addAttribute("dossiers", monDossier);
        return "frmUploadDossier";
    }


    @RequestMapping(value = "/pej/candidats/dossier/", method = RequestMethod.POST)
    public String uploadDossier(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
        Parametres parametres = parametresRepository.findOne(1);
        System.out.println("servletContext.getContextPath() " + servletContext.getRealPath("/"));
        System.out.println("Fetching file");

        //MultipartFile multipartFile = fileBucket.getFile();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString();
        fileName = fileName + "." + FilenameUtils.getExtension(fileBucket.getFile().getOriginalFilename());
        byte[] imageByteArray = new byte[999999];


        //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
        String fileLocation = servletContext.getRealPath("/") + fileName;

			 /*FileOutputStream fos = new FileOutputStream(fileLocation);
			 fos.write(imageByteArray);
			 fos.close();*/


        //String fileLocation = new File("static\\images").getAbsolutePath() + "\\" + fileName;
        System.out.println("fileLocation " + fileLocation);
        FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fileLocation));
        Candidat candidat = candidatRepository.findOne(fileBucket.getId());

        Dossiers dossier = new Dossiers();
        dossier.setCandidat(candidat);
        dossier.setNature(fileBucket.getNature());
        dossier.setLink(fileName);
        dossierRepository.save(dossier);

        notifyService.addSucceesgMessage("Fichier enregistrée avec succès.");
        System.out.println("End copy file");
        return "redirect:/pej/candidats/dossier/" + candidat.getIdcandidat();

    }

    @GetMapping("/pej/candidats/materiels/{id}")
    public String candidatMateriels(@PathVariable Integer id, ModelMap model) {
        List<Fichefinancement> fichefinancements = (List<Fichefinancement>) ficheFinancementRepository.getFicheFinancement(id);
        model.addAttribute("fichefinancements", fichefinancements);
        model.addAttribute("idcandidat", id);
        return "fichemateriels";
    }

    @GetMapping("/pej/candidats/materiels/edit/{id}/{slug}")
    public String editFicheFinancement(@PathVariable Integer id, @PathVariable Integer slug, ModelMap model) {
        fiche = ficheFinancementRepository.findOne(slug);
        List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
        List<Materiel> materiels = (List<Materiel>) materielRepository.findAll();
        Candidat candidat = candidatRepository.findOne(id);
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("materiels", materiels);
        model.addAttribute("candidats", candidat);
        model.addAttribute("objFichefinancement", fiche);
        return "frmFicheFinancement";
    }

    @GetMapping("/pej/candidats/materiels/delete/{id}/{slug}")
    public String deleteFicheFinancement(@PathVariable Integer id, @PathVariable Integer slug, ModelMap model) {
        Fichefinancement fiche = ficheFinancementRepository.findOne(slug);
        ficheFinancementRepository.delete(fiche);
        notifyService.addInfoMessage("Suppression  effectuée avec succès.");
        return "redirect:/pej/candidats/materiels/{id}";
    }

    @GetMapping("/pej/candidats/materiels/add/{id}")
    public String addcandidatMateriels(@PathVariable Integer id, @ModelAttribute("objFichefinancement") Fichefinancement objFicheFinancement, ModelMap model) {
        List<Fournisseur> fournisseurs = (List<Fournisseur>) fournisseurRepository.findAll();
        List<Materiel> materiels = (List<Materiel>) materielRepository.findAll();
        Candidat candidat = candidatRepository.findOne(id);
        model.addAttribute("fournisseurs", fournisseurs);
        model.addAttribute("materiels", materiels);
        model.addAttribute("candidats", candidat);
        return "frmFicheFinancement";
    }

    @GetMapping("/pej/candidats/materiels/add/action/{id}")
    public String actioncandidatMateriels(@PathVariable Integer id, @ModelAttribute("objFichefinancement") Fichefinancement objFicheFinancement, ModelMap model) {
        if (fiche.getIdfichefinancement() != null && fiche.getIdfichefinancement().intValue() > 0) {
            Fichefinancement objet = ficheFinancementRepository.findOne(fiche.getIdfichefinancement());
            objet.setCandidat(objFicheFinancement.getCandidat());
            objet.setMateriel(objFicheFinancement.getMateriel());
            objet.setFournisseur(objFicheFinancement.getFournisseur());
            objet.setPrixunitaire(objFicheFinancement.getPrixunitaire());
            objet.setQuantite(objFicheFinancement.getQuantite());
            objFicheFinancement.setCandidat(candidatRepository.findOne(id));
            objet.setCandidat(objFicheFinancement.getCandidat());
            ficheFinancementRepository.save(objet);
            notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
            fiche = new Fichefinancement();
            return "redirect:/pej/candidats/materiels/{id}";
        }
        objFicheFinancement.setCandidat(candidatRepository.findOne(id));
        ficheFinancementRepository.save(objFicheFinancement);
        notifyService.addInfoMessage("Enrégistrement  effectuée avec succès.");
        return "redirect:/pej/candidats/materiels/add/{id}";
    }

    @GetMapping("/pej/candidats/profil/{id}")
    public String showprofile(@PathVariable Integer id, ModelMap model) {
        Candidat candidat = candidatRepository.findOne(id);
        if (candidat == null) {
            notifyService.addErrorMessage("Ce candidat n'existe pas.");
            return "redirect:/pej/candidats";
        }
        model.addAttribute("candidat", candidat);
        return "contact";
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/pej/candidats/searchContrat")
    String searchContrat(@ModelAttribute(value = "objCritere") FCandidat fCandidat, BindingResult result, Model model) {

        Pageable request = new PageRequest(0, 50);
        String nomcandidat = fCandidat.getSearchnom() == null ? "" : fCandidat.getSearchnom();
        String nomagent = fCandidat.getSearchagent() == null ? "" : fCandidat.getSearchagent();
        Date datenaissance = fCandidat.getSearchdate() == null ? null : fCandidat.getSearchdate();
        int statut = fCandidat.getSearchstatut() == null ? 0 : fCandidat.getSearchstatut().getId();
        int commune = fCandidat.getSearchstatut() == null ? 0 : fCandidat.getSearchstatut().getId();

        List<Candidat> candidats = (List<Candidat>) candidatJpaRepo.getSearchCandidat(commune,
                statut, nomcandidat, datenaissance, nomagent);

        List<Commune> communes = (List<Commune>) communeRepository.findAll();

        List<Statutcandidat> statuts = (List<Statutcandidat>) statutcandidatRepository.findAll();

        model.addAttribute("communes", communes);
        model.addAttribute("candidats", candidats);
        model.addAttribute("statuts", statuts);
        model.addAttribute("objCritere", fCandidat);
        return "candidats";
    }
}
