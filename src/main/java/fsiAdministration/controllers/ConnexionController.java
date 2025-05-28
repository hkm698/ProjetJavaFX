package fsiAdministration.controllers;

import fsiAdministration.BO.Utilisateur;
import fsiAdministration.DAO.UtilisateurDAO;
import fsiAdministration.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConnexionController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Rien à initialiser pour l’instant
    }

    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfMDP;
    @FXML
    private Button bConnexion;
    @FXML
    private Label lblErreur;

    @FXML
    public void bConnexionClick(ActionEvent event) {
        String login = tfLogin.getText();
        String mdp = tfMDP.getText();

        UtilisateurDAO userDAO = new UtilisateurDAO();
        Utilisateur user = userDAO.find(login, mdp);

        if (user != null) {
            user.setMdpUtilisateur(null); // sécurité : on ne garde pas le mdp
            lblErreur.setText("");

            Session.setUtilisateur(user); // ← on stocke l'utilisateur globalement
            showAccueil();

        } else {
            lblErreur.setText("Identifiant ou mot de passe incorrect.");
        }
    }

    private void showAccueil() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fsiAdministration/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Accueil FSI ADMINISTRATION");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            // ✅ Ajouter l’icône
            stage.getIcons().add(new javafx.scene.image.Image(
                    getClass().getResourceAsStream("/fsiAdministration/images/FSI_logo.png")
            ));

            stage.show();

            // Fermer la fenêtre de connexion
            Stage stageP = (Stage) bConnexion.getScene().getWindow();
            stageP.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
