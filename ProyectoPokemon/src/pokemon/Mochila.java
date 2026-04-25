package pokemon;

// Esta clase representa la mochila de un entrenador, donde guardamos cuántos objetos tiene de cada tipo
public class Mochila {
	// Atributos para saber a qué entrenador pertenece, qué objeto es y cuántos hay
	private int id_Entrenador;
	private int id_Objeto;
	private int cantidad;

	// Constructor para crear la mochila con datos ya conocidos
	public Mochila(int id_Entrenador, int id_Objeto, int cantidad) {
		super();
		this.id_Entrenador = id_Entrenador;
		this.id_Objeto = id_Objeto;
		this.cantidad = cantidad;
	}

	// Constructor vacío por si necesitamos instanciarla sin datos iniciales
	public Mochila() {
		super();
	}

	// Método para sumar objetos a la mochila (por ejemplo, al comprar o encontrar cosas)
	public void anadirObjeto(int cantidadExtra) {
		if (cantidadExtra > 0) {
			this.cantidad += cantidadExtra;
		}
	}

	// Método para restar objetos cuando se usan, comprobando siempre que tengamos suficientes
	public boolean usarObjeto(int cantidadUsada) {
		if (cantidadUsada > 0 && this.cantidad >= cantidadUsada) {
			this.cantidad -= cantidadUsada;
			return true; // Devuelve true si se pudo usar
		}
		return false; // Devuelve false si no hay suficientes objetos
	}

	// Método rápido para chequear si tenemos una cantidad específica de ese objeto
	public boolean haySuficiente(int cantidadRequerida) {
		return this.cantidad >= cantidadRequerida;
	}

	// ----- GETTERS Y SETTERS -----
	// Los usamos para leer y modificar los atributos privados desde otras clases

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