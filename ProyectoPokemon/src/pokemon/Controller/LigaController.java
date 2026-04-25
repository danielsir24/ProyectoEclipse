package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pokemon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LigaController {


    @FXML private Label lblNombreRival, lblPokedollars, lblCombateNum;
    @FXML private Label nombreRivalSeleccionado;


    @FXML private Label lblNombreRival1, lblNombreRival2, lblNombreRival3,
                        lblNombreRival4, lblNombreRival5;


    @FXML private StackPane cardRival1, cardRival2, cardRival3,
                            cardRival4, cardRival5;


    @FXML private ImageView imgRival1, imgRival2, imgRival3,
                            imgRival4, imgRival5;


    @FXML private Button btnCombatir, btnCurar, btnSalir;

    public static int combateActual = 1;
    public static boolean seHaCuradoEnEsteTurno = false;

    private static List<Entrenador> listaAltoMando;

    // Rival seleccionado por el jugador
    private int rivalSeleccionado = -1;
    
    public static Liga partidaActual;

    @FXML
    public void initialize() {
        if (listaAltoMando == null) {
            prepararLiga();
        }
        actualizarInfoLiga();
        // Deshabilitar combatir hasta que se seleccione rival
        btnCombatir.setDisable(true);
    }

    private void prepararLiga() {
        listaAltoMando = new ArrayList<>();

        String[] nombres = {"Lorelei", "Bruno", "Agatha", "Lance"};
        for (String nombre : nombres) {
            Entrenador e = new Entrenador();
            e.setNom_Entrenador(nombre);
            e.setImg_Entrenador(nombre.toLowerCase() + ".png");
            e.setTipo_Entrenador("ALTO_MANDO");
            e.setPokedollars(0);
            listaAltoMando.add(e);
        }

        Collections.shuffle(listaAltoMando);

        Entrenador campeon = new Entrenador();
        campeon.setNom_Entrenador("Azul");
        campeon.setImg_Entrenador("azul.png");
        campeon.setTipo_Entrenador("ALTO_MANDO");
        listaAltoMando.add(campeon);
    }

    private void actualizarInfoLiga() {
        if (Main.entrenadorLogueado == null) return;

        if (lblPokedollars != null)
            lblPokedollars.setText("Pokedollars: " + Main.entrenadorLogueado.getPokedollars());

        if (combateActual > 5) {
            if (lblNombreRival != null) lblNombreRival.setText("¡ERES EL CAMPEÓN!");
            btnCombatir.setDisable(true);
            btnCurar.setDisable(true);
        } else {
            Entrenador rival = listaAltoMando.get(combateActual - 1);
            if (lblNombreRival != null)
                lblNombreRival.setText("Rival: " + rival.getNom_Entrenador());
            if (lblCombateNum != null)
                lblCombateNum.setText("Combate " + combateActual + " de 5");
            btnCurar.setDisable(seHaCuradoEnEsteTurno);
        }
    }

    @FXML
    private void seleccionarRival1(MouseEvent event) {
        if (combateActual == 1) seleccionarRival(1);
    }

    @FXML
    private void seleccionarRival2(MouseEvent event) {
    	 if (combateActual == 2) seleccionarRival(2);
    }

    @FXML
    private void seleccionarRival3(MouseEvent event) {
    	 if (combateActual == 3) seleccionarRival(3);    }

    @FXML
    private void seleccionarRival4(MouseEvent event) {
    	 if (combateActual == 4) seleccionarRival(4);    }

    @FXML
    private void seleccionarRival5(MouseEvent event) {
    	 if (combateActual == 5) seleccionarRival(5);    }

    private void seleccionarRival(int numero) {
        rivalSeleccionado = numero;
        Entrenador rival = listaAltoMando.get(numero - 1);
        nombreRivalSeleccionado.setText(rival.getNom_Entrenador());
        btnCombatir.setDisable(false);
        System.out.println("Rival " + numero + " seleccionado: " + rival.getNom_Entrenador());
    }

    // Botones
    @FXML
    private void handleCurar(ActionEvent event) {
        // 1. Verificar que el equipo existe
        if (Main.miEquipo != null && !Main.miEquipo.isEmpty()) {
            
            // 2. Aplicar restauración total
            for (Pokemon p : Main.miEquipo) {
                p.setVitalidad(p.getVitalidadMaxima());
                p.setEstamina(100);
                p.setEstado(Estado.NORMAL); // Elimina parálisis, sueño, etc.
            }

            // 3. Activar bandera de penalización
            // Esta variable estática DEBE ser consultada por el controlador de combate
            // al finalizar la batalla para dividir el premio entre 2.
            seHaCuradoEnEsteTurno = true;

            // 4. Visual y deshabilitar botón
            btnCurar.setDisable(true);
            actualizarInfoLiga();
            
            System.out.println("Equipo curado. Se ha aplicado la penalización de recompensa (50%).");
        } else {
            System.err.println("Error: No se encontró el equipo del entrenador.");
        }
    }

    @FXML
    private void handleCombatir(ActionEvent event) {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("/EscenaCombateLiga.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        try {
            // 1. Verificamos la ruta antes de intentar cargarla
            java.net.URL urlVistas = getClass().getResource("/EscenaMenu.fxml");
            
            if (urlVistas == null) {
                // Si entra aquí, es que el archivo NO está en la raíz del classpath (carpeta bin/)
                // Intenta buscarlo relativo al controlador
                urlVistas = getClass().getResource("EscenaMenu.fxml");
                
                // Si sigue siendo nulo, el archivo no se llama así o no está compilado.
                if(urlVistas == null){
                    throw new IllegalStateException("FATAL: No se encuentra el archivo EscenaMenuPrincipal.fxml. "
                            + "Revisa que el nombre sea exacto y que esté dentro de la carpeta src (o resources).");
                }
            }

            // 2. Cargamos el FXML validado
            FXMLLoader loader = new FXMLLoader(urlVistas);
            Parent root = loader.load();
            
            // 3. Cambiamos la escena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            System.err.println("Error interno de JavaFX al procesar el archivo FXML.");
            e.printStackTrace();
        }
    }

    public static void resetearLiga() {
        combateActual = 1;
        listaAltoMando = null;
        seHaCuradoEnEsteTurno = false;
    }
}