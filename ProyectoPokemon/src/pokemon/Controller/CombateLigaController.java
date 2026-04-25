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
import javafx.stage.Stage;
import pokemon.Entrenador;
import pokemon.EntrenadorDAO;
import pokemon.Liga;
import pokemon.Main;
import pokemon.Pokemon;

import java.io.IOException;
import java.io.InputStream;

public class CombateLigaController {
    
    private Pokemon pokemonJugadorActual;
    private Pokemon pokemonRivalActual; // RECUPERADO: El Pokémon Rival
    
    private Liga partidaLiga;
    private Entrenador entrenadorJugador;
    private EntrenadorDAO entrenadorDAO = new EntrenadorDAO();
    
    private int koJugador = 0; // CORREGIDO: Declarado a nivel de clase
    private int koRival = 0;
    private int turno = 1;
    private boolean combateEnPausa = false;
    private final java.util.Random random = new java.util.Random();

    // --- ELEMENTOS---
    @FXML private Label lblNombreRival;
    @FXML private Label lblNivelRival;
    @FXML private ProgressBar hpBarRival;
    @FXML private ImageView spritePokemonRival;
    @FXML private TextArea txtLog;
    @FXML private Label lblTurno;
    
    @FXML private Button btnMovimiento1;
    @FXML private Button btnMovimiento2;
    @FXML private Button btnMovimiento3;
    @FXML private Button btnMovimiento4;
    
    @FXML private Label lblNombreJugador;
    @FXML private Label lblNivelJugador;
    @FXML private Label lblPsJugador;
    @FXML private ProgressBar hpBarJugador;
    @FXML private ImageView spritePokemon;

    // ELEMENTOS DA INTERFACE
    @FXML private VBox panelAcciones;      
    @FXML private HBox panelMovimientos;   
    @FXML private VBox panelCambioPokemon; 
    
    @FXML private Button btnLuchar, btnMochila, btnPokemon, btnHuir;

    @FXML private VBox slotCambio1, slotCambio2, slotCambio3, slotCambio4, slotCambio5, slotCambio6;
    @FXML private ImageView imgCambio1, imgCambio2, imgCambio3, imgCambio4, imgCambio5, imgCambio6;
    @FXML private Label lblCambio1, lblCambio2, lblCambio3, lblCambio4, lblCambio5, lblCambio6;
    @FXML private ProgressBar hpCambio1, hpCambio2, hpCambio3, hpCambio4, hpCambio5, hpCambio6;

