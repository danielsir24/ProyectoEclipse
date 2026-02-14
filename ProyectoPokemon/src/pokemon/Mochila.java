package pokemon;

public class Mochila {
    private int id_Entrenador;
    private int id_Objeto;
    private int cantidad;
    
    
    //constructor con todos los parámetros
	public Mochila(int id_Entrenador, int id_Objeto, int cantidad) {
		super();
		this.id_Entrenador = id_Entrenador;
		this.id_Objeto = id_Objeto;
		this.cantidad = cantidad;
	}
    
    // constructor vacío
	public Mochila() {
		super();
		
	}

	//Getters and Setters
	public int getId_Entrenador() {
		return id_Entrenador;
	}

	public void setId_Entrenador(int id_Entrenador) {
		this.id_Entrenador = id_Entrenador;
	}

	public int getId_Objeto() {
		return id_Objeto;
	}

	public void setId_Objeto(int id_Objeto) {
		this.id_Objeto = id_Objeto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
    
    
    
}
