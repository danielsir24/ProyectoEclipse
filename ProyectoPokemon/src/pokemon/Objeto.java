package pokemon;

// Esta clase representa los objetos o ítems que pueden equiparse los Pokémon para mejorar sus stats
public class Objeto {

	// Atributos para el nombre del ítem, su ID y todos los posibles bonos o penalizaciones de combate
	private String nombre;
	private int idObjeto;
	private double bonusAtaque;
	private double bonusDefensa;
	private double penalizacionAtaque;
	private double penalizacionDefensa;
	private double bonusAtaqueEspecial;
	private double bonusDefensaEspecial;
	private double penalizacionAtaqueEspecial;
	private double penalizacionDefensaEspecial;
	private double bonusVelocidad;
	private double penalizacionVelocidad;

	// Constructor completo para crear un objeto con todos sus efectos de estadísticas definidos
	public Objeto(String nombre, int idObjeto, double bonusAtaque, double bonusDefensa, double penalizacionAtaque,
			double penalizacionDefensa, double bonusAtaqueEspecial, double bonusDefensaEspecial, double bonusVelocidad,
			double penalizacionAtaqueEspecial, double penalizacionDefensaEspecial, double penalizacionVelocidad) {
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

	// Constructor por defecto que inicializa los valores para que no afecten a las stats (multiplicador 1.0)
	public Objeto() {
		nombre = "";
		this.idObjeto = 0;
		this.bonusAtaque = 1.0;
		this.bonusDefensa = 1.0;
		this.penalizacionAtaque = 1.0;
		this.penalizacionDefensa = 1.0;
	}

	// ----- GETTERS Y SETTERS -----
	// Los usamos para que otras clases puedan consultar cuánto mejora o empeora cada stat el objeto

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

	public double getBonusAtaque() {
		return bonusAtaque;
	}

	public void setBonusAtaque(double d) {
		this.bonusAtaque = d;
	}

	public double getBonusDefensa() {
		return bonusDefensa;
	}

	public void setBonusDefensa(double bonusDefensa) {
		this.bonusDefensa = bonusDefensa;
	}

	public double getPenalizacionAtaque() {
		return penalizacionAtaque;
	}

	public void setPenalizacionAtaque(double penalizacionAtaque) {
		this.penalizacionAtaque = penalizacionAtaque;
	}

	public double getPenalizacionDefensa() {
		return penalizacionDefensa;
	}

	public void setPenalizacionDefensa(double penalizacionDefensa) {
		this.penalizacionDefensa = penalizacionDefensa;
	}

	public double getBonusAtaqueEspecial() {
		return bonusAtaqueEspecial;
	}

	public void setBonusAtaqueEspecial(double bonusAtaqueEspecial) {
		this.bonusAtaqueEspecial = bonusAtaqueEspecial;
	}

	public double getBonusDefensaEspecial() {
		return bonusDefensaEspecial;
	}

	public void setBonusDefensaEspecial(double bonusDefensaEspecial) {
		this.bonusDefensaEspecial = bonusDefensaEspecial;
	}

	public double getPenalizacionAtaqueEspecial() {
		return penalizacionAtaqueEspecial;
	}

	public void setPenalizacionAtaqueEspecial(double penalizacionAtaqueEspecial) {
		this.penalizacionAtaqueEspecial = penalizacionAtaqueEspecial;
	}

	public double getPenalizacionDefensaEspecial() {
		return penalizacionDefensaEspecial;
	}

	public void setPenalizacionDefensaEspecial(double penalizacionDefensaEspecial) {
		this.penalizacionDefensaEspecial = penalizacionDefensaEspecial;
	}

	public double getBonusVelocidad() {
		return bonusVelocidad;
	}

	public void setBonusVelocidad(double bonusVelocidad) {
		this.bonusVelocidad = bonusVelocidad;
	}

	public double getPenalizacionVelocidad() {
		return penalizacionVelocidad;
	}

	public void setPenalizacionVelocidad(double penalizacionVelocidad) {
		this.penalizacionVelocidad = penalizacionVelocidad;
	}

	// Devuelve una frase corta con el resumen de lo que hace el objeto (útil para la interfaz)
	public String obtenerInfo() {
		return nombre + " (Ataque: +" + bonusAtaque + " | Defensa: -" + penalizacionDefensa + ")";
	}

	// Comprueba si el objeto es principalmente para atacar (si el bono de ataque es mayor que lo que quita de defensa)
	public boolean esOfensivo() {
		return bonusAtaque > penalizacionDefensa;
	}
}