    @FXML
    public void initialize() {
        if (panelAcciones != null) panelAcciones.setVisible(true);
        if (panelMovimientos != null) panelMovimientos.setVisible(false);
        if (panelCambioPokemon != null) panelCambioPokemon.setVisible(false);

        this.partidaLiga = LigaController.partidaActual; 
        if (this.partidaLiga == null) {
            this.partidaLiga = new Liga();
            this.partidaLiga.setCombateActual(LigaController.combateActual);
        }

       
        if (Main.entrenadorLogueado != null) {
            pokemon.PokemonDAO pDAO = new pokemon.PokemonDAO();
            Main.miEquipo = pDAO.obtenerEquipo(Main.entrenadorLogueado.getId_Entrenador());
        }

        // 2. Cargar el primer pokemon vivo del equipo
        if (Main.miEquipo != null) {
            for (Pokemon p : Main.miEquipo) {
                if (!p.estaDebilitado()) {
                    pokemonJugadorActual = p;
                    break;
                }
            }
        }
        
        
        if (pokemonJugadorActual == null) {
            System.err.println("CRÍTICO: No tienes Pokémon vivos para combatir.");
            log("No tienes Pokémon en condiciones de luchar. Huye del combate.");
            if (panelAcciones != null) panelAcciones.setDisable(true);
            return; // Salimos de la función para que no lance NullPointerException
        }

        pokemonRivalActual = new Pokemon();
        pokemonRivalActual.setNombre("Rival Liga");
        pokemonRivalActual.setMote("Rival Liga");
     // Sincronizamos el nivel y los stats para que el combate sea justo
        pokemonRivalActual.setNivel(pokemonJugadorActual.getNivel()); 
        pokemonRivalActual.setAtaque(pokemonJugadorActual.getAtaque());
        pokemonRivalActual.setDefensa(pokemonJugadorActual.getDefensa());

        // Calculamos una vida base razonable según el nivel
        int hpBase = 20 + (pokemonRivalActual.getNivel() * 2);
        pokemonRivalActual.setVitalidadMaxima(hpBase);
        pokemonRivalActual.setVitalidad(hpBase);

        // Asignamos un tipo para evitar errores en el cálculo de efectividad
        java.util.List<pokemon.Tipo> tiposRival = new java.util.ArrayList<>();
        tiposRival.add(pokemon.Tipo.NORMAL);
        pokemonRivalActual.setTipos(tiposRival);
        
        actualizarPantalla(); 
        log("¡Comienza el combate de Liga Nivel: " + this.partidaLiga.getCombateActual() + "!");
    }
    @FXML
    private void handleLuchar(ActionEvent event) {
        
        Button[] botones = { btnMovimiento1, btnMovimiento2, btnMovimiento3, btnMovimiento4 };

        if (pokemonJugadorActual.getMovimientos() == null || pokemonJugadorActual.getMovimientos().isEmpty()) {
            btnMovimiento1.setText("Placaje");
            for(int i=1; i<4; i++) { botones[i].setText("---"); botones[i].setDisable(true); }
        } else {
            for (int i = 0; i < 4; i++) {
                if (i < pokemonJugadorActual.getMovimientos().size()) {
                    botones[i].setText(pokemonJugadorActual.getMovimientos().get(i).getNombre());
                    botones[i].setDisable(false);
                } else {
                    botones[i].setText("---");
                    botones[i].setDisable(true);
                }
            }
        }
        mostrarPanel(panelMovimientos);
    }

