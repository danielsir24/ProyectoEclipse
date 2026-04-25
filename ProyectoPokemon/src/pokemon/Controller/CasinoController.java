package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button; 
import pokemon.EntrenadorDAO;
import pokemon.Main;

public class CasinoController {

    @FXML
    private ComboBox<String> comboCaraCruz;

    @FXML
    private ComboBox<String> comboColorRuleta;

    @FXML
    private Label lblResultadoAdivinar;

    @FXML
    private Label lblResultadoCaraCruz;

    @FXML
    private Label lblResultadoRuleta;

    // AQUÍ ESTÁN TUS 3 MARCADORES INDEPENDIENTES
    // Nota: Si en Scene Builder arrastraste un "Button" en vez de un "Label", 
    // cambia la palabra "Label" por "Button" en estas 3 líneas.
    @FXML
    private Label lblPokedollars;
    @FXML
    private Label lblPokedollars1;
    @FXML
    private Label lblPokedollars2;

    @FXML
    private TextField txtApuestaAdivinar;

    @FXML
    private TextField txtApuestaCaraCruz;

    @FXML
    private TextField txtApuestaRuleta;

    @FXML
    private TextField txtNumeroAdivinar;

    @FXML
    private TextField txtNumeroRuleta;

    // Conectamos con el DAO para guardar el dinero
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

    @FXML
    void initialize() {
        comboCaraCruz.getItems().addAll("Cara", "Cruz");
        comboColorRuleta.getItems().addAll("Rojo", "Negro");
        
        // Nada más abrir el casino, mostramos el dinero actual en las 3 pestañas
        actualizarMarcadorDinero();
    }

    // Método que actualiza los 3 marcadores a la vez
    private void actualizarMarcadorDinero() {
        if (Main.entrenadorLogueado != null) {
            String textoDinero = "Saldo: " + Main.entrenadorLogueado.getPokedollars() + " $";
            
            if (lblPokedollars != null) lblPokedollars.setText(textoDinero);
            if (lblPokedollars1 != null) lblPokedollars1.setText(textoDinero);
            if (lblPokedollars2 != null) lblPokedollars2.setText(textoDinero);
        }
    }

    // Cara o Cruz
    @FXML
    void jugarCaraCruz(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaCaraCruz.getText());
            String eleccion = comboCaraCruz.getValue();

            if (eleccion == null) {
                lblResultadoCaraCruz.setText("¡Debes elegir Cara o Cruz!");
                return;
            }

            // Comprobar si hay dinero suficiente
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoCaraCruz.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Restamos la apuesta
            Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() - apuesta);

            String[] opciones = {"Cara", "Cruz"};
            String resultadoMoneda = opciones[(int) (Math.random() * 2)];

            if (eleccion.equals(resultadoMoneda)) {
                int premio = apuesta * 2;
                // Sumamos el premio
                Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() + premio);
                lblResultadoCaraCruz.setText("¡Salió " + resultadoMoneda + "! Ganas " + premio + " $.");
            } else {
                lblResultadoCaraCruz.setText("Salió " + resultadoMoneda + ". Pierdes " + apuesta + " $.");
            }

            // Guardamos en BD y actualizamos los marcadores
            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoCaraCruz.setText("Escribe una apuesta válida.");
        }
    }

    // Ruleta
    @FXML
    void jugarRuleta(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaRuleta.getText());
            String colorElegido = comboColorRuleta.getValue();
            String numTexto = txtNumeroRuleta.getText();

            // Comprobar si hay dinero suficiente
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoRuleta.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Restamos la apuesta
            Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() - apuesta);

            int premioTotal = 0;
            // La máquina genera número (1-37) y color
            int numeroRuleta = (int) (Math.random() * 37) + 1; 
            String[] colores = {"Rojo", "Negro"};
            String colorRuleta = colores[(int) (Math.random() * 2)];

            StringBuilder mensaje = new StringBuilder("Salió " + numeroRuleta + " " + colorRuleta + ". ");

            if (!numTexto.isEmpty()) {
                int numeroElegido = Integer.parseInt(numTexto);
                if (numeroElegido == numeroRuleta) {
                    premioTotal += apuesta * 10;
                    mensaje.append("¡Acertaste número! ");
                }
            }

            if (colorElegido != null && colorElegido.equals(colorRuleta)) {
                premioTotal += apuesta * 2;
                mensaje.append("¡Acertaste color! ");
            }

            if (premioTotal > 0) {
                // Sumamos el premio total
                Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() + premioTotal);
                lblResultadoRuleta.setText("✨ " + mensaje.toString() + "Ganas " + premioTotal + " $.");
            } else {
                lblResultadoRuleta.setText("❌ " + mensaje.toString() + "Pierdes " + apuesta + " $.");
            }

            // Guardamos en BD y actualizamos los marcadores
            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoRuleta.setText("Revisa que la apuesta y el número estén bien.");
        }
    }

    // Adivinar Número
    @FXML
    void jugarAdivinarNumero(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaAdivinar.getText());
            int numeroElegido = Integer.parseInt(txtNumeroAdivinar.getText());

            // Comprobar si hay dinero suficiente
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoAdivinar.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Restamos la apuesta
            Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() - apuesta);

            int numeroSecreto = (int) (Math.random() * 10) + 1; 

            if (numeroElegido == numeroSecreto) {
                int premio = apuesta * 10; 
                // Sumamos el premio
                Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() + premio);
                lblResultadoAdivinar.setText("¡Acertaste! Era el " + numeroSecreto + ". Ganas " + premio + " $.");
            } else {
                lblResultadoAdivinar.setText("Salió el " + numeroSecreto + ". Pierdes " + apuesta + " $.");
            }

            // Guardamos en BD y actualizamos los marcadores
            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoAdivinar.setText("Escribe números válidos.");
        }
    }

    // Botón de escape al menú principal
    @FXML
    void volverAlMenu(ActionEvent event) {
        try {
            // Cargamos el diseño del menú principal
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/EscenaMenu.fxml"));
            javafx.scene.Parent root = loader.load();
            
            // Cogemos la ventana actual y le cambiamos la escena
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            
            stage.setTitle("Menú Principal Pokémon");
            // Mantenemos las proporciones gigantes 1280x761 para volver al menú y no se minimice
            stage.setScene(new javafx.scene.Scene(root, 1280, 761));
            stage.show();
            
        } catch (Exception e) {
            System.out.println("Error al volver al menú:");
            e.printStackTrace();
        }
    }
}