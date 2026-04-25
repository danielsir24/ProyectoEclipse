package pokemon.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pokemon.Main;
import pokemon.Pokemon;

import java.io.IOException;
import java.io.InputStream; // IMPORTANTE PARA AS IMAGENS

public class CombateLigaController {
    
    private Pokemon pokemonJugadorActual;

    // --- ELEMENTOS PRINCIPAIS DA TELA (Copiados de CombateController) ---
    @FXML private Label lblNombreJugador;
    @FXML private Label lblNivelJugador;
    @FXML private Label lblPsJugador;
    @FXML private ProgressBar hpBarJugador;
    @FXML private ImageView spritePokemon;

    // --- ELEMENTOS DA INTERFACE (Painéis) ---
    @FXML private VBox panelAcciones;      
    @FXML private HBox panelMovimientos;   
    @FXML private VBox panelCambioPokemon; 
    
    @FXML private Button btnLuchar, btnMochila, btnPokemon, btnHuir;

    // --- SLOTS DE MUDANÇA DE POKÉMON ---
    @FXML private VBox slotCambio1, slotCambio2, slotCambio3, slotCambio4, slotCambio5, slotCambio6;
    @FXML private ImageView imgCambio1, imgCambio2, imgCambio3, imgCambio4, imgCambio5, imgCambio6;
    @FXML private Label lblCambio1, lblCambio2, lblCambio3, lblCambio4, lblCambio5, lblCambio6;
    @FXML private ProgressBar hpCambio1, hpCambio2, hpCambio3, hpCambio4, hpCambio5, hpCambio6;

    @FXML
    public void initialize() {
        if (panelAcciones != null) panelAcciones.setVisible(true);
        if (panelMovimientos != null) panelMovimientos.setVisible(false);
        if (panelCambioPokemon != null) panelCambioPokemon.setVisible(false);

        // Carregar o primeiro pokemon vivo do equipamento
        for (Pokemon p : Main.miEquipo) {
            if (!p.estaDebilitado()) {
                pokemonJugadorActual = p;
                break;
            }
        }
        
        // ISTO FALTAVA: Atualizar os dados visuais assim que começa!
        actualizarPantalla(); 
        System.out.println("Combate de Liga Nivel: " + LigaController.combateActual);
    }

    @FXML
    private void handleLuchar(ActionEvent event) {
        panelAcciones.setVisible(false);
        panelMovimientos.setVisible(true);
    }

