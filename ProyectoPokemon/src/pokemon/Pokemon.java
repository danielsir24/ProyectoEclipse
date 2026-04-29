package pokemon;

import java.util.*;
import pokemon.Entrenador;

/**
 * Clase principal del pokemon que tiene todos los atributos del pokemon
 * 
 *
 * @author DanielSirbuMihuta
 * @version 18-04-2026 a las 19.15 H
 */
public class Pokemon {

	

	/** La informacion de la pokedex */
	private Pokedex infoPokedex;
	/** Nombre de los pokemon */
	private String nombre;
	/** mote que se le pone a los pokemon */
	private String mote;
	/** id que le asignamos a los pokemon en la base de datos*/
	private int idPokemon;
	/** vida del pokemon */
	private int vitalidad;
	/** vida maxima del pokemon */
	private int vitalidadMaxima;
	/** estadistica del ataque del pokemon */
	private int ataque;
	/** estadistica de la defensa del pokemon */
	private int defensa;
	/** estadistica del ataque especial del pokemon */
	private int ataqueEspecial;
	/** estadistica de la defensa especial del pokemon */
	private int defensaEspecial;
	/** velocidad del pokemon */
	private int velocidad;
	/** estamina del pokemon */
	private int estamina;
	/** nivel del pokemon */
	private int nivel;
	/** experiencia que tiene el pokemon, solo puede gamar */
	private int experiencia;
	/** la fertilidad del pokemon para la crianza */
	private int fertilidad;
	/** el sexo del pokemon si es macho o hembra */
	private Sexo sexo;
	/** una lista de los movimientos del pokemon */
	private List<Movimiento> movimientos;
	/** una lista de los movimientos disponibles que tiene el pokemon */
	private List<Movimiento> movimientosDisponibles;
	/** una lista de los tipos que es el pokemon */
	private List<Tipo> tipos;
	/** un estado del pokemon que pueden ser muchas formas */
	private Estado estado;
	/** un objeto de un objeto, que es el objeto que lleva equipado el pokemon */
	private Objeto objeto;
	/** la ubicacion del pokemon en el equipò */
	private int ubicacion;
	/** el id del entrenador que tiene el pokemon */
	private int idEntrenador;
	/** un generador de numeros aleatorios */
	private Random random = new Random();

	/**
	 * Un constructor de pokemon donde se introducen todos los atributos del pokemon
	 * 
	 *
	 * @param infoPokedex datos del pokemon
	 * @param nombre nombre del pokemon
	 * @param mote mote del pokemon
	 * @param idPokemon id del pokemon que se guarda en la base de datos
	 * @param vitalidad vida del pokemon
	 * @param vitalidadMaxima vida maxima del pokemon
	 * @param ataque ataque del pokemon
	 * @param defensa defenesa del pokemon
	 * @param ataqueEspecial ataque epescial del pokemon
	 * @param defensaEspecial defensa especial del pokemonm
	 * @param velocidad velocidad del pokemon
	 * @param estamina estamina del pokemon
	 * @param nivel nivel del pokemon
	 * @param experiencia experiencia del pokemon, solo puede ganar
	 * @param fertilidad fertilidad del pokemon para crianza
	 * @param sexo sexo del pokemon si es macho o hmebra
	 * @param movimientos lista de movimientos del pokemon
	 * @param movimientosDisponibles lista de movimientos disponibles del pokemon 
	 * @param tipos lista de tipos del pokemon
	 * @param estado estado del pokemon
	 * @param objeto objeto del pokemon que puede tner equipado
	 * @param ubicacion posicion en la pokedex del pokemon
	 * @param idEntrenador id del entrenador que tiene el pokemon
	 */

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

	/**
	 * Constructor copia donde se crea un pokemon nuevo que se copian todos los atributos del otro
	 * 
	 *
	 * @param p pokemon del cual se le copian los datos
	 */
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

