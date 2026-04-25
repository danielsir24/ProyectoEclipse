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
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import java.util.regex.Pattern;

public class CombateController {

    
    //Jugador
    @FXML private Label lblNombreJugador, lblNivelJugador, lblHpJugador, lblStaminaJugador;
    @FXML private ProgressBar hpBarJugador, staminaBarJugador;
    @FXML private ImageView imgJugador;
    
    //Rival
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
        // 1. Obtener el primer Pokemon de tu equipo
        Pokemon miPokemon = obtenerPrimerPokemonVivo();
        if (miPokemon == null) {
            txtLogCombate.setText("¡No tienes Pokémon en condiciones para luchar!\n");
            desactivarBotones();
            return;
        }

        Entrenador rivalCombate;
        Pokemon pokemonRival;

        // 2. DETECCIÓN: ¿Venimos de la Liga o es un combate Salvaje?
        if (Main.rivalActual != null && "ALTO_MANDO".equals(Main.rivalActual.getTipo_Entrenador())) {
            
            // LÓGICA DE LIGA POKÉMON
            rivalCombate = Main.rivalActual;
            pokemonRival = generarRivalAleatorio(); // Genera un pokemon que escale a tu nivel
            txtLogCombate.setText("¡El Alto Mando " + rivalCombate.getNom_Entrenador() + " te desafía!\n");
            
        } else {
            
            // LÓGICA DE COMBATE SALVAJE
            rivalCombate = new Entrenador();
            rivalCombate.setNom_Entrenador("Entrenador Rival");
            rivalCombate.setTipo_Entrenador("SALVAJE"); // Para evitar confusiones
            pokemonRival = generarRivalAleatorio();
            txtLogCombate.setText("¡Un " + pokemonRival.getNombre() + " salvaje ha aparecido!\n");
            
        }

        // 3. Inicializar el motor de combate con los datos correctos
        motorCombate = new Combate(Main.entrenadorLogueado, rivalCombate, miPokemon, pokemonRival);

        // 4. Cargar sonidos base
        try {
            sonidoAtaque = new AudioClip(getClass().getResource("/sounds/tackle.wav").toExternalForm());
        } catch (Exception e) {
            System.out.println("Sonido de ataque no encontrado. Se omitirá.");
        }

        String nombreMote = miPokemon.getMote() != null && !miPokemon.getMote().isEmpty() ? miPokemon.getMote() : miPokemon.getNombre();
        txtLogCombate.appendText("¡Adelante, " + nombreMote + "!\n\n");
        
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
    private void handleCapturar(ActionEvent event) {
        if (motorCombate.getEntrenadorRival() != null && 
            !"SALVAJE".equals(motorCombate.getEntrenadorRival().getTipo_Entrenador())) {
            txtLogCombate.appendText("¡No puedes capturar el Pokémon de otro entrenador!\n");
            return;
        }

        if (Math.random() <= (2.0 / 3.0)) { // Probabilidad 2/3
            txtLogCombate.appendText("¡La Pokéball ha capturado al Pokémon!\n");
            
            Pokemon pokemonCapturado = motorCombate.getPokemonRival();
            
            // 1. Solicitar y validar mote con Regex
            String mote = solicitarMoteValidado();
            pokemonCapturado.setMote(mote);
            
            // 2. Recalcular estadísticas 
            pokemonCapturado.inicializarEstadisticasBase(); 
            
            // 3. Guardar en la caja del entrenador
            try {
                // 1. Instanciar el DAO
                pokemon.PokemonDAO pDAO = new pokemon.PokemonDAO();
                
                // 2. Obtener el ID del entrenador
                int idDuenio = Main.entrenadorLogueado.getId_Entrenador();
                
                // 3. Ejecutar la persistencia
                int ubicacionDestino = (Main.miEquipo.size() < 6) ? 1 : 0;
                boolean exito = pDAO.guardarPokemon(pokemonCapturado, idDuenio, ubicacionDestino);
                
                if (exito) {
                    txtLogCombate.appendText("¡" + mote + " ha sido guardado en tu caja!\n");
                } else {
                    txtLogCombate.appendText("Error: No se pudo guardar en la base de datos.\n");
                }
            } catch (Exception e) {
                txtLogCombate.appendText("Error al guardar en la caja: " + e.getMessage() + "\n");
            }
        }
    }
    
    @FXML
    private void handleDescansar(ActionEvent event) {
        Pokemon miPokemon = motorCombate.getPokemonJugador();
        miPokemon.setEstamina(100);
        
        txtLogCombate.appendText(miPokemon.getNombre() + " descansa y recupera su estamina.\n");
        
        // El rival ataca mientras descansamos
        String resultadoRival = motorCombate.ejecutarTurno(new pokemon.Movimiento("Descanso", 0, pokemon.Tipo.NORMAL,"ESTADO",0, 999)); 
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

        //Actualizar Jugador
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
            btnHuir.setText("Salir"); //Cambiamos el texto de Huir para que sea el botón de salir
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
        rival.setNivel(nivelMaximo);
        
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
    private String solicitarMoteValidado() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Mote del Pokémon");
        dialog.setHeaderText("¡Has capturado un Pokémon!");
        dialog.setContentText("Introduce su mote (solo letras):");

        // Expresión regular: ^[a-zA-Z]+$ (Solo letras de la A a la Z, mayúsculas o minúsculas) 
        String regex = "^[a-zA-Z]+$";
        
        while (true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String input = result.get().trim();
                if (Pattern.matches(regex, input)) {
                    return input; // Mote válido
                } else {
                    dialog.setHeaderText("Mote inválido. Solo letras, sin espacios ni números.");
                }
            } else {
                // Si el usuario cancela o cierra, devolvemos el nombre original por defecto
                return motorCombate.getPokemonRival().getNombre();
            }
        }
    }

    private void volverAlMenu(ActionEvent event) {
        try {
            // 1. Definimos la ruta por defecto (combate salvaje o normal)
            String rutaEscena = "/EscenaMenu.fxml";
            
            // 2. Extraemos el objeto rival directamente del motor de combate
            Entrenador rival = motorCombate.getEntrenadorRival(); 

            // 3. Verificamos si el rival pertenece al Alto Mando
            if (rival != null && "ALTO_MANDO".equals(rival.getTipo_Entrenador())) {
                
                // Si el jugador ganó (el Pokémon rival está debilitado)
                if (motorCombate.getPokemonRival().estaDebilitado()) {
                    // Incrementamos el progreso de la liga y volvemos a la pantalla de la Liga
                    LigaController.combateActual++; 
                    rutaEscena = "/EscenaLiga.fxml";
                } else {
                    // Si el jugador perdió, la liga se resetea y vuelve al menú principal
                    LigaController.resetearLiga();
                    rutaEscena = "/EscenaMenu.fxml";
                }
            }

            // 4. Cambiamos la escena
            Parent root = FXMLLoader.load(getClass().getResource(rutaEscena));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.err.println("Error crítico al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
