package pokemon;

import java.util.*;

public class Pokemon {

	private String nombre;
	private String mote;

	private int vitalidad;
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
	private List<Movimiento> movimientos; // Máx 4
	private List<Movimiento> movimientosDisponibles;
	private List<Tipo> tipos;
	private Estado estado;
	private Objeto objeto;
	private Random random = new Random();

	public enum Sexo {
		MACHO, HEMBRA
	}

	public enum Tipo {
		NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, LUCHA, VENENO, TIERRA, VOLADOR, PSIQUICO, BICHO, ROCA, FANTASMA,
		DRAGON
	}
	
	public enum Estado {
	    NORMAL,
	    PARALIZADO,
	    QUEMADO,
	    ENVENENADO,
	    DORMIDO,
	    CONGELADO,
	    CONFUSO
	}
	//Constructor

	public Pokemon(String nombre, String mote, int vitalidad, int ataque, int defensa, int ataqueEspecial,
			int defensaEspecial, int velocidad, int estamina, int nivel, int experiencia, int fertilidad, Sexo sexo,
			List<Movimiento> movimientos, List<Movimiento> movimientosDisponibles, List<Tipo> tipos, Estado estado,
			Objeto objeto) {
		super();
		this.nombre = nombre;
		this.mote = mote;
		this.vitalidad = vitalidad;
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
	}
	//Constructor copia
	public Pokemon(Pokemon p) {
		super();
		this.nombre = p.nombre;
		this.mote = p.mote;
		this.vitalidad = p.vitalidad;
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
		this.movimientos = p.movimientos;
		this.movimientosDisponibles = p.movimientosDisponibles;
		this.tipos = p.tipos;
		this.estado = p.estado;
		this.objeto = p.objeto;
	}

	
	//Constructor por defecto
	public Pokemon() {
		super();
		this.nombre = "";
		this.mote = "";
		this.vitalidad = random.nextInt(10) + 1;
		this.ataque = random.nextInt(10) + 1;
		this.defensa = random.nextInt(10) + 1;
		this.ataqueEspecial = random.nextInt(10) + 1;
		this.defensaEspecial = random.nextInt(10) + 1;
		this.velocidad = random.nextInt(10) + 1;
		this.estamina = random.nextInt(10) + 1;
		this.nivel = 1;
		this.experiencia = 0;
		this.fertilidad = 5;
		this.sexo = sexo.MACHO;
		this.movimientos = new ArrayList<>();
		this.movimientosDisponibles =  new ArrayList<>();
		this.tipos = new ArrayList<>();
		this.estado = Estado.NORMAL;
		this.objeto = null;
	}
	
	
	//Getter y setter
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
	public int getVitalidad() {
		return vitalidad;
	}
	public void setVitalidad(int vitalidad) {
		this.vitalidad = vitalidad;
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
	
	//Metodos (NO ESTAN HECHOS TODAVIA)
}
