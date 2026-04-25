package pokemon.Controller;

import javafx.event.ActionEvent;
import pokemon.MovimientoDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import pokemon.Movimiento;
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

	// Estos son todos los elementos visuales que hemos puesto en el Scene Builder
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

	// Variables que usamos por detrás para la lógica, como la base de datos o el sonido
	private PokedexDAO pokedexDAO = new PokedexDAO();
	private AudioClip sonidoCaptura;
	private Pokemon pokemonActual;

	// Este método se ejecuta automáticamente nada más abrir la pantalla de captura
	@FXML
	public void initialize() {
		System.out.println("DEBUG: La ventana se ha cargado. Generando Pokemon");
		// Cargamos el sonido de la Pokéball desde la carpeta de recursos
		sonidoCaptura = new AudioClip(getClass().getResource("/sounds/capture.wav").toExternalForm());
		sonidoCaptura.setVolume(0.2); // Le bajamos el volumen para que no reviente los oídos

		// Llamamos al método para que aparezca un Pokémon salvaje nada más entrar
		generarPokemonAleatorio();
	}

	// Método para generar un Pokémon salvaje al azar usando los datos de la Pokédex
	public void generarPokemonAleatorio() {
		// Lo primero es resetear la pantalla: mostramos el Pokémon, ocultamos la Pokéball, etc.
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

		// Pedimos a la base de datos un ID de especie al azar
		int idPokedex = pokedexDAO.generarIdPokedexAleatorio();

		// Buscamos toda la info de esa especie en concreto
		Pokedex especie = pokedexDAO.buscarPorIdPokedex(idPokedex);
		PokemonDAO pDAO = new PokemonDAO();
		// Si hemos encontrado la especie bien en la BD, creamos el Pokémon
		if (especie != null) {

			// Ponemos su nombre en la etiqueta de arriba
			lblNombre.setText(especie.getNombreEspecie());
			

			// Le generamos un nivel aleatorio entre el 2 y el 10
			int nivelAleatorio = (int) (Math.random() * (10 - 2 + 1) + 2);
			lblNivel.setText("Niv." + nivelAleatorio);

			// Creamos el objeto Pokémon y le pasamos todos los datos que hemos generado
			this.pokemonActual = new Pokemon();
			this.pokemonActual.inicializarEstadisticasBase();

			int idGenerado = pDAO.obtenerUltimoIdGenerado();
			
			this.pokemonActual.setIdPokemon(idGenerado);
			this.pokemonActual.setInfoPokedex(especie);
			this.pokemonActual.setNombre(especie.getNombreEspecie());
			this.pokemonActual.setNivel(nivelAleatorio);
			this.pokemonActual.setMote(especie.getNombreEspecie()); // Por defecto su mote es su nombre
			// 50% de probabilidad de que sea macho o hembra
			this.pokemonActual.setSexo(Math.random() < 0.5 ? pokemon.Sexo.MACHO : pokemon.Sexo.HEMBRA);
			
			
			// Actualizamos las etiquetas de la pantalla con los datos reales
			lblNombre.setText(pokemonActual.getNombre());
			lblNivel.setText("Niv." + pokemonActual.getNivel());

			// Buscamos sus ataques básicos en la base de datos y se los ponemos
			MovimientoDAO movDAO =new MovimientoDAO();
			List<Movimiento> ataquesPred = movDAO.obtenerMovimientosDePokemon(especie.getNum_Pokedex());
			this.pokemonActual.setMovimientos(ataquesPred);

			// Ahora cargamos su GIF animado (imagen frontal)
			int numPokedex = especie.getNum_Pokedex();
			String rutaImagenFrontal = "/spritesPokemonsGifsFront/" + numPokedex + ".gif";

			if (rutaImagenFrontal != null) {
				try {
					Image img = new Image(getClass().getResourceAsStream(rutaImagenFrontal));
					pokemonImg.setImage(img); // Ponemos la imagen en el ImageView de la pantalla

				} catch (Exception e) {
					// Si falla (por ejemplo, si no existe el archivo), lo decimos por consola
					System.out.println("Error al cargar la imagen: " + rutaImagenFrontal);
					e.printStackTrace();

				}
			}

		} else {
			System.out.println("No se pudo generar el pokemon (especie null)");
		}
	}

	// Método que se ejecuta al darle al botón de Huir
	@FXML
	private void handleHuir(ActionEvent event) {
		// Nos devuelve a la pantalla del menú principal
		cambiarEscena(event, "/EscenaMenu.fxml", "Menú Principal");

		System.out.println("Has vuelto al menú principal");
	}


	// Este método salta cuando le damos al botón de "Capturar"
	@FXML
	private void handleCaptura(ActionEvent event) {

		// Usamos un Timeline para crear una pequeña animación de texto, como en los juegos de Gameboy
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(0), e -> txtLog.appendText("¡Lanzas la Pokéball con fuerza!\n")),
				new KeyFrame(Duration.seconds(1), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.seconds(2), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.seconds(3), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.seconds(4), e -> resultadoCaptura(event))); // Al final llamamos a ver si hubo suerte
		timeline.play();

	}


	// Muestra los botones de "Sí" o "No" para preguntar si queremos seguir cazando
	private void menuRepetirCaptura() {
		btnBuscarSi.setVisible(true);
		btnBuscarNo.setVisible(true);
		lblBuscar.setVisible(true);
	}

	// Método para dar feedback cuando terminamos de poner el mote
	private void moteAsignado(ActionEvent event) {
		txtLog.appendText("¡El Pokémon ha sido añadido a tu aventura! \n "); //
		// Le preguntamos si quiere capturar otro y ocultamos la caja del mote
		menuRepetirCaptura();
		txtMote.setVisible(false);
		lblMote.setVisible(false);
		btnMote.setVisible(false);

	}

	// Método que salta si le damos a "No" en buscar otro Pokémon
	private void noRepetirCaptura(ActionEvent event) {
		// Usamos otro Timeline para salir con estilo y dar tiempo a leer el mensaje final
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

	// Método puente que usa el botón desde la interfaz gráfica
	@FXML
	private void moteAsignadoNoRepetir(ActionEvent event) {
		noRepetirCaptura(event);

	}

	// Si le damos a "Sí" buscar otro, hacemos una pequeña cuenta atrás y reiniciamos el encuentro
	private void repetirCaptura() {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.millis(0),
						e -> txtLog.appendText("Has elegido buscar otro Pokémon. Buscando en...\n")),
				new KeyFrame(Duration.millis(500), e -> txtLog.appendText("3...\n")),
				new KeyFrame(Duration.millis(1000), e -> txtLog.appendText("2...\n")),
				new KeyFrame(Duration.millis(1500), e -> txtLog.appendText("1...\n")),
				new KeyFrame(Duration.millis(2000), e -> generarPokemonAleatorio())); // Generamos uno nuevo aquí
		timeline.play();
	}

	// Puente para el botón de "Sí" de la interfaz
	@FXML
	private void handleBuscarOtroPokemon() {
		repetirCaptura();
	}

	// Aquí es donde se calcula matemáticamente si hemos atrapado al bicho
	@FXML
	private void resultadoCaptura(ActionEvent event) {
		// Escondemos el GIF del Pokémon porque ya está dentro de la bola
		pokemonImg.setVisible(false);

		String nombre = lblNombre.getText(); //
		String nivel = lblNivel.getText(); //
		
		int probabilidad = 70; // Tienes un 70% de probabilidades de cazarlo siempre
		int suerte = (int) (Math.random() * 100) + 1; // Tiramos un dado de 100 caras

		// Comparamos nuestra tirada con la probabilidad base
		if (suerte <= probabilidad) {
			// Si hemos ganado la tirada: éxito total
			imgPokeball.setVisible(true); // Mostramos la bola
			sonidoCaptura.play(); // Suena el click de captura
			txtLog.appendText("...\n"); //
			txtLog.appendText("¡1... 2... 3...!\n"); //
			txtLog.appendText("¡HECHO! El " + nombre + " de " + nivel + " ha sido capturado.\n"); //

			// Bloqueamos los botones para que no intente cazarlo o huir otra vez
			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);

			// Aparece el campo para ponerle un mote personalizado
			txtMote.setVisible(true);
			lblMote.setVisible(true);
			btnMote.setVisible(true);

			txtMote.setDisable(false);
			lblMote.setDisable(false);

		} else {
			// Si la tirada falló (suerte fue mayor a 70), el Pokémon escapa
			txtLog.appendText("...\n"); //
			txtLog.appendText("¡Oh no! El Pokémon se ha escapado de la bola.\n"); //
			txtLog.appendText("¡Ha huido!\n"); //
			// Bloqueamos los botones de pelea
			btnCapturar.setDisable(true);
			btnHuir.setDisable(true);

			// Y sacamos el menú para ver si quiere seguir cazando
			menuRepetirCaptura();

		}

	}

	// Función típica de JavaFX para moverte de una pantalla a otra pasando el FXML
	private void cambiarEscena(ActionEvent event, String fxml, String titulo) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource(fxml)); //

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //

			Scene scene = new Scene(root); //
			stage.setScene(scene); //
			stage.setTitle(titulo); //
			stage.setMaximized(false); //
			stage.show(); //

		} catch (IOException e) {
			// Si falla la carga del archivo, saltará un error
			e.printStackTrace();
			if (errorLabel != null) {
				errorLabel.setText("Error al cargar la escena"); //
			}
		}
	}

	// Cuando el usuario escribe el mote y le da al botón de guardar
	@FXML
	private void handleGuardarMote(ActionEvent event) {

		// Preparamos la base de datos para meter al bicho nuevo
		PokemonDAO pDAO = new PokemonDAO(); //

		int destinoUbicacion; //
		String mote = txtMote.getText().trim(); // Quitamos espacios en blanco

		// Si le ha dado a guardar sin escribir nada, usamos su nombre de especie
		if (mote.isEmpty()) {
			mote = pokemonActual.getNombre(); //
			txtLog.appendText("No has añadido ningún mote, el pokémon se unirá a tu equipo como: " + pokemonActual.getNombre()+"\n"); //
			txtLog.appendText("\n"); //
		}
		

		pokemonActual.setMote(mote); // Guardamos el mote definitivo en el objeto
		
		// Comprobamos si el entrenador tiene hueco en su equipo principal (máximo 6)
		if (Main.miEquipo.size() < 6) {

			// Si hay hueco, su ubicación es "1" (Equipo) y se añade a la lista local
			destinoUbicacion = 1;
			Main.miEquipo.add(pokemonActual);
			txtLog.appendText("¡El pokemon se ha unido a tu equipo como: " + mote + "!\n"); //
		} else {
			// Si el equipo está lleno, su ubicación es "0" (PC) y se va al almacenamiento
			destinoUbicacion = 0;
			Main.pcPokemon.add(pokemonActual);
			txtLog.appendText("Espacio insuficiente en el equipo. ¡El pokemon ha sido enviaado a la caja del PC como: "
					+ mote + "!\n"); //
		}

		// Y finalmente hacemos el INSERT en MySQL pasándole todos los datos
		pDAO.guardarPokemon(pokemonActual, Main.entrenadorLogueado.getId_Entrenador(), destinoUbicacion); //
		
		// Un parche para recuperar qué ID le ha puesto MySQL y darle sus ataques
		int idPkemonBD = pDAO.obtenerUltimoIdGenerado(); //
		pDAO.asignarAtaquesPredetermiandos(idPkemonBD, pokemonActual.getInfoPokedex().getNum_Pokedex()); //

		// Terminamos mostrando el menú para ver si quiere seguir cazando
		txtMote.clear();
		moteAsignado(event); //
		

	}

}