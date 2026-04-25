package pokemon;

// Esta clase sirve para guardar la información de lo que pasa en un turno concreto del combate
public class Turno {
    // Atributos para saber el número de turno y qué ataque o acción ha hecho cada uno
    private int numeroTurno;
    private String accionEntrenador;
    private String accionRival;

    // Constructor para crear el objeto Turno con toda la información de ese momento
    public Turno(int numeroTurno, String accionEntrenador, String accionRival) {
        this.numeroTurno = numeroTurno;
        this.accionEntrenador = accionEntrenador;
        this.accionRival = accionRival;
    }

    // ----- GETTERS -----
    // Los usamos para poder leer la información del turno desde el Log o la interfaz

    public int getNumeroTurno() { 
        return numeroTurno; 
    }
    
    public String getAccionEntrenador() { 
        return accionEntrenador; 
    }
    
    public String getAccionRival() { 
        return accionRival; 
    }
}