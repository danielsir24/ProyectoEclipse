package pokemon.Controller;

//Aqui hay pila cosas que no hacen falta, pero ya as quitaré mas tarde q es q las he copiado y pegado de ootro controlador xd
import javafx.scene.control.Alert;
import pokemon.Main;
import pokemon.Pokemon;
import pokemon.PokedexDAO;
import pokemon.PokemonDAO;

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
import pokemon.MovimientoDAO;
import pokemon.Movimiento;
import pokemon.Pokedex;
import pokemon.Pokemon;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;

public class pcController {

	// Panel grande
	@FXML
	private Label nombrePokemon;
	@FXML
	private Label generoPokemon;
	@FXML
	private ImageView imgPokemon;
	@FXML
	private Label nomNivel;
	@FXML
	private Label nomTipo1;
	@FXML
	private Label nomTipo2;
	@FXML
	private Label nomEntrenador;
	@FXML
	private Label nomObjeto;
	@FXML
	private Label nomMov1;
	@FXML
	private Label nomMov2;
	@FXML
	private Label nomMov3;
	@FXML
	private Label nomMov4;

	// Botones de cambiar de caja (no se ni si los vamos a usar)
	@FXML
	private Button btnCajaAnterior;
	@FXML
	private Button btnCajaSiguiente;
	@FXML
	private Label NombreCaja;

	// Los 25 mf slots
	// Si alguna ez quereis torturar a alguien, ponedlo a instanciar todo esto a
	// mano (Podría haver copiado y pegado pero no se me ha ocurrido)
	@FXML
	private GridPane Caja5x5;
	@FXML
	private ImageView slot_0_0;
	@FXML
	private ImageView slot_0_1;
	@FXML
	private ImageView slot_0_2;
	@FXML
	private ImageView slot_0_3;
	@FXML
	private ImageView slot_0_4;
	@FXML
	private ImageView slot_1_0;
	@FXML
	private ImageView slot_1_1;
	@FXML
	private ImageView slot_1_2;
	@FXML
	private ImageView slot_1_3;
	@FXML
	private ImageView slot_1_4;
	@FXML
	private ImageView slot_2_0;
	@FXML
	private ImageView slot_2_1;
	@FXML
	private ImageView slot_2_2;
	@FXML
	private ImageView slot_2_3;
	@FXML
	private ImageView slot_2_4;
	@FXML
	private ImageView slot_3_0;
	@FXML
	private ImageView slot_3_1;
	@FXML
	private ImageView slot_3_2;
	@FXML
	private ImageView slot_3_3;
	@FXML
	private ImageView slot_3_4;
	@FXML
	private ImageView slot_4_0;
	@FXML
	private ImageView slot_4_1;
	@FXML
	private ImageView slot_4_2;
	@FXML
	private ImageView slot_4_3;
	@FXML
	private ImageView slot_4_4;

	// Botones
	@FXML
	private Button btnEquipo;
	@FXML
	private Button btnSalir;

	private ImageView[][] matrizSlots;

	// la pagina actual en la que estamos en la caja, esto es pa luego pa los
	// botones y el metodo de cargar la caja
	private int paginaActual = 1;

	@FXML
	public void initialize() {
		// Inicializamos un array matriz para tener localizadas filas y columnas pq si
		// no esto es imposible
		matrizSlots = new ImageView[][] { { slot_0_0, slot_0_1, slot_0_2, slot_0_3, slot_0_4 },
				{ slot_1_0, slot_1_1, slot_1_2, slot_1_3, slot_1_4 },
				{ slot_2_0, slot_2_1, slot_2_2, slot_2_3, slot_2_4 },
				{ slot_3_0, slot_3_1, slot_3_2, slot_3_3, slot_3_4 },
				{ slot_4_0, slot_4_1, slot_4_2, slot_4_3, slot_4_4 } };

		cargarCaja(paginaActual);

	}

	// Metodo que carga la caja correspondiente
	// Tambien te digo que si te pones a capturar pokemon como pa llenar una caja te
	// vas a tirar un rato

	private void cargarCaja(int numeroCaja) {

		NombreCaja.setText("Caja " + paginaActual);
		
		PokemonDAO pDAO = new PokemonDAO();

		
		ArrayList<Pokemon> pokemonEnCaja = pDAO.obtenerPokemonPC(Main.entrenadorLogueado.getId_Entrenador(), numeroCaja);
	    System.out.println("Pokémon encontrados en el PC: " + pokemonEnCaja.size()); //Debug para saber cuantos pokemon ha encontrado
	    //Contador para que recorra los espacios del array bidimensional
		try {int contador = 0;

		for (int fila = 0; fila < 5; fila++) {
			for (int col = 0; col < 5; col++) {

				if (contador < pokemonEnCaja.size()) {
					Pokemon p = pokemonEnCaja.get(contador);					
					// Con esto, hacemos que laa imagenes de los pokemon aparezca en sus respectivos
					// slots
					matrizSlots[fila][col].setImage(new Image(getClass().getResourceAsStream(p.getInfoPokedex().getRutaImagen(true))));
					
					// Con este if nos aseguramos de que la informacion de la pokedex exista
					if (p.getInfoPokedex() != null) {

						// Con esto, hacemos que las imagenes de los pokemon aparezca en sus respectivos
						// slots
						matrizSlots[fila][col].setImage(
								(new Image(getClass().getResourceAsStream(p.getInfoPokedex().getRutaImagen(true)))));
						matrizSlots[fila][col].setOnMouseClicked(e -> mostrarDetalles(p));
					
						

						// En caso de haberlos, que nos avise por consola, lismpiamos el slot y
						// desactivamos la opción de pulsarlo
					} else {
						System.out.println("Error: Información no encontrada");
						matrizSlots[fila][col].setImage(null);
						matrizSlots[fila][col].setOnMouseClicked(null);
					}
					contador++;
				}
			}
		}
		
		} catch (Exception e) { 
			System.out.println("ERROR AQUI: " + e.getMessage());
			e.printStackTrace();
			
		}

	}

