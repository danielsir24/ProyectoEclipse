package pokemon.Controller;

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
import javafx.scene.text.Font;

public class MainController {

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Label errorLabel;

	@FXML
	private Label labelUser;

	@FXML
	private Label labelPassword;

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

	@FXML
	private void handleEquipo(ActionEvent event) {
		cambiarEscena(event, "/EscenaMenuEquipo.fxml", "Menú Equipo");

	}
	
	@FXML
	private void handleCaptura(ActionEvent event) {
		cambiarEscena(event, "/EscenaCaptura.fxml", "Escena Captura");
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

	@FXML
	public void initialize() {
		cargarFuentePersonalizada();
	}

	private void cargarFuentePersonalizada() {
		try {
			Font pokemonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/pokemon.ttf"), 18);

			if (pokemonFont != null) {
				usernameField.setFont(pokemonFont);
				passwordField.setFont(pokemonFont);
				errorLabel.setFont(pokemonFont);
				labelUser.setFont(pokemonFont);
				labelPassword.setFont(pokemonFont);

			} else {
				System.out.println("No se pudo cargar la fuente: comprueba la ruta.");
			}
		} catch (Exception e) {
			System.out.println("Error al cargar la fuente: " + e.getMessage());
		}
	}
}