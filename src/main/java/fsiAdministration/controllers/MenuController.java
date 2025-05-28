package fsiAdministration.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

public class MenuController {

    @FXML protected MenuItem bListeEtud, bAjouterEtud, bListeSection, bAjouterSection, bQuitter, bAccueil;
    @FXML protected MenuItem bListeCours, bAjouterCours;

    // Récupère la fenêtre à partir de l'événement
    private Window getWindowFromEvent(ActionEvent event) {
        return ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
    }

    @FXML
    public void bQuitterClick(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void bAccueilClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bListEtudClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_liste_etudiant.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bAjouterEtudClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_ajout_etudiant.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bListeSectionClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_liste_section.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bAjouterSectionClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_ajout_section.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bListeCoursClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_liste_cours.fxml", getWindowFromEvent(event));
    }

    @FXML
    public void bAjouterCoursClick(ActionEvent event) {
        Navigation.goTo("/fsiAdministration/views/page_ajout_cours.fxml", getWindowFromEvent(event));
    }
}
