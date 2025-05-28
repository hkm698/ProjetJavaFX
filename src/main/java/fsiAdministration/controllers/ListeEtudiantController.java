package fsiAdministration.controllers;

import fsiAdministration.BO.Etudiant;
import fsiAdministration.BO.Section;
import fsiAdministration.DAO.EtudiantDAO;
import fsiAdministration.DAO.SectionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEtudiantController extends MenuController implements Initializable {

    @FXML private TableView<Etudiant> tvEtudiants;
    @FXML private TableColumn<Etudiant, String> tcNomEtud, tcPrenomEtud, tcSectionEtud;
    @FXML private TableColumn<Etudiant, LocalDate> tcDateNaissance;
    @FXML private TableColumn<Etudiant, Void> tcActions;
    @FXML private Button btnRetour;
    @FXML private ComboBox<Section> cbSections;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerColonnes();
        chargerSections();

        Integer sectionId = Navigation.getParam("sectionId");
        if (sectionId != null) {
            afficherEtudiantsParSection(sectionId);
            btnRetour.setVisible(true);
        } else {
            afficherTousLesEtudiants();
        }

        cbSections.setOnAction(e -> {
            Section selected = cbSections.getValue();
            if (selected != null) {
                afficherEtudiantsParSection(selected.getIdSection());
                btnRetour.setVisible(true);
            }
        });

        addActionButtonsToTable();
    }

    private void chargerColonnes() {
        tcNomEtud.setCellValueFactory(cell -> cell.getValue().nomEtudiantProperty());
        tcPrenomEtud.setCellValueFactory(cell -> cell.getValue().prenomEtudiantProperty());
        tcDateNaissance.setCellValueFactory(cell -> cell.getValue().dateNaissanceProperty());
        tcSectionEtud.setCellValueFactory(cell -> cell.getValue().libelleSectionProperty());
    }

    private void chargerSections() {
        SectionDAO dao = new SectionDAO();
        List<Section> list = dao.findAll();
        cbSections.setItems(FXCollections.observableArrayList(list));

        cbSections.setConverter(new StringConverter<>() {
            @Override public String toString(Section s) { return s != null ? s.getLibelleSection() : ""; }
            @Override public Section fromString(String s) { return null; }
        });
    }

    private void afficherEtudiantsParSection(int idSection) {
        EtudiantDAO dao = new EtudiantDAO();
        List<Etudiant> liste = dao.findBySection(idSection);
        tvEtudiants.setItems(FXCollections.observableArrayList(liste));
    }

    @FXML
    private void afficherTousLesEtudiants() {
        EtudiantDAO dao = new EtudiantDAO();
        List<Etudiant> liste = dao.findAll();
        tvEtudiants.setItems(FXCollections.observableArrayList(liste));
        btnRetour.setVisible(false);
    }

    private void addActionButtonsToTable() {
        tcActions.setCellFactory(col -> new TableCell<>() {
            private final Button modifier = new Button("Modifier");
            private final Button supprimer = new Button("Supprimer");
            {
                modifier.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                supprimer.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                modifier.setOnAction(e -> ouvrirPopupModification(getTableView().getItems().get(getIndex())));
                supprimer.setOnAction(e -> supprimerEtudiant(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, modifier, supprimer));
                }
            }
        });
    }

    private void ouvrirPopupModification(Etudiant etudiant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fsiAdministration/views/modifier_etudiant.fxml"));
            Parent root = loader.load();
            ModifierEtudiantController controller = loader.getController();
            controller.setEtudiant(etudiant);
            Stage stage = new Stage();
            controller.setDialogStage(stage);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            tvEtudiants.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void supprimerEtudiant(Etudiant etud) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer " + etud.getNomEtudiant() + " ?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = new EtudiantDAO().delete(etud);
                if (deleted) tvEtudiants.getItems().remove(etud);
            }
        });
    }

    @FXML
    private void handleRetour() {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", tvEtudiants.getScene().getWindow());
    }
}