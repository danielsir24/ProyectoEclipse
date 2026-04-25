package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pokemon.Estado;
import pokemon.Main;
import pokemon.Pokedex;
import pokemon.Pokemon;
import pokemon.PokemonDAO;
import pokemon.Sexo;
import pokemon.Tipo;

public class CrianzaController {

	// Elementos de la interfaz para mostrar las imágenes y nombres de los padres
	@FXML
	private ImageView imageMacho;
	@FXML
	private ImageView imageHembra;
	@FXML
	private Label nombreMacho;
	@FXML
	private Label nombreHembra;

	// Variables para guardar los dos Pokémon que el usuario elija para criar
	private Pokemon pokemonMacho = null;
	private Pokemon pokemonHembra = null;
	private final Random random = new Random();

	// ══════════════════════════════════════════════
	// INICIALIZACIÓN
	// ══════════════════════════════════════════════

	@FXML
	public void initialize() {
		// Al cargar la ventana, refrescamos el equipo desde la base de datos para tener los datos actualizados
		try {
			PokemonDAO pokemonDAO = new PokemonDAO();
			Main.miEquipo = pokemonDAO.obtenerEquipo(Main.entrenadorLogueado.getId_Entrenador());
			System.out.println("[Crianza] Equipo recargado: " + Main.miEquipo.size() + " Pokemon.");
			for (Pokemon p : Main.miEquipo) {
				System.out.println("  - " + p.getMote() + " | Sexo: " + p.getSexo());
			}
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println("Mensaje: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ══════════════════════════════════════════════
	// SELECCIÓN DE POKÉMON
	// ══════════════════════════════════════════════

	// Abre la lista de selección filtrando solo por machos
	@FXML
	private void seleccionarMacho(ActionEvent event) {
		Pokemon elegido = abrirVentanaSeleccion(Sexo.MACHO, pokemonHembra);
		if (elegido == null)
			return;
		pokemonMacho = elegido;
		// Ponemos el mote (o el nombre si no tiene mote) en la etiqueta
		nombreMacho.setText(pokemonMacho.getMote() != null && !pokemonMacho.getMote().isEmpty() ? pokemonMacho.getMote()
				: pokemonMacho.getNombre());
		cargarSprite(imageMacho, pokemonMacho);
		System.out.println("Macho seleccionado: " + pokemonMacho.getNombre());
	}

	// Abre la lista de selección filtrando solo por hembras
	@FXML
	private void seleccionarHembra(ActionEvent event) {
		Pokemon elegido = abrirVentanaSeleccion(Sexo.HEMBRA, pokemonMacho);
		if (elegido == null)
			return;
		pokemonHembra = elegido;
		nombreHembra
				.setText(pokemonHembra.getMote() != null && !pokemonHembra.getMote().isEmpty() ? pokemonHembra.getMote()
						: pokemonHembra.getNombre());
		cargarSprite(imageHembra, pokemonHembra);
		System.out.println("Hembra seleccionada: " + pokemonHembra.getNombre());
	}

	// Crea una ventana emergente (Stage modal) para elegir un Pokémon del equipo o del PC
	private Pokemon abrirVentanaSeleccion(Sexo sexo, Pokemon excluir) {
		List<Pokemon> candidatos = new ArrayList<>();
		// Filtramos por sexo y evitamos que el mismo Pokémon sea padre y madre a la vez
		if (Main.miEquipo != null) {
			for (Pokemon p : Main.miEquipo) {
				if (p.getSexo() == sexo && (excluir == null || p.getIdPokemon() != excluir.getIdPokemon())) {
					candidatos.add(p);
				}
			}
		}
		if (Main.pcPokemon != null) {
			for (Pokemon p : Main.pcPokemon) {
				if (p.getSexo() == sexo && (excluir == null || p.getIdPokemon() != excluir.getIdPokemon())) {
					candidatos.add(p);
				}
			}
		}

		if (candidatos.isEmpty()) {
			mostrarMensaje("No tienes ningun Pokemon " + sexo.name() + " disponible.");
			return null;
		}

		Pokemon[] resultado = { null };

		// Configuramos la ventana visualmente (estilos, scroll y botones)
		Stage ventana = new Stage();
		ventana.initModality(Modality.APPLICATION_MODAL);
		ventana.setTitle("Elige un Pokemon " + (sexo == Sexo.MACHO ? "Macho" : "Hembra"));
		ventana.setResizable(false);

		VBox root = new VBox(10);
		root.setStyle("-fx-background-color: #e8f4fc; -fx-padding: 16;");
		root.setAlignment(Pos.TOP_CENTER);

		Label titulo = new Label("Selecciona un Pokemon " + (sexo == Sexo.MACHO ? "MACHO" : "HEMBRA"));
		titulo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 0 0 8 0;");
		root.getChildren().add(titulo);

		// Creamos una "fila" clicable por cada Pokémon candidato
		for (Pokemon p : candidatos) {
			HBox fila = new HBox(12);
			fila.setAlignment(Pos.CENTER_LEFT);
			fila.setStyle("-fx-background-color: white; -fx-background-radius: 8; "
					+ "-fx-border-color: #cccccc; -fx-border-radius: 8; "
					+ "-fx-padding: 8 14 8 10; -fx-cursor: hand;");

			ImageView sprite = new ImageView();
			sprite.setFitWidth(48);
			sprite.setFitHeight(48);
			sprite.setPreserveRatio(true);
			if (p.getInfoPokedex() != null) {
				try {
					Image img = new Image(getClass().getResourceAsStream(
							"/spritesPokemons/Front/" + p.getInfoPokedex().getNum_Pokedex() + ".png"));
					sprite.setImage(img);
				} catch (Exception ignored) {
				}
			}

			String mote = (p.getMote() != null && !p.getMote().isEmpty()) ? p.getMote() : p.getNombre();
			String origen = Main.miEquipo != null && Main.miEquipo.contains(p) ? "[Equipo]" : "[PC]";
			String simbolo = sexo == Sexo.MACHO ? "♂" : "♀";
			Label info = new Label(
					mote + " " + simbolo + "  Nv." + p.getNivel() + "  |  " + p.getNombre() + "  " + origen);
			info.setStyle("-fx-font-size: 12px;");

			// Al hacer clic, guardamos el Pokémon elegido y cerramos el modal
			final Pokemon pFinal = p;
			fila.setOnMouseClicked(e -> {
				resultado[0] = pFinal;
				ventana.close();
			});
			// Efectos visuales de Hover (pasar el ratón por encima)
			fila.setOnMouseEntered(e -> fila.setStyle("-fx-background-color: #d0eaff; -fx-background-radius: 8; "
					+ "-fx-border-color: #4aabff; -fx-border-radius: 8; "
					+ "-fx-padding: 8 14 8 10; -fx-cursor: hand;"));
			fila.setOnMouseExited(e -> fila.setStyle("-fx-background-color: white; -fx-background-radius: 8; "
					+ "-fx-border-color: #cccccc; -fx-border-radius: 8; "
					+ "-fx-padding: 8 14 8 10; -fx-cursor: hand;"));

			fila.getChildren().addAll(sprite, info);
			root.getChildren().add(fila);
		}

		Button btnCancelar = new Button("Cancelar");
		btnCancelar.setStyle("-fx-background-color: #cc4444; -fx-text-fill: white; "
				+ "-fx-background-radius: 6; -fx-padding: 6 20 6 20; -fx-cursor: hand;");
		btnCancelar.setOnAction(e -> ventana.close());
		root.getChildren().add(btnCancelar);

		ScrollPane scroll = new ScrollPane(root);
		scroll.setFitToWidth(true);
		scroll.setStyle("-fx-background-color: transparent;");

		Scene escena = new Scene(scroll, 420, Math.min(500, 80 + candidatos.size() * 74));
		ventana.setScene(escena);
		ventana.showAndWait();

		return resultado[0];
	}

	// ══════════════════════════════════════════════
	// CRIAR
	// ══════════════════════════════════════════════

	// Lógica principal para cruzar a los dos padres y generar el bebé
	@FXML
	private void criar(ActionEvent event) {
		if (pokemonMacho == null || pokemonHembra == null) {
			mostrarMensaje("Selecciona un Pokemon MACHO y una HEMBRA antes de criar.");
			return;
		}
		// Comprobamos que ambos tengan puntos de fertilidad (intentos de crianza)
		if (pokemonMacho.getFertilidad() <= 0 || pokemonHembra.getFertilidad() <= 0) {
			mostrarMensaje("Uno de los Pokemon no tiene fertilidad suficiente para criar.");
			return;
		}

		// Creamos el objeto del bebé heredando cosas de los padres
		Pokemon bebe = generarBebe(pokemonMacho, pokemonHembra);

		// Gastamos un punto de fertilidad a cada padre
		pokemonMacho.setFertilidad(pokemonMacho.getFertilidad() - 1);
		pokemonHembra.setFertilidad(pokemonHembra.getFertilidad() - 1);

		// Lo guardamos en el equipo si hay sitio (máximo 6), si no, lo mandamos al PC
		int ubicacion;
		if (Main.miEquipo.size() < 6) {
			ubicacion = 1;
			Main.miEquipo.add(bebe);
		} else {
			ubicacion = 0;
			Main.pcPokemon.add(bebe);
		}

		// Guardamos el nuevo Pokémon en la base de datos
		PokemonDAO pokemonDAO = new PokemonDAO();
		boolean guardado = pokemonDAO.guardarPokemon(bebe, Main.entrenadorLogueado.getId_Entrenador(), ubicacion);

		if (guardado) {
			String destino = ubicacion == 1 ? "se ha unido a tu equipo" : "se ha enviado al PC";
			System.out.println("Bebe guardado: " + bebe.getMote() + " | ubicacion=" + ubicacion);
			mostrarMensaje("Ha nacido " + bebe.getMote() + " y " + destino + "!" + " ATK:" + bebe.getAtaque() + " DEF:"
					+ bebe.getDefensa() + " VEL:" + bebe.getVelocidad());
			cargarSprite(imageMacho, bebe);
			nombreMacho.setText("Ha nacido " + bebe.getMote() + "!");
		} else {
			mostrarMensaje("Error al guardar el Pokemon bebe. Intentalo de nuevo.");
		}

		// Limpiamos la selección para la siguiente crianza
		pokemonMacho = null;
		pokemonHembra = null;
		nombreHembra.setText("");
		imageHembra.setImage(null);
	}

	// ══════════════════════════════════════════════
	// GENERACIÓN DEL BEBÉ
	// ══════════════════════════════════════════════

	// Aquí calculamos las estadísticas y el nombre del nuevo Pokémon
	private Pokemon generarBebe(Pokemon padre, Pokemon madre) {
		Pokemon bebe = new Pokemon();

		// La especie siempre es la misma que la de la madre
		Pokedex especieBebe = madre.getInfoPokedex();
		bebe.setInfoPokedex(especieBebe);
		bebe.setNombre(especieBebe != null ? especieBebe.getNombreEspecie() : "Huevo");

		// Creamos un mote chulo mezclando los nombres de los padres
		bebe.setMote(generarNombreFusion(padre.getNombre(), madre.getNombre()));

		// Las estadísticas son la media de los padres más/menos un toque de azar
		bebe.setAtaque(promedioConVariacion(padre.getAtaque(), madre.getAtaque()));
		bebe.setDefensa(promedioConVariacion(padre.getDefensa(), madre.getDefensa()));
		bebe.setAtaqueEspecial(promedioConVariacion(padre.getAtaqueEspecial(), madre.getAtaqueEspecial()));
		bebe.setDefensaEspecial(promedioConVariacion(padre.getDefensaEspecial(), madre.getDefensaEspecial()));
		bebe.setVelocidad(promedioConVariacion(padre.getVelocidad(), madre.getVelocidad()));

		int vitalidadBase = promedioConVariacion(padre.getVitalidad(), madre.getVitalidad());
		bebe.setVitalidad(vitalidadBase);
		bebe.setVitalidadMaxima(vitalidadBase);

		// Datos por defecto para un recién nacido
		bebe.setNivel(1);
		bebe.setExperiencia(0);
		// La fertilidad del bebé es la media de los padres menos 1
		bebe.setFertilidad(Math.max(1, (padre.getFertilidad() + madre.getFertilidad()) / 2 - 1));
		bebe.setSexo(random.nextBoolean() ? Sexo.MACHO : Sexo.HEMBRA);
		bebe.setEstado(Estado.NORMAL);
		bebe.setObjeto(null);
		bebe.setUbicacion(0);

		// Hereda los tipos de la especie de la madre
		if (madre.getTipos() != null && !madre.getTipos().isEmpty()) {
			bebe.setTipos(new ArrayList<>(madre.getTipos()));
		} else if (especieBebe != null) {
			List<Tipo> tiposBebe = new ArrayList<>();
			if (especieBebe.getTipo1() != null) {
				try {
					tiposBebe.add(Tipo.valueOf(especieBebe.getTipo1().toUpperCase()));
				} catch (Exception ignored) {
				}
			}
			if (especieBebe.getTipo2() != null) {
				try {
					tiposBebe.add(Tipo.valueOf(especieBebe.getTipo2().toUpperCase()));
				} catch (Exception ignored) {
				}
			}
			bebe.setTipos(tiposBebe);
		}

		System.out.println(
				"Bebe generado: " + bebe.getMote() + " (" + bebe.getNombre() + ")" + " | ATK:" + bebe.getAtaque()
						+ " DEF:" + bebe.getDefensa() + " VEL:" + bebe.getVelocidad() + " | Sexo: " + bebe.getSexo());
		return bebe;
	}

	// Mezcla los nombres de los padres (mitad y mitad)
	private String generarNombreFusion(String nombrePadre, String nombreMadre) {
		if (nombrePadre == null || nombrePadre.isEmpty())
			nombrePadre = "Poke";
		if (nombreMadre == null || nombreMadre.isEmpty())
			nombreMadre = "Mon";
		int mitadPadre = (int) Math.ceil(nombrePadre.length() / 2.0);
		int mitadMadre = nombreMadre.length() / 2;
		String fusion = nombrePadre.substring(0, mitadPadre) + nombreMadre.substring(mitadMadre);
		// Capitalizamos el nombre (primera mayúscula, resto minúsculas)
		return fusion.substring(0, 1).toUpperCase() + fusion.substring(1).toLowerCase();
	}

	// Calcula el promedio y le suma un valor entre -2 y +2 para que no sea siempre igual
	private int promedioConVariacion(int statPadre, int statMadre) {
		int promedio = (statPadre + statMadre) / 2;
		int variacion = random.nextInt(5) - 2;
		return Math.max(1, promedio + variacion);
	}

	// ══════════════════════════════════════════════
	// UTILIDADES
	// ══════════════════════════════════════════════

	// Carga la imagen del Pokémon en un ImageView pasando su número de Pokédex
	private void cargarSprite(ImageView imageView, Pokemon pokemon) {
		if (pokemon.getInfoPokedex() == null)
			return;
		String rutaSprite = "/spritesPokemons/Front/" + pokemon.getInfoPokedex().getNum_Pokedex() + ".png";
		try {
			Image img = new Image(getClass().getResourceAsStream(rutaSprite));
			imageView.setImage(img);
		} catch (Exception e) {
			System.out.println("No se pudo cargar el sprite de " + pokemon.getNombre());
		}
	}

	private void mostrarMensaje(String mensaje) {
		System.out.println("[Crianza] " + mensaje);
	}

	// ══════════════════════════════════════════════
	// VOLVER AL MENÚ
	// ══════════════════════════════════════════════

	// Método para cerrar esta pantalla y volver al menú principal de JavaFX
	@FXML
	private void volverAlMenu(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenu.fxml"));
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Menu Principal");
			stage.setMaximized(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error al volver al menu: " + e.getMessage());
		}
	}
}