package pokemon;

// Esta clase representa la información de la enciclopedia Pokémon (la Pokédex)
public class Pokedex {

	// Atributos con los datos fijos de cada especie: número, nombre, tipos y rutas de archivos
	private int num_Pokedex;
	private String nombreEspecie;
	private String tipo1;
	private String tipo2;
	private String img_Back;
	private String sonido;
	private String img_Frontal;

	// Constructor para crear una entrada de la Pokédex con sus datos principales
	public Pokedex(int num_Pokedex, String tipo1, String tipo2, String img_Back, String sonido, String img_Frontal) {
		super();
		this.num_Pokedex = num_Pokedex;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.img_Back = img_Back;
		this.sonido = sonido;
		this.img_Frontal = img_Frontal;
	}

	// Constructor vacío por si necesitamos crear el objeto y rellenarlo luego
	public Pokedex() {
		super();
	}

	// Método para saber si un Pokémon tiene un segundo tipo o solo uno
	public boolean tieneSegundoTipo() {
		return tipo2 != null && !tipo2.isEmpty() && !tipo2.equalsIgnoreCase("ninguno");
	}

	// Este método nos devuelve la ruta de la carpeta donde están guardados los sprites (imágenes)
	public String getRutaImagen(boolean frontal) {
		// Si pedimos la imagen frontal nos da una ruta, si no, nos da la de espalda
		if (frontal = true) {
			return "/spritesPokemons/Front/" + this.num_Pokedex + ".png";
		} else {
			return "/spritesPokemons/Back/" + this.num_Pokedex + ".png";
		}
	}

	// Un método rápido para imprimir por consola el número y los tipos del Pokémon
	public void mostrarInfoBasica() {
		String tipos = tipo1;
		if (tieneSegundoTipo()) {
			tipos += " / " + tipo2;
		}
		System.out.println("Nº Pokedex: " + num_Pokedex + " | Tipos: " + tipos);
	}

	// ----- GETTERS Y SETTERS -----
	// Los usamos para leer y modificar la información de la Pokédex desde otras clases

	public int getNum_Pokedex() {
		return num_Pokedex;
	}

	public void setNum_Pokedex(int num_Pokedex) {
		this.num_Pokedex = num_Pokedex;
	}

	public String getNombreEspecie() {
		return nombreEspecie;
	}

	public void setNombreEspecia(String nombreEspecie) {
		this.nombreEspecie = nombreEspecie;
	}

	public String getTipo1() {
		return tipo1;
	}

	public void setTipo1(String tipo1) {
		this.tipo1 = tipo1;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public String getImg_Back() {
		return img_Back;
	}

	public void setImg_Back(String img_Back) {
		this.img_Back = img_Back;
	}

	public String getSonido() {
		return sonido;
	}

	public void setSonido(String sonido) {
		this.sonido = sonido;
	}

	public String getImg_Frontal() {
		return img_Frontal;
	}

	public void setImg_Frontal(String img_Frontal) {
		this.img_Frontal = img_Frontal;
	}
}