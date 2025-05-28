package fsiAdministration.controllers;

import fsiAdministration.BO.Etudiant;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.EtudiantDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ModifierEtudiantController {

    @FXML
    private TextField tfNom, tfPrenom;

    @FXML
    private DatePicker dpNaissance;

    @FXML
    private ComboBox<String> cbSections;

    private Etudiant etudiant;
    private Stage dialogStage;


    private final Map<String, Integer> mapSection = new HashMap<>();


    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        tfNom.setText(etudiant.getNomEtudiant());
        tfPrenom.setText(etudiant.getPrenomEtudiant());
        dpNaissance.setValue(etudiant.getDateNaissance());

        SectionDAO sectionDAO = new SectionDAO();
        for (Section s : sectionDAO.findAll()) {
            mapSection.put(s.getLibelleSection(), s.getIdSection());
            cbSections.getItems().add(s.getLibelleSection());
        }

        cbSections.setValue(etudiant.getLibelleSection());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void validerModification() {
        if (tfNom.getText().isEmpty() || tfPrenom.getText().isEmpty() || dpNaissance.getValue() == null || cbSections.getValue() == null) {
            showAlert("Tous les champs sont obligatoires.");
            return;
        }

        try {
            etudiant.setNomEtudiant(tfNom.getText());
            etudiant.setPrenomEtudiant(tfPrenom.getText());
            etudiant.setDateNaissance(dpNaissance.getValue());

            String libelle = cbSections.getValue();
            etudiant.setIdSection(mapSection.get(libelle));
            etudiant.setLibelleSection(libelle);

            new EtudiantDAO().update(etudiant);
            dialogStage.close();

        } catch (Exception e) {
            showAlert("Erreur : " + e.getMessage());
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