    @FXML
    private void handleMochila(ActionEvent event) {
        Main.venimosDeCombate = true;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaMochila.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método de mudança EXATAMENTE IGUAL ao CombateController
    @FXML
    private void handlePokemon(ActionEvent event) {
        VBox[] slots = {slotCambio1, slotCambio2, slotCambio3, slotCambio4, slotCambio5, slotCambio6};
        ImageView[] imgs = {imgCambio1, imgCambio2, imgCambio3, imgCambio4, imgCambio5, imgCambio6};
        Label[] nombres = {lblCambio1, lblCambio2, lblCambio3, lblCambio4, lblCambio5, lblCambio6};
        ProgressBar[] barras = {hpCambio1, hpCambio2, hpCambio3, hpCambio4, hpCambio5, hpCambio6};

        for (int i = 0; i < 6; i++) {
            if (Main.miEquipo != null && i < Main.miEquipo.size()) {
                Pokemon p = Main.miEquipo.get(i);

                String nombre = (p.getMote() != null && !p.getMote().isEmpty()) ? p.getMote() : p.getNombre();
                nombres[i].setText(nombre);

                double porcentaje = (double) p.getVitalidad() / p.getVitalidadMaxima();
                barras[i].setProgress(Math.max(0, porcentaje));

                if (p.getInfoPokedex() != null) {
                    try {
                        InputStream is = getClass().getResourceAsStream("/spritesPokemons/Front/" + p.getInfoPokedex().getNum_Pokedex() + ".png");
                        if (is != null) imgs[i].setImage(new Image(is));
                    } catch (Exception e) {
                        System.out.println("[CombateLiga] No se pudo cargar sprite slot " + i);
                    }
                }

                slots[i].setOpacity(p.estaDebilitado() ? 0.4 : 1.0);
                final int indice = i;
                slots[i].setOnMouseClicked(e -> cambiarPokemon(indice));

            } else {
                nombres[i].setText("---");
                barras[i].setProgress(0);
                imgs[i].setImage(null);
                slots[i].setOnMouseClicked(null);
            }
        }
        mostrarPanel(panelCambioPokemon);
    }

    private void cambiarPokemon(int indice) {
        Pokemon seleccionado = Main.miEquipo.get(indice);

        if (seleccionado.estaDebilitado()) {
            System.out.println(seleccionado.getMote() + " no puede pelear, esta muerto");
            return;
        }

        if (seleccionado == pokemonJugadorActual) {
            System.out.println(seleccionado.getMote() + " ya esta peleando");
            return;
        }

        pokemonJugadorActual = seleccionado;
        
        // ISTO FALTAVA: Atualizar a imagem e a vida visualmente ao mudar!
        actualizarPantalla(); 
        
        System.out.println("Peleamos con " + seleccionado.getMote());
        mostrarPanel(panelAcciones);
    }

    //
    private void actualizarPantalla() {
        if (pokemonJugadorActual == null) return;

        String mote = (pokemonJugadorActual.getMote() != null && !pokemonJugadorActual.getMote().isEmpty())
                ? pokemonJugadorActual.getMote()
                : pokemonJugadorActual.getNombre();
        
        if (lblNombreJugador != null) lblNombreJugador.setText(mote);
        if (lblNivelJugador != null) lblNivelJugador.setText("Nv." + pokemonJugadorActual.getNivel());
        if (lblPsJugador != null) lblPsJugador.setText(pokemonJugadorActual.getVitalidad() + "/" + pokemonJugadorActual.getVitalidadMaxima());

        if (hpBarJugador != null) {
            double porcentajeJugador = (double) pokemonJugadorActual.getVitalidad() / pokemonJugadorActual.getVitalidadMaxima();
            hpBarJugador.setProgress(Math.max(0, porcentajeJugador));
        }

        // Carregar a imagem (gif de costas)
        cargarSprite(spritePokemon, pokemonJugadorActual, false);
    }

    private void cargarSprite(ImageView imageView, Pokemon pokemon, boolean frontal) {
        if (pokemon == null || pokemon.getInfoPokedex() == null || imageView == null) return;

        String carpeta = frontal ? "spritesPokemonsGifsFront" : "spritesPokemonsGifsBack";
        String ruta = "/" + carpeta + "/" + pokemon.getInfoPokedex().getNum_Pokedex() + ".gif";

        try {
            InputStream is = getClass().getResourceAsStream(ruta);
            if (is != null) {
                imageView.setImage(new Image(is));
            } else {
                System.out.println("[CombateLiga] Sprite no encontrado: " + ruta);
            }
        } catch (Exception e) {
            System.out.println("[CombateLiga] Error al cargar sprite: " + ruta);
        }
    }

    private void mostrarPanel(Object panel) {
        // 1. Ocultar y des-gestionar todo
        panelAcciones.setVisible(false);

        panelMovimientos.setVisible(false);
        panelMovimientos.setManaged(false);

        panelCambioPokemon.setVisible(false);
        panelCambioPokemon.setManaged(false);

        // 2. Mostrar y gestionar SOLO el panel que hemos pedido
        if (panel == panelAcciones) {
            panelAcciones.setVisible(true);
        } else if (panel == panelMovimientos) {
            panelMovimientos.setVisible(true);
            panelMovimientos.setManaged(true); // ¡Clave para que no flote!
        } else if (panel == panelCambioPokemon) {
            panelCambioPokemon.setVisible(true);
            panelCambioPokemon.setManaged(true); // ¡Clave para que vaya abajo!
        }
    }

    @FXML
    private void handleHuir(ActionEvent event) {
        if (Main.entrenadorLogueado != null) {
            Main.entrenadorLogueado.setPokedollars(Main.entrenadorLogueado.getPokedollars() / 2);
        }
        LigaController.resetearLiga();
        volverAlMenuPrincipal(event);
    }

    @FXML
    private void cancelarAccion(ActionEvent event) {
        mostrarPanel(panelAcciones);
    }

    private void volverAlMenuPrincipal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaLiga.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
}