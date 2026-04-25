package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pokemon.Entrenador;
import pokemon.Main;
import pokemon.Pokemon;

import java.io.IOException;

public class CombateLigaController {

    // --- ELEMENTOS DE LA INTERFAZ ---
    @FXML private VBox panelAcciones; // 4 botones (Luchar, Mochila, Pokemon, Huir)
    @FXML private HBox panelMovimientos;            
    @FXML private VBox panelPokemon;          
    
    // Si necesitas referenciar los botones directamente
    @FXML private Button btnLuchar, btnMochila, btnPokemon, btnHuir;

    @FXML
    public void initialize() {
        // 1. Configurar estado inicial de la UI
    	if (panelAcciones != null) panelAcciones.setVisible(true);
        if (panelMovimientos != null) panelMovimientos.setVisible(false);
        if (panelPokemon != null) panelPokemon.setVisible(false);

        // 2. Cargar datos del Alto Mando
        System.out.println("Combate de Liga Nivel: " + LigaController.combateActual);
        // Aquí deberías cargar el modelo 3D/Imagen del Pokémon rival, la vida, etc.
    }



    @FXML
    private void handleLuchar(ActionEvent event) {
        System.out.println("Acción: LUCHAR");
        // Oculta los 4 botones y muestra el panel con los 4 ataques del Pokémon
        panelAcciones.setVisible(false);
        panelMovimientos.setVisible(true);
        
      
    }

    @FXML
    private void handleMochila(ActionEvent event) {
        System.out.println("Acción: MOCHILA (Descansar)");
        // Según los requisitos, en combate se puede descansar para recuperar estamina[cite: 228].
        if (Main.miEquipo != null && !Main.miEquipo.isEmpty()) {
            Pokemon activo = Main.miEquipo.get(0); // Suponiendo que el índice 0 es el activo
            // activo.setEstamina(activo.getEstamina() + X); // Aplica tu fórmula de recuperación
            
            System.out.println(activo.getNombre() + " ha descansado y recuperado estamina.");
            
        
        }
    }

    @FXML
    private void handlePokemon(ActionEvent event) {
        System.out.println("Acción: POKEMON (Cambiar)");
        // Oculta los botones y muestra la lista de tu equipo[cite: 230].
        panelAcciones.setVisible(false);
        panelMovimientos.setVisible(true);
        
        // TODO: Lógica para renderizar los 6 Pokémon vivos de tu equipo.
    }

    @FXML
    private void handleHuir(ActionEvent event) {
        System.out.println("Acción: HUIR");
        
        // REGLA DE LIGA: El jugador pierde la mitad de sus pokedollars[cite: 343].
        if (Main.entrenadorLogueado != null) {
            int dineroActual = Main.entrenadorLogueado.getPokedollars();
            Main.entrenadorLogueado.setPokedollars(dineroActual / 2);
            System.out.println("Has huido. Penalización aplicada. Nuevo saldo: " + Main.entrenadorLogueado.getPokedollars());
        }

        // Al huir/perder, la Liga se reinicia.
        LigaController.resetearLiga();

        // Volver al Menú Principal de forma segura
        volverAlMenuPrincipal(event);
    }


    @FXML
    private void cancelarAccion() {
        // Este método se lo asignas a un botón "Volver" dentro de panelAtaques o panelCambioPokemon
    	panelMovimientos.setVisible(false);
        panelPokemon.setVisible(false);
        panelAcciones.setVisible(true);
    }

    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            java.net.URL url = getClass().getResource("/EscenaLiga.fxml");
            if(url == null) url = getClass().getResource("EscenaLiga.fxml");
            
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException | NullPointerException e) {
            System.err.println("FATAL: No se pudo cargar EscenaLiga.fxml tras huir.");
            e.printStackTrace();
        }
    }
}