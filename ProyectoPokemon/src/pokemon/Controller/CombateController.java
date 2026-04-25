package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.List;
import pokemon.PokemonDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javafx.geometry.Pos;
import pokemon.PokemonDAO;
import pokemon.Estado;
import pokemon.Main;
import pokemon.Movimiento;
import pokemon.Pokedex;
import pokemon.PokedexDAO;
import pokemon.Pokemon;
import pokemon.PokemonDAO;
import pokemon.Tipo;
import pokemon.Entrenador;

public class CombateController {

	@FXML
	private Label lblNombreRival;
	@FXML
	private Label lblNivelRival;

	@FXML
	private Label lblNombreJugador;
	@FXML
	private Label lblNivelJugador;
	@FXML
	private Label lblPsJugador;

	@FXML
	private ProgressBar hpBarRival;
	@FXML
	private ProgressBar hpBarJugador;
	@FXML
	private ProgressBar expBarJugador;

	@FXML
	private Circle estadoRival;
	@FXML
	private Circle estadoJugador;

	@FXML
	private ImageView spritePokemonRival;
	@FXML
	private ImageView spritePokemon;

	@FXML
	private TextArea txtLog;

	@FXML
	private Label lblTurno;

	@FXML
	private VBox panelAcciones;

	@FXML
	private HBox panelMovimientos;

	@FXML
	private VBox panelCambioPokemon;

	@FXML
	private Button btnMovimiento1;
	@FXML
	private Button btnMovimiento2;
	@FXML
	private Button btnMovimiento3;
	@FXML
	private Button btnMovimiento4;
	@FXML
	private Button btnLuchar;
	@FXML
	private Button btnMochila;
	@FXML
	private Button btnHuir;
	@FXML
	private Button btnCambiarPokemon;

	// Botones: btnMovimiento1 btnMovimiento2 btnMovimiento3 btnMovimiento4
	// btnLuchar btnMochila btnHuir btnCambiarPokemon

	@FXML
	private Label lblTipoMovimiento;
	@FXML
	private Label lblPPMovimiento;

	@FXML
	private VBox slotCambio1, slotCambio2, slotCambio3;
	@FXML
	private VBox slotCambio4, slotCambio5, slotCambio6;
	@FXML
	private ImageView imgCambio1, imgCambio2, imgCambio3;
	@FXML
	private ImageView imgCambio4, imgCambio5, imgCambio6;
	@FXML
	private Label lblCambio1, lblCambio2, lblCambio3;
	@FXML
	private Label lblCambio4, lblCambio5, lblCambio6;
	@FXML
	private ProgressBar hpCambio1, hpCambio2, hpCambio3;
	@FXML
	private ProgressBar hpCambio4, hpCambio5, hpCambio6;

	private Pokemon pokemonJugadorActual;

	private Pokemon pokemonRivalActual;

	private int koJugador = 0;
	private int koRival = 0;

	private int turno = 1;

	private boolean combateEnPausa = false;

	private final Random random = new Random();

