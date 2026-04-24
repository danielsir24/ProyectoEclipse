package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pokemon.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LigaController {

    @FXML private Label lblNombreRival, lblPokedollars, lblCombateNum;
    @FXML private Button btnCombatir, btnCurar;

    public static int combateActual = 1;
    public static boolean seHaCuradoEnEsteTurno = false;
    
    // Lista para manejar el orden aleatorio del Alto Mando
    private static List<Entrenador> listaAltoMando;

    @FXML
    public void initialize() {
        if (listaAltoMando == null) {
            prepararLiga();
        }
        actualizarInfoLiga();
    }

    private void prepararLiga() {
        listaAltoMando = new ArrayList<>();
        
        // Creamos los entrenadores usando el constructor vacío + setters
        // Esto evita el error de "constructor not found"
        String[] nombres = {"Lorelei", "Bruno", "Agatha", "Lance"};
        for (String nombre : nombres) {
            Entrenador e = new Entrenador();
            e.setNom_Entrenador(nombre);
            e.setImg_Entrenador(nombre.toLowerCase() + ".png");
            e.setTipo_Entrenador("ALTO_MANDO");
            e.setPokedollars(0); 
            listaAltoMando.add(e);
        }

        // 1. Aleatorizamos los primeros 4 según el requisito
        Collections.shuffle(listaAltoMando);
        
        // 2. Añadimos al Campeón fijo al final (posición 5)
        Entrenador campeon = new Entrenador();
        campeon.setNom_Entrenador("Azul");
        campeon.setImg_Entrenador("azul.png");
        campeon.setTipo_Entrenador("ALTO_MANDO");
        listaAltoMando.add(campeon);
    }

    private void actualizarInfoLiga() {
        if (Main.entrenadorLogueado == null) return;

        lblPokedollars.setText("Pokedollars: " + Main.entrenadorLogueado.getPokedollars());

        if (combateActual > 5) {
            lblNombreRival.setText("¡ERES EL CAMPEÓN!");
            btnCombatir.setDisable(true);
            btnCurar.setDisable(true);
        } else {
            Entrenador rival = listaAltoMando.get(combateActual - 1);
            lblNombreRival.setText("Rival: " + rival.getNom_Entrenador());
            lblCombateNum.setText("Combate " + combateActual + " de 5");
            btnCurar.setDisable(seHaCuradoEnEsteTurno);
        }
    }

    @FXML
    private void handleCurar(ActionEvent event) {
        // Requisito: Restablecer vitalidad y estamina
        if (Main.miEquipo != null) {
            for (Pokemon p : Main.miEquipo) {
                p.setVitalidad(p.getVitalidadMaxima());
                p.setEstamina(100);
                p.setEstado(Estado.NORMAL);
            }
            seHaCuradoEnEsteTurno = true;
            btnCurar.setDisable(true);
            actualizarInfoLiga();
        }
    }

    @FXML
    private void handleCombatir(ActionEvent event) {
        try {
            // Pasamos el rival actual al motor de combate antes de cambiar de escena
            // Asegúrate de que Main.rivalActual exista
            // Main.rivalActual = listaAltoMando.get(combateActual - 1);
            
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaCombate.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetearLiga() {
        combateActual = 1;
        listaAltoMando = null;
        seHaCuradoEnEsteTurno = false;
    }
}