	private void mostrarDetalles(Pokemon p) {
		// Datos basicosdel pokemon
		nombrePokemon.setText(p.getMote());
		generoPokemon.setText(p.getSexo().name().equalsIgnoreCase("MACHO") ? "♂" : "♀");
		nomNivel.setText(String.valueOf(p.getNivel()));

		// Pokemon que sale en grande a la izquierda
		//
		if (p.getInfoPokedex() != null) {
			imgPokemon.setImage(new Image(getClass().getResourceAsStream(p.getInfoPokedex().getRutaImagen(true))));

			// Los tipos
			nomTipo1.setText(p.getInfoPokedex().getTipo1());

			if (p.getInfoPokedex().getTipo2() != null) {
				nomTipo2.setText(p.getInfoPokedex().getTipo2());
				nomTipo2.setVisible(true);
			} else {
				nomTipo2.setVisible(false);
			}
		}

		// Entrenador y Objeto
		nomEntrenador.setText(Main.entrenadorLogueado.getNom_Entrenador());

		if (p.getObjeto() != null) {
			nomObjeto.setText(p.getObjeto().getNombre());
		} else {
			nomObjeto.setText("Sin objeto");
		}

		// Movimientos
		MovimientoDAO movDAO = new MovimientoDAO();
		ArrayList<Movimiento> movimientos = movDAO.obtenerMovimientosDePokemon(p.getIdPokemon());

		// Limpiamos los textos de los movimientos antes de poner los nuevos
		nomMov1.setText("-----------");
		nomMov2.setText("-----------");
		nomMov3.setText("-----------");
		nomMov4.setText("-----------");

		if (movimientos.size() > 0) {
			nomMov1.setText(movimientos.get(0).getNombre());
		}
		if (movimientos.size() > 1) {
			nomMov2.setText(movimientos.get(1).getNombre());
		}
		if (movimientos.size() > 2) {
			nomMov3.setText(movimientos.get(2).getNombre());
		}
		if (movimientos.size() > 3) {
			nomMov4.setText(movimientos.get(3).getNombre());
		}
		System.out.println("Movimientos encontrados para " + p.getNombre() + ": " + movimientos.size());
	}

	// Metodos para cambiar de pagina de la Caja

	// Palante
	@FXML
	private void clickSiguiente() {
		paginaActual++;
		cargarCaja(paginaActual);
	}

	// Patrá
	@FXML
	private void clickAnterior() {
		if (paginaActual > 1) {
			paginaActual--;
			cargarCaja(paginaActual);
		}
	}

	public void handleSalirAlMenu(ActionEvent event) {
		cambiarEscena(event, "/EscenaMenu.fxml", " Menú Principal");
	}

	public void handleEntrarEquipo(ActionEvent event) {
		cambiarEscena(event, "/EscenaEquipo.fxml", " Menú Principal");
	}

	// Metodo para mover el Pokemon del PC al equipo
	public void handleMoverAlEquipo(Pokemon seleccionado) {

		// Verificamos espacio en el main
		if (Main.miEquipo.size() >= 6) {
			System.out.println("El equipo está lleno. Suelta a alguien primero.");
			return;
		}
		// Llamamos al DAO para actualizar la DB
		PokemonDAO pDAO = new PokemonDAO();
		if (pDAO.moverAlEquipo(seleccionado.getIdPokemon())) {

			// Actualizamos el main si todo está correcto en la BD
			seleccionado.setUbicacion(1);
			Main.miEquipo.add(seleccionado);

			// Refrescamos la interfaz (quitarlo de la lista del PC)
			System.out.println(seleccionado.getMote() + " se ha unido al equipo.");

		} else {
			System.out.println("Error: No se pudo mover el Pokémon.");
		}
	}

	/*
	 * public void handleMoverAlPC(Pokemon seleccionado) { // Llamamos al DAO para
	 * actualizar la DB PokemonDAO pDAO = new PokemonDAO(); if
	 * (pDAO.moverAlEquipo(seleccionado.getIdPokemon())) {
	 * 
	 * // Actualizamos el main si todo está correcto en la BD
	 * seleccionado.setUbicacion(1); Main.miEquipo.add(seleccionado);
	 * 
	 * // Refrescamos la interfaz (quitarlo de la lista del PC)
	 * System.out.println(seleccionado.getMote() + " se ha unido al equipo.");
	 * 
	 * } else { System.out.println("Error: No se pudo mover el Pokémon."); } }
	 */
	// Esto es que lo he hecho aqui pero debería usarlo luego pa la del equipo xd

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
		}
	}

}
