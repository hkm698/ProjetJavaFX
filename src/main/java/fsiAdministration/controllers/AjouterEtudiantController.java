package fsiAdministration.controllers;

import fsiAdministration.BO.Etudiant;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.EtudiantDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterEtudiantController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomEtud, tfPrenomEtud;

    @FXML
    private Button bRetour;

    @FXML
    private ComboBox<Section> cbSectionEtud;

    @FXML
    private DatePicker dtNaissance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SectionDAO sectionDAO = new SectionDAO();
        List<Section> sections = sectionDAO.findAll();

        ObservableList<Section> observableSections = FXCollections.observableArrayList(sections);
        cbSectionEtud.setItems(observableSections);

        cbSectionEtud.setConverter(new StringConverter<>() {
            @Override
            public String toString(Section section) {
                return section != null ? section.getLibelleSection() : "";
            }

            @Override
            public Section fromString(String string) {
                return null; // Non utilisé ici
            }
        });
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomEtud.getText().trim();
        String prenom = tfPrenomEtud.getText().trim();
        LocalDate dateNaissance = dtNaissance.getValue();
        Section selectedSection = cbSectionEtud.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance == null || selectedSection == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs pour enregistrer un étudiant.");
            alert.showAndWait();

            cbSectionEtud.setStyle("-fx-border-color: red;");
            return;
        }

        cbSectionEtud.setStyle(""); // Réinitialise le style

        int idSection = selectedSection.getIdSection();
        Etudiant newEtud = new Etudiant(0, nom, prenom, dateNaissance);
        newEtud.setIdSection(idSection);

        EtudiantDAO etudDAO = new EtudiantDAO();
        boolean controle = etudDAO.create(newEtud);

        if (controle) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Étudiant ajouté avec succès !");
            alert.showAndWait();
        }

        // Nettoyage des champs
        tfNomEtud.clear();
        tfPrenomEtud.clear();
        dtNaissance.setValue(null);
        cbSectionEtud.getSelectionModel().clearSelection();
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        tfNomEtud.clear();
        tfPrenomEtud.clear();
        dtNaissance.setValue(null);
        cbSectionEtud.getSelectionModel().clearSelection();
        cbSectionEtud.setStyle("");
    }

    @FXML
    public void handleRetour() {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", bRetour.getScene().getWindow());
    }
}