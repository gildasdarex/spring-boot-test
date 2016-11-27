package com.pej.domains;
// Generated 30 oct. 2016 22:41:09 by Hibernate Tools 5.2.0.Beta1


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
 * Formationbeneficaire generated by hbm2java
 */
@Entity
@Table(name = "FORMATIONBENEFICAIRE", schema = "ADMINPEJ")
public class Formationbeneficaire implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idformationbeneficiaire;
	private Candidat candidat;
	private Formation formation;
	private Date dateajout;
	private String observation;

	public Formationbeneficaire() {
	}

	public Formationbeneficaire(Integer idformationbeneficiaire) {
		this.idformationbeneficiaire = idformationbeneficiaire;
	}

	public Formationbeneficaire(Integer idformationbeneficiaire, Candidat candidat, Formation formation,
			Date dateajout, String observation) {
		this.idformationbeneficiaire = idformationbeneficiaire;
		this.candidat = candidat;
		this.formation = formation;
		this.dateajout = dateajout;
		this.observation = observation;
	}

	@Id
  	@GeneratedValue(generator = "SEQ_IDFORMATIONBENEFICIAIRE", strategy = GenerationType.SEQUENCE)
  	@SequenceGenerator(name = "SEQ_IDFORMATIONBENEFICIAIRE", sequenceName = "SEQ_IDFORMATIONBENEFICIAIRE",allocationSize=1)
	@Column(name = "IDFORMATIONBENEFICIAIRE", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdformationbeneficiaire() {
		return this.idformationbeneficiaire;
	}

	public void setIdformationbeneficiaire(Integer idformationbeneficiaire) {
		this.idformationbeneficiaire = idformationbeneficiaire;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDCANDIDAT")
	public Candidat getCandidat() {
		return this.candidat;
	}

	public void setCandidat(Candidat candidat) {
		this.candidat = candidat;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDFORMATION")
	public Formation getFormation() {
		return this.formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATEAJOUT", length = 7)
	public Date getDateajout() {
		return this.dateajout;
	}

	public void setDateajout(Date dateajout) {
		this.dateajout = dateajout;
	}

	@Column(name = "OBSERVATION")
	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

}
