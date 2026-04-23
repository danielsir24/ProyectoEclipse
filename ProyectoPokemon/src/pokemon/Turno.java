package pokemon;

public class Turno {
    private int numeroTurno;
    private String accionEntrenador;
    private String accionRival;

    public Turno(int numeroTurno, String accionEntrenador, String accionRival) {
        this.numeroTurno = numeroTurno;
        this.accionEntrenador = accionEntrenador;
        this.accionRival = accionRival;
    }

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
