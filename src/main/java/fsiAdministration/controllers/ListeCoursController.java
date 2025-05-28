package fsiAdministration.controllers;

import fsiAdministration.BO.Cours;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.CoursDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListeCoursController extends MenuController implements Initializable {

    @FXML private TableView<Cours> tvCours;
    @FXML private TableColumn<Cours, String> tcLibelleCours, tcDescriptionCours, tcIdSection;
    @FXML private Button btnRetourCours;
    @FXML private ComboBox<Section> cbFiltreSection;

    private final SectionDAO sectionDAO = new SectionDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcLibelleCours.setCellValueFactory(cell -> cell.getValue().libelleCoursProperty());
        tcDescriptionCours.setCellValueFactory(cell -> cell.getValue().descriptionCoursProperty());
        tcIdSection.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLibelleSection()));

        chargerSectionsComboBox();

        cbFiltreSection.setConverter(new StringConverter<>() {
            @Override public String toString(Section section) { return section != null ? section.getLibelleSection() : ""; }
            @Override public Section fromString(String string) { return null; }
        });

        cbFiltreSection.setOnAction(e -> {
            Section selected = cbFiltreSection.getValue();
            if (selected != null) afficherCoursParSection(selected.getIdSection());
        });

        Integer sectionId = Navigation.getParam("sectionId");
        if (sectionId != null) {
            afficherCoursParSection(sectionId);
            btnRetourCours.setVisible(true);
        } else {
            afficherTousLesCours();
        }
    }

    @FXML
    private void modifierCours() {
        Cours cours = tvCours.getSelectionModel().getSelectedItem();
        if (cours == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fsiAdministration/views/modifier_cours.fxml"));
            Parent root = loader.load();
            ModifierCoursController controller = loader.getController();
            controller.setCours(cours);
            Stage stage = new Stage();
            controller.setDialogStage(stage);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tvCours.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerCours() {
        Cours cours = tvCours.getSelectionModel().getSelectedItem();
        if (cours == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer " + cours.getLibelleCours() + " ?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = new CoursDAO().delete(cours);
                if (deleted) tvCours.getItems().remove(cours);
            }
        });
    }

    private void afficherCoursParSection(int idSection) {
        CoursDAO dao = new CoursDAO();
        List<Cours> coursList = dao.findBySection(idSection);
        tvCours.setItems(FXCollections.observableArrayList(coursList));
        btnRetourCours.setVisible(true);
    }

    @FXML
    private void afficherTousLesCours() {
        List<Cours> coursList = new CoursDAO().findAll();
        tvCours.setItems(FXCollections.observableArrayList(coursList));
        btnRetourCours.setVisible(false);
    }

    private void chargerSectionsComboBox() {
        List<Section> sections = sectionDAO.findAll();
        cbFiltreSection.setItems(FXCollections.observableArrayList(sections));
    }

    @FXML
    private void handleRetour() {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", tvCours.getScene().getWindow());
    }
}