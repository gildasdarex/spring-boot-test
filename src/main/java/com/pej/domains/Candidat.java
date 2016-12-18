package com.pej.domains;
// Generated 30 oct. 2016 22:13:12 by Hibernate Tools 5.2.0.Beta1



import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Candidat generated by hbm2java
 */
@Entity
@Table(name="CANDIDAT"
    ,schema="ADMINPEJ"
)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Candidat  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idcandidat;
     private Statutcandidat statutcandidat;
     private Agent agent;
     private String nom;
     private String prenom;
     @DateTimeFormat(pattern = "dd/MM/yyyy")
     private Date datenaissance;
     private String docidentite;
     private String niveau;
     private String diplome;
     private String codearrondissement;
     private String userlogin;
     @DateTimeFormat(pattern = "dd/MM/yyyy")
     private Date datecreation;
     private String refdocidentite;
     private String telprincipal;
     private String telalternatif;
     private Integer age;
     private String sexe;
     private String situationmatrimoniale;
     private String parentechefmenage;
     private Integer nbpersonnemenage;
     private Integer menagebeneficiaire;
     private String scolarise;
     private String dernierniveauetude;
     private String lecture;
     private String ecriture;
     private String qualificationpersonelle;
     private Integer worklastmonth;
     private Integer recherchetravail;
     private String motifnonrecherche;
     private String activiteactuelle;
     private String asoncompte;
     private Integer nbmoistravail;
     private Integer nbjoursmoyen;
     private Integer nbheuremoyen;
     private String revenumoyen;
     private String domainesouhait;
     private Integer travailgroupe;
     private String numcarteagratter;
     @JsonIgnore
     private Quartier quartier;
     private String commune;
     private String departement;
     @DateTimeFormat(pattern = "dd/MM/yyyy")
     private Date dateenregistrement;
     private String numeroagent;
     private String numerofiche;
     private String languesparlees;
     private String precisionactivite;
     private String precisionactivitesouhaiter;
     private int niveauvalidation;
     private Set<Formationbeneficaire> formationbeneficaires = new HashSet<Formationbeneficaire>(0);
     
    public Candidat() {
    }

	
    public Candidat(Integer idcandidat) {
        this.idcandidat = idcandidat;
    }
    public Candidat(Integer idcandidat, Agent agent, String nom, String prenom, Date datenaissance, String docidentite, String niveau, String diplome, String codearrondissement, String userlogin, Date datecreation, String refdocidentite, String telprincipal, String telalternatif, Integer age, String sexe, String situationmatrimoniale, String parentechefmenage, Integer nbpersonnemenage, Integer menagebeneficiaire, String scolarise, String dernierniveauetude, Integer niveaualphabetisation, String qualificationpersonelle, Integer worklastmonth, Integer recherchetravail, String motifnonrecherche, String activiteactuelle, String asoncompte, Integer nbmoistravail, Integer nbjoursmoyen, Integer nbheuremoyen, String revenumoyen, String domainesouhait, Integer travailgroupe, String numcarteagratter, Quartier quartier, String commune, String departement, Date dateenregistrement, String numeroagent, String numerofiche, Set<Beneficiaire> beneficiaires) {
       this.idcandidat = idcandidat;
       this.agent = agent;
       this.nom = nom;
       this.prenom = prenom;
       this.datenaissance = datenaissance;
       this.docidentite = docidentite;
       this.niveau = niveau;
       this.diplome = diplome;
       this.codearrondissement = codearrondissement;
       this.userlogin = userlogin;
       this.datecreation = datecreation;
       this.refdocidentite = refdocidentite;
       this.telprincipal = telprincipal;
       this.telalternatif = telalternatif;
       this.age = age;
       this.sexe = sexe;
       this.situationmatrimoniale = situationmatrimoniale;
       this.parentechefmenage = parentechefmenage;
       this.nbpersonnemenage = nbpersonnemenage;
       this.menagebeneficiaire = menagebeneficiaire;
       this.scolarise = scolarise;
       this.dernierniveauetude = dernierniveauetude;
       this.lecture = lecture;
       this.qualificationpersonelle = qualificationpersonelle;
       this.worklastmonth = worklastmonth;
       this.recherchetravail = recherchetravail;
       this.motifnonrecherche = motifnonrecherche;
       this.activiteactuelle = activiteactuelle;
       this.asoncompte = asoncompte;
       this.nbmoistravail = nbmoistravail;
       this.nbjoursmoyen = nbjoursmoyen;
       this.nbheuremoyen = nbheuremoyen;
       this.revenumoyen = revenumoyen;
       this.domainesouhait = domainesouhait;
       this.travailgroupe = travailgroupe;
       this.numcarteagratter = numcarteagratter;
       this.quartier = quartier;
       this.commune = commune;
       this.departement = departement;
       this.dateenregistrement = dateenregistrement;
       this.numeroagent = numeroagent;
       this.numerofiche = numerofiche;
      
    }
   
	@Id 
	@GeneratedValue(generator = "SEQ_IDCANDIDAT", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_IDCANDIDAT", sequenceName = "SEQ_IDCANDIDAT",allocationSize=1)
	@Column(name="IDCANDIDAT", unique=true, nullable=false, precision=22, scale=0)
    public Integer getIdcandidat() {
        return this.idcandidat;
    }
    
    public void setIdcandidat(Integer idcandidat) {
        this.idcandidat = idcandidat;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDAGENT")
    public Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    
    @Column(name="NOM", length=50)
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    @Column(name="PRENOM", length=50)
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATENAISSANCE", length=7)
    public Date getDatenaissance() {
        return this.datenaissance;
    }
    
    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    
    @Column(name="DOCIDENTITE", length=20)
    public String getDocidentite() {
        return this.docidentite;
    }
    
    public void setDocidentite(String docidentite) {
        this.docidentite = docidentite;
    }

    
    @Column(name="NIVEAU", length=50)
    public String getNiveau() {
        return this.niveau;
    }
    
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    
    @Column(name="DIPLOME", length=50)
    public String getDiplome() {
        return this.diplome;
    }
    
    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    
    @Column(name="CODEARRONDISSEMENT", length=20)
    public String getCodearrondissement() {
        return this.codearrondissement;
    }
    
    public void setCodearrondissement(String codearrondissement) {
        this.codearrondissement = codearrondissement;
    }

    
    @Column(name="USERLOGIN", length=50)
    public String getUserlogin() {
        return this.userlogin;
    }
    
    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATECREATION", length=7)
    public Date getDatecreation() {
        return this.datecreation;
    }
    
    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    
    @Column(name="REFDOCIDENTITE", length=50)
    public String getRefdocidentite() {
        return this.refdocidentite;
    }
    
    public void setRefdocidentite(String refdocidentite) {
        this.refdocidentite = refdocidentite;
    }

    
    @Column(name="TELPRINCIPAL", length=20)
    public String getTelprincipal() {
        return this.telprincipal;
    }
    
    public void setTelprincipal(String telprincipal) {
        this.telprincipal = telprincipal;
    }

    
    @Column(name="TELALTERNATIF", length=20)
    public String getTelalternatif() {
        return this.telalternatif;
    }
    
    public void setTelalternatif(String telalternatif) {
        this.telalternatif = telalternatif;
    }

    
    @Column(name="AGE", precision=22, scale=0)
    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }

    
    @Column(name="SEXE", length=20)
    public String getSexe() {
        return this.sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    
    @Column(name="SITUATIONMATRIMONIALE", length=20)
    public String getSituationmatrimoniale() {
        return this.situationmatrimoniale;
    }
    
    public void setSituationmatrimoniale(String situationmatrimoniale) {
        this.situationmatrimoniale = situationmatrimoniale;
    }

    
    @Column(name="PARENTECHEFMENAGE", length=128)
    public String getParentechefmenage() {
        return this.parentechefmenage;
    }
    
    public void setParentechefmenage(String parentechefmenage) {
        this.parentechefmenage = parentechefmenage;
    }

    
    @Column(name="NBPERSONNEMENAGE", precision=22, scale=0)
    public Integer getNbpersonnemenage() {
        return this.nbpersonnemenage;
    }
    
    public void setNbpersonnemenage(Integer nbpersonnemenage) {
        this.nbpersonnemenage = nbpersonnemenage;
    }

    
    @Column(name="MENAGEBENEFICIAIRE", precision=22, scale=0)
    public Integer getMenagebeneficiaire() {
        return this.menagebeneficiaire;
    }
    
    public void setMenagebeneficiaire(Integer menagebeneficiaire) {
        this.menagebeneficiaire = menagebeneficiaire;
    }

    
    @Column(name="SCOLARISE", precision=22, scale=0)
    public String getScolarise() {
        return this.scolarise;
    }
    
    public void setScolarise(String scolarise) {
        this.scolarise = scolarise;
    }

    
    @Column(name="DERNIERNIVEAUETUDE", length=128)
    public String getDernierniveauetude() {
        return this.dernierniveauetude;
    }
    
    public void setDernierniveauetude(String dernierniveauetude) {
        this.dernierniveauetude = dernierniveauetude;
    }

    
    @Column(name="LECTURE", precision=22, scale=0)
    public String getLecture() {
        return this.lecture;
    }
    
    public void setLecture(String lecture) {
        this.lecture =lecture;
    }

    @Column(name="ECRITURE", precision=22, scale=0)
    public String getEcriture() {
        return this.ecriture;
    }
    
    public void setEcriture(String ecriture) {
        this.ecriture = ecriture;
    }
    
    @Column(name="QUALIFICATIONPERSONELLE", length=128)
    public String getQualificationpersonelle() {
        return this.qualificationpersonelle;
    }
    
    public void setQualificationpersonelle(String qualificationpersonelle) {
        this.qualificationpersonelle = qualificationpersonelle;
    }

    
    @Column(name="WORKLASTMONTH", precision=22, scale=0)
    public Integer getWorklastmonth() {
        return this.worklastmonth;
    }
    
    public void setWorklastmonth(Integer worklastmonth) {
        this.worklastmonth = worklastmonth;
    }

    
    @Column(name="RECHERCHETRAVAIL", precision=22, scale=0)
    public Integer getRecherchetravail() {
        return this.recherchetravail;
    }
    
    public void setRecherchetravail(Integer recherchetravail) {
        this.recherchetravail = recherchetravail;
    }

    
    @Column(name="MOTIFNONRECHERCHE")
    public String getMotifnonrecherche() {
        return this.motifnonrecherche;
    }
    
    public void setMotifnonrecherche(String motifnonrecherche) {
        this.motifnonrecherche = motifnonrecherche;
    }

    
    @Column(name="ACTIVITEACTUELLE")
    public String getActiviteactuelle() {
        return this.activiteactuelle;
    }
    
    public void setActiviteactuelle(String activiteactuelle) {
        this.activiteactuelle = activiteactuelle;
    }

    
    @Column(name="ASONCOMPTE", length=128)
    public String getAsoncompte() {
        return this.asoncompte;
    }
    
    public void setAsoncompte(String asoncompte) {
        this.asoncompte = asoncompte;
    }

    
    @Column(name="NBMOISTRAVAIL", precision=22, scale=0)
    public Integer getNbmoistravail() {
        return this.nbmoistravail;
    }
    
    public void setNbmoistravail(Integer nbmoistravail) {
        this.nbmoistravail = nbmoistravail;
    }

    
    @Column(name="NBJOURSMOYEN", precision=22, scale=0)
    public Integer getNbjoursmoyen() {
        return this.nbjoursmoyen;
    }
    
    public void setNbjoursmoyen(Integer nbjoursmoyen) {
        this.nbjoursmoyen = nbjoursmoyen;
    }

    
    @Column(name="NBHEUREMOYEN", precision=22, scale=0)
    public Integer getNbheuremoyen() {
        return this.nbheuremoyen;
    }
    
    public void setNbheuremoyen(Integer nbheuremoyen) {
        this.nbheuremoyen = nbheuremoyen;
    }

    
    @Column(name="REVENUMOYEN", precision=22, scale=0)
    public String getRevenumoyen() {
        return this.revenumoyen;
    }
    
    public void setRevenumoyen(String revenumoyen) {
        this.revenumoyen = revenumoyen;
    }

    
    @Column(name="DOMAINESOUHAIT")
    public String getDomainesouhait() {
        return this.domainesouhait;
    }
    
    public void setDomainesouhait(String domainesouhait) {
        this.domainesouhait = domainesouhait;
    }

    
    @Column(name="TRAVAILGROUPE", precision=22, scale=0)
    public Integer getTravailgroupe() {
        return this.travailgroupe;
    }
    
    public void setTravailgroupe(Integer travailgroupe) {
        this.travailgroupe = travailgroupe;
    }

    
    @Column(name="NUMCARTEAGRATTER", length=20)
    public String getNumcarteagratter() {
        return this.numcarteagratter;
    }
    
    public void setNumcarteagratter(String numcarteagratter) {
        this.numcarteagratter = numcarteagratter;
    }

    

    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDQUARTIER")
    public Quartier getQuartier() {
        return this.quartier;
    }
    
    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    
    @Column(name="COMMUNE", length=128)
    public String getCommune() {
        return this.commune;
    }
    
    public void setCommune(String commune) {
        this.commune = commune;
    }

    
    @Column(name="DEPARTEMENT", length=20)
    public String getDepartement() {
        return this.departement;
    }
    
    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATEENREGISTREMENT", length=7)
    public Date getDateenregistrement() {
        return this.dateenregistrement;
    }
    
    public void setDateenregistrement(Date dateenregistrement) {
        this.dateenregistrement = dateenregistrement;
    }

    
    @Column(name="NUMEROAGENT", length=20)
    public String getNumeroagent() {
        return this.numeroagent;
    }
    
    public void setNumeroagent(String numeroagent) {
        this.numeroagent = numeroagent;
    }

    
    @Column(name="NUMEROFICHE", length=20)
    public String getNumerofiche() {
        return this.numerofiche;
    }
    
    public void setNumerofiche(String numerofiche) {
        this.numerofiche = numerofiche;
    }


    @Column(name="LANGUESPARLEES", length=50)
    public String getLanguesparlees() {
        return this.languesparlees;
    }
    
    public void setLanguesparlees(String languesparlees) {
        this.nom = languesparlees;
    }
    @Column(name="PRECISIONACTIVITE", length=50)
    public String getPrecisionactivite() {
        return this.precisionactivite;
    }
    
    public void setPrecisionactivite(String precisionactivite) {
        this.precisionactivite = precisionactivite;
    }
    @Column(name="PRECISIONACTIVITESOUHAITER", length=50)
    public String getPrecisionactivitesouhaiter() {
        return this.precisionactivitesouhaiter;
    }
    
    public void setPrecisionactivitesouhaiter(String precisionactivitesouhaiter) {
        this.precisionactivitesouhaiter = precisionactivitesouhaiter;
    }
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDSTATUT")
	public Statutcandidat getStatutcandidat() {
		return this.statutcandidat;
	}

	public void setStatutcandidat(Statutcandidat statutcandidat) {
		this.statutcandidat = statutcandidat;
	}
	
	
	
	@Column(name="NIVEAUVALIDATION", precision=22, scale=0)
	public int getNiveauvalidation() {
		return niveauvalidation;
	}


	public void setNiveauvalidation(int niveauvalidation) {
		this.niveauvalidation = niveauvalidation;
	}

	@OneToMany(fetch=FetchType.LAZY, mappedBy="formation")
    public Set<Formationbeneficaire> getFormationbeneficaires() {
        return this.formationbeneficaires;
    }
    
    public void setFormationbeneficaires(Set<Formationbeneficaire> formationbeneficaires) {
        this.formationbeneficaires = formationbeneficaires;
    }
	@Override
	public String toString() {
		return "Candidat [idcandidat=" + idcandidat + ", agent=" + agent + ", nom=" + nom + ", prenom=" + prenom
				+ ", datenaissance=" + datenaissance + ", docidentite=" + docidentite + ", niveau=" + niveau
				+ ", diplome=" + diplome + ", codearrondissement=" + codearrondissement + ", userlogin=" + userlogin
				+ ", datecreation=" + datecreation + ", refdocidentite=" + refdocidentite + ", telprincipal="
				+ telprincipal + ", telalternatif=" + telalternatif + ", age=" + age + ", sexe=" + sexe
				+ ", situationmatrimoniale=" + situationmatrimoniale + ", parentechefmenage=" + parentechefmenage
				+ ", nbpersonnemenage=" + nbpersonnemenage + ", menagebeneficiaire=" + menagebeneficiaire
				+ ", scolarise=" + scolarise + ", dernierniveauetude=" + dernierniveauetude + ", lecture=" + lecture
				+ ", ecriture=" + ecriture + ", qualificationpersonelle=" + qualificationpersonelle + ", worklastmonth="
				+ worklastmonth + ", recherchetravail=" + recherchetravail + ", motifnonrecherche=" + motifnonrecherche
				+ ", activiteactuelle=" + activiteactuelle + ", asoncompte=" + asoncompte + ", nbmoistravail="
				+ nbmoistravail + ", nbjoursmoyen=" + nbjoursmoyen + ", nbheuremoyen=" + nbheuremoyen + ", revenumoyen="
				+ revenumoyen + ", domainesouhait=" + domainesouhait + ", travailgroupe=" + travailgroupe
				+ ", numcarteagratter=" + numcarteagratter  + ", quartier=" + quartier
				+ ", commune=" + commune + ", departement=" + departement + ", dateenregistrement=" + dateenregistrement
				+ ", numeroagent=" + numeroagent + ", numerofiche=" + numerofiche + ", languesparlees=" + languesparlees
				+ ", precisionactivite=" + precisionactivite + ", precisionactivitesouhaiter="
				+ precisionactivitesouhaiter + ", beneficiaires="  + "]";
	}


}


