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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pokemon.Main;
import pokemon.Objeto;
import pokemon.ObjetoDAO;

import java.io.IOException;
import java.util.ArrayList;

public class MochilaController {

    // Elementos del FXML
    @FXML private VBox vboxObjetos;
    @FXML private Label lblPokedollars;
    @FXML private Label lblTotal;



    @FXML
    public void initialize() {


        // Cargamos y mostramos los objetos de la mochila
        cargarMochila();
    }



    private void cargarMochila() {
        // Limpiamos las filas anteriores por si se recarga
        vboxObjetos.getChildren().clear();

        // Cargamos la mochila desde la base de datos
        ObjetoDAO objetoDAO = new ObjetoDAO();
        ArrayList<Object[]> mochila = objetoDAO.obtenerMochila(
                Main.entrenadorLogueado.getId_Entrenador());

        // Actualizamos el contador del footer
        lblTotal.setText("Total de tipos de objeto: " + mochila.size());

        if (mochila.isEmpty()) {
            // Si no hay objetos mostramos un mensaje
            Label lblVacio = new Label("La mochila esta vacia.");
            lblVacio.setStyle("-fx-font-size: 14px; -fx-padding: 30;");
            lblVacio.setTextFill(javafx.scene.paint.Color.WHITE);
            vboxObjetos.getChildren().add(lblVacio);
            return;
        }

        // Creamos una fila por cada objeto
        for (Object[] entrada : mochila) {
            Objeto obj   = (Objeto) entrada[0];
            int cantidad = (int)   entrada[1];
            vboxObjetos.getChildren().add(crearFilaObjeto(obj, cantidad));
        }
    }



    private HBox crearFilaObjeto(Objeto obj, int cantidad) {

        // Contenedor principal de la fila
        HBox fila = new HBox(16);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setStyle("-fx-background-color: rgba(255,255,255,0.06);"
                + "-fx-background-radius: 14px;"
                + "-fx-border-color: rgba(255,255,255,0.10);"
                + "-fx-border-radius: 14px;"
                + "-fx-border-width: 0.5px;"
                + "-fx-padding: 14 18 14 18;");

        // Nombre del objeto + cantidad
        VBox infoNombre = new VBox(4);
        Label lblNombre = new Label(obj.getNombre());
        lblNombre.setStyle("-fx-font-family: 'Pokemon Solid Normal'; -fx-font-size: 15px;");
        lblNombre.setTextFill(javafx.scene.paint.Color.WHITE);

        Label lblCantidad = new Label("x" + cantidad + " en mochila");
        lblCantidad.setStyle("-fx-font-size: 11px;");
        lblCantidad.setTextFill(javafx.scene.paint.Color.web("#ffd700"));

        infoNombre.getChildren().addAll(lblNombre, lblCantidad);

        // Stats del objeto — solo mostramos los que son distintos de 1.0
        VBox infoStats = new VBox(3);
        infoStats.setAlignment(Pos.CENTER_LEFT);
        agregarStatSiDistinto(infoStats, "ATK",      obj.getBonusAtaque(),              true);
        agregarStatSiDistinto(infoStats, "DEF",      obj.getBonusDefensa(),              true);
        agregarStatSiDistinto(infoStats, "ATK ESP",  obj.getBonusAtaqueEspecial(),       true);
        agregarStatSiDistinto(infoStats, "DEF ESP",  obj.getBonusDefensaEspecial(),      true);
        agregarStatSiDistinto(infoStats, "VEL",      obj.getBonusVelocidad(),            true);
        agregarStatSiDistinto(infoStats, "ATK -",    obj.getPenalizacionAtaque(),        false);
        agregarStatSiDistinto(infoStats, "DEF -",    obj.getPenalizacionDefensa(),       false);

        // Si no tiene ningun stat relevante lo indicamos
        if (infoStats.getChildren().isEmpty()) {
            Label lblSinStats = new Label("Sin efectos en combate");
            lblSinStats.setStyle("-fx-font-size: 10px;");
            lblSinStats.setTextFill(javafx.scene.paint.Color.web("#888888"));
            infoStats.getChildren().add(lblSinStats);
        }

        // Separador flexible
        HBox separador = new HBox();
        HBox.setHgrow(separador, Priority.ALWAYS);

        // Fila completa
        fila.getChildren().addAll(infoNombre, separador, infoStats);

        // Efecto hover
        fila.setOnMouseEntered(e -> fila.setStyle(
                "-fx-background-color: rgba(255,255,255,0.10);"
                + "-fx-background-radius: 14px;"
                + "-fx-border-color: rgba(255,255,255,0.25);"
                + "-fx-border-radius: 14px;"
                + "-fx-border-width: 0.5px;"
                + "-fx-padding: 14 18 14 18;"));
        fila.setOnMouseExited(e -> fila.setStyle(
                "-fx-background-color: rgba(255,255,255,0.06);"
                + "-fx-background-radius: 14px;"
                + "-fx-border-color: rgba(255,255,255,0.10);"
                + "-fx-border-radius: 14px;"
                + "-fx-border-width: 0.5px;"
                + "-fx-padding: 14 18 14 18;"));

        return fila;
    }

    // Añade una linea de stat solo si el valor es distinto de 1.0 (si tiene efecto real)
    private void agregarStatSiDistinto(VBox contenedor, String nombre, double valor, boolean esBonus) {
        if (Math.abs(valor - 1.0) < 0.001) return; // si es 1.0 no hace nada, lo ignoramos

        String texto;
        String color;

        if (esBonus) {
            // Es un bonus (valor > 1.0 = sube el stat)
            texto = nombre + ": x" + String.format("%.2f", valor);
            color = valor > 1.0 ? "#4cd96a" : "#ff6b6b"; // verde si sube, rojo si baja
        } else {
            // Es una penalizacion (valor < 1.0 = baja el stat)
            texto = nombre + ": x" + String.format("%.2f", valor);
            color = valor < 1.0 ? "#ff6b6b" : "#4cd96a";
        }

        Label lbl = new Label(texto);
        lbl.setStyle("-fx-font-size: 10px;");
        lbl.setTextFill(javafx.scene.paint.Color.web(color));
        contenedor.getChildren().add(lbl);
    }


    @FXML
    private void handleVolver(ActionEvent event) {
    	
    	String destino = Main.venimosDeCombate ? "/EscenaCombate.fxml" : "/EscenaMenu.fxml";
        String titulo  = Main.venimosDeCombate ? "Combate"             : "Menu Principal";
        //Esto es lo mismo que en combate es para no perderlo
        Main.venimosDeCombate = false;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            System.out.println("[Mochila] Error al volver al menu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
