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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import pokemon.Estado;
import pokemon.Main;
import pokemon.Movimiento;
import pokemon.Pokedex;
import pokemon.PokedexDAO;
import pokemon.Pokemon;
import pokemon.Tipo;

public class CombateController {



    @FXML private Label lblNombreRival;
    @FXML private Label lblNivelRival;


    @FXML private Label lblNombreJugador;
    @FXML private Label lblNivelJugador;
    @FXML private Label lblPsJugador;


    @FXML private ProgressBar hpBarRival;
    @FXML private ProgressBar hpBarJugador;
    @FXML private ProgressBar expBarJugador;


    @FXML private Circle estadoRival;
    @FXML private Circle estadoJugador;


    @FXML private ImageView spriteRival;   
    @FXML private ImageView spriteJugador; 

    @FXML private TextArea txtLog;


    @FXML private Label lblTurno;


    @FXML private HBox panelAcciones;


    @FXML private HBox panelMovimientos;

    @FXML private VBox panelCambioPokemon;


    @FXML private Button btnMovimiento1;
    @FXML private Button btnMovimiento2;
    @FXML private Button btnMovimiento3;
    @FXML private Button btnMovimiento4;


    @FXML private Label lblTipoMovimiento;
    @FXML private Label lblPPMovimiento;


    @FXML private VBox slotCambio1, slotCambio2, slotCambio3;
    @FXML private VBox slotCambio4, slotCambio5, slotCambio6;
    @FXML private ImageView imgCambio1, imgCambio2, imgCambio3;
    @FXML private ImageView imgCambio4, imgCambio5, imgCambio6;
    @FXML private Label lblCambio1, lblCambio2, lblCambio3;
    @FXML private Label lblCambio4, lblCambio5, lblCambio6;
    @FXML private ProgressBar hpCambio1, hpCambio2, hpCambio3;
    @FXML private ProgressBar hpCambio4, hpCambio5, hpCambio6;


    private Pokemon pokemonJugadorActual;


    private Pokemon pokemonRivalActual;

    private int koJugador = 0;
    private int koRival   = 0;

    private int turno = 1;

    private boolean combateEnPausa = false;


    private final Random random = new Random();



    @FXML
    public void initialize() {

        // TODO: Coger el primer pokemon vivo del equipo del jugador
        // y guardarlo en pokemonJugadorActual
        // Ejemplo: pokemonJugadorActual = Main.miEquipo.get(0);

        // TODO: Generar el pokemon rival aleatorio
        // y guardarlo en pokemonRivalActual
        // Puedes usar PokedexDAO para sacar una especie aleatoria

        // TODO: Llamar a actualizarPantalla() para mostrar
        // los datos en la pantalla

        // TODO: Escribir en el log el mensaje de inicio
        // Ejemplo: log("Un Pikachu salvaje aparecio!");
    }

    // ══════════════════════════════════════════════════
    // BOTON LUCHAR - muestra el panel con los 4 movimientos
    // ══════════════════════════════════════════════════

    @FXML
    private void handleLuchar(ActionEvent event) {

        // TODO: Mostrar el panelMovimientos
        // y ocultar el panelAcciones

        // TODO: Poner los nombres de los movimientos
        // en los botones btnMovimiento1, 2, 3, 4
        // Sacalos de pokemonJugadorActual.getMovimientos()
    }

    // ══════════════════════════════════════════════════
    // BOTONES DE MOVIMIENTO - cada uno llama a
    // ejecutarMovimiento() con el indice del movimiento
    // ══════════════════════════════════════════════════

    @FXML
    private void handleMovimiento1(ActionEvent event) {
        // TODO: Llamar a ejecutarMovimiento(0)
    }

    @FXML
    private void handleMovimiento2(ActionEvent event) {
        // TODO: Llamar a ejecutarMovimiento(1)
    }

    @FXML
    private void handleMovimiento3(ActionEvent event) {
        // TODO: Llamar a ejecutarMovimiento(2)
    }

    @FXML
    private void handleMovimiento4(ActionEvent event) {
        // TODO: Llamar a ejecutarMovimiento(3)
    }

