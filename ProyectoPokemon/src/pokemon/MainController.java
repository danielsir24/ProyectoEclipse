package pokemon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;  // opcional, para mensajes de error

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // Validación opcional
        if(user.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("Por favor completa todos los campos");
            return;
        }

        // Aquí puedes poner tu validación real (usuario/contraseña)
        if(user.equals("admin") && pass.equals("1234")) {  // ejemplo simple
            try {
                // Cargar la siguiente vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                Parent root = loader.load();

                // Obtener el Stage actual desde el evento
                Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();

                // Cambiar la escena
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch(Exception e) {
                e.printStackTrace();
                errorLabel.setText("Error al cargar la vista");
            }
        } else {
            errorLabel.setText("Usuario o contraseña incorrectos");
        }
    }
    
    
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/EscenaLogin.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mi Login");
        
        // Opción 1: maximizar la ventana
        primaryStage.setMaximized(true);

        // Opción 2: pantalla completa (oculta barra de tareas)
        // primaryStage.setFullScreen(true);

        primaryStage.show();
    }
}