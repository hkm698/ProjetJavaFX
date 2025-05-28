package fsiAdministration.controllers;

import fsiAdministration.BO.Section;
import fsiAdministration.DAO.SectionDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListeSectionController extends MenuController implements Initializable {

    @FXML private TableView<Section> tvSections;
    @FXML private TableColumn<Section, String> tcLibelleSection;
    @FXML private TableColumn<Section, Void> tcActions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerSections();
    }

    private void chargerSections() {
        List<Section> sections = new SectionDAO().findAll();
        ObservableList<Section> list = FXCollections.observableArrayList(sections);
        tcLibelleSection.setCellValueFactory(cell -> cell.getValue().libelleSectionProperty());
        tvSections.setItems(list);
        ajouterBoutonsActions();
    }

    private void ajouterBoutonsActions() {
        tcActions.setCellFactory(col -> new TableCell<>() {
            private final Button btnModifier = new Button("Modifier");
            private final Button btnSupprimer = new Button("Supprimer");
            private final Button btnEtudiants = new Button("Liste Étudiants");
            private final Button btnCours = new Button("Liste Cours");

            {
                btnModifier.setOnAction(e -> modifierSection(getTableView().getItems().get(getIndex())));
                btnSupprimer.setOnAction(e -> supprimerSection(getTableView().getItems().get(getIndex())));
                btnEtudiants.setOnAction(e -> {
                    Section s = getTableView().getItems().get(getIndex());
                    Window window = tvSections.getScene().getWindow();
                    Navigation.goTo("/fsiAdministration/views/page_liste_etudiant.fxml", "sectionId", s.getIdSection(), window);
                });
                btnCours.setOnAction(e -> {
                    Section s = getTableView().getItems().get(getIndex());
                    Window window = tvSections.getScene().getWindow();
                    Navigation.goTo("/fsiAdministration/views/page_liste_cours.fxml", "sectionId", s.getIdSection(), window);
                });

                btnModifier.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnSupprimer.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                btnEtudiants.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                btnCours.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, btnModifier, btnSupprimer, btnEtudiants, btnCours));
                }
            }
        });
    }

    private void modifierSection(Section section) {
        TextInputDialog dialog = new TextInputDialog(section.getLibelleSection());
        dialog.setHeaderText("Modifier le libellé");
        dialog.setContentText("Libellé :");
        String nouveau = dialog.showAndWait().orElse("").trim();
        if (!nouveau.isEmpty()) {
            section.setLibelleSection(nouveau);
            if (new SectionDAO().update(section)) {
                tvSections.refresh();
            }
        }
    }

    private void supprimerSection(Section section) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer cette section ?", ButtonType.OK, ButtonType.CANCEL);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (new SectionDAO().delete(section)) {
                        tvSections.getItems().remove(section);
                    }
                } catch (RuntimeException ex) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Suppression impossible");
                    error.setHeaderText("Erreur de suppression");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            }
        });
    }

    @FXML
    public void handleRetour() {
        Navigation.goTo("/fsiAdministration/views/page_accueil.fxml", tvSections.getScene().getWindow());
    }
}