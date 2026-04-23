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

public class LigaController {

    @FXML private Label lblNombreRival, lblNivelRival, lblPokedollars;
    @FXML private Button btnCombatir, btnCurar;

    //Variables de estado de la Liga
    public static int combateActual = 1;
    public static int premioAcumulado = 0;
    private static boolean seHaCurado = false;

    private String[] nombresAltoMando = {"Lorelei", "Bruno", "Agatha", "Lance", "Azul"};
    private int[] nivelesAltoMando = {50, 52, 54, 56, 60};

    @FXML
    public void initialize() {
        actualizarInfoLiga();
    }

    private void actualizarInfoLiga() {
        if (combateActual > 5) {
            lblNombreRival.setText("¡CAMPEÓN!");
            btnCombatir.setDisable(true);
            return;
        }

        lblNombreRival.setText(nombresAltoMando[combateActual - 1]);
        lblNivelRival.setText("Nivel medio: " + nivelesAltoMando[combateActual - 1]);
        lblPokedollars.setText("Pokédollars: " + Main.entrenadorLogueado.getPokedollars() + " ₽");
    }

    @FXML
    private void handleCombatir(ActionEvent event) {
        //Lógica para lanzar el combate contra el miembro del Alto Mando
        try {
            //Guardamos el progreso antes de ir al combate
            System.out.println("Iniciando combate " + combateActual + " contra " + nombresAltoMando[combateActual-1]);
            
            //Cargamos la escena de combate de la liga
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaCombateLiga.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCurar(ActionEvent event) {
        //Requisito: Curar cuesta la mitad del premio acumulado o una tasa fija
        for (Pokemon p : Main.miEquipo) {
            p.setVitalidad(p.getVitalidadMaxima());
            p.setEstamina(100);
            p.setEstado(Estado.NORMAL);
        }
        
        seHaCurado = true;
        Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() - 500);
        
        System.out.println("Equipo curado. Penalización aplicada.");
        actualizarInfoLiga();
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        //Resetear liga si se sale
        combateActual = 1;
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