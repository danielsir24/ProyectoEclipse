package pokemon; // Cambia esto por el nombre de tu paquete

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class VistaEquipoController {

	// Elementos de cada cuadro de cada pokemon (1-6)
	// 1
	@FXML
	private Label lblNombre1;
	@FXML
	private Label lblNivel1;
	@FXML
	private Label lblPV1;
	@FXML
	private Label lblEXP1;

	@FXML
	private ProgressBar hpBar1;
	@FXML
	private ProgressBar expBar1;
	@FXML
	private ImageView imgPokemon1;
	@FXML
	private ImageView imgGenero1;
	@FXML
	private Rectangle rectFondo1;

	// 2
	@FXML
	private Label lblNombre2;
	@FXML
	private Label lblNivel2;
	@FXML
	private Label lblPV2;
	@FXML
	private Label lblEXP2;
	@FXML
	private ProgressBar hpBar2;
	@FXML
	private ProgressBar expBar2;
	@FXML
	private ImageView imgPokemon2;
	@FXML
	private ImageView imgGenero2;
	@FXML
	private Rectangle rectFondo2;

	// 3
	@FXML
	private Label lblNombre3;
	@FXML
	private Label lblNivel3;
	@FXML
	private Label lblPV3;
	@FXML
	private Label lblEXP3;
	@FXML
	private ProgressBar hpBar3;
	@FXML
	private ProgressBar expBar3;
	@FXML
	private ImageView imgPokemon3;
	@FXML
	private ImageView imgGenero3;
	@FXML
	private Rectangle rectFondo3;

	// 4 @FXML
	private Label lblNombre4;
	@FXML
	private Label lblNivel4;
	@FXML
	private Label lblPV4;
	@FXML
	private Label lblEXP4;
	@FXML
	private ProgressBar hpBar4;
	@FXML
	private ProgressBar expBar4;
	@FXML
	private ImageView imgPokemon4;
	@FXML
	private ImageView imgGenero4;
	@FXML
	private Rectangle rectFondo4;

	// 5
	@FXML
	private Label lblNombre5;
	@FXML
	private Label lblNivel5;
	@FXML
	private Label lblPV5;
	@FXML
	private Label lblEXP5;
	@FXML
	private ProgressBar hpBar5;
	@FXML
	private ProgressBar expBar5;
	@FXML
	private ImageView imgPokemon5;
	@FXML
	private ImageView imgGenero5;
	@FXML
	private Rectangle rectFondo5;

	// 6
	@FXML
	private Label lblNombre6;
	@FXML
	private Label lblNivel6;
	@FXML
	private Label lblPV6;
	@FXML
	private Label lblEXP6;
	@FXML
	private ProgressBar hpBar6;
	@FXML
	private ProgressBar expBar6;
	@FXML
	private ImageView imgPokemon6;
	@FXML
	private ImageView imgGenero6;
	@FXML
	private Rectangle rectFondo6;

	// Metodo para cargar la fuente
	@FXML
	public void initialize() {
		cargarFuentePersonalizada();
	}

	private void cargarFuentePersonalizada() {
		try {
			Font pokemonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/pokemon.ttf"), 18);

			if (pokemonFont != null) {
				lblNombre1.setFont(pokemonFont);
				lblNivel1.setFont(pokemonFont);
				lblPV1.setFont(pokemonFont);
				lblEXP1.setFont(pokemonFont);
				lblNombre2.setFont(pokemonFont);
				lblNivel2.setFont(pokemonFont);
				lblPV2.setFont(pokemonFont);
				lblEXP2.setFont(pokemonFont);
				lblNombre2.setFont(pokemonFont);
				lblNivel2.setFont(pokemonFont);
				lblPV2.setFont(pokemonFont);
				lblEXP2.setFont(pokemonFont);
				lblNombre3.setFont(pokemonFont);
				lblNivel3.setFont(pokemonFont);
				lblPV3.setFont(pokemonFont);
				lblEXP3.setFont(pokemonFont);
				lblNombre4.setFont(pokemonFont);
				lblNivel4.setFont(pokemonFont);
				lblPV4.setFont(pokemonFont);
				lblEXP4.setFont(pokemonFont);
				lblNombre5.setFont(pokemonFont);
				lblNivel5.setFont(pokemonFont);
				lblPV5.setFont(pokemonFont);
				lblEXP5.setFont(pokemonFont);
				lblNombre6.setFont(pokemonFont);
				lblNivel6.setFont(pokemonFont);
				lblPV6.setFont(pokemonFont);
				lblEXP6.setFont(pokemonFont);

			} else {
				System.out.println("No se pudo cargar la fuente: comprueba la ruta.");
			}
		} catch (Exception e) {
			System.out.println("Error al cargar la fuente: " + e.getMessage());
		}
	}

	@FXML
	private void seleccionarPokemon1() {
		System.out.println("Has hecho clic en el Pokémon 1");
	}
}
