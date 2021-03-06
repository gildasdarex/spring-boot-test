package com.pej.domains;

//Generated 18 nov. 2016 16:14:23 by Hibernate Tools 5.2.0.Beta1


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
* Don generated by hbm2java
*/
@Entity
@Table(name = "DONCOOPERATIVE", schema = "ADMINPEJ")
public class DonCooperative implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer iddoncooperative;
	 @JsonIgnore
	private Cooperative cooperative;
	private String libdon;
	private String nature;
	private Date dateaccord;
	private String observation;
	private Integer montant;


	public DonCooperative() {
	}

	public DonCooperative(Integer iddoncooperative) {
		this.iddoncooperative = iddoncooperative;
	}

	public DonCooperative(Integer iddoncooperative, Cooperative cooperative, String libdon, String nature, Date dateaccord, String observation,
			Integer montant) {
		this.iddoncooperative = iddoncooperative;
		this.cooperative = cooperative;
		this.libdon = libdon;
		this.nature = nature;
		this.dateaccord = dateaccord;
		this.observation = observation;
		this.montant = montant;
	}

	@Id
	@GeneratedValue(generator = "SEQ_IDDON", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_IDDON", sequenceName = "SEQ_IDDON",allocationSize=1)
	@Column(name = "IDDONCOOPERATIVE", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIddoncooperative() {
		return this.iddoncooperative;
	}

	public void setIddoncooperative(Integer iddoncooperative) {
		this.iddoncooperative = iddoncooperative;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDGROUPE")
	public Cooperative getCooperative() {
		return this.cooperative;
	}

	public void setCooperative(Cooperative cooperative) {
		this.cooperative = cooperative;
	}

	@Column(name = "LIBDON", length = 20)
	public String getLibdon() {
		return this.libdon;
	}

	public void setLibdon(String libdon) {
		this.libdon = libdon;
	}

	@Column(name = "NATURE", length = 20)
	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEACCORD", length = 7)
	public Date getDateaccord() {
		return this.dateaccord;
	}

	public void setDateaccord(Date dateaccord) {
		this.dateaccord = dateaccord;
	}

	@Column(name = "OBSERVATION")
	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Column(name = "MONTANT", precision = 22, scale = 0)
	public Integer getMontant() {
		return this.montant;
	}

	public void setMontant(Integer montant) {
		this.montant = montant;
	}

}
