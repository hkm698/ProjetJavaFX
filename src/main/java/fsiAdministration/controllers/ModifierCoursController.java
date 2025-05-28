package fsiAdministration.controllers;

import fsiAdministration.BO.Cours;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.CoursDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ModifierCoursController {

    @FXML private TextField tfLibelle;
    @FXML private TextField tfDescription;
    @FXML private ComboBox<Section> cbSection;
    private final SectionDAO sectionDAO = new SectionDAO();

    private Cours cours;
    private Stage dialogStage;


    @FXML
    public void initialize() {
        // Charger les sections
        List<Section> sections = sectionDAO.findAll();
        cbSection.setItems(FXCollections.observableArrayList(sections));

        // Afficher uniquement le libellé dans la ComboBox
        cbSection.setConverter(new StringConverter<>() {
            @Override
            public String toString(Section section) {
                return section != null ? section.getLibelleSection() : "";
            }

            @Override
            public Section fromString(String string) {
                return null; // Pas utilisé
            }
        });
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void setCours(Cours cours) {
        this.cours = cours;

        tfLibelle.setText(cours.getLibelleCours());
        tfDescription.setText(cours.getDescriptionCours());

        // Trouver et sélectionner la section actuelle dans la ComboBox
        for (Section s : cbSection.getItems()) {
            if (s.getIdSection() == cours.getIdSection()) {
                cbSection.setValue(s);
                break;
            }
        }
    }

    @FXML
    private void validerModification() {
        String libelle = tfLibelle.getText().trim();
        String description = tfDescription.getText().trim();
        Section selectedSection = cbSection.getValue();

        if (libelle.isEmpty() || description.isEmpty() || selectedSection == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        cours.setLibelleCours(libelle);
        cours.setDescriptionCours(description);
        cours.setIdSection(selectedSection.getIdSection());
        cours.setLibelleSection(selectedSection.getLibelleSection());

        CoursDAO dao = new CoursDAO();
        boolean updated = dao.update(cours);

        if (updated) {
            dialogStage.close();
        } else {
            showAlert("La mise à jour a échoué.");
        }
    }


    @FXML
    private void annuler() {
        dialogStage.close();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}