		this.movimientos = new ArrayList<>(p.movimientos);
		this.movimientosDisponibles = new ArrayList<>(p.movimientosDisponibles);
		this.tipos = new ArrayList<>(p.tipos);
		this.estado = p.estado;
		this.objeto = p.objeto;
		this.ubicacion = p.ubicacion;
		this.idEntrenador = p.idEntrenador;
	}

	/**
	 * Constructor por defecto donde se crea un pokemon con las estadisticas aleatorias y los string pasados por nosotros
	 * 
	 */
	public Pokemon() {
		super();
		this.infoPokedex = null;
		this.nombre = "";
		this.mote = "";
		this.idPokemon = 0;
		// La vida y stats empiezan con un valor al azar pequeño
		this.vitalidad = random.nextInt(10) + 20;
		this.vitalidadMaxima = this.vitalidad;
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

	/**
	 * le quita vida al pokemon segun el daño recibido, es decir, se resta si pasa a menos que 0 o 0 muere
	 *
	 * 
	 *
	 * @param dano cantidad de daño que recibe el pokemon
	 */
	public void recibirDano(int dano) {
		this.vitalidad -= dano;

		if (this.vitalidad < 0)
			this.vitalidad = 0;
	}

	/**
	 * comprueba si el pokemon esta vivo o muerto
	 *
	 * @return true si la vitalidad es 0 o menos false si sigue vivo el pokemon
	 */
	public boolean estaDebilitado() {
		return this.vitalidad <= 0;
	}

	/**
	 * Se va sumando experiencia al pokemon y se sube de nivel cuando se supera una condicion
	 * 
	 *
	 * @param cantidad puntos de experiencia que gana
	 */
	public void ganarExperiencia(int cantidad) {
		this.experiencia += cantidad;

		while (this.experiencia >= (10 * nivel)) {
			subirNivel();
		}
	}

	private void subirNivel() {
		experiencia -= 10 * nivel;
		nivel++;

		this.vitalidadMaxima += random.nextInt(5) + 1;
		this.vitalidad = vitalidadMaxima; 
		this.ataque += random.nextInt(5) + 1;
		this.defensa += random.nextInt(5) + 1;
		this.ataqueEspecial += random.nextInt(5) + 1;
		this.defensaEspecial += random.nextInt(5) + 1;
		this.velocidad += random.nextInt(5) + 1;
		System.out.println("¡" + nombre + " subió al nivel " + nivel + "!");
	}

	/**
	 * Le pone estadisticas aleatorias al pokemon, como si se hubiera generado un pokemon nuevo o hubiera aparecido un pokemon nuevo
	 * 
	 */
	public void inicializarEstadisticasBase() {
		Random rnd = new Random();

	    this.vitalidadMaxima = rnd.nextInt(30) + 20;
	    this.vitalidad = this.vitalidadMaxima; 
	    this.ataque = rnd.nextInt(10) + 1;
	    this.defensa = rnd.nextInt(10) + 1;
	    this.ataqueEspecial = rnd.nextInt(10) + 1;
	    this.defensaEspecial = rnd.nextInt(10) + 1;
	    this.velocidad = rnd.nextInt(10) + 1;
	    

	    this.estamina = 100; 
	    this.experiencia = 0;
	    this.estado = Estado.NORMAL;
	    this.nivel = 1;
	    this.fertilidad = 5;
	}

	// ----- GETTERS Y SETTERS -----


	/** @return el nombre del pokemon */
	public String getNombre() {
		return nombre;
	}

	/** @param nombre es el nuevo nombre que se le asigna */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/** @return el mote del pokemon */
	public String getMote() {
		return mote;
	}
	/** @param mote es el nuevop mote que se le asigna el pokemon*/
	public void setMote(String mote) {
		this.mote = mote;
	}
	
	/** @return la informacion del pokemon*/
	public Pokedex getInfoPokedex() {
		return infoPokedex;
	}
	/** @param  info se le asignan nueva informacion*/
	public void setInfoPokedex(Pokedex info) {
		this.infoPokedex = info;
	}

	/** @return el id del pokemon en la bbdd*/
	public int getIdPokemon() {
		return idPokemon;
	}
	/** @param idPokemon el nuevo id que se le asigna al pokemon*/
	public void setIdPokemon(int idPokemon) {
		this.idPokemon = idPokemon;
	}
	/** @return la vida del pokemon*/
	public int getVitalidad() {
		return vitalidad;
	}
	/** @param vitalidad la vida nueva del pokemon*/
	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
	}
	/** @return la vida maxima del pokemon*/
	public int getVitalidadMaxima() {
		return vitalidadMaxima;
	}
	/** @param vitalidaMaximax la vida maxima nueva del pokemon*/
	public void setVitalidadMaxima(int vitalidadMaxima) {
		this.vitalidadMaxima = vitalidadMaxima;
	}
	/** @return el ataque del pokemon*/
	public int getAtaque() {
		return ataque;
	}
	/** @param ataque es la estidistica nueva de ataque del pokemon*/
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}
	/** @return la defensa del pokemon*/
	public int getDefensa() {
		return defensa;
	}
	/** @param defensa es la estadistica nueva de la defensa del pokemon*/
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}
	/** @return el ataque especial del pokemoin*/
	public int getAtaqueEspecial() {
		return ataqueEspecial;
	}
	/** @param ataqueEspecial es la estadistica nueva del ataque especial del pokemon*/
	public void setAtaqueEspecial(int ataqueEspecial) {
		this.ataqueEspecial = ataqueEspecial;
	}
	/** @return es la defensa especial del pokemon */
	public int getDefensaEspecial() {
		return defensaEspecial;
	}
	/** @param defensaEspecial es la estadistica nueva de la defensa especial del pokemon*/
	public void setDefensaEspecial(int defensaEspecial) {
		this.defensaEspecial = defensaEspecial;
	}
	/** @return la velocidad del pokemon*/
	public int getVelocidad() {
		return velocidad;
	}
	/** @param velocidad es la estadistica nueva de la velocidad del pokemon*/
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	/** @return la estamina del pokemon*/
	public int getEstamina() {
		return estamina;
	}
	/** @param estamina es la estadistica nueva de la estamina del pokemon*/
	public void setEstamina(int estamina) {
		this.estamina = estamina;
	}
	/** @return el nivel del pokemon*/
	public int getNivel() {
		return nivel;
	}
	/** @param nivel es el nuevo nivel del pokemon*/
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	/** @return la experiencia del pokemon*/
	public int getExperiencia() {
		return experiencia;
	}
	/** @param experiencia es la nueva experiencia que se suma al pokemon*/
	public void setExperiencia(int experiencia) {
		this.experiencia = experiencia;
	}
	/** @return la fertilidad del pokemon*/
	public int getFertilidad() {
		return fertilidad;
	}
	/** @param fertilidad es la fertilidad nueva del pokemon unavez ha criado*/
	public void setFertilidad(int fertilidad) {
		this.fertilidad = fertilidad;
	}

	/** @return el sexo del pokemon, si es macho o hembra*/
	public Sexo getSexo() {
		return sexo;
	}
	/** @param sexo nuevo sexo*/
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	/** @return la lista de movimientos del pokemon*/
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}
	/** @param movimientos es una nueva lista del pokemon*/
	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	/** @return los movimientos disponibles del pokemon*/
	public List<Movimiento> getMovimientosDisponibles() {
		return movimientosDisponibles;
	}
	/** @param movimientosDisponibles es una nueva lista de movimientos disponibles del pokemon*/
	public void setMovimientosDisponibles(List<Movimiento> movimientosDisponibles) {
		this.movimientosDisponibles = movimientosDisponibles;
	}
	/** @return el tipo del pokemon o tipos*/
	public List<Tipo> getTipos() {
		return tipos;
	}
	/** @param tipos los nuevos tipos o tipo del pokemon*/
	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}
	/** @return el estado del pokemon*/
	public Estado getEstado() {
		return estado;
	}
	/** @param estado el nuevo estado del pokemon*/
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	/** @return el objeto del pokemon*/
	public Objeto getObjeto() {
		return objeto;
	}
	/** @param objeto el nuevo objeto que tiene el pokemon*/
	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	/** @return la ubicacion en la pokedex del pokemon*/
	public int getUbicacion() {
		return ubicacion;
	}
	/** @param ubicacion es la nueva ubicacion en la pokedex del pokemon*/
	public void setUbicacion(int ubicacion) {
		this.ubicacion = ubicacion;
	}
	/** @return el id del entrenador que tiene el pokemon*/
	public int getIdEntrenador() {

		return Main.entrenadorLogueado.getId_Entrenador();
	}
	
	/** @param id el nuevo id del entrenador que tiene el pokemon*/
	public void setIdEntrenador(int id) {
		this.idEntrenador = id;
	}
	/** @return numeros aleatorios de las estadisticas del pokemon*/
	public Random getRandom() {
		return random;
	}
	/** @param random son nuevos numeros aleatorios de las estadisticas del pokemon*/
	public void setRandom(Random random) {
		this.random = random;
	}

}
