package pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Esta clase controla el progreso del jugador a través de la Liga Pokémon
public class Liga {
    private List<Entrenador> altoMando; // Lista de los entrenadores a los que hay que ganar
    private int combateActual; // Índice para saber por qué combate vamos (del 0 al 4)
    private boolean haDescansado; // Variable para saber si el jugador ha curado a su equipo
    private int[] premiosSinDescanso = {1000, 2000, 4000, 6000, 8000}; // Dinero que ganas en cada fase

    // Constructor para empezar la Liga desde el principio
    public Liga() {
        this.combateActual = 0; // Empezamos en el primer combate
        this.haDescansado = false; // Al empezar nadie ha descansado aún
        this.altoMando = cargarAltoMando(); // Llamamos al método para llenar la lista de rivales
    }

    // Método para rellenar la lista de los entrenadores del Alto Mando
    private List<Entrenador> cargarAltoMando() {
        List<Entrenador> entrenadores = new ArrayList<>();
        
        // Aquí barajamos la lista para que el orden de los rivales sea aleatorio
        Collections.shuffle(entrenadores); 
        
        // El Campeón se añadiría aquí al final para que siempre sea el último escollo
        
        return entrenadores;
    }

    // Método para calcular cuánto dinero se lleva el jugador al ganar un combate
    public int calcularPremio() {
        int premioBase = premiosSinDescanso[combateActual];
        // Si el jugador decide descansar (curar), el premio se reduce a la mitad por "cobarde"
        return haDescansado ? premioBase / 2 : premioBase;
    }

    // Este método restaura la vida y la estamina de todo el equipo
    public void curarEquipo(List<Pokemon> equipo) {
        for (Pokemon p : equipo) {
            p.setVitalidad(p.getVitalidadMaxima()); // Vida al tope
            p.setEstamina(100); // Energía al tope
        }
        this.haDescansado = true; // Marcamos que ha usado la ayuda para penalizar el premio
    }

    // ----- GETTERS Y SETTERS -----
    // Los necesitamos para que el controlador pueda leer y modificar el estado de la liga

	public List<Entrenador> getAltoMando() {
		return altoMando;
	}

	public void setAltoMando(List<Entrenador> altoMando) {
		this.altoMando = altoMando;
	}

	public int getCombateActual() {
		return combateActual;
	}

	public void setCombateActual(int combateActual) {
		this.combateActual = combateActual;
	}

	public boolean isHaDescansado() {
		return haDescansado;
	}

	public void setHaDescansado(boolean haDescansado) {
		this.haDescansado = haDescansado;
	}

	public int[] getPremiosSinDescanso() {
		return premiosSinDescanso;
	}

	public void setPremiosSinDescanso(int[] premiosSinDescanso) {
		this.premiosSinDescanso = premiosSinDescanso;
	}
    
}