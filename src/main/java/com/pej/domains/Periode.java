package com.pej.domains;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "periode")
@Proxy(lazy=false)
public class Periode implements java.io.Serializable {

    private Integer idperiode;
    private String nomperiode;
    private String type;
    private Integer nombre;
    @JsonIgnore
    private Set<Suivie> suivies = new HashSet<Suivie>(0);

    public Periode() {
    }

    public Periode(String nomperiode,String type, Integer nombre ) {
        this.nomperiode = nomperiode;
        this.type = type;
        this.nombre = nombre;
    }

    @Id
    @GeneratedValue(generator = "SEQ_IDPERIODE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_IDPERIODE", sequenceName = "SEQ_IDPERIODE",allocationSize=1)

    @Column(name="idperiode", unique=true, nullable=false, length=20)
    public Integer getIdperiode() {
        return this.idperiode;
    }

    public void setIdperiode(Integer idperiode) {
        this.idperiode = idperiode;
    }

    @Column(name = "nomperiode", length = 255)
    public String getNomperiode() {
        return this.nomperiode;
    }

    public void setNomperiode(String nomperiode) {
        this.nomperiode = nomperiode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }
    @OneToMany(fetch=FetchType.LAZY, mappedBy="periode")
    public Set<Suivie> getSuivies() {
        return this.suivies;
    }

    public void setSuivies(Set<Suivie> suivies) {
        this.suivies = suivies;
    }

    @Override
    public String toString() {
        return "" +idperiode;
    }
}