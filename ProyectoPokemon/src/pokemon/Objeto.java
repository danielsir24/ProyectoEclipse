package pokemon;

public class Objeto {

	private String nombre;
	private int idObjeto;
	private int bonusAtaque;
	private int penalizacionDefensa;

	public Objeto(String nombre, int idObjeto, int bonusAtaque, int penalizacionDefensa) {
		this.nombre = nombre;
		this.idObjeto = idObjeto;
		this.bonusAtaque = bonusAtaque;
		this.penalizacionDefensa = penalizacionDefensa;
	}

	public String getNombre() {
		return nombre;
	}
	
	public int getIdObjeto() {
		return idObjeto;
	}

	public int getBonusAtaque() {
		return bonusAtaque;
	}

	public int getPenalizacionDefensa() {
		return penalizacionDefensa;
	}

	public String obtenerInfo() {
		return nombre + " (Ataque: +" + bonusAtaque + " | Defensa: -" + penalizacionDefensa + ")";
	}

	public boolean esOfensivo() {
		return bonusAtaque > penalizacionDefensa;
	}
}