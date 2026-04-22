package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.scene.media.AudioClip;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import pokemon.PokedexDAO;
import pokemon.PokemonDAO;
import pokemon.Main;
import pokemon.Pokedex;
import pokemon.Pokemon;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CapturaController {

	// Elementos de FXML
	@FXML
	private Label errorLabel;

	@FXML
	private Label lblBuscar;

	@FXML
	private Button btnCapturar;

	@FXML
	private Button btnBuscarSi;

	@FXML
	private Button btnBuscarNo;

	@FXML
	private Button btnHuir;

	@FXML
	private ImageView pokemonImg;

	@FXML
	private ImageView imgPokeball;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblNivel;

	@FXML
	private TextArea txtLog;

	@FXML
	private TextField txtMote;

	@FXML
	private Button btnMote;

	@FXML
	private Label lblMote;

	// Variables usadas en los metodos
	private PokedexDAO pokedexDAO = new PokedexDAO();
	private AudioClip sonidoCaptura;
	private Pokemon pokemonActual;

	@FXML
	public void initialize() {
		System.out.println("DEBUG: La ventana se ha cargado. Generando Pokemon");
		// Importamos el sonido de la captura
		sonidoCaptura = new AudioClip(getClass().getResource("/sounds/capture.wav").toExternalForm());
		sonidoCaptura.setVolume(0.2);

		generarPokemonAleatorio();
	}

	// Meotodo para generar un pokemon aleatorio de los 151 que hay en la base de
	// datos
	public void generarPokemonAleatorio() {
		// Hacemos que se quede todo como al principio en caso de repetir la captura
		pokemonImg.setVisible(true);
		txtMote.setDisable(false);
		imgPokeball.setVisible(false);
		btnCapturar.setDisable(false);
		btnHuir.setDisable(false);
		btnBuscarSi.setVisible(false);
		btnBuscarNo.setVisible(false);
		lblBuscar.setVisible(false);
		txtMote.setVisible(false);
		lblMote.setVisible(false);
		btnMote.setVisible(false);

		// Llamamos al metodo de PokedexDAO para que se genereel pokemon
		int idPokedex = pokedexDAO.generarIdPokedexAleatorio();

		Pokedex especie = pokedexDAO.buscarPorIdPokedex(idPokedex);

		if (especie != null) {

			// Hacemos que coja el nombre del pokemon
			lblNombre.setText(especie.getNombreEspecie());

			// Le ponemos un nivel aleatorio enre 2 y 10
			int nivelAleatorio = (int) (Math.random() * (10 - 2 + 1) + 2);
			lblNivel.setText("Niv." + nivelAleatorio);

			this.pokemonActual = new Pokemon();

			this.pokemonActual.setInfoPokedex(especie);
			this.pokemonActual.setNombre(especie.getNombreEspecie());
			this.pokemonActual.setNivel(nivelAleatorio);
			this.pokemonActual.setMote(especie.getNombreEspecie());
			this.pokemonActual.setSexo(Math.random() < 0.5 ? pokemon.Sexo.MACHO : pokemon.Sexo.HEMBRA);

			lblNombre.setText(pokemonActual.getNombre());
			lblNivel.setText("Niv." + pokemonActual.getNivel());


			int numPokedex = especie.getNum_Pokedex();

			String rutaImagenFrontal = "/spritesPokemonsGifsFront/" + numPokedex + ".gif";

			if (rutaImagenFrontal != null) {
				try {
					Image img = new Image(getClass().getResourceAsStream(rutaImagenFrontal));
					pokemonImg.setImage(img);

				} catch (Exception e) {
					System.out.println("Error al cargar la imagen: " + rutaImagenFrontal);
					e.printStackTrace();

				}
			}

		} else {
			System.out.println("No se pudo generar el pokemon (especie null)");
		}
	}

	// Metodo para sair al menú principal
	@FXML
	private void handleHuir(ActionEvent event) {
		cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");

		System.out.println("Has vuelto al menú principal");
	}


	@FXML
	private void handleCaptura(ActionEvent event) {

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), e -> txtLog.appendText("¡Lanzas la Pokéball con fuerza!\n")),
				new KeyFrame(Duration.seconds(1), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.seconds(2), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.seconds(3), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.seconds(4), e -> resultadoCaptura(event)));
		timeline.play();

	}


	private void menuRepetirCaptura() {
		btnBuscarSi.setVisible(true);
		btnBuscarNo.setVisible(true);
		lblBuscar.setVisible(true);
	}

	// Metodo para que nos de la información de cuando hemos asignado el metodo.

	private void moteAsignado(ActionEvent event) {
		txtLog.appendText("El Pokémon ha sido añadido a tu equipo.");
		menuRepetirCaptura();
		txtMote.setVisible(false);
		lblMote.setVisible(false);
		btnMote.setVisible(false);

	}

	// Metodo si decidimos no buscar otro pokemon
	private void noRepetirCaptura(ActionEvent event) {
		// Con esta timeline hacemos que los textos en el log no salgan todo de segudo y
		// de tiempo a leerlos
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(0),
						e -> txtLog
								.appendText("Has elegido no buscar otro Pokémon. Volviendo al menú principal en...\n")),
				new KeyFrame(Duration.millis(500), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.millis(1000), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.millis(1500), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.millis(2000), e -> cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal")));
		timeline.play();

	}

	/// Metodo para que una vez asignado el mote, la captura no se repita
	@FXML
	private void moteAsignadoNoRepetir(ActionEvent event) {
		noRepetirCaptura(event);

	}

	// Metodo repetir captura 
	private void repetirCaptura() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(0),
						e -> txtLog.appendText("Has elegido buscar otro Pokémon. Buscando en...\n")),
				new KeyFrame(Duration.millis(500), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.millis(1000), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.millis(1500), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.millis(2000), e -> generarPokemonAleatorio()));
		timeline.play();
	}

	@FXML
	private void handleBuscarOtroPokemon() {
		repetirCaptura();
	}

	// Metodo del resultado de la captura
	@FXML
	private void resultadoCaptura(ActionEvent event) {
		pokemonImg.setVisible(false);

		String nombre = lblNombre.getText();
		String nivel = lblNivel.getText();
		;
		int probabilidad = 70;
		int suerte = (int) (Math.random() * 100) + 1;

		// Hacemos que si lasuerte es mayor que la probabilidad, el pokemon sea
		// capturado
		if (suerte <= probabilidad) {
			// SONIDO CAPTURA
			imgPokeball.setVisible(true);
			// Reproducimos el sonido de la captura exitosa
			sonidoCaptura.play();
			txtLog.appendText("...\n");
			txtLog.appendText("¡1... 2... 3...!\n");
			txtLog.appendText("¡HECHO! El " + nombre + " de " + nivel + " ha sido capturado.\n");

			// Desactivar el boton de captura

			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);

			txtMote.setVisible(true);
			lblMote.setVisible(true);
			btnMote.setVisible(true);

			txtMote.setDisable(false);
			lblMote.setDisable(false);

			// Si la suerte no es mayor o igual, la caputra fallara y nos dara la opcion de
			// buscar otro pokemon
		} else {

			txtLog.appendText("...\n");
			txtLog.appendText("¡Oh no! El Pokémon se ha escapado de la bola.\n");
			txtLog.appendText("¡Ha huido!\n");
			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);

			menuRepetirCaptura();

		}

	}

	// Metodo para cambair las escenas
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

	// Metodo para guardar el mote del pokemon
	@FXML
	private void handleGuardarMote(ActionEvent event) {

		// Instanciar la clase DAO pa quese hagan los inserts de los pokemon y se
		// guarden
		PokemonDAO pDAO = new PokemonDAO();

		int destinoUbicacion;
		String mote = txtMote.getText().trim();

		if (mote.isEmpty()) {
			mote = pokemonActual.getNombre();
			txtLog.appendText(
					"No has añadido ningún mote, el pokémon se unirá a tu equipo como: " + pokemonActual.getNombre());
		}

		pokemonActual.setMote(mote);
		if (Main.miEquipo.size() < 6) {

			// Esto es para saber si el pokemon está en el q¡equipo o en el PC
			// Sitiene un 1 está en el equipo
			destinoUbicacion = 1;
			Main.miEquipo.add(pokemonActual);
			txtLog.appendText("¡El pokemon se ha unido a tu equipo como: " + mote + "!\n");
		} else {
			// Si tiene un se envía al PC pq no hay espacio
			destinoUbicacion = 0;
			Main.pcPokemon.add(pokemonActual);
			txtLog.appendText("Espacio insuficiente en el equipo. ¡El pokemon ha sido enviaado a la caja del PC como: "
					+ mote + "!\n");
		}

		// Almacenamos en la base de datos
		pDAO.guardarPokemon(pokemonActual, Main.entrenadorLogueado.getId_Entrenador(), destinoUbicacion);
		// Hacemos que salga el menú de repetir captura

		moteAsignado(event);

	}

}
