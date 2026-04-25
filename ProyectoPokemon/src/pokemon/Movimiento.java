package pokemon;

// Esta clase sirve para definir los ataques o movimientos que pueden usar los Pokémon
public class Movimiento {
    private String nombre;
    private int potencia;
    private Tipo tipo; // Usamos el Enum Tipo para saber si es Fuego, Agua, etc.
    private String categoria; // Aquí guardamos si es de tipo "ATAQUE", "ESTADO" o "MEJORA"
    private int turnos; 
    private int idMovimiento;

    // Constructor para crear un movimiento con todos sus datos
    public Movimiento(String nombre, int potencia, Tipo tipo, String categoria, int turnos, int idMovimiento) {
        this.nombre = nombre;
        this.potencia = potencia;
        this.tipo = tipo;
        this.categoria = categoria;
        this.turnos = turnos;
        this.idMovimiento = idMovimiento;
    }

    // Este método calcula cuánta energía (estamina) gasta el Pokémon al usar este movimiento
    public int getCosteEstamina() {
        // Si el movimiento es de ataque, el coste es la mitad de su potencia
        if ("ATAQUE".equalsIgnoreCase(categoria)) {
            return potencia / 2;
        } else {
            // Si es de estado o mejora, el coste depende de cuántos turnos dure
            return turnos * 10;
        }
    }

    // ----- GETTERS -----
    // Métodos para poder leer los datos del movimiento desde otras clases

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