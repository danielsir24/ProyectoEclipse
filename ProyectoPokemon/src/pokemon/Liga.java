package pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Liga {
    private List<Entrenador> altoMando;
    private int combateActual; // Del 0 al 4
    private boolean haDescansado;
    private int[] premiosSinDescanso = {1000, 2000, 4000, 6000, 8000};

    public Liga() {
        this.combateActual = 0;
        this.haDescansado = false;
        this.altoMando = cargarAltoMando();
    }

    private List<Entrenador> cargarAltoMando() {
        List<Entrenador> entrenadores = new ArrayList<>();
        
        Collections.shuffle(entrenadores); // Orden aleatorio 
        
        // Añadir al Campeón al final (siempre es el mismo o basado en tu récord)
        // entrenadores.add(campeon); 
        
        return entrenadores;
    }

    public int calcularPremio() {
        int premioBase = premiosSinDescanso[combateActual];
        // Si descansa entre combates (a partir del segundo), el premio es la mitad
        return haDescansado ? premioBase / 2 : premioBase;
    }

    public void curarEquipo(List<Pokemon> equipo) {
        for (Pokemon p : equipo) {
            p.setVitalidad(p.getVitalidadMaxima());
            p.setEstamina(100);
        }
        this.haDescansado = true; // Marcar que ha usado el botón 
    }

    // Getters y setters...
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
