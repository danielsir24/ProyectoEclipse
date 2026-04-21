package pokemon.Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import pokemon.Main;
import javafx.scene.control.Button;


import pokemon.Pokemon;

import java.io.InputStream;


public class VistaEquipoController {


	// Elementos generales
	@FXML
	private Button btnEntrarPC, btnVolverMenu;
	@FXML
	private Label lblEntrarPC, lblVolverMenu, errorLabel;

	// Elementos de cada Slot
	// Slot 1
	@FXML
	private Label lblNombre1, lblNivel1, lblPV1, lblEXP1;
	@FXML
	private ProgressBar hpBar1, expBar1;
	@FXML
	private ImageView imgPokemon1, imgGenero1;

	// Slot 2
	@FXML
	private Label lblNombre2, lblNivel2, lblPV2, lblEXP2;
	@FXML
	private ProgressBar hpBar2, expBar2;
	@FXML
	private ImageView imgPokemon2, imgGenero2;

	// Slot 3
	@FXML
	private Label lblNombre3, lblNivel3, lblPV3, lblEXP3;
	@FXML
	private ProgressBar hpBar3, expBar3;
	@FXML
	private ImageView imgPokemon3, imgGenero3;

	// Slot 4
	@FXML
	private Label lblNombre4, lblNivel4, lblPV4, lblEXP4;
	@FXML
	private ProgressBar hpBar4, expBar4;
	@FXML
	private ImageView imgPokemon4, imgGenero4;

	// Slot 5
	@FXML
	private Label lblNombre5, lblNivel5, lblPV5, lblEXP5;
	@FXML
	private ProgressBar hpBar5, expBar5;
	@FXML
	private ImageView imgPokemon5, imgGenero5;

	// Slot 6
	@FXML
	private Label lblNombre6, lblNivel6, lblPV6, lblEXP6;
	@FXML
	private ProgressBar hpBar6, expBar6;
	@FXML
	private ImageView imgPokemon6, imgGenero6;

	// Arrays para la optimizacion
	private Label[] nombres, niveles, pvs, exps;
	private ProgressBar[] barrasHP, barrasEXP;
	private ImageView[] fotos, generos; //Hay que meter tambien las imagenes de los generos, que se me haolvidado, ya lo haré

	// Metodo para cargar la fuente
	@FXML
	public void initialize() {

		// Incializamos los arrays en orden
		nombres = new Label[] { lblNombre1, lblNombre2, lblNombre3, lblNombre4, lblNombre5, lblNombre6 };
		niveles = new Label[] { lblNivel1, lblNivel2, lblNivel3, lblNivel4, lblNivel5, lblNivel6 };
		pvs = new Label[] { lblPV1, lblPV2, lblPV3, lblPV4, lblPV5, lblPV6 };
		exps = new Label[] { lblEXP1, lblEXP2, lblEXP3, lblEXP4, lblEXP5, lblEXP6 };

		barrasHP = new ProgressBar[] { hpBar1, hpBar2, hpBar3, hpBar4, hpBar5, hpBar6 };
		barrasEXP = new ProgressBar[] { expBar1, expBar2, expBar3, expBar4, expBar5, expBar6 };

		fotos = new ImageView[] { imgPokemon1, imgPokemon2, imgPokemon3, imgPokemon4, imgPokemon5, imgPokemon6 };
		generos = new ImageView[] { imgGenero1, imgGenero2, imgGenero3, imgGenero4, imgGenero5, imgGenero6 };

		actualizarEquipo();
		cargarFuentePersonalizada();

	}

	private void actualizarEquipo() {
		System.out.println("DEBUG: Actualizando equipo. Tamaño actual: " + Main.miEquipo.size());

		for (int i = 0; i < 6; i++) {
			// Verificamos si existe un pokemon en esta posición de la lista
			if (i < Main.miEquipo.size()) {
				Pokemon pActual = Main.miEquipo.get(i);

				if (pActual != null) {
					// Rellenamos datos
					nombres[i].setText(pActual.getMote());
					niveles[i].setText("Nv. " + pActual.getNivel());
					pvs[i].setText(pActual.getVitalidad() + "/" + pActual.getVitalidadMaxima());
					exps[i].setText(pActual.getExperiencia() + " pts");

					// Barras
					barrasHP[i].setProgress((double) pActual.getVitalidad() / pActual.getVitalidadMaxima()); //Tapoco se ve el progreso de la barra
					barrasEXP[i].setProgress(100); // Aun hay que solucionar lo de la barra de experiencia

					// Imagen
					if (pActual.getInfoPokedex() != null) {
						String ruta = pActual.getInfoPokedex().getRutaImagen(true);
						InputStream is = getClass().getResourceAsStream(ruta);
						if (is != null) {
							fotos[i].setImage(new Image(is));
							System.out.println("DEBUG: ¡Imagen cargada con éxito!"); // Comentarios debug
						} else {
							System.err.println("DEBUG: No se encuentra el archivo en la ruta: " + ruta); // Comentarios debug
							//No toqueis esto de la imagen que me ha costado que flipas																				
						}
					}
					// Aseguramos visibilidad
					nombres[i].setVisible(true);
					fotos[i].setVisible(true);
					//Esto lo pongo porque antes no salía
				}
			} else {
				// Hacemos que los slots vacios salgan con estos parametros predeterminados
				System.out.println("DEBUG: Slot " + i + " está vacío.");
				nombres[i].setText("---");
				niveles[i].setText("");
				pvs[i].setText("");
				exps[i].setText("");
				barrasHP[i].setProgress(0);
				barrasEXP[i].setProgress(0);
				fotos[i].setImage(null); 
			}
		}
	}

	//La fuente esta que al final no se ni si la vamos a utilizar
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
				lblEntrarPC.setFont(pokemonFont);
				lblVolverMenu.setFont(pokemonFont);

			} else {
				System.out.println("No se pudo cargar la fuente: comprueba la ruta.");
			}
		} catch (Exception e) {
			System.out.println("Error al cargar la fuente: " + e.getMessage());
		}
	}

	
	//Metodos de cambiode escena 
	@FXML
	private void entrarPc(ActionEvent event) {
		cambiarEscena(event, "/EscenaPC.fxml", "PC de Pokemon");

		System.out.println("Has entrado en el PC");
	}

	@FXML
	private void volverMenu(ActionEvent event) {
		cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");

		System.out.println("Has vuelto al menú principal");
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
