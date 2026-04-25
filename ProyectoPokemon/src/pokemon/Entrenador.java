package pokemon;

// Esta clase representa a los entrenadores del juego (jugador y rivales)
public class Entrenador {
	// Atributos básicos que guardamos en la base de datos para cada entrenador
	private int id_Entrenador;
	private String nom_Entrenador;
	private String password;
	private String img_Entrenador;
	private int pokedollars;
	private String tipo_Entrenador;

	// Constructor para crear un entrenador con todos sus datos ya rellenos
	public Entrenador(int id_Entrenador, String nom_Entrenador, String password, String img_Entrenador, int pokedollars,
			String tipo_Entrenador) {
		super();
		this.id_Entrenador = id_Entrenador;
		this.nom_Entrenador = nom_Entrenador;
		this.password = password;
		this.img_Entrenador = img_Entrenador;
		this.pokedollars = pokedollars;
		this.tipo_Entrenador = tipo_Entrenador;
	}

	// Constructor vacío que usamos para registros nuevos, dándole 1000 monedas por defecto
	public Entrenador() {
		super();
		this.pokedollars = 1000;
	}

	// Método para sumar dinero al saldo del entrenador (por ejemplo, tras ganar un combate)
	public void ganarPokedollars(int cantidad) {
		if (cantidad > 0) {
			this.pokedollars += cantidad;
		}
	}

	// Método para restar dinero cuando compramos algo, comprobando primero si nos llega el saldo
	public boolean gastarPokedollars(int precio) {
		if (precio > 0 && this.pokedollars >= precio) {
			this.pokedollars -= precio;
			return true;
		}
		return false; // Si no hay dinero suficiente, devuelve false
	}

	// Sirve para verificar si la contraseña escrita en el login coincide con la guardada
	public boolean comprobarPassword(String intento) {
		return this.password != null && this.password.equals(intento);
	}

	// Método rápido para imprimir los datos principales por la consola
	public void mostrarPerfil() {
		System.out.println("ID: " + this.id_Entrenador + " | Nombre: " + this.nom_Entrenador + " | Clase: "
				+ this.tipo_Entrenador + " | Dinero: " + this.pokedollars + " ₽");
	}

	// ----- GETTERS Y SETTERS -----
	// Métodos necesarios para poder leer o modificar los atributos privados desde otras clases

	public int getId_Entrenador() {
		return id_Entrenador;
	}

	public void setId_Entrenador(int id_Entrenador) {
		this.id_Entrenador = id_Entrenador;
	}

	public String getNom_Entrenador() {
		return nom_Entrenador;
	}

	public void setNom_Entrenador(String nom_Entrenador) {
		this.nom_Entrenador = nom_Entrenador;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImg_Entrenador() {
		return img_Entrenador;
	}

	public void setImg_Entrenador(String img_Entrenador) {
		this.img_Entrenador = img_Entrenador;
	}

	public int getPokedollars() {
		return pokedollars;
	}

	public void setPokedollars(int pokedollars) {
		this.pokedollars = pokedollars;
	}

	public String getTipo_Entrenador() {
		return tipo_Entrenador;
	}

	public void setTipo_Entrenador(String tipo_Entrenador) {
		this.tipo_Entrenador = tipo_Entrenador;
	}
}