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

    
    // Calcula el daño y aplica los efectos a los Pokemon
    private String realizarAtaque(Pokemon atacante, Pokemon defensor, Movimiento mov, StringBuilder sb) {
        String descripcion = atacante.getNombre() + " usa " + mov.getNombre();
        
        // 1. Gasto de estamina real
        atacante.setEstamina(Math.max(0, atacante.getEstamina() - mov.getCosteEstamina()));

        // 2. Calculo de Efectividad usando tu Enum Tipo
        double efectividad = 1.0;
        if (defensor.getTipos() != null && !defensor.getTipos().isEmpty()) {
            Tipo tDef1 = defensor.getTipos().get(0);
            Tipo tDef2 = (defensor.getTipos().size() > 1) ? defensor.getTipos().get(1) : null;
            
            efectividad = mov.getTipo().calcularEfectividadDoble(tDef1, tDef2);
        }

        // 3. Modificador STAB (Bono x1.5 si coincide tipo) 
        double stab = 1.0;
        for (Tipo t : atacante.getTipos()) {
            if (t == mov.getTipo()) {
                stab = 1.5;
                break;
            }
        }

        // 4. Mensaje de efectividad
        String msgEfectividad = mov.getTipo().getMensajeEfectividad(efectividad);
        if (!msgEfectividad.isEmpty()) sb.append(" ").append(msgEfectividad);

        // 5. Fórmula de daño
        double nivelComp = (2.0 * atacante.getNivel() / 5.0) + 2.0;
        double statsComp = (double) atacante.getAtaque() / defensor.getDefensa();
        double danoBase = ((nivelComp * mov.getPotencia() * statsComp) / 50.0) + 2.0;

        int danoFinal = (int) (danoBase * stab * efectividad);
        defensor.recibirDano(danoFinal);
        
        sb.append(" (Daño: ").append(danoFinal).append(" PS)");
        return descripcion;
    }

    private Movimiento elegirMovimientoRival() {
        if (pokemonRival.getMovimientos() != null && !pokemonRival.getMovimientos().isEmpty()) {
            return pokemonRival.getMovimientos().get(random.nextInt(pokemonRival.getMovimientos().size()));
        }
        
        //Movimiento por defecto si no tiene lista cargada
        return new Movimiento("Placaje", 40, Tipo.NORMAL, "ATAQUE",0,0);
    }

    
    //Finaliza el combate
    public void finalizarCombate(boolean victoria, StringBuilder sb) {
        if (victoria) {
            sb.append("\n¡HAS GANADO!");
            // Recompensa: 1/3 del dinero del rival 
            int botin = rival.getPokedollars() / 3;
            jugador.setPokedollars(jugador.getPokedollars() + botin);
            rival.setPokedollars(rival.getPokedollars() - botin);
            sb.append("\nGanaste ").append(botin).append(" Pokedollars.");
        } else {
            sb.append("\nDERROTA...");
            // Penalización: 1/3 normal o 1/2 si es la Liga 
            int perdida;
            if ("ALTO_MANDO".equals(rival.getTipo_Entrenador())) {
                perdida = jugador.getPokedollars() / 2;
            } else {
                perdida = jugador.getPokedollars() / 3;
            }
            jugador.setPokedollars(jugador.getPokedollars() - perdida);
            sb.append("\nPerdiste ").append(perdida).append(" Pokedollars.");
        }

        // Guardar en BD
        new PokemonDAO().actualizarPokemon(pokemonJugador);
        new EntrenadorDAO().actualizarPokedollars(jugador);
        logCombate.guardarEnFichero();
    }

    public Pokemon getPokemonJugador() { 
    	return pokemonJugador; 
    }
    
    
    public Pokemon getPokemonRival() { 
    	return pokemonRival; 
    }
 
    public Entrenador getEntrenadorRival() {
        return this.rival;
    }
}