package pokemon;

import java.util.*;
import pokemon.Entrenador;

public class Pokemon {

	// Atributos principales que definen a cada Pokémon (stats, niveles, tipos, etc.)
	private Pokedex infoPokedex;
	private String nombre;
	private String mote;
	private int idPokemon;
	private int vitalidad;
	private int vitalidadMaxima; // Para saber el tope de vida al curar
	private int ataque;
	private int defensa;
	private int ataqueEspecial;
	private int defensaEspecial;
	private int velocidad;
	private int estamina;
	private int nivel;
	private int experiencia;
	private int fertilidad;
	private Sexo sexo;
	private List<Movimiento> movimientos;
	private List<Movimiento> movimientosDisponibles;
	private List<Tipo> tipos;
	private Estado estado;
	private Objeto objeto;
	private int ubicacion; // Para saber si está en el equipo o en el PC
	private int idEntrenador;
	private Random random = new Random();


	// Constructor completo para crear un Pokémon con todos sus datos ya definidos
	public Pokemon(Pokedex infoPokedex, String nombre, String mote, int idPokemon, int vitalidad, int vitalidadMaxima, int ataque, int defensa,
			int ataqueEspecial, int defensaEspecial, int velocidad, int estamina, int nivel, int experiencia,
			int fertilidad, Sexo sexo, List<Movimiento> movimientos, List<Movimiento> movimientosDisponibles,
			List<Tipo> tipos, Estado estado, Objeto objeto, int ubicacion, int idEntrenador) {
		super();
		this.infoPokedex = infoPokedex;
		this.nombre = nombre;
		this.mote = mote;
		this.idPokemon = idPokemon;
		this.vitalidad = vitalidad;
		this.vitalidadMaxima = vitalidadMaxima;
		this.ataque = ataque;
		this.defensa = defensa;
		this.ataqueEspecial = ataqueEspecial;
		this.defensaEspecial = defensaEspecial;
		this.velocidad = velocidad;
		this.estamina = estamina;
		this.nivel = nivel;
		this.experiencia = experiencia;
		this.fertilidad = fertilidad;
		this.sexo = sexo;
		this.movimientos = movimientos;
		this.movimientosDisponibles = movimientosDisponibles;
		this.tipos = tipos;
		this.estado = estado;
		this.objeto = objeto;
		this.ubicacion = ubicacion;
		this.idEntrenador = idEntrenador;
	}

	// Constructor copia: sirve para crear un Pokémon exactamente igual a otro
	public Pokemon(Pokemon p) {
		super();
		this.infoPokedex = p.infoPokedex;
		this.nombre = p.nombre;
		this.mote = p.mote;
		this.idPokemon = p.idPokemon;
		this.vitalidad = p.vitalidad;
		this.vitalidadMaxima = p.vitalidadMaxima;
		this.ataque = p.ataque;
		this.defensa = p.defensa;
		this.ataqueEspecial = p.ataqueEspecial;
		this.defensaEspecial = p.defensaEspecial;
		this.velocidad = p.velocidad;
		this.estamina = p.estamina;
		this.nivel = p.nivel;
		this.experiencia = p.experiencia;
		this.fertilidad = p.fertilidad;
		this.sexo = p.sexo;
		// Copiamos las listas para que sean objetos independientes
		this.movimientos = new ArrayList<>(p.movimientos);
		this.movimientosDisponibles = new ArrayList<>(p.movimientosDisponibles);
		this.tipos = new ArrayList<>(p.tipos);
		this.estado = p.estado;
		this.objeto = p.objeto;
		this.ubicacion = p.ubicacion;
		this.idEntrenador = p.idEntrenador;
	}

	// Constructor por defecto: crea un Pokémon con stats aleatorios básicos
	public Pokemon() {
		super();
		this.infoPokedex = null;
		this.nombre = "";
		this.mote = "";
		this.idPokemon = 0;
		// La vida y stats empiezan con un valor al azar pequeño
		this.vitalidad = random.nextInt(10) + 20;
		this.vitalidadMaxima = 0;
		this.ataque = random.nextInt(10) + 1;
		this.defensa = random.nextInt(10) + 1;
		this.ataqueEspecial = random.nextInt(10) + 1;
		this.defensaEspecial = random.nextInt(10) + 1;
		this.velocidad = random.nextInt(10) + 1;
		this.estamina = 100;
		this.nivel = 1;
		this.experiencia = 0;
		this.fertilidad = 5;
		this.sexo = Sexo.MACHO;
		this.movimientos = new ArrayList<>();
		this.movimientosDisponibles = new ArrayList<>();
		this.tipos = new ArrayList<>();
		this.estado = Estado.NORMAL;
		this.objeto = null;
		this.ubicacion = 0;
		this.idEntrenador = 0;
	}

