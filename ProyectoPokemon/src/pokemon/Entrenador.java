package pokemon;

/**
 * Clase que representa a los entrenadores del juego (el jugador y los rivales)
 * Guarda los datos que van a la base de datos y controla el dinero y la contraseña
 * * @author [Adrian Rodriguez, Juan Carlos Benitez, Daniel Sirbu, Alejandro Varela]
 * @version 1.0
 */
public class Entrenador {
	
	/** ID del entrenador en la base de datos */
	private int id_Entrenador;
	
	/** Nombre del entrenador */
	private String nom_Entrenador;
	
	/** Contraseña para hacer login */
	private String password;
	
	/** Ruta de la imagen del entrenador */
	private String img_Entrenador;
	
	/** Cantidad de Pokedollars que tiene */
	private int pokedollars;
	
	/** Tipo de entrenador (Jugador, Rival, etc) */
	private String tipo_Entrenador;

	/**
	 * Constructor con todos los parametros para crear un entrenador
	 * * @param id_Entrenador El ID del entrenador
	 * @param nom_Entrenador El nombre del entrenador
	 * @param password La contraseña
	 * @param img_Entrenador La imagen del perfil
	 * @param pokedollars El dinero inicial
	 * @param tipo_Entrenador El tipo de entrenador
	 */
	public Entrenador(int id_Entrenador, String nom_Entrenador, String password, String img_Entrenador, int pokedollars, String tipo_Entrenador) {
		super();
		this.id_Entrenador = id_Entrenador;
		this.nom_Entrenador = nom_Entrenador;
		this.password = password;
		this.img_Entrenador = img_Entrenador;
		this.pokedollars = pokedollars;
		this.tipo_Entrenador = tipo_Entrenador;
	}

	/**
	 * Constructor vacio para nuevos registros
	 * Le da 1000 pokedollars por defecto al empezar
	 */
	public Entrenador() {
		super();
		this.pokedollars = 1000;
	}

	/**
	 * Suma dinero al entrenador cuando gana un combate
	 * * @param cantidad El dinero a sumar (tiene que ser mayor que 0)
	 */
	public void ganarPokedollars(int cantidad) {
		if (cantidad > 0) {
			this.pokedollars += cantidad;
		}
	}

	/**
	 * Resta dinero si compramos algo y comprueba si tenemos suficiente saldo
	 * * @param precio Lo que cuesta el objeto
	 * @return true si se ha podido comprar, false si no hay dinero suficiente
	 */
	public boolean gastarPokedollars(int precio) {
		if (precio > 0 && this.pokedollars >= precio) {
			this.pokedollars -= precio;
			return true;
		}
		return false; 
	}

	/**
	 * Comprueba si la contraseña escrita en el login es igual a la guardada
	 * * @param intento La contraseña que mete el usuario
	 * @return true si coinciden, false si no
	 */
	public boolean comprobarPassword(String intento) {
		return this.password != null && this.password.equals(intento);
	}

	/**
	 * Imprime por consola los datos principales del entrenador
	 */
	public void mostrarPerfil() {
		System.out.println("ID: " + this.id_Entrenador + " | Nombre: " + this.nom_Entrenador + " | Clase: "
				+ this.tipo_Entrenador + " | Dinero: " + this.pokedollars + " ₽");
	}

	// GETTERS Y SETTERS

	/**
	 * Obtiene el ID del entrenador
	 * @return El ID del entrenador
	 */
	public int getId_Entrenador() {
		return id_Entrenador;
	}

	/**
	 * Cambia el ID del entrenador
	 * @param id_Entrenador El nuevo ID
	 */
	public void setId_Entrenador(int id_Entrenador) {
		this.id_Entrenador = id_Entrenador;
	}

	/**
	 * Obtiene el nombre del entrenador
	 * @return El nombre del entrenador
	 */
	public String getNom_Entrenador() {
		return nom_Entrenador;
	}

	/**
	 * Cambia el nombre del entrenador
	 * @param nom_Entrenador El nuevo nombre
	 */
	public void setNom_Entrenador(String nom_Entrenador) {
		this.nom_Entrenador = nom_Entrenador;
	}

	/**
	 * Obtiene la contraseña
	 * @return La contraseña
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Cambia la contraseña
	 * @param password La nueva contraseña
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene la imagen del entrenador
	 * @return La ruta de la imagen
	 */
	public String getImg_Entrenador() {
		return img_Entrenador;
	}

	/**
	 * Cambia la imagen del entrenador
	 * @param img_Entrenador La nueva ruta de la imagen
	 */
	public void setImg_Entrenador(String img_Entrenador) {
		this.img_Entrenador = img_Entrenador;
	}

	/**
	 * Obtiene el dinero del entrenador
	 * @return Los pokedollars
	 */
	public int getPokedollars() {
		return pokedollars;
	}

	/**
	 * Cambia la cantidad de dinero
	 * @param pokedollars El nuevo dinero
	 */
	public void setPokedollars(int pokedollars) {
		this.pokedollars = pokedollars;
	}

	/**
	 * Obtiene el tipo de entrenador
	 * @return El tipo de entrenador
	 */
	public String getTipo_Entrenador() {
		return tipo_Entrenador;
	}

	/**
	 * Cambia el tipo de entrenador
	 * @param tipo_Entrenador El nuevo tipo
	 */
	public void setTipo_Entrenador(String tipo_Entrenador) {
		this.tipo_Entrenador = tipo_Entrenador;
	}
}