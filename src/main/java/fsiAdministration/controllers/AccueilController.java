package fsiAdministration.controllers;

import fsiAdministration.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController extends MenuController implements Initializable {

    @FXML
    private Label labelBienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Affiche le prénom de l'utilisateur connecté (si dispo dans la session)
        if (Session.getUtilisateur() != null && labelBienvenue != null) {
            labelBienvenue.setText("BIENVENUE " + Session.getUtilisateur().getLoginUtilisateur());

        }
    }
}