    // ══════════════════════════════════════════════════
    // EJECUTAR MOVIMIENTO - aqui va toda la logica
    // de un turno de combate:
    // 1. El jugador ataca al rival
    // 2. El rival ataca al jugador
    // 3. Comprobamos si alguno se ha debilitado
    // ══════════════════════════════════════════════════

    private void ejecutarMovimiento(int indice) {

        // TODO: Comprobar que combateEnPausa es false
        // Si es true, no hacemos nada (ya se esta ejecutando un turno)

        // TODO: Poner combateEnPausa = true para bloquear
        // los botones mientras se ejecuta el turno

        // TODO: Volver al panelAcciones (ocultar panelMovimientos)

        // TODO: Calcular el dano que hace el jugador al rival
        // usando calcularDano()

        // TODO: Calcular el dano que hace el rival al jugador
        // usando calcularDanoRival()

        // TODO: Usar un Timeline para mostrar los mensajes
        // con un retardo de 1 segundo entre cada uno.
        // El Timeline debe hacer esto en orden:
        //   - Segundo 0: mostrar "X uso Y!"
        //   - Segundo 1: aplicar dano al rival y actualizar su barra
        //   - Segundo 2: comprobar si el rival se debilito
        //   - Segundo 3: si sigue vivo, el rival ataca
        //   - Segundo 4: comprobar si el jugador se debilito
        //   - Al final: subir el turno y poner combateEnPausa = false
    }

    // ══════════════════════════════════════════════════
    // CALCULAR DANO - formula sencilla para calcular
    // cuanto dano hace un pokemon al otro
    // ══════════════════════════════════════════════════

    private int calcularDano(Pokemon atacante, Pokemon defensor, int indiceMovimiento) {

        // TODO: Sacar la potencia del movimiento si lo tiene
        // Si no tiene movimientos, usar el ataque base del pokemon

        // TODO: Aplicar la formula:
        // dano = (ataque del atacante - defensa del defensor) + numero aleatorio
        // El dano minimo siempre debe ser 1 (usar Math.max(1, dano))

        return 0; // Quitar este return cuando implementes el metodo
    }

    private int calcularDanoRival() {

        // TODO: Es igual que calcularDano pero el atacante
        // es pokemonRivalActual y el defensor es pokemonJugadorActual
        // El rival elige un movimiento aleatorio con random.nextInt()

        return 0; // Quitar este return cuando implementes el metodo
    }

    // ══════════════════════════════════════════════════
    // BOTON POKEMON - muestra el panel para cambiar
    // de pokemon durante el combate
    // ══════════════════════════════════════════════════

    @FXML
    private void handleCambiarPokemon(ActionEvent event) {

        // TODO: Rellenar los slots con los datos del equipo
        // usando Main.miEquipo (nombre, barra de vida, sprite)

        // TODO: Mostrar el panelCambioPokemon
        // y ocultar el panelAcciones

        // TODO: Asignar un setOnMouseClicked a cada slot
        // para que al hacer clic se llame a cambiarPokemon(indice)
    }

    private void cambiarPokemon(int indice) {

        // TODO: Comprobar que el pokemon seleccionado no esta debilitado
        // Si lo esta, mostrar un mensaje en el log y no hacer nada

        // TODO: Comprobar que no es el mismo pokemon que ya esta en combate

        // TODO: Cambiar pokemonJugadorActual al pokemon seleccionado

        // TODO: Llamar a actualizarPantalla()

        // TODO: Volver al panelAcciones
    }

    // ══════════════════════════════════════════════════
    // BOTON MOCHILA - para usar objetos en combate
    // ══════════════════════════════════════════════════

    @FXML
    private void handleMochila(ActionEvent event) {

        // TODO: Mostrar los objetos disponibles en la mochila
        // Por ahora puedes poner solo un mensaje en el log
        // diciendo que la mochila no esta implementada todavia
    }

    // ══════════════════════════════════════════════════
    // BOTON HUIR - el jugador abandona el combate
    // Siempre puede huir, pero el rival gana
    // ══════════════════════════════════════════════════

    @FXML
    private void handleHuir(ActionEvent event) {

        // TODO: Escribir en el log "Has huido del combate!"

        // TODO: Cargar la escena EscenaMenu.fxml
        // usando FXMLLoader igual que en los otros controllers
    }

