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

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import pokemon.Estado;
import pokemon.Main;
import pokemon.Movimiento;
import pokemon.Pokedex;
import pokemon.PokedexDAO;
import pokemon.Pokemon;
import pokemon.Tipo;

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

		// TODO: Coger el primer pokemon vivo del equipo del jugador
		for (Pokemon p : Main.miEquipo) {
			if (!p.estaDebilitado()) {
				pokemonJugadorActual = p;
				break;
			}
		}

		//Si no hay pokemon vivos no se puede lucchar
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
	        if (p.getNivel() < nivelMin) nivelMin = p.getNivel();
	        if (p.getNivel() > nivelMax) nivelMax = p.getNivel();
	    }
	    int nivelRival = nivelMin + random.nextInt(Math.max(1, nivelMax - nivelMin + 1));
	    pokemonRivalActual.setNivel(nivelRival);
	    
	    // Stats proporcionales al nivel
	    int base = 10 + nivelRival * 2;
	    pokemonRivalActual.setVitalidad(base + random.nextInt(10));
	    pokemonRivalActual.setVitalidadMaxima(pokemonRivalActual.getVitalidad());
	    pokemonRivalActual.setAtaque(base + random.nextInt(8));
	    pokemonRivalActual.setDefensa(base + random.nextInt(8));
	    pokemonRivalActual.setEstado(Estado.NORMAL);

	    // Actualizamos la pantalla con los datos de los dos pokemon
	    actualizarPantalla();

	    // Mensaje de inicio en el log
	    log("Un " + pokemonRivalActual.getNombre() + " salvaje aparecio!");
	    log("Que hara " + pokemonJugadorActual.getMote() + "?");
	}
	


	@FXML
	private void handleLuchar(ActionEvent event) {

		// TODO: Mostrar el panelMovimientos
		// y ocultar el panelAcciones

		// TODO: Poner los nombres de los movimientos
		// en los botones btnMovimiento1, 2, 3, 4
		// Sacalos de pokemonJugadorActual.getMovimientos()
	}

	// ══════════════════════════════════════════════════
	// BOTONES DE MOVIMIENTO - cada uno llama a
	// ejecutarMovimiento() con el indice del movimiento
	// ══════════════════════════════════════════════════

	@FXML
	private void handleMovimiento1(ActionEvent event) {

	}

	@FXML
	private void handleMovimiento2(ActionEvent event) {

	}

	@FXML
	private void handleMovimiento3(ActionEvent event) {

	}

	@FXML
	private void handleMovimiento4(ActionEvent event) {

	}

	private void ejecutarMovimiento(int indice) {

		// TODO: Comprobar que combateEnPausa es false
		// Si es true, no hacemos nada (ya se esta ejecutando un turno)

		// TODO: Poner combateEnPausa = true para bloquear
		// los botones mientras se ejecuta el turno

		// TODO: Volver al panelAcciones (ocultar panelMovimientos)

		// TODO: Calcular el dano que hace el jugador al rival
		// usando calcularDano()

		// TODO: Calcular el dano que hace el rival al jugador
		// usando calcularDanoRival()

		// TODO: Usar un Timeline para mostrar los mensajes
		// con un retardo de 1 segundo entre cada uno.
		// El Timeline debe hacer esto en orden:
		// - Segundo 0: mostrar "X uso Y!"
		// - Segundo 1: aplicar dano al rival y actualizar su barra
		// - Segundo 2: comprobar si el rival se debilito
		// - Segundo 3: si sigue vivo, el rival ataca
		// - Segundo 4: comprobar si el jugador se debilito
		// - Al final: subir el turno y poner combateEnPausa = false
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

		// TODO: Rellenar los slots con los datos del equipo
		// usando Main.miEquipo (nombre, barra de vida, sprite)

		// TODO: Mostrar el panelCambioPokemon
		// y ocultar el panelAcciones

		// TODO: Asignar un setOnMouseClicked a cada slot
		// para que al hacer clic se llame a cambiarPokemon(indice)
	}

	private void cambiarPokemon(int indice) {
		Pokemon seleccionado = Main.miEquipo.get(indice);
		//Vemos si el pokemon esta debilitado ono
		
		if (seleccionado.estaDebilitado()) {
			log(seleccionado.getMote() + "no puede pelear, esta muerto");
			return;
		}
		
		//Comprobnacion de que noe s el mismo pokemon
		if (seleccionado == pokemonJugadorActual) {
			log(seleccionado.getMote() + "ya esta peleando");
			return;
		}
		
		//Camiamos el pokemon
		pokemonJugadorActual = seleccionado;
		actualizarPantalla();
		log("Pelemaos con "+ seleccionado.getMote());
		mostrarPanel(panelAcciones);

	}



	@FXML
	private void handleMochila(ActionEvent event) {
		//Metemos esto para que cuando nos metamos a mochila no se pierda el combate si no se quede guardado y poder seguir desde donde hemos salidlo.
		 Main.venimosDeCombate = true;
		//Abirmos mochila para ver neustros objetos
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

	// ══════════════════════════════════════════════════
	// BOTON VOLVER - vuelve al panel de acciones
	// principal desde el panel de movimientos
	// o desde el panel de cambio de pokemon
	// ══════════════════════════════════════════════════

	@FXML
	private void handleVolverAcciones(ActionEvent event) {

		// TODO: Mostrar el panelAcciones
		// y ocultar los demas paneles
	}

	// ══════════════════════════════════════════════════
	// FINALIZAR COMBATE - se llama cuando alguno de
	// los dos llega a 6 KO o cuando no quedan pokemon
	// ══════════════════════════════════════════════════

	private void finalizarCombate(boolean ganoJugador) {

		// TODO: Si ganoJugador es true:
		// - Calcular experiencia con la formula:
		// (nivelJugador + nivelRival * 10) / 4
		// - Sumarle la experiencia al pokemon jugador
		// - El rival pierde 1/3 de sus pokedollars
		// - Escribir en el log "Ganaste el combate!"

		// TODO: Si ganoJugador es false:
		// - El jugador pierde 1/3 de sus pokedollars
		// - Escribir en el log "Perdiste el combate..."

		// TODO: Desactivar todos los botones para que
		// no se pueda seguir jugando
		// panelAcciones.setDisable(true);
	}

	// ══════════════════════════════════════════════════
	// ACTUALIZAR PANTALLA - refresca todos los labels,
	// barras de vida y sprites con los datos actuales
	// ══════════════════════════════════════════════════

	private void actualizarPantalla() {

		// TODO: Actualizar el label del nombre del rival
		// lblNombreRival.setText(pokemonRivalActual.getNombre());

		// TODO: Actualizar el label del nivel del rival
		// lblNivelRival.setText("Nv." + pokemonRivalActual.getNivel());

		// TODO: Actualizar la barra de vida del rival
		// hpBarRival.setProgress((double) vida / vidaMaxima);

		// TODO: Lo mismo para el jugador con sus labels y barra

		// TODO: Cargar los sprites (gifs) de los pokemon
		// cargarSprite(spriteRival, pokemonRivalActual, true);
		// cargarSprite(spriteJugador, pokemonJugadorActual, false);

		// TODO: Actualizar el label del turno
		// lblTurno.setText("Turno " + turno);
	}

	// ══════════════════════════════════════════════════
	// CARGAR SPRITE - carga el gif del pokemon en el
	// ImageView. Si frontal es true carga el gif de
	// frente, si es false carga el de espalda
	// ══════════════════════════════════════════════════

	private void cargarSprite(ImageView imageView, Pokemon pokemon, boolean frontal) {

		// TODO: Construir la ruta del gif segun si es frontal o espalda
		// Frontal: "/spritesPokemonsGifsFront/" + numPokedex + ".gif"
		// Espalda: "/spritesPokemonsGifsBack/" + numPokedex + ".gif"

		// TODO: Cargar la imagen con getClass().getResourceAsStream(ruta)
		// y asignarla al imageView con imageView.setImage(new Image(is))

		// TODO: Manejar el caso de que la imagen no exista (try/catch)
	}

	// ══════════════════════════════════════════════════
	// METODO LOG - escribe un mensaje en el TextArea
	// del combate y tambien lo imprime en consola
	// ══════════════════════════════════════════════════

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
}