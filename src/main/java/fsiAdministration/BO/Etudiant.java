package fsiAdministration.BO;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;


public class Etudiant {

    private int idEtudiant;
    private SimpleStringProperty nomEtudiant;
    private SimpleStringProperty prenomEtudiant;
    private LocalDate dateNaissance;
    private int idSection;

    public int getIdSection() {
        return idSection;
    }

    public void setIdSection(int idSection) {
        this.idSection = idSection;
    }

    public Etudiant(int idEtudiant, String nomEtudiant, String prenomEtudiant, LocalDate dateNaissance) {
        this.idEtudiant = idEtudiant;
        this.nomEtudiant = new SimpleStringProperty(nomEtudiant);
        this.prenomEtudiant = new SimpleStringProperty(prenomEtudiant);
        this.dateNaissance = dateNaissance;
    }

    public int getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(int idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant.get();
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant.set(nomEtudiant);
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant.get();
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant.set(prenomEtudiant);
    }

    public SimpleStringProperty nomEtudiantProperty() {
        return nomEtudiant;
    }

    public SimpleStringProperty prenomEtudiantProperty() {
        return prenomEtudiant;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public ObjectProperty<LocalDate> dateNaissanceProperty() {
        return new SimpleObjectProperty<>(dateNaissance);
    }

    private SimpleStringProperty libelleSection = new SimpleStringProperty();

    public String getLibelleSection() {
        return libelleSection.get();
    }

    public void setLibelleSection(String libelle) {
        this.libelleSection.set(libelle);
    }

    public StringProperty libelleSectionProperty() {
        return libelleSection;
    }
    public Etudiant() {
        // Constructeur vide nécessaire pour la création manuelle
    }



}