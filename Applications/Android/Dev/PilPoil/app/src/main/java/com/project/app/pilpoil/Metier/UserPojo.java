package com.project.app.pilpoil.Metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPojo implements Serializable {

    private Boolean activated;
    private List<String> authorities = new ArrayList<String>();
    private String createdDate;
    private Integer distance;
    private String email;
    private String firstName;
    private Integer id;
    private String langKey;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private String lastName;
    private String login;
    private String password;
    private String phone;

    /**
     *
     * @return
     * The activated
     */
    public Boolean getActivated() {
        return activated;
    }

    /**
     *
     * @param activated
     * The activated
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    /**
     *
     * @return
     * The authorities
     */
    public List<String> getAuthorities() {
        return authorities;
    }

    /**
     *
     * @param authorities
     * The authorities
     */
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     * The distance
     */
    public void setDistance(Integer distance) {
        this.distance = distance;
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
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * The langKey
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     *
     * @param langKey
     * The langKey
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     *
     * @return
     * The lastModifiedBy
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     *
     * @param lastModifiedBy
     * The lastModifiedBy
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     *
     * @return
     * The lastModifiedDate
     */
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     *
     * @param lastModifiedDate
     * The lastModifiedDate
     */
    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
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
}