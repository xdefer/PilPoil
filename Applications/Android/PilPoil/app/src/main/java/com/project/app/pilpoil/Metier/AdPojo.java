package com.project.app.pilpoil.Metier;

import java.io.Serializable;

public class AdPojo implements Serializable {

    private AdTypePojo adType;
    private String adress;
    private AnimalPojo animal;
    private String city;
    private String complement;
    private String country;
    private String date;
    private String description;
    private String email;
    private Integer id;
    private String lattitude;
    private String longitude;
    private String phone;
    private String postalCode;
    private Boolean recover;
    private UserPojo user;

    /**
     *
     * @return
     * The adType
     */
    public AdTypePojo getAdType() {
        return adType;
    }

    /**
     *
     * @param adType
     * The adType
     */
    public void setAdType(AdTypePojo adType) {
        this.adType = adType;
    }

    /**
     *
     * @return
     * The adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     *
     * @param adress
     * The adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }

    /**
     *
     * @return
     * The animal
     */
    public AnimalPojo getAnimal() {
        return animal;
    }

    /**
     *
     * @param animal
     * The animal
     */
    public void setAnimal(AnimalPojo animal) {
        this.animal = animal;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The complement
     */
    public String getComplement() {
        return complement;
    }

    /**
     *
     * @param complement
     * The complement
     */
    public void setComplement(String complement) {
        this.complement = complement;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The lattitude
     */
    public String getLattitude() {
        return lattitude;
    }

    /**
     *
     * @param lattitude
     * The lattitude
     */
    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return
     * The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     * The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return
     * The recover
     */
    public Boolean getRecover() {
        return recover;
    }

    /**
     *
     * @param recover
     * The recover
     */
    public void setRecover(Boolean recover) {
        this.recover = recover;
    }

    /**
     *
     * @return
     * The user
     */
    public UserPojo getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(UserPojo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ad : " + this.id;
    }

}