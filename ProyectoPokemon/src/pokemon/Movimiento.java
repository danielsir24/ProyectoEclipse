package pokemon;

public class Movimiento {

	private String nombre;
	private int potencia;
	private int costeEstamina;
	private int idMovimiento;

	public Movimiento(String nombre, int potencia, int costeEstamina, int idMovimiento) {
		this.nombre = nombre;
		this.potencia = potencia;
		this.costeEstamina = costeEstamina;
		this.idMovimiento = idMovimiento;
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
	public int getIdMovimiento() {
		return idMovimiento;
	}
}
