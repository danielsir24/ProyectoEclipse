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
import pokemon.Pokedex;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CapturaController {

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

	private PokedexDAO pokedexDAO = new PokedexDAO();
	private AudioClip sonidoCaptura;

	
	
	@FXML
	public void initialize() {
		System.out.println("DEBUG: La ventana se ha cargado. Generando Pokemon");
		sonidoCaptura = new AudioClip(getClass().getResource("/sounds/capture.wav").toExternalForm());
		sonidoCaptura.setVolume(0.2);

		generarPokemonAleatorioCaptura();
	}

	public void generarPokemonAleatorioCaptura() {
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

		int idPokedex = pokedexDAO.generarIdPokedexAleatorio();

		Pokedex especie = pokedexDAO.buscarPorIdPokedex(idPokedex);

		if (especie != null) {

			lblNombre.setText(especie.getNombreEspecie());

			int nivelAleatorio = (int) (Math.random() * (10 - 2 + 1) + 2);
			lblNivel.setText("Niv." + nivelAleatorio);

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

	private void moteAsignado(ActionEvent event) {
		txtLog.appendText("El Pokémon ha sido añadido a tu equipo.");
		menuRepetirCaptura();
		txtMote.setVisible(false);
		lblMote.setVisible(false);
		btnMote.setVisible(false);

	}

	private void noRepetirCaptura(ActionEvent event) {
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

	@FXML
	private void moteAsignadoNoRepetir(ActionEvent event) {
		noRepetirCaptura(event);

	}

	private void repetirCaptura() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(0),
						e -> txtLog.appendText("Has elegido buscar otro Pokémon. Buscando en...\n")),
				new KeyFrame(Duration.millis(500), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.millis(1000), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.millis(1500), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.millis(2000), e -> generarPokemonAleatorioCaptura()));
		timeline.play();
	}

	@FXML
	private void handleBuscarOtroPokemon() {
		repetirCaptura();
	}

	@FXML
	private void resultadoCaptura(ActionEvent event) {
		pokemonImg.setVisible(false);

		String nombre = lblNombre.getText();
		String nivel = lblNivel.getText();
		;
		int probabilidad = 50;
		int suerte = (int) (Math.random() * 100) + 1;

		if (suerte <= probabilidad) {
			// SONIDO CAPTURA
			imgPokeball.setVisible(true);
			sonidoCaptura.play();
			txtLog.appendText("...\n");
			txtLog.appendText("¡1... 2... 3...!\n");
			txtLog.appendText("¡HECHO! El " + nombre + " de " + nivel + " ha sido capturado.\n");

			// Desactivar el boton de captura para que no suceda un accidente y se
			// reiniciela captura y elde huir para no salir sin ponerle un mote al pokemon
			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);

			txtMote.setVisible(true);
			lblMote.setVisible(true);
			btnMote.setVisible(true);
			
			txtMote.setDisable(false);
			lblMote.setDisable(false);

		} else {

			txtLog.appendText("...\n");
			txtLog.appendText("¡Oh no! El Pokémon se ha escapado de la bola.\n");
			txtLog.appendText("¡Ha huido!\n");
			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);
			
			menuRepetirCaptura();


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

	@FXML
	private void handleGuardarMote(ActionEvent event) {
		String mote = txtMote.getText().trim();

		if (mote.isEmpty()) {
			mote = txtMote.getText().trim();
			txtLog.appendText("No has añadido ningún mote, el pokémon se unirá atu equipo como: " + mote);

		}

		txtLog.appendText("¡El pokemon se ha unido a tu equipo como: " + mote + "!\n");
		txtLog.appendText("");

		moteAsignado(event);

	}

}
