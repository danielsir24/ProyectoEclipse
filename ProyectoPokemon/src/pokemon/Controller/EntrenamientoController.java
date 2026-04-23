package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import pokemon.EntrenadorDAO;
import pokemon.Main;
import pokemon.Pokemon;
import pokemon.PokemonDAO;

public class EntrenamientoController {

    @FXML private ComboBox<String> comboPokemon;
    @FXML private ComboBox<String> comboEntrenamiento;
    @FXML private Label lblPokedollars;
    @FXML private TextArea txtResultado;
    @FXML private Button btnEntrenar;

    private ArrayList<Pokemon> equipoActual;

    @FXML
    public void initialize() {
        equipoActual = Main.miEquipo;
        lblPokedollars.setText("Tus Pokedollars: " + Main.entrenadorLogueado.getPokedollars() + " ₽");

        //Cargar el equipo en el desplegable
        for (Pokemon p : equipoActual) {
            String nombreMostrar = p.getMote() != null && !p.getMote().isEmpty() ? p.getMote() : p.getNombre();
            comboPokemon.getItems().add(nombreMostrar + " (Nv. " + p.getNivel() + ")");
        }

        //Cargar los tipos de entrenamiento según el requisito
        comboEntrenamiento.getItems().addAll(
            "EntrenamientoLevel1 (20xNivel Pokedollars) [+Def, +DefEsp, +Vit]",
            "EntrenamientoLevel2 (30xNivel Pokedollars) [+Atq, +AtqEsp, +Vel]",
            "EntrenamientoLevel3 (40xNivel Pokedollars) [+Vel, +Atq, +Def, +Vit]",
            "EntrenamientoLevel4 (40xNivel Pokedollars) [+Vel, +AtqEsp, +DefEsp, +Vit]"
        );
    }

    @FXML
    private void handleEntrenar(ActionEvent event) {
        int indicePokemon = comboPokemon.getSelectionModel().getSelectedIndex();
        int indiceTipo = comboEntrenamiento.getSelectionModel().getSelectedIndex();

        if (indicePokemon < 0 || indiceTipo < 0) {
            txtResultado.setText("Por favor, selecciona un Pokémon y un tipo de entrenamiento.");
            return;
        }

        Pokemon pElegido = equipoActual.get(indicePokemon);
        int coste = 0;

        //Calcular coste según el tipo de entrenamiento
        switch (indiceTipo) {
            case 0: coste = 20 * pElegido.getNivel(); break; // Entrenamiento Level 1
            case 1: coste = 30 * pElegido.getNivel(); break; // Entrenamiento Level 2
            case 2: coste = 40 * pElegido.getNivel(); break; // Entrenamiento Level 3
            case 3: coste = 40 * pElegido.getNivel(); break; // Entrenamiento Level 4
        }

        //Comprobar dinero
        if (!Main.entrenadorLogueado.gastarPokedollars(coste)) {
            txtResultado.setText("¡No tienes suficientes Dinero! Cuesta " + coste + " Pokedollars.");
            return;
        }

        //Aplicar las mejoras (+5 puntos según requisito)
        switch (indiceTipo) {
            case 0: //EntrenamientoLevel1
                pElegido.setDefensa(pElegido.getDefensa() + 5);
                pElegido.setDefensaEspecial(pElegido.getDefensaEspecial() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
            case 1: //EntrenamientoLevel2
                pElegido.setAtaque(pElegido.getAtaque() + 5);
                pElegido.setAtaqueEspecial(pElegido.getAtaqueEspecial() + 5);
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                break;
            case 2: //EntrenamientoLevel3
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                pElegido.setAtaque(pElegido.getAtaque() + 5);
                pElegido.setDefensa(pElegido.getDefensa() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
            case 3: //EntrenamientoLevel4
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                pElegido.setAtaqueEspecial(pElegido.getAtaqueEspecial() + 5);
                pElegido.setDefensaEspecial(pElegido.getDefensaEspecial() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
        }
        
        //Curamos al Pokemon la vitalidad nueva que ha ganado
        pElegido.setVitalidad(pElegido.getVitalidadMaxima());

        //Guardar cambios en Base de Datos
        PokemonDAO pDAO = new PokemonDAO();
        pDAO.actualizarPokemon(pElegido);
        
        EntrenadorDAO eDAO = new EntrenadorDAO();
        eDAO.actualizarPokedollars(Main.entrenadorLogueado);

        //Actualizar UI
        lblPokedollars.setText("Tus Pokedollars: " + Main.entrenadorLogueado.getPokedollars() + " ₽");
        txtResultado.setText("¡Entrenamiento completado! Has gastado " + coste + " Pokedollars " + pElegido.getNombre() + " se ha vuelto más fuerte.");
    }

    @FXML
    private void volverAlMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}