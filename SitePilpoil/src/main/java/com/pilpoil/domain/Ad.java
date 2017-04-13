package com.pilpoil.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.pilpoil.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ad.
 */
@Entity
@Table(name = "ad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;
    
    @Column(name = "recover")
    private Boolean recover;
    
    @Column(name = "date")
    private String date;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "adress")
    private String adress;
    
    @Column(name = "complement")
    private String complement;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "longitude")
    private String longitude;
    
    @Column(name = "lattitude")
    private String lattitude;
    
    @ManyToOne
    @JoinColumn(name = "ad_type_id")
    private AdType adType;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @ManyToOne
    private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRecover() {
        return recover;
    }
    
    public void setRecover(Boolean recover) {
        this.recover = recover;
    }

    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }
    
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getComplement() {
        return complement;
    }
    
    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }
    
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public AdType getAdType() {
        return adType;
    }

    public void setAdType(AdType adType) {
        this.adType = adType;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        if(ad.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ad{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", recover='" + recover + "'" +
            ", date='" + date + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", adress='" + adress + "'" +
            ", complement='" + complement + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            ", country='" + country + "'" +
            ", longitude='" + longitude + "'" +
            ", lattitude='" + lattitude + "'" +
            '}';
    }
}