	@FXML
	public void initialize() {

		// Recargamos el equipo desde la BD para tener los movimientos actualizados
		PokemonDAO pDAO = new PokemonDAO();
		Main.miEquipo = pDAO.obtenerEquipo(Main.entrenadorLogueado.getId_Entrenador());

		// Coger el primer pokemon vivo del equipo del jugador
		for (Pokemon p : Main.miEquipo) {
			if (!p.estaDebilitado()) {
				pokemonJugadorActual = p;
				break;
			}
		}

		// Si no hay pokemon vivos no se puede lucchar
		if (pokemonJugadorActual == null) {
			log("Tienes muerto todos los pokemon");
			panelAcciones.setDisable(true);
			return;
		}
		// Generamos el pokemon rival aleatorio desde la pokedex
		PokedexDAO pokedexDAO = new PokedexDAO();
		int idAleatorio = pokedexDAO.generarIdPokedexAleatorio();
		Pokedex especie = pokedexDAO.buscarPorIdPokedex(idAleatorio);

		pokemonRivalActual = new Pokemon();
		pokemonRivalActual.setInfoPokedex(especie);

		if (especie != null) {
			pokemonRivalActual.setNombre(especie.getNombreEspecie());
			pokemonRivalActual.setMote(especie.getNombreEspecie());
		} else {
			pokemonRivalActual.setNombre("Pokemon");
			pokemonRivalActual.setMote("Pokemon");
		}

		// El nivel del rival es aleatorio dentro del rango de niveles de tu equipo
		int nivelMin = Integer.MAX_VALUE;
		int nivelMax = Integer.MIN_VALUE;
		for (Pokemon p : Main.miEquipo) {
			if (p.getNivel() < nivelMin)
				nivelMin = p.getNivel();
			if (p.getNivel() > nivelMax)
				nivelMax = p.getNivel();
		}
		int nivelRival = nivelMin + random.nextInt(Math.max(1, nivelMax - nivelMin + 1));
		pokemonRivalActual.setNivel(nivelRival);

		// Stats proporcionales al nivel
		// Calculamos la media de ataque y defensa de tu equipo
		int mediaAtaque = 0, mediaDefensa = 0;
		for (Pokemon p : Main.miEquipo) {
		    mediaAtaque  += p.getAtaque();
		    mediaDefensa += p.getDefensa();
		}
		mediaAtaque  /= Main.miEquipo.size();
		mediaDefensa /= Main.miEquipo.size();

		// El rival tiene stats similares a los tuyos con pequeña variacion
		pokemonRivalActual.setAtaque(mediaAtaque  + random.nextInt(5) - 2);
		pokemonRivalActual.setDefensa(mediaDefensa + random.nextInt(5) - 2);
		pokemonRivalActual.setVitalidad(20 + nivelRival * 3);
		pokemonRivalActual.setVitalidadMaxima(pokemonRivalActual.getVitalidad());
		// Asignamos el tipo del rival desde su info de pokedex
		if (especie != null) {
			java.util.List<Tipo> tipos = new java.util.ArrayList<>();
			try {
				if (especie.getTipo1() != null && !especie.getTipo1().isEmpty()) {
					tipos.add(Tipo.valueOf(especie.getTipo1().toUpperCase()));
				}
				if (especie.getTipo2() != null && !especie.getTipo2().isEmpty()
						&& !especie.getTipo2().equalsIgnoreCase("ninguno")) {
					tipos.add(Tipo.valueOf(especie.getTipo2().toUpperCase()));
				}
			} catch (Exception ex) {
				System.out.println("[Combate] Tipo no reconocido: " + ex.getMessage());
			}
			pokemonRivalActual.setTipos(tipos);
		}

		// Actualizamos la pantalla con los datos de los dos pokemon
		actualizarPantalla();

		// Mensaje de inicio en el log
		log("Un " + pokemonRivalActual.getNombre() + " salvaje aparecio!");
		log("Que hara " + pokemonJugadorActual.getMote() + "?");
	}

	@FXML
	private void handleLuchar(ActionEvent event) {

		// Ponemos los nombres de los movimientos en los botones
		Button[] botones = { btnMovimiento1, btnMovimiento2, btnMovimiento3, btnMovimiento4 };

		if (pokemonJugadorActual.getMovimientos() == null || pokemonJugadorActual.getMovimientos().isEmpty()) {
			// Si no tiene movimientos usamos Placaje por defecto
			btnMovimiento1.setText("Placaje");
			btnMovimiento2.setText("---");
			btnMovimiento3.setText("---");
			btnMovimiento4.setText("---");
			btnMovimiento2.setDisable(true);
			btnMovimiento3.setDisable(true);
			btnMovimiento4.setDisable(true);
		} else {
			for (int i = 0; i < 4; i++) {
				if (i < pokemonJugadorActual.getMovimientos().size()) {
					botones[i].setText(pokemonJugadorActual.getMovimientos().get(i).getNombre());
					botones[i].setDisable(false);
				} else {
					// Si tiene menos de 4 movimientos dejamos los sobrantes desactivados
					botones[i].setText("---");
					botones[i].setDisable(true);
				}
			}
		}

		// Mostramos el panel de movimientos
		mostrarPanel(panelMovimientos);
	}

	@FXML
	private void handleMovimiento1(ActionEvent event) {
		ejecutarMovimiento(0);
	}

	@FXML
	private void handleMovimiento2(ActionEvent event) {
		ejecutarMovimiento(1);
	}

