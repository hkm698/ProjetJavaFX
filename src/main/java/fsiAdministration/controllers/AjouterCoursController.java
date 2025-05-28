package fsiAdministration.controllers;

import fsiAdministration.BO.Cours;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.CoursDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class AjouterCoursController extends MenuController {

    @FXML private TextField tfLibelleCours;
    @FXML private TextField tfDescriptionCours;
    @FXML private ComboBox<Section> cbSections;

    private final SectionDAO sectionDAO = new SectionDAO();
    private final CoursDAO coursDAO = new CoursDAO();

    @FXML
    public void initialize() {
        chargerSections();
        cbSections.setPromptText("Choisir une section");

        // 🔧 Affichage uniquement du libellé dans le ComboBox
        cbSections.setConverter(new StringConverter<Section>() {
            @Override
            public String toString(Section section) {
                return section != null ? section.getLibelleSection() : "";
            }

            @Override
            public Section fromString(String string) {
                return null; // Pas utilisé car sélection uniquement par clic
            }
        });
    }

    private void chargerSections() {
        List<Section> sections = sectionDAO.findAll();
        if (sections != null && !sections.isEmpty()) {
            cbSections.setItems(FXCollections.observableArrayList(sections));
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune section", "Aucune section disponible. Veuillez en ajouter avant de créer un cours.");
        }
    }

    @FXML
    public void ajouterCours() {
        String libelle = tfLibelleCours.getText().trim();
        String description = tfDescriptionCours.getText().trim();
        Section section = cbSections.getValue();

        if (!champsValides(libelle, description, section)) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs.");
            return;
        }

        Cours nouveauCours = new Cours();
        nouveauCours.setLibelleCours(libelle);
        nouveauCours.setDescriptionCours(description);
        nouveauCours.setIdSection(section.getIdSection());

        boolean success = coursDAO.create(nouveauCours);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le cours a bien été ajouté.");
            viderChamps();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du cours.");
        }
    }

    private boolean champsValides(String libelle, String description, Section section) {
        return !(libelle.isEmpty() || description.isEmpty() || section == null);
    }

    private void viderChamps() {
        tfLibelleCours.clear();
        tfDescriptionCours.clear();
        cbSections.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    public void handleRetour() {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", tfLibelleCours.getScene().getWindow());
    }
}
