package com.pej.domains;

import java.util.Date;

import org.dozer.Mapping;

public class Odkcandidat {
	@Mapping("dateenregistrement")
	private Date date_enregistrement;
	@Mapping("deviceid")
	private Integer deviceid;//A créer
	@Mapping("agentenregistreur")//A créer
	private String ae;
	@Mapping("agentautre")//A créer
	private String ae_autre;
	@Mapping("enqueteur")//A créer
	private String enqueteur;
	@Mapping("localdepartement")//departement
	private String departement;
	@Mapping("localcommune") //A créer
	private String commune;
	@Mapping("arrondissement")// A créer
	private String arrondissement;
	@Mapping("village") // A créer
	private String village;
	@Mapping("nom")
	private String nom_candidat;
	@Mapping("prenom")
	private String prenom_candidat;
	@Mapping("nourrison")//A créer
	private String avoirbebe_moins2ans;
	@Mapping("sexe")
	private String sexe;
	@Mapping("age")
	private Integer age;
	@Mapping("telprincipal")
	private String telephone_principal;
	@Mapping("telalternatif")
	private String telephone_alternatif;
	@Mapping("documentidentite")//A créer
	private String document_identite;
	@Mapping("autredocidentite")//A créer
	private String document_identite_autre;
	@Mapping("refdocidentite")
	private String numero_piece;
	@Mapping("photolink")
	private String photo;
	@Mapping("languesparlees")
	private String langue_parlee;
	@Mapping("autreslanguesparlees")
	private String langue_parlee_autre;
	@Mapping("situationmatrimoniale")
	private String situation_matri;
	@Mapping("beneficiairepsdcc")//A créer
	private String beneficiaire_psdcc;
	@Mapping("liencm")//A créer
	private String lien_cm;
	@Mapping("nbpersonnemenage")
	private Integer effectif_menage;
	@Mapping("scolarise")
	private String ecole_formelle;
	@Mapping("dernierniveauetude")
	private String niveau_scolaire;
	@Mapping("autreniveauetude")//A créer
	private String niveau_scolaire_autre;
	@Mapping("niveaualphabetfr")//A créer
	private String niveau_alphabet_fr;
	@Mapping("niveaualphabetlocal")//A créer
	private String niveau_alphabet_local;
	@Mapping("qualificationpersonelle")
	private String qual_prof;
	@Mapping("autrequalifpersonelle")
	private String qual_prof_autre;
	@Mapping("formationentrepreunariat")//A créer
	private String formation_entrepreunariat;
	@Mapping("worklastmonth")
	private String travailer_3_der_mois;
	@Mapping("recherchetravail")
	private String chercher_travail_3_der_mois;
	@Mapping("motifnonrecherche")
	private String raison_chercher_travail;
	@Mapping("autrerecherchetravail")//A créer
	private String raison_chercher_travail_autre;
	@Mapping("activiteprincipale")//A créer
	private String activite_principale;
	@Mapping("employeur") //A créer
	private String employeur;
	@Mapping("nbmoistravail")
	private Integer nb_mois_travail_par_an;
	@Mapping("nbjoursmoyen")
	private Integer nb_jour_travail_par_mois;
	@Mapping("nbheuremoyen")
	private Integer nb_heure_travail_par_jour;
	@Mapping("revenumoyen")
	private Integer revenu_mensuel;
	@Mapping("domainesouhait")
	private String domaine_activite;
	@Mapping("travailgroupe")
	private String travail_en_cooperative;
	@Mapping("codebarre")//A créer
	private String code_barre;
	@Mapping("numeroserie")//Numéro série
	private String numero_serie;
	@Mapping("numerocandidat")//Numéro candidat
	private String numero_candidat;
	@Mapping("nomradio")//Nomradio
	private String nomradio; 
	@Mapping("commentaire")//commentaire
	private String commentaire;
	
