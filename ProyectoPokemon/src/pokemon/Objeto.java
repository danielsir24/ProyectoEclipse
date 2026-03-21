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
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getIdObjeto() {
		return idObjeto;
	}
	
	public void setIdObjeto(int idObjeto) {
		this.idObjeto = idObjeto;
	}

	public int getBonusAtaque() {
		return bonusAtaque;
	}
	
	public void setBonusAtaque(int bonusAtaque) {
		this.bonusAtaque = bonusAtaque;
	}

	public int getPenalizacionDefensa() {
		return penalizacionDefensa;
	}
	
	public void setPenalizacionDefensa(int penalizacionDefensa) {
		this.penalizacionDefensa = penalizacionDefensa;
	}

	public String obtenerInfo() {
		return nombre + " (Ataque: +" + bonusAtaque + " | Defensa: -" + penalizacionDefensa + ")";
	}

	public boolean esOfensivo() {
		return bonusAtaque > penalizacionDefensa;
	}
}