	@FXML
	private void handleMovimiento3(ActionEvent event) {
		ejecutarMovimiento(2);
	}

	@FXML
	private void handleMovimiento4(ActionEvent event) {
		ejecutarMovimiento(3);
	}

	private void ejecutarMovimiento(int indice) {

		// Si se esyta haciendo un turno no hacemos nada
		if (combateEnPausa)
			return;
		combateEnPausa = true;
		System.out.println("[DEBUG] Movimientos: " + (pokemonJugadorActual.getMovimientos() == null ? "null"
				: pokemonJugadorActual.getMovimientos().size()));

		// Se vueleve al panel mientras se ahce el turno
		mostrarPanel(panelAcciones);

		// Se coge el movimiento que quereamos
		Movimiento movimiento;
		// Hemoscreado un objeto movimiento

		if (pokemonJugadorActual.getMovimientos() != null && !pokemonJugadorActual.getMovimientos().isEmpty()
				&& indice < pokemonJugadorActual.getMovimientos().size()) {
			movimiento = pokemonJugadorActual.getMovimientos().get(indice);
		} else {
			// Si no tienen ningun efecto se pone este por defecto, pero es imposible porque
			// todos los pokemon ya vivnen generados con sus movimientos y ya
			movimiento = new Movimiento("Placaje", 40, Tipo.NORMAL, "ATAQUE", 0, 0);
		}

		// Ahora hacemos el metodo para comprobar que tiene suficiente estamiona
		if (pokemonJugadorActual.getEstamina() < movimiento.getCosteEstamina()) {
			log(pokemonJugadorActual.getMote() + " no tiene estamina para usar " + movimiento.getNombre() + "!");
			combateEnPausa = false;
			return;

		}

		// Si se usa el ataque se gasta la estamina
		pokemonJugadorActual.setEstamina(pokemonJugadorActual.getEstamina() - movimiento.getCosteEstamina());

		// Ahora calculamos la efectividad de los tipos de ataque que hemos creado en
		// tipos.java
		// Creamos la variable efectividad
		double efectividad = 1.0;
		String mensajeEfectividad = "";
		String resultadoTipo = "NEUTRO";

		if (pokemonRivalActual.getTipos() != null && !pokemonRivalActual.getTipos().isEmpty()) {
			Tipo tipo1 = pokemonRivalActual.getTipos().get(0);
			Tipo tipo2 = pokemonRivalActual.getTipos().size() > 1 ? pokemonRivalActual.getTipos().get(1) : null;

			efectividad = movimiento.getTipo().calcularEfectividadDoble(tipo1, tipo2);

		}

		// Deteminamos la ventaja si ess doble ventaja, ventaja neutro o desventaa,
		// hacemos una condicional para comprobarlo, si la eefectivad es mayor a lo que
		// se pide, es doble ventaja
		if (efectividad >= 4.0) {
			resultadoTipo = "DOBLE_VENTAJA";
			mensajeEfectividad = "Es doblemente efectivo!";
		} else if (efectividad >= 2.0) {
			resultadoTipo = "VENTAJA";
			mensajeEfectividad = "Es super efectivo";
		} else if (efectividad < 1.0 && efectividad > 0.0) {
			resultadoTipo = "DESVENTAJA";
			mensajeEfectividad = "No es muy efectivo";
		} else if (efectividad == 0.0) {
			resultadoTipo = "DESVENTAJA";
			mensajeEfectividad = "No afecta a " + pokemonRivalActual.getNombre();
		}

		// Calculamos el dano aplicando la efectividad de tipos
		int danoJugador = (int) (calcularDano(pokemonJugadorActual, pokemonRivalActual, indice) * efectividad);
		danoJugador = Math.max(1, danoJugador);

		// Calculamos el dano que hara el rival
		int danoRival = calcularDanoRival();

		// Variables final para usarlas dentro del Timeline
		final String nombreMov = movimiento.getNombre();
		final String msgEfectividad = mensajeEfectividad;
		final String resumenTipo = resultadoTipo;
		final int danoFinalJugador = danoJugador;
		final int danoFinalRival = danoRival;

		// Timeline - cada KeyFrame es un segundo de retardo
		// para que el jugador pueda leer los mensajes uno a uno
		javafx.animation.Timeline timeline = new javafx.animation.Timeline(

				// Segundo 0: mostramos el ataque del jugador
				new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0), e -> {
					log(pokemonJugadorActual.getMote() + " uso " + nombreMov + "!");
					if (!msgEfectividad.isEmpty())
						log(msgEfectividad);
					log("[Tipo: " + resumenTipo + "]");
				}),

