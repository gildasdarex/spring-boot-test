package com.pej.domains;
// Generated 3 janv. 2017 23:38:37 by Hibernate Tools 5.2.0.Beta1


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

/**
 * Cabinet generated by hbm2java
 */
@Entity
@Table(name = "CABINET")
public class Cabinet implements java.io.Serializable {

	private Integer idcabinet;
	private String intitule;
	private String responsable;
	private String telephone;
	private Set<Lot> lots = new HashSet<Lot>(0);
	private Set<Formateur> formateurs = new HashSet<Formateur>(0);

	public Cabinet() {
	}

	public Cabinet(Integer idcabinet) {
		this.idcabinet = idcabinet;
	}

	public Cabinet(Integer idcabinet, String intitule, String responsable, String telephone, Set<Lot> lots,
			Set<Formateur> formateurs) {
		this.idcabinet = idcabinet;
		this.intitule = intitule;
		this.responsable = responsable;
		this.telephone = telephone;
		this.lots = lots;
		this.formateurs = formateurs;
	}

	@Id
	@GeneratedValue(generator = "SEQ_IDCABINET", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SEQ_IDCABINET", sequenceName = "SEQ_IDCABINET",allocationSize=1)
	@Column(name = "IDCABINET", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getIdcabinet() {
		return this.idcabinet;
	}

	public void setIdcabinet(Integer idcabinet) {
		this.idcabinet = idcabinet;
	}

	@Column(name = "INTITULE")
	public String getIntitule() {
		return this.intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	@Column(name = "RESPONSABLE")
	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	@Column(name = "TELEPHONE", length = 50)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cabinet")
	public Set<Lot> getLots() {
		return this.lots;
	}

	public void setLots(Set<Lot> lots) {
		this.lots = lots;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cabinet")
	public Set<Formateur> getFormateurs() {
		return this.formateurs;
	}

	public void setFormateurs(Set<Formateur> formateurs) {
		this.formateurs = formateurs;
	}

}
