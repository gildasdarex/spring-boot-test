package com.pej.domains;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Departementt {

		private Integer codedepartement;
		private String libdepartement;
		private String description;
		

		public Departementt() {
		}

		public Departementt(Integer codedepartement) {
			this.codedepartement = codedepartement;
		}

		public Departementt(Integer codedepartement, String libdeparteement, String description) {
			this.codedepartement = codedepartement;
			this.libdepartement = libdeparteement;
			this.description = description;
			
		}

		@Id
		@GeneratedValue
		@Column(name = "CODEDEPARTEMENT", unique = true, nullable = false, length = 20)
		public Integer getCodedepartement() {
			return this.codedepartement;
		}

		public void setCodedepartement(Integer codedepartement) {
			this.codedepartement = codedepartement;
		}

		@Column(name = "LIBDEPARTEEMENT", length = 50)
		public String getLibdepartement() {
			return this.libdepartement;
		}

		public void setLibdepartement(String libdeparteement) {
			this.libdepartement = libdeparteement;
		}

		@Column(name = "DESCRIPTION", length = 256)
		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
