package pokemon;

public class Movimiento {

	private String nombre;
	private int potencia;
	private int costeEstamina;

	public Movimiento(String nombre, int potencia, int costeEstamina) {
		this.nombre = nombre;
		this.potencia = potencia;
		this.costeEstamina = costeEstamina;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPotencia() {
		return potencia;
	}

	public int getCosteEstamina() {
		return costeEstamina;
	}
}
