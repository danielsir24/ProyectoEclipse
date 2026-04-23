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
import pokemon.Musica;

import java.io.IOException;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import pokemon.Entrenador;
import pokemon.EntrenadorDAO;
import pokemon.Main;
import pokemon.PokemonDAO;
import pokemon.PokedexDAO;

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
	private void handleEquipo(ActionEvent event) {
		cambiarEscena(event, "/EscenaEquipo.fxml", "Menú Equipo");

	}

	@FXML
	private void handleEntrarCaptura(ActionEvent event) {
		cambiarEscena(event, "/EscenaCaptura.fxml", "Escena Captura");
	}

	@FXML
	private void handleEntrarCrianza(ActionEvent event) {
		cambiarEscena(event, "/EscenaCrianza.fxml", "Menú Crianza");

	}
	
	@FXML
	private void handleCasino(ActionEvent event) {
		cambiarEscena(event, "/EscenaCasino.fxml", "Menú Casino");

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

	@FXML
	private Button BotonSonido;

	@FXML
	private void handleMute(ActionEvent event) {
		Musica.toggleMute();
		BotonSonido.setText(Musica.isMuted() ? "🔇" : "🔊");
	}

	// Este es el registro del entrenador al darle al boton Registro
	private final EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

	@FXML
	private void handleLogin(ActionEvent event) {
		String user = usernameField.getText().trim();
		String pass = passwordField.getText().trim();

		if (user.isEmpty() || pass.isEmpty()) {
			errorLabel.setText("Rellena usuario y contraseña.");
			return;
		}

		Entrenador entrenador = entrenadorDAO.login(user, pass);
		if (entrenador != null) {
			cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");
			Main.entrenadorLogueado = entrenador;
			PokemonDAO pDAO = new PokemonDAO();
			Main.miEquipo = pDAO.obtenerEquipo(entrenador.getId_Entrenador());
		} else {
			errorLabel.setText("Usuario o contraseña incorrectos.");
		}
	}

	@FXML
	private void handleRegistro(ActionEvent event) {
		String user = usernameField.getText().trim();
		String pass = passwordField.getText().trim();

		if (user.isEmpty() || pass.isEmpty()) {
			errorLabel.setText("Rellena usuario y contraseña.");
			return;
		}

		if (entrenadorDAO.existeNombre(user)) {
			errorLabel.setText("Ese nombre de entrenador ya existe.");
			return;
		}

		Entrenador nuevo = new Entrenador();
		nuevo.setNom_Entrenador(user);
		nuevo.setPassword(pass);
		nuevo.setPokedollars(500);
		nuevo.setTipo_Entrenador("Novato");
		nuevo.setImg_Entrenador(null);

		if (entrenadorDAO.registrar(nuevo)) {
			errorLabel.setStyle("-fx-text-fill: green;");
			errorLabel.setText("¡Entrenador registrado con éxito!");
			Main.entrenadorLogueado = entrenadorDAO.login(user, pass);
		} else {
			errorLabel.setText("Error al registrar. Inténtalo de nuevo.");
		}
	}

	@FXML
	void abrirCasino() {
		try {
			// Carga el diseño del casino
			javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/EscenaCasino.fxml"));
			javafx.scene.Parent root = loader.load();

			// Crea y muestra la nueva ventana
			javafx.stage.Stage stage = new javafx.stage.Stage();
			stage.setTitle("Casino Ciudad Azulona");
			stage.setScene(new javafx.scene.Scene(root));
			stage.show();

		} catch (Exception e) {
			System.out.println("No se pudo abrir el casino:");
			e.printStackTrace();
		}
	}
}