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

    // --- ELEMENTOS DE LA INTERFAZ ---
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

    // Marcadores independientes de dinero para cada pestaña del casino
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

    // Objeto para conectar con la base de datos y guardar el dinero
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();

    // ══════════════════════════════════════════════
    // INICIALIZACIÓN
    // ══════════════════════════════════════════════
    @FXML
    void initialize() {
        // Rellenamos los desplegables de los juegos
        comboCaraCruz.getItems().addAll("Cara", "Cruz");
        comboColorRuleta.getItems().addAll("Rojo", "Negro");
        
        // Actualizamos los 3 marcadores de dinero nada más entrar
        actualizarMarcadorDinero();
    }

    // Método interno que actualiza los 3 labels a la vez
    private void actualizarMarcadorDinero() {
        if (Main.entrenadorLogueado != null) {
            String textoDinero = "Saldo: " + Main.entrenadorLogueado.getPokedollars() + " $";
            
            if (lblPokedollars != null) lblPokedollars.setText(textoDinero);
            if (lblPokedollars1 != null) lblPokedollars1.setText(textoDinero);
            if (lblPokedollars2 != null) lblPokedollars2.setText(textoDinero);
        }
    }

    // ══════════════════════════════════════════════
    // MINIJUEGO 1: CARA O CRUZ
    // ══════════════════════════════════════════════
    @FXML
    void jugarCaraCruz(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaCaraCruz.getText());
            String eleccion = comboCaraCruz.getValue();

            // Comprobamos que haya elegido una opción
            if (eleccion == null) {
                lblResultadoCaraCruz.setText("¡Debes elegir Cara o Cruz!");
                return;
            }

            // Comprobamos si tiene el dinero suficiente para apostar
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoCaraCruz.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Restamos la apuesta
            Main.entrenadorLogueado.gastarPokedollars(apuesta);

            // Tiramos la moneda al azar
            String[] opciones = {"Cara", "Cruz"};
            String resultadoMoneda = opciones[(int) (Math.random() * 2)];

            // Comprobamos si ha ganado (Premio x2)
            if (eleccion.equals(resultadoMoneda)) {
                int premio = apuesta * 2;
                Main.entrenadorLogueado.ganarPokedollars(premio);
                lblResultadoCaraCruz.setText("¡Salió " + resultadoMoneda + "! Ganas " + premio + " $.");
            } else {
                lblResultadoCaraCruz.setText("Salió " + resultadoMoneda + ". Pierdes " + apuesta + " $.");
            }

            // Guardamos en BD y actualizamos los marcadores visuales
            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoCaraCruz.setText("Escribe una apuesta válida.");
        }
    }

    // ══════════════════════════════════════════════
    // MINIJUEGO 2: RULETA
    // ══════════════════════════════════════════════
    @FXML
    void jugarRuleta(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaRuleta.getText());
            String colorElegido = comboColorRuleta.getValue();
            String numTexto = txtNumeroRuleta.getText().trim();

            // ¡AQUÍ ESTÁ EL ARREGLO! 
            // Si el color es nulo o vacío Y ADEMÁS el texto del número está vacío, cortamos.
            if ((colorElegido == null || colorElegido.isEmpty()) && numTexto.isEmpty()) {
                lblResultadoRuleta.setText("¡Debes elegir un número, un color o ambos!");
                return; // El return hace que el método acabe aquí y no te quite dinero
            }

            // Comprobamos saldo
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoRuleta.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Como ha pasado los filtros, le cobramos la apuesta al darle al botón
            Main.entrenadorLogueado.gastarPokedollars(apuesta);

            int premioTotal = 0;
            // Tirada de la ruleta: Número (1-37) y Color (Rojo/Negro)
            int numeroRuleta = (int) (Math.random() * 37) + 1; 
            String[] colores = {"Rojo", "Negro"};
            String colorRuleta = colores[(int) (Math.random() * 2)];

            StringBuilder mensaje = new StringBuilder("Salió " + numeroRuleta + " " + colorRuleta + ". ");

            // Si apostó a un número, comprobamos si ha acertado (Premio x10)
            if (!numTexto.isEmpty()) {
                int numeroElegido = Integer.parseInt(numTexto);
                if (numeroElegido == numeroRuleta) {
                    premioTotal += apuesta * 10;
                    mensaje.append("¡Acertaste número! ");
                }
            }

            // Si apostó a color, comprobamos si acierta (Premio x2)
            if (colorElegido != null && colorElegido.equals(colorRuleta)) {
                premioTotal += apuesta * 2;
                mensaje.append("¡Acertaste color! ");
            }

            // Mostramos resultado final y pagamos el premio si toca
            if (premioTotal > 0) {
                Main.entrenadorLogueado.ganarPokedollars(premioTotal);
                lblResultadoRuleta.setText("✨ " + mensaje.toString() + "Ganas " + premioTotal + " $.");
            } else {
                lblResultadoRuleta.setText("❌ " + mensaje.toString() + "Pierdes " + apuesta + " $.");
            }

            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoRuleta.setText("Revisa que la apuesta y el número estén bien.");
        }
    }

    // ══════════════════════════════════════════════
    // MINIJUEGO 3: ADIVINAR NÚMERO
    // ══════════════════════════════════════════════
    @FXML
    void jugarAdivinarNumero(ActionEvent event) {
        try {
            int apuesta = Integer.parseInt(txtApuestaAdivinar.getText());
            int numeroElegido = Integer.parseInt(txtNumeroAdivinar.getText());

            // Comprobamos saldo
            if (apuesta <= 0 || Main.entrenadorLogueado.getPokedollars() < apuesta) {
                lblResultadoAdivinar.setText("No tienes suficientes Pokedólares.");
                return;
            }

            // Cobramos la apuesta
            Main.entrenadorLogueado.gastarPokedollars(apuesta);

            // Generamos número secreto del 1 al 10
            int numeroSecreto = (int) (Math.random() * 10) + 1; 

            // Si acierta, el premio es x10
            if (numeroElegido == numeroSecreto) {
                int premio = apuesta * 10; 
                Main.entrenadorLogueado.ganarPokedollars(premio);
                lblResultadoAdivinar.setText("¡Acertaste! Era el " + numeroSecreto + ". Ganas " + premio + " $.");
            } else {
                lblResultadoAdivinar.setText("Salió el " + numeroSecreto + ". Pierdes " + apuesta + " $.");
            }

            entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado);
            actualizarMarcadorDinero();

        } catch (NumberFormatException e) {
            lblResultadoAdivinar.setText("Escribe números válidos.");
        }
    }

   //Botones de salida
    
    // Botón de salir de la Pestaña 1
    @FXML
    void volverAlMenu(ActionEvent event) {
        ejecutarSalida(event);
    }
    
    // Botón de salir de la Pestaña 2
    @FXML
    void volverAlMenu1(ActionEvent event) {
        ejecutarSalida(event);
    }
    
    // Botón de salir de la Pestaña 3
    @FXML
    void volverAlMenu2(ActionEvent event) {
        ejecutarSalida(event);
    }
    
    // Lógica común para no repetir el código del FXMLLoader 3 veces
    private void ejecutarSalida(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/EscenaMenu.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Menú Principal Pokémon");
            stage.setScene(new javafx.scene.Scene(root, 1280, 761));
            stage.show();
        } catch (Exception e) {
            System.out.println("Error al volver al menú:");
            e.printStackTrace();
        }
    }
}