	public Date getDate_enregistrement() {
		return date_enregistrement;
	}
	public void setDate_enregistrement(Date date_enregistrement) {
		this.date_enregistrement = date_enregistrement;
	}
	public Integer getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(Integer deviceid) {
		this.deviceid = deviceid;
	}
	public String getAe() {
		return ae;
	}
	public void setAe(String ae) {
		this.ae = ae;
	}
	public String getAe_autre() {
		return ae_autre;
	}
	public void setAe_autre(String ae_autre) {
		this.ae_autre = ae_autre;
	}
	public String getEnqueteur() {
		return enqueteur;
	}
	public void setEnqueteur(String enqueteur) {
		this.enqueteur = enqueteur;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getArrondissement() {
		return arrondissement;
	}
	public void setArrondissement(String arrondissement) {
		this.arrondissement = arrondissement;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getNom_candidat() {
		return nom_candidat;
	}
	public void setNom_candidat(String nom_candidat) {
		this.nom_candidat = nom_candidat;
	}
	public String getPrenom_candidat() {
		return prenom_candidat;
	}
	public void setPrenom_candidat(String prenom_candidat) {
		this.prenom_candidat = prenom_candidat;
	}
	public String getAvoirbebe_moins2ans() {
		return avoirbebe_moins2ans;
	}
	public void setAvoirbebe_moins2ans(String avoirbebe_moins2ans) {
		this.avoirbebe_moins2ans = avoirbebe_moins2ans;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getTelephone_principal() {
		return telephone_principal;
	}
	public void setTelephone_principal(String telephone_principal) {
		this.telephone_principal = telephone_principal;
	}
	public String getTelephone_alternatif() {
		return telephone_alternatif;
	}
	public void setTelephone_alternatif(String telephone_alternatif) {
		this.telephone_alternatif = telephone_alternatif;
	}
	public String getDocument_identite() {
		return document_identite;
	}
	public void setDocument_identite(String document_identite) {
		this.document_identite = document_identite;
	}
	public String getDocument_identite_autre() {
		return document_identite_autre;
	}
	public void setDocument_identite_autre(String document_identite_autre) {
		this.document_identite_autre = document_identite_autre;
	}
	public String getNumero_piece() {
		return numero_piece;
	}
	public void setNumero_piece(String numero_piece) {
		this.numero_piece = numero_piece;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getLangue_parlee() {
		return langue_parlee;
	}
	public void setLangue_parlee(String langue_parlee) {
		this.langue_parlee = langue_parlee;
	}
	public String getLangue_parlee_autre() {
		return langue_parlee_autre;
	}
	public void setLangue_parlee_autre(String langue_parlee_autre) {
		this.langue_parlee_autre = langue_parlee_autre;
	}
	public String getSituation_matri() {
		return situation_matri;
	}
	public void setSituation_matri(String situation_matri) {
		this.situation_matri = situation_matri;
	}
	public String getBeneficiaire_psdcc() {
		return beneficiaire_psdcc;
	}
	public void setBeneficiaire_psdcc(String beneficiaire_psdcc) {
		this.beneficiaire_psdcc = beneficiaire_psdcc;
	}
	public String getLien_cm() {
		return lien_cm;
	}
	public void setLien_cm(String lien_cm) {
		this.lien_cm = lien_cm;
	}
	public Integer getEffectif_menage() {
		return effectif_menage;
	}
	public void setEffectif_menage(Integer effectif_menage) {
		this.effectif_menage = effectif_menage;
	}
	public String getEcole_formelle() {
		return ecole_formelle;
	}
	public void setEcole_formelle(String ecole_formelle) {
		this.ecole_formelle = ecole_formelle;
	}
	public String getNiveau_scolaire() {
		return niveau_scolaire;
	}
	public void setNiveau_scolaire(String niveau_scolaire) {
		this.niveau_scolaire = niveau_scolaire;
	}
	public String getNiveau_scolaire_autre() {
		return niveau_scolaire_autre;
	}
	public void setNiveau_scolaire_autre(String niveau_scolaire_autre) {
		this.niveau_scolaire_autre = niveau_scolaire_autre;
	}
	public String getNiveau_alphabet_fr() {
		return niveau_alphabet_fr;
	}
	public void setNiveau_alphabet_fr(String niveau_alphabet_fr) {
		this.niveau_alphabet_fr = niveau_alphabet_fr;
	}
	public String getNiveau_alphabet_local() {
		return niveau_alphabet_local;
	}
	public void setNiveau_alphabet_local(String niveau_alphabet_local) {
		this.niveau_alphabet_local = niveau_alphabet_local;
	}
	public String getQual_prof() {
		return qual_prof;
	}
	public void setQual_prof(String qual_prof) {
		this.qual_prof = qual_prof;
	}
	public String getQual_prof_autre() {
		return qual_prof_autre;
	}
	public void setQual_prof_autre(String qual_prof_autre) {
		this.qual_prof_autre = qual_prof_autre;
	}
	public String getFormation_entrepreunariat() {
		return formation_entrepreunariat;
	}
	public void setFormation_entrepreunariat(String formation_entrepreunariat) {
		this.formation_entrepreunariat = formation_entrepreunariat;
	}
	public String getTravailer_3_der_mois() {
		return travailer_3_der_mois;
	}
	public void setTravailer_3_der_mois(String travailer_3_der_mois) {
		this.travailer_3_der_mois = travailer_3_der_mois;
	}
	public String getChercher_travail_3_der_mois() {
		return chercher_travail_3_der_mois;
	}
	public void setChercher_travail_3_der_mois(String chercher_travail_3_der_mois) {
		this.chercher_travail_3_der_mois = chercher_travail_3_der_mois;
	}
	public String getRaison_chercher_travail() {
		return raison_chercher_travail;
	}
	public void setRaison_chercher_travail(String raison_chercher_travail) {
		this.raison_chercher_travail = raison_chercher_travail;
	}
	public String getRaison_chercher_travail_autre() {
		return raison_chercher_travail_autre;
	}
	public void setRaison_chercher_travail_autre(String raison_chercher_travail_autre) {
		this.raison_chercher_travail_autre = raison_chercher_travail_autre;
	}
	public String getActivite_principale() {
		return activite_principale;
	}
	public void setActivite_principale(String activite_principale) {
		this.activite_principale = activite_principale;
	}
	public String getEmployeur() {
		return employeur;
	}
	public void setEmployeur(String employeur) {
		this.employeur = employeur;
	}
	public Integer getNb_mois_travail_par_an() {
		return nb_mois_travail_par_an;
	}
	public void setNb_mois_travail_par_an(Integer nb_mois_travail_par_an) {
		this.nb_mois_travail_par_an = nb_mois_travail_par_an;
	}
	public Integer getNb_jour_travail_par_mois() {
		return nb_jour_travail_par_mois;
	}
	public void setNb_jour_travail_par_mois(Integer nb_jour_travail_par_mois) {
		this.nb_jour_travail_par_mois = nb_jour_travail_par_mois;
	}
	public Integer getNb_heure_travail_par_jour() {
		return nb_heure_travail_par_jour;
	}
	public void setNb_heure_travail_par_jour(Integer nb_heure_travail_par_jour) {
		this.nb_heure_travail_par_jour = nb_heure_travail_par_jour;
	}
	public Integer getRevenu_mensuel() {
		return revenu_mensuel;
	}
	public void setRevenu_mensuel(Integer revenu_mensuel) {
		this.revenu_mensuel = revenu_mensuel;
	}
	public String getDomaine_activite() {
		return domaine_activite;
	}
	public void setDomaine_activite(String domaine_activite) {
		this.domaine_activite = domaine_activite;
	}
	public String getTravail_en_cooperative() {
		return travail_en_cooperative;
	}
	public void setTravail_en_cooperative(String travail_en_cooperative) {
		this.travail_en_cooperative = travail_en_cooperative;
	}
	public String getCode_barre() {
		return code_barre;
	}
	public void setCode_barre(String code_barre) {
		this.code_barre = code_barre;
	}
	public String getNumero_serie() {
		return numero_serie;
	}
	public void setNumero_serie(String numero_serie) {
		this.numero_serie = numero_serie;
	}
	public String getNumero_candidat() {
		return numero_candidat;
	}
	public void setNumero_candidat(String numero_candidat) {
		this.numero_candidat = numero_candidat;
	}
	public String getNomradio() {
		return nomradio;
	}
	public void setNomradio(String nomradio) {
		this.nomradio = nomradio;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	

}