				// Segundo 1: aplicamos el dano al rival y actualizamos su barra
				new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
					pokemonRivalActual.recibirDano(danoFinalJugador);
					log("Hizo " + danoFinalJugador + " puntos de dano!");
					actualizarPantalla();
				}),

				// Segundo 2: comprobamos si el rival se debilito
				new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), e -> {
					if (pokemonRivalActual.estaDebilitado()) {
						log(pokemonRivalActual.getNombre() + " se debilito!");
						koRival++;
						// El combate termina cuando el rival cae
						finalizarCombate(true);
					} else {
						// Si sigue vivo el rival contraataca
						log(pokemonRivalActual.getNombre() + " ataco!");
					}
				}),

				// Segundo 3: aplicamos el dano del rival al jugador
				new javafx.animation.KeyFrame(javafx.util.Duration.seconds(3), e -> {
					if (!pokemonRivalActual.estaDebilitado()) {
						pokemonJugadorActual.recibirDano(danoFinalRival);
						log("Recibiste " + danoFinalRival + " puntos de dano!");
						actualizarPantalla();
					}
				}),

				// Segundo 4: comprobamos si el jugador se debilito
				new javafx.animation.KeyFrame(javafx.util.Duration.seconds(4), e -> {
					if (!pokemonRivalActual.estaDebilitado()) {
						if (pokemonJugadorActual.estaDebilitado()) {
							log(pokemonJugadorActual.getMote() + " se debilito!");
							koJugador++;

							// Buscamos el siguiente pokemon vivo
							Pokemon siguiente = null;
							for (Pokemon p : Main.miEquipo) {
								if (!p.estaDebilitado() && p != pokemonJugadorActual) {
									siguiente = p;
									break;
								}
							}

							if (siguiente != null) {
								pokemonJugadorActual = siguiente;
								actualizarPantalla();
								log("Vamos " + pokemonJugadorActual.getMote() + "!");
							} else {
								log("No te quedan pokemon...");
								finalizarCombate(false);
								return;
							}
						}

						// Subimos el turno y dejamos actuar al jugador
						turno++;
						lblTurno.setText("Turno " + turno);
						log("Que hara " + pokemonJugadorActual.getMote() + "?");
						combateEnPausa = false;
					}
				}));

		timeline.play();
	}

	private void finalizarCombate(boolean ganoJugador) {
		combateEnPausa = true;

		if (ganoJugador) {
			// Formula de experiencia: (nivelJugador + nivelRival * 10) / 4
			int expGanada = (pokemonJugadorActual.getNivel() + pokemonRivalActual.getNivel() * 10) / 4;

			// Le damos la experiencia al pokemon jugador
			// el metodo ganarExperiencia ya comprueba si sube de nivel
			pokemonJugadorActual.ganarExperiencia(expGanada);

			log("Ganaste el combate!");
			log(pokemonJugadorActual.getMote() + " gano " + expGanada + " puntos de experiencia!");

			// Actualizamos la barra de exp en pantalla
			actualizarPantalla();

		} else {
			log("Perdiste el combate");
		}

		// Desactivamos los botones para que no se pueda seguir jugando
		panelAcciones.setDisable(true);

		// Creamos dos botones dinamicamente para repetir o salir
		javafx.scene.control.Button btnRepetir = new javafx.scene.control.Button("Repetir combate");
		btnRepetir.setStyle("-fx-background-color: #44bb44; -fx-text-fill: white;"
				+ "-fx-font-family: 'Pokemon Solid Normal'; -fx-font-size: 14px;"
				+ "-fx-background-radius: 10; -fx-padding: 10 20 10 20; -fx-cursor: hand;");

		javafx.scene.control.Button btnSalir = new javafx.scene.control.Button("Volver al menu");
		btnSalir.setStyle("-fx-background-color: #3377dd; -fx-text-fill: white;"
				+ "-fx-font-family: 'Pokemon Solid Normal'; -fx-font-size: 14px;"
				+ "-fx-background-radius: 10; -fx-padding: 10 20 10 20; -fx-cursor: hand;");

		// Al pulsar repetir recargamos la escena de combate
		btnRepetir.setOnAction(e -> {
			try {
				// Recargamos el equipo antes de volver a combatir
				PokemonDAO pDAO = new PokemonDAO();
				Main.miEquipo = pDAO.obtenerEquipo(Main.entrenadorLogueado.getId_Entrenador());

				Parent root = FXMLLoader.load(getClass().getResource("/EscenaCombate.fxml"));
				Stage stage = (Stage) panelAcciones.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.setTitle("Combate");
				stage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		// Al pulsar salir volvemos al menu principal
		btnSalir.setOnAction(e -> {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenu.fxml"));
				Stage stage = (Stage) panelAcciones.getScene().getWindow();
				stage.setScene(new Scene(root));
				stage.setTitle("Menu Principal");
				stage.show();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		// Añadimos los botones al panel de acciones en un HBox centrado
		javafx.scene.layout.HBox hboxBotones = new javafx.scene.layout.HBox(20);
		hboxBotones.setAlignment(javafx.geometry.Pos.CENTER);
		hboxBotones.getChildren().addAll(btnRepetir, btnSalir);
		panelAcciones.getChildren().add(hboxBotones);
		panelAcciones.setDisable(false);
	}

	// Hemos hecho el metodo de calcular el daño por ambos, del rival y del equipo.

	private int calcularDano(Pokemon atacante, Pokemon defensor, int indiceMovimiento) {
		// Ponemos como principal la potencia a 50 porque es la potencia del ataque
		// basico como asi
		int potencia = 50;
		if (atacante.getMovimientos() != null && !atacante.getMovimientos().isEmpty()
				&& indiceMovimiento < atacante.getMovimientos().size()) {
			potencia = atacante.getMovimientos().get(indiceMovimiento).getPotencia();
		}
		// Estas formulas son por la clase combate del teams
		double nivelComp = (2.0 * atacante.getNivel() / 5.0) + 2.0;
		double statsComp = (double) atacante.getAtaque() / defensor.getDefensa();
		double danoBase = ((nivelComp * potencia * statsComp) / 50.0) + 2.0;

		int variacion = random.nextInt(5) - 2;
		int danoFinal = (int) danoBase + variacion;

		return Math.max(1, danoFinal);

	}

	private int calcularDanoRival() {
		// aqui hacemos lo mismo, los mismos metoedos pero solo cambiamos nuestro
		// pokemon por el pokemonRIval
		double nivelComp = (2.0 * pokemonRivalActual.getNivel() / 5.0) + 2.0;
		double statsComp = (double) pokemonRivalActual.getAtaque() / pokemonJugadorActual.getDefensa();
		double danoBase = ((nivelComp * 40 * statsComp) / 50.0) + 2.0;

		int variacion = random.nextInt(5) - 2;

		return Math.max(1, (int) danoBase + variacion);
	}

	@FXML
	private void handleCambiarPokemon(ActionEvent event) {

		// Juntamos los slots en arrays para manejarlos con un bucle
		VBox[] slots = { slotCambio1, slotCambio2, slotCambio3, slotCambio4, slotCambio5, slotCambio6 };
		ImageView[] imgs = { imgCambio1, imgCambio2, imgCambio3, imgCambio4, imgCambio5, imgCambio6 };
		Label[] nombres = { lblCambio1, lblCambio2, lblCambio3, lblCambio4, lblCambio5, lblCambio6 };
		ProgressBar[] barras = { hpCambio1, hpCambio2, hpCambio3, hpCambio4, hpCambio5, hpCambio6 };

		// Rellenamos cada slot con los datos del pokemon del equipo
		for (int i = 0; i < 6; i++) {
			if (Main.miEquipo != null && i < Main.miEquipo.size()) {
				Pokemon p = Main.miEquipo.get(i);

				// Este es el nombre o si tiene mote el mote
				String nombre = (p.getMote() != null && !p.getMote().isEmpty()) ? p.getMote() : p.getNombre();
				nombres[i].setText(nombre);

				// Esta es la barra de vida
				double porcentaje = (double) p.getVitalidad() / p.getVitalidadMaxima();
				barras[i].setProgress(Math.max(0, porcentaje));

				if (p.getInfoPokedex() != null) {
					try {
						InputStream is = getClass().getResourceAsStream(
								"/spritesPokemons/Front/" + p.getInfoPokedex().getNum_Pokedex() + ".png");
						if (is != null)
							imgs[i].setImage(new Image(is));
					} catch (Exception e) {
						System.out.println("[Combate] No se pudo cargar sprite slot " + i);
					}
				}

				// Si esta debilitado lo ponemos transparente para que se vea que no se puede
				// usar
				slots[i].setOpacity(p.estaDebilitado() ? 0.4 : 1.0);

				// Al hacer clic en el slot cambiamos al pokemon

				final int indice = i;
				slots[i].setOnMouseClicked(e -> cambiarPokemon(indice));

			} else {
				// Slot vacio
				nombres[i].setText("---");
				barras[i].setProgress(0);
				imgs[i].setImage(null);
				slots[i].setOnMouseClicked(null);
			}
		}
		// Aqui se muetra el cambio
		mostrarPanel(panelCambioPokemon);

	}

	private void cambiarPokemon(int indice) {
		Pokemon seleccionado = Main.miEquipo.get(indice);
		// Vemos si el pokemon esta debilitado ono

		if (seleccionado.estaDebilitado()) {
			log(seleccionado.getMote() + "no puede pelear, esta muerto");
			return;
		}

		// Comprobnacion de que noe s el mismo pokemon
		if (seleccionado == pokemonJugadorActual) {
			log(seleccionado.getMote() + "ya esta peleando");
			return;
		}

		// Camiamos el pokemon
		pokemonJugadorActual = seleccionado;
		actualizarPantalla();
		log("Pelemaos con " + seleccionado.getMote());
		mostrarPanel(panelAcciones);

	}

	@FXML
	private void handleMochila(ActionEvent event) {

		// Metemos esto para que cuando nos metamos a mochila no se pierda el combate si
		// no se quede guardado y poder seguir desde donde hemos salidlo.
		Main.venimosDeCombate = true;
		// Abirmos mochila para ver neustros objetos
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/EscenaMochila.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Mochila");
			stage.show();

		} catch (IOException e) {
			log("Error al entrar a mochila" + e.getMessage());
			e.printStackTrace();
		}

	}

	@FXML
	private void handleHuir(ActionEvent event) {
		// Aqui escribimos que queremos que salga en el lgol
		log("Has huido del combate");

		// Desoues del mensaje de huiir cambiamos a escenamenu que lo reciclamos de
		// otros controlelrs

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenu.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.setTitle("Menu Principal");
			stage.show();
		} catch (IOException e) {
			log("Error al volver al menu: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@FXML
	private void handleVolverAcciones(ActionEvent event) {
		mostrarPanel(panelAcciones);
	}

	public void calcularPokedollars(int pdGanados) {
		Entrenador e = new Entrenador();

		int pdActuales = e.getPokedollars();

		pdGanados = pdActuales / 3;

	}

	private void actualizarPantalla() {

		// Nombre y nivel del rival
		lblNombreRival.setText(pokemonRivalActual.getNombre());
		lblNivelRival.setText("Nv." + pokemonRivalActual.getNivel());

		// Barra de vida del rival
		double porcentajeRival = (double) pokemonRivalActual.getVitalidad() / pokemonRivalActual.getVitalidadMaxima();
		hpBarRival.setProgress(Math.max(0, porcentajeRival));

		// Nombre, nivel y vida del jugador
		String mote = (pokemonJugadorActual.getMote() != null && !pokemonJugadorActual.getMote().isEmpty())
				? pokemonJugadorActual.getMote()
				: pokemonJugadorActual.getNombre();
		lblNombreJugador.setText(mote);
		lblNivelJugador.setText("Nv." + pokemonJugadorActual.getNivel());
		lblPsJugador.setText(pokemonJugadorActual.getVitalidad() + "/" + pokemonJugadorActual.getVitalidadMaxima());

		// Barra de vida del jugador
		double porcentajeJugador = (double) pokemonJugadorActual.getVitalidad()
				/ pokemonJugadorActual.getVitalidadMaxima();
		hpBarJugador.setProgress(Math.max(0, porcentajeJugador));

		cargarSprite(spritePokemonRival, pokemonRivalActual, true);
		cargarSprite(spritePokemon, pokemonJugadorActual, false);

		// Turno actual
		lblTurno.setText("Turno " + turno);
	}

	private void cargarSprite(ImageView imageView, Pokemon pokemon, boolean frontal) {

		// Comprobamos que el pokemon tiene info de la pokedex
		if (pokemon.getInfoPokedex() == null)
			return;

		// Construimos la ruta segun si el gif es para nuestro pokemon o para el suyo
		String carpeta = frontal ? "spritesPokemonsGifsFront" : "spritesPokemonsGifsBack";
		String ruta = "/" + carpeta + "/" + pokemon.getInfoPokedex().getNum_Pokedex() + ".gif";

		// Cargamos el gif
		try {
			InputStream is = getClass().getResourceAsStream(ruta);
			if (is != null) {
				imageView.setImage(new Image(is));
			} else {
				System.out.println("[Combate] Sprite no encontrado: " + ruta);
			}
		} catch (Exception e) {
			System.out.println("[Combate] Error al cargar sprite: " + ruta);
		}
	}

	private void log(String mensaje) {
		// Aqui se escribe el mensaje en el textarea del combate
		if (txtLog != null) {
			txtLog.appendText(mensaje + "\n");
		}

		System.out.println("[Combate] " + mensaje);
	}

	private void mostrarPanel(Object panel) {
		// sE Oocutaln todos los paneles

		panelAcciones.setVisible(false);
		panelAcciones.setManaged(false);
		panelMovimientos.setVisible(false);
		panelMovimientos.setManaged(false);
		panelCambioPokemon.setVisible(false);
		panelCambioPokemon.setManaged(false);
		// aqui ssolo se pasa lo que hay como parametro
		if (panel instanceof HBox) {
			((HBox) panel).setVisible(true);
			((HBox) panel).setManaged(true);
		} else if (panel instanceof VBox) {
			((VBox) panel).setVisible(true);
			((VBox) panel).setManaged(true);
		}

	}
	
	@FXML
	private void handleDescansar(ActionEvent event) {
	    // Si ya se esta ejecutando un turno no hacemos nada
	    if (combateEnPausa) return;
	    combateEnPausa = true;

	    // Recuperamos toda la estamina del pokemon jugador
	    pokemonJugadorActual.setEstamina(100);
	    log(pokemonJugadorActual.getMote() + " descansa y recupera su estamina!");

	    // El rival aprovecha para atacar mientras descansamos
	    int danoRival = calcularDanoRival();
	    final int danoFinal = danoRival;

	    javafx.animation.Timeline timeline = new javafx.animation.Timeline(

	        // Segundo 1: el rival ataca
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
	            log(pokemonRivalActual.getNombre() + " aprovecho y ataco!");
	        }),

	        // Segundo 2: aplicamos el dano del rival
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), e -> {
	            pokemonJugadorActual.recibirDano(danoFinal);
	            log("Recibiste " + danoFinal + " puntos de dano!");
	            actualizarPantalla();
	        }),

	        // Segundo 3: comprobamos si el jugador se debilito
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(3), e -> {
	            if (pokemonJugadorActual.estaDebilitado()) {
	                log(pokemonJugadorActual.getMote() + " se debilito!");
	                koJugador++;

	                Pokemon siguiente = null;
	                for (Pokemon p : Main.miEquipo) {
	                    if (!p.estaDebilitado() && p != pokemonJugadorActual) {
	                        siguiente = p;
	                        break;
	                    }
	                }

	                if (siguiente != null) {
	                    pokemonJugadorActual = siguiente;
	                    actualizarPantalla();
	                    log("Vamos " + pokemonJugadorActual.getMote() + "!");
	                } else {
	                    finalizarCombate(false);
	                    return;
	                }
	            }

	            turno++;
	            lblTurno.setText("Turno " + turno);
	            log("Que hara " + pokemonJugadorActual.getMote() + "?");
	            combateEnPausa = false;
	        })
	    );

	    timeline.play();
	}

}