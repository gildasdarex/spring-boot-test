package com.pej.domains;
// Generated 30 oct. 2016 22:13:12 by Hibernate Tools 5.2.0.Beta1



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Candidatcooperative generated by hbm2java
 */
@Entity
@Table(name="BENEFICIAIRECOOPERATIVE"
    ,schema="ADMINPEJ"
)
public class Beneficiairecooperative  implements java.io.Serializable {


     private Integer idcandidatgroupe;
     private Candidat candidat;
     private Cooperative cooperative;
     private Date dateajout;
     private Date datecreation;
     private String userlogin;
     private String remarque;

    public Beneficiairecooperative() {
    }

	
    public Beneficiairecooperative(Integer idcandidatgroupe) {
        this.idcandidatgroupe = idcandidatgroupe;
    }
    public Beneficiairecooperative(Integer idcandidatgroupe, Candidat candidat, Cooperative cooperative, Date dateajout, Date datecreation, String userlogin, String remarque) {
       this.idcandidatgroupe = idcandidatgroupe;
       this.candidat = candidat;
       this.cooperative = cooperative;
       this.dateajout = dateajout;
       this.datecreation = datecreation;
       this.userlogin = userlogin;
       this.remarque = remarque;
    }
   
     @Id 

 	@GeneratedValue(generator = "SEQ_IDBENEFICIAIREGROUPE", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDBENEFICIAIREGROUPE", sequenceName = "SEQ_IDBENEFICIAIREGROUPE",allocationSize=1)
    @Column(name="IDBENEFICIAIREGROUPE", unique=true, nullable=false, precision=22, scale=0)
    public Integer getIdcandidatgroupe() {
        return this.idcandidatgroupe;
    }
    
    public void setIdcandidatgroupe(Integer idcandidatgroupe) {
        this.idcandidatgroupe = idcandidatgroupe;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDCANDIDAT")
    public Candidat getCandidat() {
        return this.candidat;
    }
    
    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="IDGROUPE")
    public Cooperative getCooperative() {
        return this.cooperative;
    }
    
    public void setCooperative(Cooperative cooperative) {
        this.cooperative = cooperative;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATEAJOUT", length=7)
    public Date getDateajout() {
        return this.dateajout;
    }
    
    public void setDateajout(Date dateajout) {
        this.dateajout = dateajout;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="DATECREATION", length=7)
    public Date getDatecreation() {
        return this.datecreation;
    }
    
    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    
    @Column(name="USERLOGIN", length=50)
    public String getUserlogin() {
        return this.userlogin;
    }
    
    public void setUserlogin(String userlogin) {
        this.userlogin = userlogin;
    }

    
    @Column(name="REMARQUE")
    public String getRemarque() {
        return this.remarque;
    }
    
    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }




}


