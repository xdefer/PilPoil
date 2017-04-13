package com.project.app.pilpoil.Metier;

import java.io.Serializable;

public class BreedPojo implements Serializable {

    private AnimalTypePojo animalType;
    private Integer id;
    private String label;

    /**
     *
     * @return
     * The animalType
     */
    public AnimalTypePojo getAnimalType() {
        return animalType;
    }

    /**
     *
     * @param animalType
     * The animalType
     */
    public void setAnimalType(AnimalTypePojo animalType) {
        this.animalType = animalType;
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
     * The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     * The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}