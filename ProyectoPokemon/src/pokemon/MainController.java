package pokemon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {

        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Validación simple
        if (user.equals("admin") && pass.equals("1234")) {
            cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos");
        }
    }

    private void cambiarEscena(ActionEvent event, String fxml, String titulo) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setMaximized(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            if (errorLabel != null) {
                errorLabel.setText("Error al cargar la escena");
            }
        }
    }
}