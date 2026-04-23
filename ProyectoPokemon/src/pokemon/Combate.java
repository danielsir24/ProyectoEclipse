package pokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

	//Combate Pokemon.
	public class Combate {
    
		private Entrenador jugador;
		private Entrenador rival; 
		private Pokemon pokemonJugador;
		private Pokemon pokemonRival;
		private int turnoActual;
		private Log logCombate;
		private Random random = new Random();

    public Combate(Entrenador jugador, Entrenador rival, Pokemon pokemonJugador, Pokemon pokemonRival) {
        this.jugador = jugador;
        this.rival = rival;
        this.pokemonJugador = pokemonJugador;
        this.pokemonRival = pokemonRival;
        this.turnoActual = 1;
        this.logCombate = new Log();
        
        //Log
        logCombate.registrarEvento("inicioCombate", pokemonJugador, pokemonRival, null, null, turnoActual);
    }


    //Ejecuta un turno completo de combate.
    public String ejecutarTurno(Movimiento movJugador) {
        StringBuilder sb = new StringBuilder();
        sb.append("TURNO ").append(turnoActual).append(" .");

        // 1.TURNO DEL JUGADOR
        
        //Comprobar estamina
        if (pokemonJugador.getEstamina() < movJugador.getCosteEstamina()) {
            sb.append(pokemonJugador.getNombre()).append(" no tiene estamina suficiente para ").append(movJugador.getNombre()).append(".");
        } else {
            String logJugador = realizarAtaque(pokemonJugador, pokemonRival, movJugador, sb);
            
            //Verificar si el rival ha caído
            if (pokemonRival.estaDebilitado()) {
                sb.append("¡").append(pokemonRival.getNombre()).append(" se ha debilitado!");
                logCombate.registrarEvento("debilitado2", pokemonJugador, pokemonRival, logJugador, null, turnoActual);
                finalizarCombate(true, sb);
                return sb.toString();
            }
        }

        //2.TURNO DEL RIVAL
        
        //El rival elige un movimiento aleatorio de su lista
        Movimiento movRival = elegirMovimientoRival();
        String logRival = realizarAtaque(pokemonRival, pokemonJugador, movRival, sb);

        // Verificar si el jugador ha caído
        if (pokemonJugador.estaDebilitado()) {
            sb.append("¡").append(pokemonJugador.getNombre()).append(" se ha debilitado!");
            logCombate.registrarEvento("debilitado1", pokemonJugador, pokemonRival, null, logRival, turnoActual);
            finalizarCombate(false, sb);
            return sb.toString();
        }

        //3.REGISTRO Y CIERRE DE TURNO
        logCombate.registrarEvento("turnoNormal", pokemonJugador, pokemonRival, null, null, turnoActual);
        turnoActual++;
        
        return sb.toString();
    }

    
    //Calcula el daño y aplica los efectos a los Pokemon
    private String realizarAtaque(Pokemon atacante, Pokemon defensor, Movimiento mov, StringBuilder sb) {
        String descripcion = atacante.getNombre() + " usa " + mov.getNombre();
        sb.append(descripcion).append(".");
        
        // Gasto de estamina
        atacante.setEstamina(Math.max(0, atacante.getEstamina() - mov.getCosteEstamina()));

        //FORMULA DE DAÑO
        double nivelComp = (2.0 * atacante.getNivel() / 5.0) + 2.0;
        double statsComp = (double) atacante.getAtaque() / defensor.getDefensa();
        double danoBase = ((nivelComp * mov.getPotencia() * statsComp) / 50.0) + 2.0;

        // Modificador 1: STAB (Bono por mismo tipo)
        double stab = 1.0;
        if (atacante.getTipos() != null) {
          
        }

        //Modificador 2: Efectividad de Tipos
        double efectividad = 1.0;
        if (defensor.getTipos() != null && !defensor.getTipos().isEmpty()) {
            Tipo tDef1 = defensor.getTipos().get(0);
            Tipo tDef2 = defensor.getTipos().size() > 1 ? defensor.getTipos().get(1) : null;
            
            //Suponemos que el movimiento es de tipo NORMAL por ahora
            efectividad = Tipo.NORMAL.calcularEfectividadDoble(tDef1, tDef2);
        }

        //Mensajes de efectividad según requisito
        if (efectividad > 1.0) sb.append("¡Es súper efectivo!");
        else if (efectividad < 1.0 && efectividad > 0) sb.append("No es muy efectivo...");
        else if (efectividad == 0) sb.append("No afecta a ").append(defensor.getNombre()).append(".");

        int danoFinal = (int) (danoBase * stab * efectividad);
        defensor.recibirDano(danoFinal);
        
        sb.append(" Daño causado: ").append(danoFinal).append(" PS.");
        return descripcion;
    }

    private Movimiento elegirMovimientoRival() {
        if (pokemonRival.getMovimientos() != null && !pokemonRival.getMovimientos().isEmpty()) {
            return pokemonRival.getMovimientos().get(random.nextInt(pokemonRival.getMovimientos().size()));
        }
        //Movimiento por defecto si no tiene lista cargada
        return new Movimiento("Placaje", 40, 10, 0);
    }

    
    //Finaliza el combate
    public void finalizarCombate(boolean victoria, StringBuilder sb) {
        String evento = victoria ? "finGanaCombate" : "finPierdeCombate";
        logCombate.registrarEvento(evento, pokemonJugador, pokemonRival, null, null, turnoActual);
        logCombate.guardarEnFichero();

        if (victoria) {
            sb.append("¡HAS GANADO EL COMBATE!");
            // Calculo de Exp
            int expGanada = (pokemonJugador.getNivel() + (pokemonRival.getNivel() * 10)) / 4;
            pokemonJugador.ganarExperiencia(expGanada);
            sb.append("Has ganado ").append(expGanada).append(" puntos de experiencia.");
            
            //Recompensa economica
            int recompensa = 100 * pokemonRival.getNivel();
            jugador.ganarPokedollars(recompensa);
            sb.append("Recibes ").append(recompensa).append(" Pokedollars por la victoria.");
        } else {
            sb.append("Has sido derrotado...");
        }

        //GUARDADO EN LA BASE DE DATOS
        PokemonDAO pDAO = new PokemonDAO();
        pDAO.actualizarPokemon(pokemonJugador);
        
        EntrenadorDAO eDAO = new EntrenadorDAO();
        eDAO.actualizarPokedollars(jugador);
    }

    public Pokemon getPokemonJugador() { return pokemonJugador; }
    public Pokemon getPokemonRival() { return pokemonRival; }
}