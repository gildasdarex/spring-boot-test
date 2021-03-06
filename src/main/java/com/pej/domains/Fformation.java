package com.pej.domains;
// Generated 20 janv. 2017 09:45:19 by Hibernate Tools 5.2.0.Beta1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pej.domains.Formationformateur;

/**
 * Fformation generated by hbm2java
 */
@Entity
@Table(name = "FFORMATION", schema = "ADMINPEJ")
public class Fformation implements java.io.Serializable {

	private Integer idformation;
	private String intitule;
	private String theme;
	private String description;
	private Date datedebut;
	private Date datefin;
	private String heuredebut;
	private String heurefin;
	private Set<Formationformateur> formationformateurs = new HashSet<Formationformateur>(0);
	public Fformation() {
	}

	public Fformation(Integer idformation) {
		this.idformation = idformation;
	}

	public Fformation(Integer idformation, String intitule, String theme, String description, Date datedebut,
			Date datefin, String heuredebut, String heurefin) {
		this.idformation = idformation;
		this.intitule = intitule;
		this.theme = theme;
		this.description = description;
		this.datedebut = datedebut;
		this.datefin = datefin;
		this.heuredebut = heuredebut;
		this.heurefin = heurefin;
	}

	@Id
  	@GeneratedValue(generator = "SEQ_IDFORMATION", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDFORMATION", sequenceName = "SEQ_IDFORMATION",allocationSize=1)
	@Column(name = "IDFORMATION", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdformation() {
		return this.idformation;
	}

	public void setIdformation(Integer idformation) {
		this.idformation = idformation;
	}

	@Column(name = "INTITULE")
	public String getIntitule() {
		return this.intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	@Column(name = "THEME")
	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEDEBUT", length = 7)
	public Date getDatedebut() {
		return this.datedebut;
	}

	public void setDatedebut(Date datedebut) {
		this.datedebut = datedebut;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEFIN", length = 7)
	public Date getDatefin() {
		return this.datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	@Column(name = "HEUREDEBUT", length = 20)
	public String getHeuredebut() {
		return this.heuredebut;
	}

	public void setHeuredebut(String heuredebut) {
		this.heuredebut = heuredebut;
	}

	@Column(name = "HEUREFIN", length = 20)
	public String getHeurefin() {
		return this.heurefin;
	}

	public void setHeurefin(String heurefin) {
		this.heurefin = heurefin;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fformation")
	public Set<Formationformateur> getFormationformateurs() {
		return this.formationformateurs;
	}

	public void setFormationformateurs(Set<Formationformateur> formationformateurs) {
		this.formationformateurs = formationformateurs;
	}

}
