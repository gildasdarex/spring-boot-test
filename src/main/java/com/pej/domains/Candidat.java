package com.pej.domains;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Candidat {
	private Integer idcandidat;
	@NotNull
	@Size(min=2, max=30)
	private String nom;
	@NotNull
	@Min(18)
	private String prenom;
	
	public Integer getIdcandidat() {
		return idcandidat;
	}
	public void setIdcandidat(Integer idcandidat) {
		this.idcandidat = idcandidat;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

}
