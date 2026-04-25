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

    // Elementos de la interfaz para elegir Pokémon, tipo de entrenamiento y ver el saldo
    @FXML private ComboBox<String> comboPokemon;
    @FXML private ComboBox<String> comboEntrenamiento;
    @FXML private Label lblPokedollars;
    @FXML private TextArea txtResultado;
    @FXML private Button btnEntrenar;

    // Lista para manejar los Pokémon que el entrenador lleva encima
    private ArrayList<Pokemon> equipoActual;

    // Se ejecuta al abrir la pantalla para preparar los desplegables y el dinero
    @FXML
    public void initialize() {
        equipoActual = Main.miEquipo;
        lblPokedollars.setText("Tus Pokedollars: " + Main.entrenadorLogueado.getPokedollars() + " ₽");

        // Recorremos el equipo para llenar el ComboBox con el mote o nombre y su nivel
        for (Pokemon p : equipoActual) {
            String nombreMostrar = p.getMote() != null && !p.getMote().isEmpty() ? p.getMote() : p.getNombre();
            comboPokemon.getItems().add(nombreMostrar + " (Nv. " + p.getNivel() + ")");
        }

        // Cargamos las 4 opciones de entrenamiento con sus beneficios y costes
        comboEntrenamiento.getItems().addAll(
            "EntrenamientoLevel1 (20xNivel Pokedollars) [+Def, +DefEsp, +Vit]",
            "EntrenamientoLevel2 (30xNivel Pokedollars) [+Atq, +AtqEsp, +Vel]",
            "EntrenamientoLevel3 (40xNivel Pokedollars) [+Vel, +Atq, +Def, +Vit]",
            "EntrenamientoLevel4 (40xNivel Pokedollars) [+Vel, +AtqEsp, +DefEsp, +Vit]"
        );
    }

    // Método principal que se activa al pulsar el botón "Entrenar"
    @FXML
    private void handleEntrenar(ActionEvent event) {
        // Obtenemos qué Pokémon y qué entrenamiento se han seleccionado
        int indicePokemon = comboPokemon.getSelectionModel().getSelectedIndex();
        int indiceTipo = comboEntrenamiento.getSelectionModel().getSelectedIndex();

        // Validación básica por si no han seleccionado nada
        if (indicePokemon < 0 || indiceTipo < 0) {
            txtResultado.setText("Por favor, selecciona un Pokémon y un tipo de entrenamiento.");
            return;
        }

        Pokemon pElegido = equipoActual.get(indicePokemon);
        int coste = 0;

        // Calculamos el precio multiplicando el factor del entrenamiento por el nivel del Pokémon
        switch (indiceTipo) {
            case 0: coste = 20 * pElegido.getNivel(); break; // Entrenamiento Level 1
            case 1: coste = 30 * pElegido.getNivel(); break; // Entrenamiento Level 2
            case 2: coste = 40 * pElegido.getNivel(); break; // Entrenamiento Level 3
            case 3: coste = 40 * pElegido.getNivel(); break; // Entrenamiento Level 4
        }

        // Comprobamos si el entrenador tiene dinero suficiente antes de seguir
        if (!Main.entrenadorLogueado.gastarPokedollars(coste)) {
            txtResultado.setText("¡No tienes suficientes Dinero! Cuesta " + coste + " Pokedollars.");
            return;
        }

        // Aplicamos la subida de +5 puntos en los atributos correspondientes según el nivel elegido
        switch (indiceTipo) {
            case 0: // EntrenamientoLevel1 enfocada a defensa y vida
                pElegido.setDefensa(pElegido.getDefensa() + 5);
                pElegido.setDefensaEspecial(pElegido.getDefensaEspecial() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
            case 1: // EntrenamientoLevel2 enfocada a ataque y velocidad
                pElegido.setAtaque(pElegido.getAtaque() + 5);
                pElegido.setAtaqueEspecial(pElegido.getAtaqueEspecial() + 5);
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                break;
            case 2: // EntrenamientoLevel3 equilibrado (Vel, Atq, Def, Vit)
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                pElegido.setAtaque(pElegido.getAtaque() + 5);
                pElegido.setDefensa(pElegido.getDefensa() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
            case 3: // EntrenamientoLevel4 especial (Vel, AtqEsp, DefEsp, Vit)
                pElegido.setVelocidad(pElegido.getVelocidad() + 5);
                pElegido.setAtaqueEspecial(pElegido.getAtaqueEspecial() + 5);
                pElegido.setDefensaEspecial(pElegido.getDefensaEspecial() + 5);
                pElegido.setVitalidadMaxima(pElegido.getVitalidadMaxima() + 5);
                break;
        }
        
        // Al mejorar su vitalidad máxima, lo curamos para que esté a tope
        pElegido.setVitalidad(pElegido.getVitalidadMaxima());

        // Guardamos los nuevos parámetros del Pokémon en la Base de Datos
        PokemonDAO pDAO = new PokemonDAO();
        pDAO.actualizarPokemon(pElegido);
        
        // También guardamos el nuevo saldo del entrenador en la BD
        EntrenadorDAO eDAO = new EntrenadorDAO();
        eDAO.actualizarPokedollars(Main.entrenadorLogueado);

        // Actualizamos la información visual de la pantalla
        lblPokedollars.setText("Tus Pokedollars: " + Main.entrenadorLogueado.getPokedollars() + " ₽");
        txtResultado.setText("¡Entrenamiento completado! Has gastado " + coste + " Pokedollars " + pElegido.getNombre() + " se ha vuelto más fuerte.");
    }

    // Método para cerrar la sesión de entrenamiento y regresar a la escena principal
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