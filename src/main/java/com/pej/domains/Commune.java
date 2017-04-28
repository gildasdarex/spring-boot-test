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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Commune generated by hbm2java
 */
@Entity
@Table(name="COMMUNE"
    ,schema="ADMINPEJ"
)
public class Commune  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer codecommune;
	 @JsonIgnore
     private Departement departement;
     private String libcommune;
     private String description;
     private Integer nbrebeneficiaire;
     private Set<Arrondissement> arrondissements = new HashSet<Arrondissement>(0);

    public Commune() {
    }

	
    public Commune(Integer codecommune) {
        this.codecommune = codecommune;
    }
    public Commune(Integer codecommune, Departement departement, String libcommune, String description, Set<Arrondissement> arrondissements) {
       this.codecommune = codecommune;
       this.departement = departement;
       this.libcommune = libcommune;
       this.description = description;
       this.arrondissements = arrondissements;
    }
   
    @Id 
	@GeneratedValue(generator = "SEQ_CODECOMMUNE", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_CODECOMMUNE", sequenceName = "SEQ_CODECOMMUNE",allocationSize=1)    
    @Column(name="CODECOMMUNE", unique=true, nullable=false, precision=22, scale=0)
    public Integer getCodecommune() {
        return this.codecommune;
    }
    
    public void setCodecommune(Integer codecommune) {
        this.codecommune = codecommune;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CODEDEPARTEMENT")
    public Departement getDepartement() {
        return this.departement;
    }
    
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    
    @Column(name="LIBCOMMUNE", length=50)
    public String getLibcommune() {
        return this.libcommune;
    }
    
    public void setLibcommune(String libcommune) {
        this.libcommune = libcommune;
    }

    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="commune")
    public Set<Arrondissement> getArrondissements() {
        return this.arrondissements;
    }
    
    public void setArrondissements(Set<Arrondissement> arrondissements) {
        this.arrondissements = arrondissements;
    }

    @Column(name="NBREBENEFICIAIRE", unique=true, nullable=false, precision=22, scale=0)
	public Integer getNbrebeneficiaire() {
		return nbrebeneficiaire;
	}


	public void setNbrebeneficiaire(Integer nbrebeneficiaire) {
		this.nbrebeneficiaire = nbrebeneficiaire;
	}

    


}


