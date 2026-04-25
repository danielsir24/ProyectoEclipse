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

	// Campos de texto para el login y etiquetas de la interfaz
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

	// --- MÉTODOS DE NAVEGACIÓN ---
	// Estos métodos se activan al pulsar los botones del menú para ir a cada sección

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
	
	@FXML
	private void handleEntrarLiga(ActionEvent event) {
		cambiarEscena(event, "/EscenaLiga.fxml", "Menú Liga");
	}
	
	@FXML
	private void handleEntrarCombate(ActionEvent event) {
		cambiarEscena(event, "/EscenaCombate.fxml", "Menú Combate");
	}

	// Método genérico para cambiar de pantalla (Stage) cargando un nuevo archivo FXML
	private void cambiarEscena(ActionEvent event, String fxml, String titulo) {
		try {
			// Cargamos el diseño de la nueva escena
			Parent root = FXMLLoader.load(getClass().getResource(fxml));

			// Obtenemos la ventana actual a partir del evento del botón
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			// Configuramos la nueva escena con un tamaño fijo para que no se descuadre
			Scene scene = new Scene(root, 1280, 761);
			stage.setScene(scene);
			stage.setTitle(titulo);
			stage.setMaximized(false);
			
			// Forzamos el tamaño mínimo para mantener la estética
			stage.setMinWidth(1280);
			stage.setMinHeight(761);
			
			stage.centerOnScreen(); // Centramos la ventana en el monitor
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			if (errorLabel != null) {
				errorLabel.setText("Error al cargar la escena");
			}
		}
	}

	// Se ejecuta al iniciar el controlador: aquí cargamos la estética de la fuente
	@FXML
	public void initialize() {
		cargarFuentePersonalizada();
	}

	// Método para aplicar la fuente .ttf de Pokémon a los textos de la interfaz
	private void cargarFuentePersonalizada() {
		try {
			// Intentamos cargar el archivo de fuente desde los recursos
			Font pokemonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/pokemon.ttf"), 18);

			if (pokemonFont != null) {
				// Si la fuente existe, se la aplicamos a todos los campos y etiquetas
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

	// Método para silenciar o activar la música del juego
	@FXML
	private void handleMute(ActionEvent event) {
		Musica.toggleMute();
		// Cambiamos el icono del botón según si está silenciado o no
		BotonSonido.setText(Musica.isMuted() ? "🔇" : "🔊");
	}

	// Objeto para gestionar las operaciones del entrenador en la base de datos
	private final EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

	// Gestiona el acceso de usuarios ya registrados
	@FXML
	private void handleLogin(ActionEvent event) {
		String user = usernameField.getText().trim();
		String pass = passwordField.getText().trim();

		// Validación: no pueden estar vacíos
		if (user.isEmpty() || pass.isEmpty()) {
			errorLabel.setText("Rellena usuario y contraseña.");
			return;
		}

		// Consultamos en la BD si el usuario y la clave coinciden
		Entrenador entrenador = entrenadorDAO.login(user, pass);
		if (entrenador != null) {
			// Si el login es correcto, entramos al menú principal y cargamos su equipo
			cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");
			Main.entrenadorLogueado = entrenador;
			PokemonDAO pDAO = new PokemonDAO();
			Main.miEquipo = pDAO.obtenerEquipo(entrenador.getId_Entrenador());
		} else {
			errorLabel.setText("Usuario o contraseña incorrectos.");
		}
	}

	// Gestiona el registro de nuevos entrenadores en el sistema
	@FXML
	private void handleRegistro(ActionEvent event) {
		String user = usernameField.getText().trim();
		String pass = passwordField.getText().trim();

		if (user.isEmpty() || pass.isEmpty()) {
			errorLabel.setText("Rellena usuario y contraseña.");
			return;
		}

		// Comprobamos que el nombre de usuario no esté pillado
		if (entrenadorDAO.existeNombre(user)) {
			errorLabel.setText("Ese nombre de entrenador ya existe.");
			return;
		}

		// Creamos el nuevo objeto entrenador con valores iniciales
		Entrenador nuevo = new Entrenador();
		nuevo.setNom_Entrenador(user);
		nuevo.setPassword(pass);
		nuevo.setPokedollars(500); // Empezamos con 500 Pokedollars
		nuevo.setTipo_Entrenador("Novato");
		nuevo.setImg_Entrenador(null);

		// Intentamos guardarlo en la base de datos
		if (entrenadorDAO.registrar(nuevo)) {
			errorLabel.setStyle("-fx-text-fill: green;");
			errorLabel.setText("¡Entrenador registrado con éxito!");
			// Hacemos el login automático tras registrarse
			Main.entrenadorLogueado = entrenadorDAO.login(user, pass);
		} else {
			errorLabel.setText("Error al registrar. Inténtalo de nuevo.");
		}
	}
}