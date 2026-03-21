package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import pokemon.PokedexDAO;
import pokemon.Pokedex;
import java.io.File;
import javafx.scene.image.Image;

public class CapturaController {

	@FXML
	private Label errorLabel;
	
	@FXML
	private Button btnCapturar;

	@FXML
	private Button btnHuir;

	@FXML
	private ImageView pokemonImg;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblNivel;

	private PokedexDAO pokedexDAO = new PokedexDAO();
	
	@FXML
    public void initialize() {
        System.out.println("DEBUG: La ventana se ha cargado. Generando Pokemon");
        generarPokemonAleatorioCaptura();}

	public void generarPokemonAleatorioCaptura() {
		int idPokedex = pokedexDAO.generarIdPokedexAleatorio();

		Pokedex especie = pokedexDAO.buscarPorIdPokedex(idPokedex);

		if (especie != null) {

			lblNombre.setText(especie.getNombreEspecie());

			int nivelAleatorio = (int) (Math.random() * (10 - 2 + 1) + 2);
			lblNivel.setText("Niv." + nivelAleatorio);
			
			
			String rutaImagenFrontal = especie.getImg_Frontal();

			if (rutaImagenFrontal != null) {
				try {
					File file = new File(rutaImagenFrontal);
					Image img = new Image(file.toURI().toString());

					pokemonImg.setImage(img);

				} catch (Exception e) {
					System.out.println("Error al cargar la imagen: " + rutaImagenFrontal);
					e.printStackTrace();

				}
			} else {
				pokemonImg.setImage(null);

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
