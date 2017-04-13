package com.project.app.pilpoil.Metier;

import java.io.Serializable;

public class AnimalPojo implements Serializable {

    private String age;
    private AnimalTypePojo animalType;
    private BreedPojo breed;
    private String chip;
    private String colors;
    private Integer id;
    private String name;
    private String photo;
    private String sexe;
    private String tatoo;
    private UserPojo user;

    /**
     * @return The age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return The animalType
     */
    public AnimalTypePojo getAnimalType() {
        return animalType;
    }

    /**
     * @param animalType The animalType
     */
    public void setAnimalType(AnimalTypePojo animalType) {
        this.animalType = animalType;
    }

    /**
     * @return The breed
     */
    public BreedPojo getBreed() {
        return breed;
    }

    /**
     * @param breed The breed
     */
    public void setBreed(BreedPojo breed) {
        this.breed = breed;
    }

    /**
     * @return The chip
     */
    public String getChip() {
        return chip;
    }

    /**
     * @param chip The chip
     */
    public void setChip(String chip) {
        this.chip = chip;
    }

    /**
     * @return The colors
     */
    public String getColors() {
        return colors;
    }

    /**
     * @param colors The colors
     */
    public void setColors(String colors) {
        this.colors = colors;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return The sexe
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * @param sexe The sexe
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * @return The tatoo
     */
    public String getTatoo() {
        return tatoo;
    }

    /**
     * @param tatoo The tatoo
     */
    public void setTatoo(String tatoo) {
        this.tatoo = tatoo;
    }

    /**
     * @return The user
     */
    public UserPojo getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(UserPojo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return name;
    }
}