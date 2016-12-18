package com.pej.domains;
// Generated 30 oct. 2016 22:13:12 by Hibernate Tools 5.2.0.Beta1



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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pej.domains.Candidat;

/**
 * Quartier generated by hbm2java
 */
@Entity
@Table(name="QUARTIER"
    ,schema="ADMINPEJ"
)

public class Quartier  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idquartier;
     private String libquartier;
     private String description;
     @JsonIgnore
     private Arrondissement arrondissement;
     @JsonIgnore
     private Set<Candidat> candidats = new HashSet<Candidat>(0);
     //private Beneficiaire beneficiaire;

     @ManyToOne(fetch=FetchType.LAZY)
     @JoinColumn(name="CODEARRONDISSEMENT")
	 public Arrondissement getArrondissement() {
			return arrondissement;
     }


	public void setArrondissement(Arrondissement arrondissement) {
		this.arrondissement = arrondissement;
	}


	public Quartier() {
    }

	
    public Quartier(Integer idquartier) {
        this.idquartier = idquartier;
    }
    public Quartier(Integer idquartier, String libquartier, String description) {
       this.idquartier = idquartier;
       this.libquartier = libquartier;
       this.description = description;
       //this.beneficiaire = beneficiaire;
    }
   
    @Id
 	@GeneratedValue(generator = "SEQ_IDQUARTIER", strategy = GenerationType.SEQUENCE)
 	@SequenceGenerator(name = "SEQ_IDQUARTIER", sequenceName = "SEQ_IDQUARTIER",allocationSize=1)
    @Column(name="IDQUARTIER", unique=true, nullable=false, precision=22, scale=0)
    public Integer getIdquartier() {
        return this.idquartier;
    }
    
    public void setIdquartier(Integer idquartier) {
        this.idquartier = idquartier;
    }

    
    @Column(name="LIBQUARTIER", length=20)
    public String getLibquartier() {
        return this.libquartier;
    }
    
    public void setLibquartier(String libquartier) {
        this.libquartier = libquartier;
    }

    
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quartier")
	public Set<Candidat> getCandidats() {
		return this.candidats;
	}
    
    public void setCandidats(Set<Candidat> candidats) {
        this.candidats = candidats;
    }




}


