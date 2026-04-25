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
import java.util.List;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import pokemon.Main;
import javafx.scene.control.Button;

import pokemon.Pokemon;
import pokemon.PokemonDAO;

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
	private Label lblNombre1, lblNivel1, lblPV1, lblEXP1, lblGenero1;
	@FXML
	private ProgressBar hpBar1, expBar1;
	@FXML
	private ImageView imgPokemon1;
	@FXML
	private Button btnMover1, btnLiberar1;

	// Slot 2
	@FXML
	private Label lblNombre2, lblNivel2, lblPV2, lblEXP2, lblGenero2;
	@FXML
	private ProgressBar hpBar2, expBar2;
	@FXML
	private ImageView imgPokemon2;
	@FXML
	private Button btnMover2, btnLiberar2;
	// Slot 3
	@FXML
	private Label lblNombre3, lblNivel3, lblPV3, lblEXP3, lblGenero3;
	@FXML
	private ProgressBar hpBar3, expBar3;
	@FXML
	private ImageView imgPokemon3;
	@FXML
	private Button btnMover3, btnLiberar3;

	// Slot 4
	@FXML
	private Label lblNombre4, lblNivel4, lblPV4, lblEXP4, lblGenero4;
	@FXML
	private ProgressBar hpBar4, expBar4;
	@FXML
	private ImageView imgPokemon4;
	@FXML
	private Button btnMover4, btnLiberar4;

	// Slot 5
	@FXML
	private Label lblNombre5, lblNivel5, lblPV5, lblEXP5, lblGenero5;
	@FXML
	private ProgressBar hpBar5, expBar5;
	@FXML
	private ImageView imgPokemon5;
	@FXML
	private Button btnMover5, btnLiberar5;

	// Slot 6
	@FXML
	private Label lblNombre6, lblNivel6, lblPV6, lblEXP6, lblGenero6;
	@FXML
	private ProgressBar hpBar6, expBar6;
	@FXML
	private ImageView imgPokemon6;
	@FXML
	private Button btnMover6, btnLiberar6;

	// Arrays para la optimizacion
	private Label[] nombres, niveles, pvs, exps, generos;
	private ProgressBar[] barrasHP, barrasEXP;
	private ImageView[] fotos;

	// Metodo para cargar la fuente

	@FXML
	public void initialize() {
		PokemonDAO pDAO = new PokemonDAO();

		// Incializamos los arrays en orden
		nombres = new Label[] { lblNombre1, lblNombre2, lblNombre3, lblNombre4, lblNombre5, lblNombre6 };
		niveles = new Label[] { lblNivel1, lblNivel2, lblNivel3, lblNivel4, lblNivel5, lblNivel6 };
		pvs = new Label[] { lblPV1, lblPV2, lblPV3, lblPV4, lblPV5, lblPV6 };
		exps = new Label[] { lblEXP1, lblEXP2, lblEXP3, lblEXP4, lblEXP5, lblEXP6 };
		generos = new Label[] { lblGenero1, lblGenero2, lblGenero3, lblGenero4, lblGenero5, lblGenero6 };

		barrasHP = new ProgressBar[] { hpBar1, hpBar2, hpBar3, hpBar4, hpBar5, hpBar6 };
		barrasEXP = new ProgressBar[] { expBar1, expBar2, expBar3, expBar4, expBar5, expBar6 };

		fotos = new ImageView[] { imgPokemon1, imgPokemon2, imgPokemon3, imgPokemon4, imgPokemon5, imgPokemon6 };

		List<Pokemon> desdeBD = pDAO.obtenerEquipo(Main.entrenadorLogueado.getId_Entrenador());
		Main.miEquipo.clear();
		Main.miEquipo.addAll(desdeBD);
		actualizarEquipo();
//		cargarFuentePersonalizada();

	}

	private void actualizarEquipo() {
		System.out.println("DEBUG: Actualizando equipo. Tamaño actual: " + Main.miEquipo.size());

		for (int i = 0; i < 6; i++) {
			// Verificamos si existe un pokemon en esta posición de la lista
			if (i < Main.miEquipo.size()) {
				Pokemon pActual = Main.miEquipo.get(i);
				System.out.println("DEBUG: Vida del bicho: " + pActual.getVitalidadMaxima());

				if (pActual != null) {
					// Rellenamos datos
					nombres[i].setText(pActual.getMote());
					niveles[i].setText("Nv. " + pActual.getNivel());
					pvs[i].setText(pActual.getVitalidad() + "/" + pActual.getVitalidadMaxima());
					exps[i].setText(pActual.getExperiencia() + " pts");

					// Símbolo de género ♂ o ♀ con su color
					if (pActual.getSexo() != null) {
						if (pActual.getSexo() == pokemon.Sexo.HEMBRA) {
							generos[i].setText("♀");
							generos[i].setStyle("-fx-text-fill: #ff6eb4;"); // rosa para hembra
						} else {
							generos[i].setText("♂");
							generos[i].setStyle("-fx-text-fill: #6eb4ff;"); // azul para macho
						}
					}

					// Barras
					barrasHP[i].setProgress((double) pActual.getVitalidad() / pActual.getVitalidadMaxima());
					// El progreso de la barra no funciona chat, ayuda
					barrasEXP[i].setProgress(100); // Aun hay que solucionar lo de la barra de experiencia

					// Imagen
					if (pActual.getInfoPokedex() != null) {
						String ruta = pActual.getInfoPokedex().getRutaImagen(true);
						InputStream is = getClass().getResourceAsStream(ruta);
						if (is != null) {
							fotos[i].setImage(new Image(is));
							System.out.println("DEBUG: ¡Imagen cargada con éxito!"); // Comentarios debug
						} else {
							System.err.println("DEBUG: No se encuentra el archivo en la ruta: " + ruta); // Comentarios
																											// debug
							// No toqueis esto de la imagen que me ha costado que flipas
						}
					}
					// Aseguramos visibilidad
					nombres[i].setVisible(true);
					fotos[i].setVisible(true);
					// Esto lo pongo porque antes no salía
				}
			} else {
				// Hacemos que los slots vacios salgan con estos parametros predeterminados
				System.out.println("DEBUG: Slot " + i + " está vacío.");
				nombres[i].setText("---");
				niveles[i].setText("");
				pvs[i].setText("");
				exps[i].setText("");
				generos[i].setText("");
				barrasHP[i].setProgress(0);
				barrasEXP[i].setProgress(0);
				fotos[i].setImage(null);
			}
		}
	}

	@FXML
	private void moverAlPC(int i) {

		try {
			// Esto es para verificar que haya un pokemon en ese slot pq si no el metodono
			// funcionaría o daría problemas
			if (i < Main.miEquipo.size()) {
				Pokemon p = Main.miEquipo.get(i);

				PokemonDAO pDAO = new PokemonDAO();

				if (pDAO.moverAlPC(p.getIdPokemon())) {

					System.out.println(p.getMote() + " ha sido enviado al  PC");

					// Le cambiamos la ubicacion al pokemon para que la base de datos detecte que
					// está en la caja y lo quitamos del ArrayList del equipo
					p.setUbicacion(0);
					Main.miEquipo.remove(i);

					// Actualizamos el equipo para que desaparezca insta y que los pokemon se
					// ordenen
					actualizarEquipo();
				} else {
					System.out.println("DEBUG: Error al mover en la base de datos"); // Un DEBUG por aqui
				}

			} else {
				System.out.println("DEBUG: El slot está vacio"); // Y otro por aquí
			}
			// Gracias comentarios debug, no se que haría sin vosotros
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println("Mensaje: " + e.getMessage());
			e.printStackTrace();

		}
	}

	// Ahora creamos un metodo para cada botón del menú del equipo
	// El numero es la i del metodo de movverAlPC (indice del array, que empieza en
	// 0)
	@FXML
	private void handleMover1() {
		moverAlPC(0);
	}

	@FXML
	private void handleMover2() {
		moverAlPC(1);
	}

	@FXML
	private void handleMover3() {
		moverAlPC(2);
	}

	@FXML
	private void handleMover4() {
		moverAlPC(3);
	}

	@FXML
	private void handleMover5() {
		moverAlPC(4);
	}

	@FXML
	private void handleMover6() {
		moverAlPC(5);
	}

	// El metodo es practicamente igual que el de mover al PC, pero cambia que este
	// lo libera y el pokemon se borra xd
	public void liberarPokemon(int i) {
		try {
			// Esto es para verificar que haya un pokemon en ese slot pq si no el metodono
			// funcionaría o daría problemas
			if (i < Main.miEquipo.size()) {
				Pokemon p = Main.miEquipo.get(i);

				PokemonDAO pDAO = new PokemonDAO();

				try{ if (pDAO.liberarPokemon(p.getIdPokemon())) {

					System.out.println(p.getMote() + " ha sido enviado al  PC");

					// Noscargamos al pokemon y buenas noches
					Main.miEquipo.remove(i);

					// Actualizamos el equipo para que desaparezca insta y que los pokemon se
					// ordenen
					actualizarEquipo();
				} else {
					System.out.println("DEBUG: Error al eliminar en la base de datos"); // Un DEBUG por aqui
				}
				} catch(Exception e) {
					System.out.println("ERROR:");
					System.out.println("Mensaje: " + e.getMessage());
					e.printStackTrace();
					
				}

			} else {
				System.out.println("DEBUG: El slot está vacio"); // Y otro por aquí
			}
			// Gracias comentarios debug, no se que haría sin vosotros
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println("Mensaje: " + e.getMessage());
			e.printStackTrace();

		}

	}

	// Y hacemos lo mismo queconlos de mover al equipo, solo que esto en vez de
	// moverlos, obliteran al pokemon de la existencia
	@FXML
	private void handleLiberar1() {
		liberarPokemon(0);
	}

	@FXML
	private void handleLiberar2() {
		liberarPokemon(1);
	}

	@FXML
	private void handleLiberar3() {
		liberarPokemon(2);
	}

	@FXML
	private void handleLiberar4() {
		liberarPokemon(3);
	}

	@FXML
	private void handleLiberar5() {
		liberarPokemon(4);
	}

	@FXML
	private void handleLiberar6() {
		liberarPokemon(5);
	}

	// La dejo comentada de momentopq me esta dando problemas de superposición
	// visual en la vista
