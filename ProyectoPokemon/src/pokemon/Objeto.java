package pokemon;

public class Objeto {

	private String nombre;
	private int idObjeto;
	private int bonusAtaque;
	private int bonusDefensa;
	private int penalizacionAtaque;
	private int penalizacionDefensa;
	private int bonusAtaqueEspecial;
	private int bonusDefensaEspecial;
	private int penalizacionAtaqueEspecial;
	private int penalizacionDefensaEspecial;
	private int bonusVelocidad;
	private int penalizacionVelocidad;

	public Objeto(String nombre, int idObjeto, int bonusAtaque, int bonusDefensa, int penalizacionAtaque,
			int penalizacionDefensa, int bonusAtaqueEspecial, int bonusDefensaEspecial, int bonusVelocidad,
			int penalizacionAtaqueEspecial, int penalizacionDefensaEspecial, int penalizacionVelocidad) {
		this.nombre = nombre;
		this.idObjeto = idObjeto;
		this.bonusAtaque = bonusAtaque;
		this.bonusDefensa = bonusDefensa;
		this.penalizacionDefensa = penalizacionDefensa;
		this.penalizacionAtaqueEspecial = penalizacionAtaqueEspecial;
		this.bonusAtaqueEspecial = bonusAtaqueEspecial;
		this.bonusDefensaEspecial = bonusDefensaEspecial;
		this.penalizacionDefensaEspecial = penalizacionDefensaEspecial;
		this.penalizacionAtaqueEspecial = penalizacionAtaqueEspecial;
		this.bonusVelocidad = bonusVelocidad;
		this.penalizacionVelocidad = penalizacionVelocidad;
	}

	public Objeto() {
		nombre = "";
		this.idObjeto = 0;
		this.bonusAtaque = 0;
		this.bonusDefensa = 0;
		this.penalizacionAtaque = 0;
		this.penalizacionDefensa = 0;
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

	public int getBonusDefensa() {
		return bonusDefensa;
	}

	public void setBonusDefensa(int bonusDefensa) {
		this.bonusDefensa = bonusDefensa;
	}

	public int getPenalizacionAtaque() {
		return penalizacionAtaque;
	}

	public void setPenalizacionAtaque(int penalizacionAtaque) {
		this.penalizacionAtaque = penalizacionAtaque;
	}

	public int getPenalizacionDefensa() {
		return penalizacionDefensa;
	}

	public void setPenalizacionDefensa(int penalizacionDefensa) {
		this.penalizacionDefensa = penalizacionDefensa;
	}

	public int getBonusAtaqueEspecial() {
		return bonusAtaqueEspecial;
	}

	public void setBonusAtaqueEspecial(int bonusAtaqueEspecial) {
		this.bonusAtaqueEspecial = bonusAtaqueEspecial;
	}

	public int getBonusDefensaEspecial() {
		return bonusDefensaEspecial;
	}

	public void setBonusDefensaEspecial(int bonusDefensaEspecial) {
		this.bonusDefensaEspecial = bonusDefensaEspecial;
	}

	public int getPenalizacionAtaqueEspecial() {
		return penalizacionAtaqueEspecial;
	}

	public void setPenalizacionAtaqueEspecial(int penalizacionAtaqueEspecial) {
		this.penalizacionAtaqueEspecial = penalizacionAtaqueEspecial;
	}

	public int getPenalizacionDefensaEspecial() {
		return penalizacionDefensaEspecial;
	}

	public void setPenalizacionDefensaEspecial(int penalizacionDefensaEspecial) {
		this.penalizacionDefensaEspecial = penalizacionDefensa;
	}

	public int getBonusVelocidad() {
		return bonusVelocidad;
	}

	public void setBonusVelocidad(int bonusVelocidad) {
		this.bonusVelocidad = bonusVelocidad;
	}

	public int getPenalizacionVelocidad() {
		return penalizacionVelocidad;
	}

	public void setPenalizacionVelocidad(int penalizacionVelocidad) {
		this.penalizacionVelocidad = penalizacionVelocidad;
	}

	public String obtenerInfo() {
		return nombre + " (Ataque: +" + bonusAtaque + " | Defensa: -" + penalizacionDefensa + ")";
	}

	public boolean esOfensivo() {
		return bonusAtaque > penalizacionDefensa;
	}
}