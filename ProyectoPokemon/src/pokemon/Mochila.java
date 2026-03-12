package pokemon;

public class Mochila {
	private int id_Entrenador;
	private int id_Objeto;
	private int cantidad;

	public Mochila(int id_Entrenador, int id_Objeto, int cantidad) {
		super();
		this.id_Entrenador = id_Entrenador;
		this.id_Objeto = id_Objeto;
		this.cantidad = cantidad;
	}

	public Mochila() {
		super();
	}

	public void anadirObjeto(int cantidadExtra) {
		if (cantidadExtra > 0) {
			this.cantidad += cantidadExtra;
		}
	}

	public boolean usarObjeto(int cantidadUsada) {
		if (cantidadUsada > 0 && this.cantidad >= cantidadUsada) {
			this.cantidad -= cantidadUsada;
			return true;
		}
		return false;
	}

	public boolean haySuficiente(int cantidadRequerida) {
		return this.cantidad >= cantidadRequerida;
	}

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