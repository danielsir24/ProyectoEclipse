package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    @FXML
    void initialize() {
        comboCaraCruz.getItems().addAll("Cara", "Cruz");
        comboColorRuleta.getItems().addAll("Rojo", "Negro");
    }

    // --- MINIJUEGO 1: CARA O CRUZ ---
    @FXML
    void jugarCaraCruz(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaCaraCruz.getText());
            String eleccion = comboCaraCruz.getValue();

            if (eleccion == null) {
                lblResultadoCaraCruz.setText("️¡Debes elegir Cara o Cruz!");
                return;
            }

            String[] opciones = {"Cara", "Cruz"};
            String resultadoMoneda = opciones[(int) (Math.random() * 2)];

            if (eleccion.equals(resultadoMoneda)) {
                int premio = apuesta * 2;
                lblResultadoCaraCruz.setText("✨ ¡Salió " + resultadoMoneda + "! Has ganado " + premio + " Pokedólares.");
            } else {
                lblResultadoCaraCruz.setText("❌ Salió " + resultadoMoneda + ". Has perdido.");
            }
        } catch (NumberFormatException e) {
            lblResultadoCaraCruz.setText("⚠️ Escribe una apuesta válida.");
        }
    }

    // --- MINIJUEGO 2: LA RULETA ---
    @FXML
    void jugarRuleta(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaRuleta.getText());
            String colorElegido = comboColorRuleta.getValue();
            String numTexto = txtNumeroRuleta.getText();

            int premioTotal = 0;
            // La máquina genera número (1-37) y color
            int numeroRuleta = (int) (Math.random() * 37) + 1; 
            String[] colores = {"Rojo", "Negro"};
            String colorRuleta = colores[(int) (Math.random() * 2)];

            StringBuilder mensaje = new StringBuilder("Salió " + numeroRuleta + " " + colorRuleta + ". ");

            // Comprueba si acertó el número (Premio x10)
            if (!numTexto.isEmpty()) {
                int numeroElegido = Integer.parseInt(numTexto);
                if (numeroElegido == numeroRuleta) {
                    premioTotal += apuesta * 10;
                    mensaje.append("¡Acertaste número! ");
                }
            }

            // Comprueba si acertó el color (Premio x2)
            if (colorElegido != null && colorElegido.equals(colorRuleta)) {
                premioTotal += apuesta * 2;
                mensaje.append("¡Acertaste color! ");
            }

            // Resultado final
            if (premioTotal > 0) {
                lblResultadoRuleta.setText("✨ " + mensaje.toString() + "Ganas " + premioTotal + " Pokedólares.");
            } else {
                lblResultadoRuleta.setText("❌ " + mensaje.toString() + "Pierdes la apuesta.");
            }
        } catch (NumberFormatException e) {
            lblResultadoRuleta.setText("️Revisa que la apuesta y el número estén bien escritos.");
        }
    }

    // --- MINIJUEGO 3: ADIVINAR NÚMERO ---
    @FXML
    void jugarAdivinarNumero(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaAdivinar.getText());
            int numeroElegido = Integer.parseInt(txtNumeroAdivinar.getText());

            // La máquina elige un número del 1 al 10 (para que no sea imposible ganar)
            int numeroSecreto = (int) (Math.random() * 10) + 1; 

            if (numeroElegido == numeroSecreto) {
                int premio = apuesta * 10; // Premio x10
                lblResultadoAdivinar.setText(" ¡Acertaste! Era el " + numeroSecreto + ". Ganas " + premio + " Pokedólares.");
            } else {
                lblResultadoAdivinar.setText(" Salió el " + numeroSecreto + ". Has perdido.");
            }
        } catch (NumberFormatException e) {
            lblResultadoAdivinar.setText("️ Escribe números válidos.");
        }
    }
}