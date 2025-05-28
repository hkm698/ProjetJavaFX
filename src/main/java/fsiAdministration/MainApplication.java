package fsiAdministration;

import fsiAdministration.controllers.Navigation;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Ajout du logo FSI
        Image icon = new Image(getClass().getResourceAsStream("/fsiAdministration/images/FSI_logo.png"));
        stage.getIcons().add(icon);

        // Définir la taille initiale plus grande pour correspondre au visuel
        stage.setWidth(550);
        stage.setHeight(500);

        Navigation.setPrimaryStage(stage);
        Navigation.goTo("/fsiAdministration/views/page_connexion.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
