package com.project.app.pilpoil.Metier;

import java.io.Serializable;

public class Adress implements Serializable {

    private String adress;
    private String complement;
    private String postalCode;
    private String city;
    private String country;
    private String longitude;
    private String lattitude;

    @Override
    public String toString() {
        return this.adress + ' ' + this.postalCode + ' ' + this.city;
    }

    public Adress() {}

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

}