	// Método para restar vida cuando recibe un golpe en combate
	public void recibirDano(int dano) {
		this.vitalidad -= dano;
		// Si la vida baja de 0, la dejamos en 0 para que no sea un número negativo
		if (this.vitalidad < 0)
			this.vitalidad = 0;
	}

	// Indica si el Pokémon se ha quedado sin vida
	public boolean estaDebilitado() {
		return this.vitalidad <= 0;
	}

	// Suma experiencia y comprueba si tiene la suficiente para subir de nivel
	public void ganarExperiencia(int cantidad) {
		this.experiencia += cantidad;
		// La fórmula para subir es 10 veces su nivel actual
		while (this.experiencia >= (10 * nivel)) {
			subirNivel();
		}
	}

	// Sube el nivel y mejora las estadísticas del Pokémon al azar
	private void subirNivel() {
		experiencia -= 10 * nivel;
		nivel++;
		// Al subir de nivel, aumentamos un poco todos sus atributos
		this.vitalidadMaxima += random.nextInt(5) + 1;
		this.vitalidad = vitalidadMaxima; // Se cura al subir de nivel
		this.ataque += random.nextInt(5) + 1;
		this.defensa += random.nextInt(5) + 1;
		this.ataqueEspecial += random.nextInt(5) + 1;
		this.defensaEspecial += random.nextInt(5) + 1;
		this.velocidad += random.nextInt(5) + 1;
		System.out.println("¡" + nombre + " subió al nivel " + nivel + "!");
	}

	// Este método resetea al Pokémon para cuando es recién capturado o nace de un huevo
	public void inicializarEstadisticasBase() {
		Random rnd = new Random();
	    // Valores aleatorios para un nivel 1
	    this.vitalidadMaxima = rnd.nextInt(10) + 1;
	    this.vitalidad = this.vitalidadMaxima; 
	    this.ataque = rnd.nextInt(10) + 1;
	    this.defensa = rnd.nextInt(10) + 1;
	    this.ataqueEspecial = rnd.nextInt(10) + 1;
	    this.defensaEspecial = rnd.nextInt(10) + 1;
	    this.velocidad = rnd.nextInt(10) + 1;
	    
	    // La estamina siempre inicia al máximo (100)
	    this.estamina = 100; 
	    this.experiencia = 0;
	    this.estado = Estado.NORMAL;
	    this.nivel = 1;
	    this.fertilidad = 5;
	}

	// ----- GETTERS Y SETTERS -----
	// Métodos para leer y escribir los atributos privados

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMote() {
		return mote;
	}

	public void setMote(String mote) {
		this.mote = mote;
	}

	public void setInfoPokedex(Pokedex info) {
		this.infoPokedex = info;
	}

	public Pokedex getInfoPokedex() {
		return infoPokedex;
	}

	public int getIdPokemon() {
		return idPokemon;
	}

	public void setIdPokemon(int idPokemon) {
		this.idPokemon = idPokemon;
	}

	public int getVitalidad() {
		return vitalidad;
	}

	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
	}

	public int getVitalidadMaxima() {
		return vitalidadMaxima;
	}

	public void setVitalidadMaxima(int vitalidadMaxima) {
		this.vitalidadMaxima = vitalidadMaxima;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}

	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}

	public int getDefensaEspecial() {
		return defensaEspecial;
	}

	public void setDefensaEspecial(int defensaEspecial) {
		this.defensaEspecial = defensaEspecial;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getEstamina() {
		return estamina;
	}

	public void setEstamina(int estamina) {
		this.estamina = estamina;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}

	public int getFertilidad() {
		return fertilidad;
	}

	public void setFertilidad(int fertilidad) {
		this.fertilidad = fertilidad;
	}


	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public List<Movimiento> getMovimientosDisponibles() {
		return movimientosDisponibles;
	}

	public void setMovimientosDisponibles(List<Movimiento> movimientosDisponibles) {
		this.movimientosDisponibles = movimientosDisponibles;
	}

	public List<Tipo> getTipos() {
		return tipos;
	}

	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public int getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(int ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public int getIdEntrenador() {
		//Cogemos el id del entrenador logueado en ese momento
		return Main.entrenadorLogueado.getId_Entrenador();
	}
	
	
	public void setIdEntrenador(int id) {
		this.idEntrenador = id;
	}
	
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
