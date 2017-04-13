package com.pilpoilwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilpoilwebapp.domain.User;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Animal.
 */
@Entity
@Table(name = "animal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "animal")
public class Animal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "age")
    private String age;
    
    @Column(name = "sexe")
    private String sexe;
    
    @Column(name = "tatoo")
    private String tatoo;
    
    @Column(name = "chip")
    private String chip;
    
    @Column(name = "photo")
    private String photo;
    
    @Column(name = "colors")
    private String colors;
    
    @ManyToOne
    private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "animal")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ad> ads = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "animal_type_id")
    private AnimalType animalType;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private Breed breed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTatoo() {
        return tatoo;
    }
    
    public void setTatoo(String tatoo) {
        this.tatoo = tatoo;
    }

    public String getChip() {
        return chip;
    }
    
    public void setChip(String chip) {
        this.chip = chip;
    }

    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getColors() {
        return colors;
    }
    
    public void setColors(String colors) {
        this.colors = colors;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Animal animal = (Animal) o;
        if(animal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", age='" + age + "'" +
            ", sexe='" + sexe + "'" +
            ", tatoo='" + tatoo + "'" +
            ", chip='" + chip + "'" +
            ", photo='" + photo + "'" +
            ", colors='" + colors + "'" +
            '}';
    }
}
