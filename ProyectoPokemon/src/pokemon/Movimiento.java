package pokemon;

public class Movimiento {
    private String nombre;
    private int potencia;
    private Tipo tipo; // CAMBIADO: Antes era int, ahora es Tipo
    private String categoria; // "ATAQUE", "ESTADO", "MEJORA"
    private int turnos; 
    private int idMovimiento;

    // Actualiza el constructor
    public Movimiento(String nombre, int potencia, Tipo tipo, String categoria, int turnos, int idMovimiento) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.tipo = tipo;
        this.categoria = categoria;
        this.turnos = turnos;
        this.idMovimiento = idMovimiento;
    }

    // REQUISITO: El coste de estamina es dinámico
    public int getCosteEstamina() {
        if ("ATAQUE".equalsIgnoreCase(categoria)) {
            return potencia / 2;
        } else {
            return turnos * 10;
        }
    }

    //Getters
    public String getNombre() {
    	return nombre; 
    }
    
    public int getPotencia() { 
    	return potencia;
    }
    
    public Tipo getTipo() { 
    	return tipo; 
    }
    
    public String getCategoria() { 
    	return categoria; 
    }
    
    public int getIdMovimiento() { 
    	return idMovimiento; 
    }
}