    // ══════════════════════════════════════════════════
    // BOTON VOLVER - vuelve al panel de acciones
    // principal desde el panel de movimientos
    // o desde el panel de cambio de pokemon
    // ══════════════════════════════════════════════════

    @FXML
    private void handleVolverAcciones(ActionEvent event) {

        // TODO: Mostrar el panelAcciones
        // y ocultar los demas paneles
    }

    // ══════════════════════════════════════════════════
    // FINALIZAR COMBATE - se llama cuando alguno de
    // los dos llega a 6 KO o cuando no quedan pokemon
    // ══════════════════════════════════════════════════

    private void finalizarCombate(boolean ganoJugador) {

        // TODO: Si ganoJugador es true:
        //   - Calcular experiencia con la formula:
        //     (nivelJugador + nivelRival * 10) / 4
        //   - Sumarle la experiencia al pokemon jugador
        //   - El rival pierde 1/3 de sus pokedollars
        //   - Escribir en el log "Ganaste el combate!"

        // TODO: Si ganoJugador es false:
        //   - El jugador pierde 1/3 de sus pokedollars
        //   - Escribir en el log "Perdiste el combate..."

        // TODO: Desactivar todos los botones para que
        // no se pueda seguir jugando
        // panelAcciones.setDisable(true);
    }

    // ══════════════════════════════════════════════════
    // ACTUALIZAR PANTALLA - refresca todos los labels,
    // barras de vida y sprites con los datos actuales
    // ══════════════════════════════════════════════════

    private void actualizarPantalla() {

        // TODO: Actualizar el label del nombre del rival
        // lblNombreRival.setText(pokemonRivalActual.getNombre());

        // TODO: Actualizar el label del nivel del rival
        // lblNivelRival.setText("Nv." + pokemonRivalActual.getNivel());

        // TODO: Actualizar la barra de vida del rival
        // hpBarRival.setProgress((double) vida / vidaMaxima);

        // TODO: Lo mismo para el jugador con sus labels y barra

        // TODO: Cargar los sprites (gifs) de los pokemon
        // cargarSprite(spriteRival, pokemonRivalActual, true);
        // cargarSprite(spriteJugador, pokemonJugadorActual, false);

        // TODO: Actualizar el label del turno
        // lblTurno.setText("Turno " + turno);
    }

    // ══════════════════════════════════════════════════
    // CARGAR SPRITE - carga el gif del pokemon en el
    // ImageView. Si frontal es true carga el gif de
    // frente, si es false carga el de espalda
    // ══════════════════════════════════════════════════

    private void cargarSprite(ImageView imageView, Pokemon pokemon, boolean frontal) {

        // TODO: Construir la ruta del gif segun si es frontal o espalda
        // Frontal:  "/spritesPokemonsGifsFront/" + numPokedex + ".gif"
        // Espalda:  "/spritesPokemonsGifsBack/"  + numPokedex + ".gif"

        // TODO: Cargar la imagen con getClass().getResourceAsStream(ruta)
        // y asignarla al imageView con imageView.setImage(new Image(is))

        // TODO: Manejar el caso de que la imagen no exista (try/catch)
    }

    // ══════════════════════════════════════════════════
    // METODO LOG - escribe un mensaje en el TextArea
    // del combate y tambien lo imprime en consola
    // ══════════════════════════════════════════════════

    private void log(String mensaje) {

        // TODO: Escribir el mensaje en txtLog
        // txtLog.appendText(mensaje + "\n");

        // TODO: Imprimir en consola para debug
        // System.out.println("[Combate] " + mensaje);
    }

    // ══════════════════════════════════════════════════
    // MOSTRAR PANEL - muestra un panel y oculta los
    // demas. Solo puede estar visible uno a la vez.
    // ══════════════════════════════════════════════════

    private void mostrarPanel(Object panel) {

        // TODO: Ocultar todos los paneles primero:
        // panelAcciones.setVisible(false);
        // panelAcciones.setManaged(false);
        // panelMovimientos.setVisible(false);
        // panelMovimientos.setManaged(false);
        // panelCambioPokemon.setVisible(false);
        // panelCambioPokemon.setManaged(false);

        // TODO: Mostrar solo el panel que nos pasan
        // comprobando si es HBox o VBox con instanceof
    }
}