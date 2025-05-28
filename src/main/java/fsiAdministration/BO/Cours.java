package fsiAdministration.BO;

import javafx.beans.property.SimpleStringProperty;

public class Cours {
    private int idCours;
    private SimpleStringProperty libelleCours;
    private SimpleStringProperty descriptionCours;
    private int idSection;

    public Cours() {
        this.libelleCours = new SimpleStringProperty();
        this.descriptionCours = new SimpleStringProperty();
    }

    public Cours(int idCours, SimpleStringProperty libelleCours, SimpleStringProperty descriptionCours, int idSection) {
        this.idCours = idCours;
        this.libelleCours = libelleCours;
        this.descriptionCours = descriptionCours;
        this.idSection = idSection;
    }

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getLibelleCours() {
        return libelleCours != null ? libelleCours.get() : null;
    }

    public SimpleStringProperty libelleCoursProperty() {
        return libelleCours;
    }

    public void setLibelleCours(String libelleCours) {
        this.libelleCours.set(libelleCours);
    }

    public String getDescriptionCours() {
        return descriptionCours != null ? descriptionCours.get() : null;
    }

    public SimpleStringProperty descriptionCoursProperty() {
        return descriptionCours;
    }

    public void setDescriptionCours(String descriptionCours) {
        this.descriptionCours.set(descriptionCours);
    }

    public int getIdSection() {
        return idSection;
    }

    public void setIdSection(int idSection) {
        this.idSection = idSection;
    }

    private String libelleSection; // Ajoute ce champ si absent

    public String getLibelleSection() {
        return libelleSection;
    }

    public void setLibelleSection(String libelleSection) {
        this.libelleSection = libelleSection;
    }


}