//	private void cargarFuentePersonalizada() {
//		try {
//			Font pokemonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/pokemon.ttf"), 18);
//
//			if (pokemonFont != null) {
//				lblNombre1.setFont(pokemonFont);
//				lblNivel1.setFont(pokemonFont);
//				lblPV1.setFont(pokemonFont);
//				lblEXP1.setFont(pokemonFont);
//				lblNombre2.setFont(pokemonFont);
//				lblNivel2.setFont(pokemonFont);
//				lblPV2.setFont(pokemonFont);
//				lblEXP2.setFont(pokemonFont);
//				lblNombre3.setFont(pokemonFont);
//				lblNivel3.setFont(pokemonFont);
//				lblPV3.setFont(pokemonFont);
//				lblEXP3.setFont(pokemonFont);
//				lblNombre4.setFont(pokemonFont);
//				lblNivel4.setFont(pokemonFont);
//				lblPV4.setFont(pokemonFont);
//				lblEXP4.setFont(pokemonFont);
//				lblNombre5.setFont(pokemonFont);
//				lblNivel5.setFont(pokemonFont);
//				lblPV5.setFont(pokemonFont);
//				lblEXP5.setFont(pokemonFont);
//				lblNombre6.setFont(pokemonFont);
//				lblNivel6.setFont(pokemonFont);
//				lblPV6.setFont(pokemonFont);
//				lblEXP6.setFont(pokemonFont);
//				lblEntrarPC.setFont(pokemonFont);
//				lblVolverMenu.setFont(pokemonFont);
//
//			} else {
//				System.out.println("No se pudo cargar la fuente: comprueba la ruta.");
//			}
//		} catch (Exception e) {
//			System.out.println("Error al cargar la fuente: " + e.getMessage());
//		}
//	}

	// Metodos de cambiode escena
	@FXML
	private void entrarPc(ActionEvent event) {
		cambiarEscena(event, "/EscenaCaja.fxml", "PC de Pokemon");

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
