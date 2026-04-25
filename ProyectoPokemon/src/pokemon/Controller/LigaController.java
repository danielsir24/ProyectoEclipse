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
    private void seleccionarRival2(MouseEvent event) {
        seleccionarRival(2);
    }

    @FXML
    private void seleccionarRival3(MouseEvent event) {
        seleccionarRival(3);
    }

    @FXML
    private void seleccionarRival4(MouseEvent event) {
        seleccionarRival(4);
    }

    @FXML
    private void seleccionarRival5(MouseEvent event) {
        seleccionarRival(5);
    }

    private void seleccionarRival(int numero) {
        rivalSeleccionado = numero;
        Entrenador rival = listaAltoMando.get(numero - 1);
        nombreRivalSeleccionado.setText(rival.getNom_Entrenador());
        btnCombatir.setDisable(false);
    }

    // Botones 

    @FXML
    private void handleCurar(ActionEvent event) {
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
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenuPrincipal.fxml"));
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