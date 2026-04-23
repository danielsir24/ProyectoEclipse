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
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import pokemon.Entrenador;
import pokemon.Main;
import pokemon.Pokemon;
import pokemon.Pokedex;
import pokemon.PokedexDAO;
import javafx.scene.media.AudioClip;
import pokemon.Combate;

public class CombateController {

    // ==== ELEMENTOS DE LA INTERFAZ (FXML) ====
    
    //Jugador
    @FXML private Label lblNombreJugador, lblNivelJugador, lblHpJugador, lblStaminaJugador;
    @FXML private ProgressBar hpBarJugador, staminaBarJugador;
    @FXML private ImageView imgJugador;
    
    // Rival
    @FXML private Label lblNombreRival, lblNivelRival, lblHpRival, lblStaminaRival;
    @FXML private ProgressBar hpBarRival, staminaBarRival;
    @FXML private ImageView imgRival;
    
    //Controles
    @FXML private Button btnAtaque1, btnAtaque2, btnAtaque3, btnAtaque4;
    @FXML private Button btnDescansar, btnHuir, btnSonidoJugador;
    @FXML private TextArea txtLogCombate;

    //VARIABLES INTERNAS
    private Combate motorCombate;
    private AudioClip sonidoAtaque;

    @FXML
    public void initialize() {
        //1.Obtener el primer Pokémon
        Pokemon miPokemon = obtenerPrimerPokemonVivo();
        if (miPokemon == null) {
            txtLogCombate.setText("¡No tienes Pokémon en condiciones para luchar!\n");
            desactivarBotones();
            return;
        }

        // 2.Generar un Pokémon Rival aleatorio (Requisito: nivel máximo de nuestro equipo)
        Pokemon rivalAleatorio = generarRivalAleatorio();

        // 3.Crear un Entrenador Rival falso para el combate salvaje
        Entrenador rivalMalo = new Entrenador();
        rivalMalo.setNom_Entrenador("Entrenador Rival");

        // 4.Inicializar el motor de combate
        motorCombate = new Combate(Main.entrenadorLogueado, rivalMalo, miPokemon, rivalAleatorio);

        // 5.Cargar sonidos base
        try {
            sonidoAtaque = new AudioClip(getClass().getResource("/sounds/tackle.wav").toExternalForm());
        } catch (Exception e) {
            System.out.println("Sonido de ataque no encontrado. Se omitirá.");
        }

        txtLogCombate.setText("¡Un " + rivalAleatorio.getNombre() + " salvaje ha aparecido!\n¡Adelante, " + miPokemon.getMote() + "!\n\n");
        
        actualizarUI();
    }

    //ACCIONES DE LOS BOTONES

    @FXML
    private void handleAtaque(ActionEvent event) {
        Button btnClick = (Button) event.getSource();
        int indiceAtaque = Integer.parseInt(btnClick.getUserData().toString());
        
        Pokemon miPokemon = motorCombate.getPokemonJugador();
        
        if (indiceAtaque < miPokemon.getMovimientos().size()) {
            pokemon.Movimiento mov = miPokemon.getMovimientos().get(indiceAtaque);
            
            //Requisito: Validar estamina
            if (miPokemon.getEstamina() < mov.getCosteEstamina()) {
                txtLogCombate.appendText("¡No tienes suficiente estamina para usar " + mov.getNombre() + "!\n");
                return;
            }

            if (sonidoAtaque != null) sonidoAtaque.play();
            
            //Ejecutar el turno en el motor y volcar el resultado en el TextArea
            String resultadoTurno = motorCombate.ejecutarTurno(mov);
            txtLogCombate.appendText(resultadoTurno + "\n");
            
            actualizarUI();
            comprobarFinCombate();
        }
    }

    @FXML
    private void handleDescansar(ActionEvent event) {
        Pokemon miPokemon = motorCombate.getPokemonJugador();
        miPokemon.setEstamina(100); // Requisito: Restaurar estamina
        
        txtLogCombate.appendText(miPokemon.getNombre() + " descansa y recupera su estamina.\n");
        
        // El rival ataca mientras descansamos (Turno ciego)
        String resultadoRival = motorCombate.ejecutarTurno(new pokemon.Movimiento("Descanso", 0, 0, 999)); 
        txtLogCombate.appendText(resultadoRival + "\n");
        
        actualizarUI();
        comprobarFinCombate();
    }

    @FXML
    private void handleHuir(ActionEvent event) {
        txtLogCombate.appendText("¡Has huido del combate!\n");
        volverAlMenu(event);
    }
    
