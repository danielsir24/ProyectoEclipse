package pokemon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

	public static ArrayList<Pokemon> miEquipo = new ArrayList<>();
	public static ArrayList<Pokemon> pcPokemon = new ArrayList<>();

	public static Entrenador entrenadorLogueado;
	public static Entrenador rivalActual;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Cargar escena de Login
		Parent root = FXMLLoader.load(getClass().getResource("/EscenaLogin.fxml"));
		primaryStage.setTitle("Login Pokémon");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root));
		primaryStage.setMaximized(false);
		primaryStage.show();

		// Musica del juego
		Musica.iniciar("/sounds/MusicaPokemon.wav");

	}

	public static void main(String[] args) {
		launch(args);
	}
}