    @FXML
    private void handleMochila(ActionEvent event) {
        Main.venimosDeCombate = true;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaMochila.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }
   
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
                    } catch (Exception e) { }
                }

                slots[i].setOpacity(p.estaDebilitado() ? 0.4 : 1.0);
                final int indice = i;
                slots[i].setOnMouseClicked(e -> cambiarPokemon(indice));

            } else {
                nombres[i].setText("---"); barras[i].setProgress(0); imgs[i].setImage(null); slots[i].setOnMouseClicked(null);
            }
        }
        mostrarPanel(panelCambioPokemon);
    }

    private void cambiarPokemon(int indice) {
        Pokemon seleccionado = Main.miEquipo.get(indice);

        if (seleccionado.estaDebilitado()) { log(seleccionado.getMote() + " no puede pelear, esta muerto"); return; }
        if (seleccionado == pokemonJugadorActual) { log(seleccionado.getMote() + " ya esta peleando"); return; }

        pokemonJugadorActual = seleccionado;
        actualizarPantalla(); 
        log("¡Adelante " + seleccionado.getMote() + "!");
        mostrarPanel(panelAcciones);
    }

    private void actualizarPantalla() {
        if (pokemonJugadorActual == null) return;

        // --- ACTUALIZAR JUGADOR ---
        String mote = (pokemonJugadorActual.getMote() != null && !pokemonJugadorActual.getMote().isEmpty())
                ? pokemonJugadorActual.getMote() : pokemonJugadorActual.getNombre();
        
        if (lblNombreJugador != null) lblNombreJugador.setText(mote);
        if (lblNivelJugador != null) lblNivelJugador.setText("Nv." + pokemonJugadorActual.getNivel());
        if (lblPsJugador != null) lblPsJugador.setText(pokemonJugadorActual.getVitalidad() + "/" + pokemonJugadorActual.getVitalidadMaxima());
        if (hpBarJugador != null) hpBarJugador.setProgress(Math.max(0, (double) pokemonJugadorActual.getVitalidad() / pokemonJugadorActual.getVitalidadMaxima()));
        cargarSprite(spritePokemon, pokemonJugadorActual, false);

        // --- ACTUALIZAR RIVAL (CORREGIDO) ---
        if (pokemonRivalActual != null) {
            if (lblNombreRival != null) lblNombreRival.setText(pokemonRivalActual.getNombre());
            if (lblNivelRival != null) lblNivelRival.setText("Nv." + pokemonRivalActual.getNivel());
            if (hpBarRival != null) hpBarRival.setProgress(Math.max(0, (double) pokemonRivalActual.getVitalidad() / pokemonRivalActual.getVitalidadMaxima()));
            cargarSprite(spritePokemonRival, pokemonRivalActual, true);
        }

        if (lblTurno != null) lblTurno.setText("Turno " + turno);
    }

    private void cargarSprite(ImageView imageView, Pokemon pokemon, boolean frontal) {
        if (pokemon == null || pokemon.getInfoPokedex() == null || imageView == null) return;

        String carpeta = frontal ? "spritesPokemonsGifsFront" : "spritesPokemonsGifsBack";
        String ruta = "/" + carpeta + "/" + pokemon.getInfoPokedex().getNum_Pokedex() + ".gif";

        try {
            InputStream is = getClass().getResourceAsStream(ruta);
            if (is != null) imageView.setImage(new Image(is));
        } catch (Exception e) { }
    }

    private void mostrarPanel(Object panel) {
        panelAcciones.setVisible(false);
        panelMovimientos.setVisible(false);
        panelMovimientos.setManaged(false);
        panelCambioPokemon.setVisible(false);
        panelCambioPokemon.setManaged(false);

        if (panel == panelAcciones) {
            panelAcciones.setVisible(true);
        } else if (panel == panelMovimientos) {
            panelMovimientos.setVisible(true);
            panelMovimientos.setManaged(true); 
        } else if (panel == panelCambioPokemon) {
            panelCambioPokemon.setVisible(true);
            panelCambioPokemon.setManaged(true); 
        }
    }

    public void finalizarCombate(boolean victoriaJugador) {
        if (Main.entrenadorLogueado == null) return;
        int dineroActual = Main.entrenadorLogueado.getPokedollars();

        if (victoriaJugador) {
            if (partidaLiga != null) {
                int premio = partidaLiga.calcularPremio(); 
                Main.entrenadorLogueado.setPokedollars(dineroActual + premio);
                log("¡Victoria! Has ganado " + premio + " pokédollars.");
                partidaLiga.setCombateActual(partidaLiga.getCombateActual() + 1);
                partidaLiga.setHaDescansado(false); 
            }
        } else {
            int nuevoSaldo = dineroActual / 2;
            Main.entrenadorLogueado.setPokedollars(nuevoSaldo);
            log("Derrota o Huida. Nuevo saldo: " + nuevoSaldo);
            // Asegurarse de que este método exista en LigaController
            // LigaController.resetearLiga(); 
        }

        entrenadorDAO.actualizarPokedollars(Main.entrenadorLogueado.getId_Entrenador(), Main.entrenadorLogueado.getPokedollars());
        volverAlMenuPrincipal();
    }

    @FXML
    private void handleHuir(ActionEvent event) {
        finalizarCombate(false);
    }

    private void volverAlMenuPrincipal() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/EscenaLiga.fxml"));
            Stage stage = (Stage) panelAcciones.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void cancelarAccion(ActionEvent event) {
        mostrarPanel(panelAcciones);
    }

    @FXML
    private void handleMovimiento1(ActionEvent event) { ejecutarMovimiento(0); }
    @FXML
    private void handleMovimiento2(ActionEvent event) { ejecutarMovimiento(1); }
    @FXML
    private void handleMovimiento3(ActionEvent event) { ejecutarMovimiento(2); }
    @FXML
    private void handleMovimiento4(ActionEvent event) { ejecutarMovimiento(3); }

    private void ejecutarMovimiento(int indice) {
        if (combateEnPausa) return;
        combateEnPausa = true;

        mostrarPanel(panelAcciones);

        pokemon.Movimiento movimiento;
        if (pokemonJugadorActual.getMovimientos() != null && !pokemonJugadorActual.getMovimientos().isEmpty() && indice < pokemonJugadorActual.getMovimientos().size()) {
            movimiento = pokemonJugadorActual.getMovimientos().get(indice);
        } else {
            movimiento = new pokemon.Movimiento("Placaje", 40, pokemon.Tipo.NORMAL, "ATAQUE", 0, 0);
        }

        if (pokemonJugadorActual.getEstamina() < movimiento.getCosteEstamina()) {
            log(pokemonJugadorActual.getMote() + " no tiene estamina para usar " + movimiento.getNombre() + "!");
            combateEnPausa = false;
            return;
        }

        pokemonJugadorActual.setEstamina(pokemonJugadorActual.getEstamina() - movimiento.getCosteEstamina());

        double efectividad = 1.0;
        String mensajeEfectividad = "";
        String resultadoTipo = "NEUTRO";

        // CORREGIDO: Comprobar tipos contra el RIVAL, no contra ti mismo
        if (pokemonRivalActual.getTipos() != null && !pokemonRivalActual.getTipos().isEmpty()) {
            pokemon.Tipo tipo1 = pokemonRivalActual.getTipos().get(0);
            pokemon.Tipo tipo2 = pokemonRivalActual.getTipos().size() > 1 ? pokemonRivalActual.getTipos().get(1) : null;
            efectividad = movimiento.getTipo().calcularEfectividadDoble(tipo1, tipo2);
        }

        if (efectividad >= 4.0) { resultadoTipo = "DOBLE_VENTAJA"; mensajeEfectividad = "Es doblemente efectivo!"; } 
        else if (efectividad >= 2.0) { resultadoTipo = "VENTAJA"; mensajeEfectividad = "Es super efectivo"; } 
        else if (efectividad < 1.0 && efectividad > 0.0) { resultadoTipo = "DESVENTAJA"; mensajeEfectividad = "No es muy efectivo"; } 
        else if (efectividad == 0.0) { resultadoTipo = "DESVENTAJA"; mensajeEfectividad = "No afecta a " + pokemonRivalActual.getNombre(); }

        // CORREGIDO: Daño calculado usando al Rival
        int danoJugador = (int) (calcularDano(pokemonJugadorActual, pokemonRivalActual, indice) * efectividad);
        danoJugador = Math.max(1, danoJugador);
        int danoRival = calcularDanoRival();

        final String nombreMov = movimiento.getNombre();
        final String msgEfectividad = mensajeEfectividad;
        final int danoFinalJugador = danoJugador;
        final int danoFinalRival = danoRival;

        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(0), e -> {
                log(pokemonJugadorActual.getMote() + " uso " + nombreMov + "!");
                if (!msgEfectividad.isEmpty()) log(msgEfectividad);
            }),
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
                // CORREGIDO: El daño se lo lleva el rival
                pokemonRivalActual.recibirDano(danoFinalJugador);
                log("Hizo " + danoFinalJugador + " de dano!");
                actualizarPantalla();
            }),
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), e -> {
                
                if (pokemonRivalActual.estaDebilitado()) {
                    log(pokemonRivalActual.getNombre() + " se debilito!");
                    koRival++;
                    finalizarCombate(true); 
                } else {
                    log(pokemonRivalActual.getNombre() + " ataco!");
                }
            }),
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(3), e -> {
                if (!pokemonRivalActual.estaDebilitado()) {
                    pokemonJugadorActual.recibirDano(danoFinalRival);
                    log("Recibiste " + danoFinalRival + " de dano!");
                    actualizarPantalla();
                }
            }),
            new javafx.animation.KeyFrame(javafx.util.Duration.seconds(4), e -> {
                if (!pokemonRivalActual.estaDebilitado()) {
                    if (pokemonJugadorActual.estaDebilitado()) {
                        log(pokemonJugadorActual.getMote() + " se debilito!");
                        koJugador++; // CORREGIDO: Usa la variable de clase correctamente
                        
                        Pokemon siguiente = null;
                        for (Pokemon p : Main.miEquipo) {
                            if (!p.estaDebilitado() && p != pokemonJugadorActual) { siguiente = p; break; }
                        }
                        if (siguiente != null) {
                            pokemonJugadorActual = siguiente;
                            actualizarPantalla();
                            log("Vamos " + pokemonJugadorActual.getMote() + "!");
                        } else {
                            log("No te quedan pokemon...");
                            finalizarCombate(false); 
                            return;
                        }
                    }
                    turno++;
                    actualizarPantalla();
                    combateEnPausa = false;
                }
            })
        );
        timeline.play();
    }
	@FXML
	private void handleDescansar(ActionEvent event) {
	    // Si ya se esta ejecutando un turno no hacemos nada
	    if (combateEnPausa) return;
	    combateEnPausa = true;

	    // Recuperamos toda la estamina del pokemon jugador
	    pokemonJugadorActual.setEstamina(100);
	    log(pokemonJugadorActual.getMote() + " descansa y recupera su estamina!");

	    // El rival aprovecha para atacar mientras descansamos
	    int danoRival = calcularDanoRival();
	    final int danoFinal = danoRival;

	    javafx.animation.Timeline timeline = new javafx.animation.Timeline(

	        // Segundo 1: el rival ataca
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1), e -> {
	            log(pokemonRivalActual.getNombre() + " aprovecho y ataco!");
	        }),

	        // Segundo 2: aplicamos el dano del rival
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(2), e -> {
	            pokemonJugadorActual.recibirDano(danoFinal);
	            log("Recibiste " + danoFinal + " puntos de dano!");
	            actualizarPantalla();
	        }),

	        // Segundo 3: comprobamos si el jugador se debilito
	        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(3), e -> {
	            if (pokemonJugadorActual.estaDebilitado()) {
	                log(pokemonJugadorActual.getMote() + " se debilito!");
	                koJugador++;

	                Pokemon siguiente = null;
	                for (Pokemon p : Main.miEquipo) {
	                    if (!p.estaDebilitado() && p != pokemonJugadorActual) {
	                        siguiente = p;
	                        break;
	                    }
	                }

	                if (siguiente != null) {
	                    pokemonJugadorActual = siguiente;
	                    actualizarPantalla();
	                    log("Vamos " + pokemonJugadorActual.getMote() + "!");
	                } else {
	                    finalizarCombate(false);
	                    return;
	                }
	            }

	            turno++;
	            lblTurno.setText("Turno " + turno);
	            log("Que hara " + pokemonJugadorActual.getMote() + "?");
	            combateEnPausa = false;
	        })
	    );

	    timeline.play();
	}

    private int calcularDano(Pokemon atacante, Pokemon defensor, int indiceMovimiento) {
        int potencia = 50;
        if (atacante.getMovimientos() != null && !atacante.getMovimientos().isEmpty() && indiceMovimiento < atacante.getMovimientos().size()) {
            potencia = atacante.getMovimientos().get(indiceMovimiento).getPotencia();
        }
        double nivelComp = (2.0 * atacante.getNivel() / 5.0) + 2.0;
        double statsComp = (double) atacante.getAtaque() / defensor.getDefensa();
        double danoBase = ((nivelComp * potencia * statsComp) / 50.0) + 2.0;
        return Math.max(1, (int) danoBase + (random.nextInt(5) - 2));
    }

    private int calcularDanoRival() {
        // CORREGIDO: Daño calculado usando al Rival como atacante
        double nivelComp = (2.0 * pokemonRivalActual.getNivel() / 5.0) + 2.0;
        double statsComp = (double) pokemonRivalActual.getAtaque() / pokemonJugadorActual.getDefensa();
        double danoBase = ((nivelComp * 40 * statsComp) / 50.0) + 2.0;
        return Math.max(1, (int) danoBase + (random.nextInt(5) - 2));
    }

    private void log(String mensaje) {
        if (txtLog != null) txtLog.appendText(mensaje + "\n");
        System.out.println("[Liga] " + mensaje);
    }
}