    @FXML
    private void handleEmitirSonido() {
        Pokemon miPokemon = motorCombate.getPokemonJugador();
        if (miPokemon.getInfoPokedex() != null) {
             System.out.println("Aquí iría el grito del Pokémon " + miPokemon.getNombre());
             // Aquí puedes añadir la ruta real: new AudioClip(getClass().getResource(miPokemon.getInfoPokedex().getSonido()).toExternalForm()).play();
        }
    }

    //MÉTODOS DE APOYO

    private void actualizarUI() {
        Pokemon pJugador = motorCombate.getPokemonJugador();
        Pokemon pRival = motorCombate.getPokemonRival();

        // --- Actualizar Jugador ---
        lblNombreJugador.setText(pJugador.getMote() != null ? pJugador.getMote() : pJugador.getNombre());
        lblNivelJugador.setText("Nv. " + pJugador.getNivel());
        lblHpJugador.setText(pJugador.getVitalidad() + "/" + pJugador.getVitalidadMaxima());
        hpBarJugador.setProgress((double) pJugador.getVitalidad() / pJugador.getVitalidadMaxima());
        
        lblStaminaJugador.setText(pJugador.getEstamina() + "/100");
        staminaBarJugador.setProgress((double) pJugador.getEstamina() / 100.0);
        
        cargarSprite(imgJugador, pJugador.getInfoPokedex().getNum_Pokedex(), false); // false = vista trasera

        //Actualizar Rival
        lblNombreRival.setText(pRival.getNombre());
        lblNivelRival.setText("Nv. " + pRival.getNivel());
        lblHpRival.setText(pRival.getVitalidad() + "/" + pRival.getVitalidadMaxima());
        hpBarRival.setProgress((double) pRival.getVitalidad() / pRival.getVitalidadMaxima());
        
        lblStaminaRival.setText(pRival.getEstamina() + "/100");
        staminaBarRival.setProgress((double) pRival.getEstamina() / 100.0);
        
        cargarSprite(imgRival, pRival.getInfoPokedex().getNum_Pokedex(), true); // true = vista frontal

        //Actualizar Botones de Ataque
        Button[] botones = {btnAtaque1, btnAtaque2, btnAtaque3, btnAtaque4};
        for (int i = 0; i < 4; i++) {
            if (i < pJugador.getMovimientos().size()) {
                botones[i].setText(pJugador.getMovimientos().get(i).getNombre());
                botones[i].setDisable(false);
            } else {
                botones[i].setText("-");
                botones[i].setDisable(true);
            }
        }
    }

    private void comprobarFinCombate() {
        if (motorCombate.getPokemonJugador().estaDebilitado() || motorCombate.getPokemonRival().estaDebilitado()) {
            desactivarBotones();
            btnHuir.setText("Salir"); // Cambiamos el texto de Huir para que sea el botón de salir
        }
    }

    private void desactivarBotones() {
        btnAtaque1.setDisable(true);
        btnAtaque2.setDisable(true);
        btnAtaque3.setDisable(true);
        btnAtaque4.setDisable(true);
        btnDescansar.setDisable(true);
    }

    private Pokemon obtenerPrimerPokemonVivo() {
        for (Pokemon p : Main.miEquipo) {
            if (!p.estaDebilitado()) return p;
        }
        return null;
    }

    private Pokemon generarRivalAleatorio() {
        int nivelMaximo = 1;
        for (Pokemon p : Main.miEquipo) {
            if (p.getNivel() > nivelMaximo) nivelMaximo = p.getNivel();
        }

        PokedexDAO pxDAO = new PokedexDAO();
        Pokedex especie = pxDAO.buscarPorIdPokedex(pxDAO.generarIdPokedexAleatorio());
        
        Pokemon rival = new Pokemon();
        rival.setInfoPokedex(especie);
        rival.setNombre(especie.getNombreEspecie());
        rival.setNivel(nivelMaximo); // Requisito de nivel
        
        //Ajustamos la vida
        rival.setVitalidadMaxima(20 + (nivelMaximo * 3));
        rival.setVitalidad(rival.getVitalidadMaxima());
        rival.setAtaque(5 + (nivelMaximo * 2));
        rival.setDefensa(5 + (nivelMaximo * 2));
        rival.setEstamina(100);
        
        return rival;
    }

    private void cargarSprite(ImageView imgView, int numPokedex, boolean frontal) {
        String carpeta = frontal ? "Front" : "Back";
        String ruta = "/spritesPokemons/" + carpeta + "/" + numPokedex + ".png";
        try {
            InputStream is = getClass().getResourceAsStream(ruta);
            if (is != null) imgView.setImage(new Image(is));
        } catch (Exception e) {
            System.err.println("Imagen no encontrada: " + ruta);
        }
    }

    private void volverAlMenu(ActionEvent event) {
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
