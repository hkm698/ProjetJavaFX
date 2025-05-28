package fsiAdministration.controllers;

import fsiAdministration.BO.Section;
import fsiAdministration.DAO.SectionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AjoutSectionController extends MenuController {


    @FXML
    private TextField tfLibelleSection;

    @FXML
    private Button bEnregistrer, bEffacer, bRetour;

    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String libelle = tfLibelleSection.getText().trim();

        if (libelle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ manquant");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un libellé de section.");
            alert.showAndWait();
            tfLibelleSection.setStyle("-fx-border-color: red;");
            return;
        }

        tfLibelleSection.setStyle("");

        Section nouvelleSection = new Section(0, new SimpleStringProperty(libelle));

        SectionDAO sectionDAO = new SectionDAO();
        boolean resultat = sectionDAO.create(nouvelleSection);

        if (resultat) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Section ajoutée avec succès !");
            alert.showAndWait();
            tfLibelleSection.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout de la section.");
            alert.showAndWait();
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        tfLibelleSection.clear();
        tfLibelleSection.setStyle("");
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", bRetour.getScene().getWindow());
    }

}
