package pokemon;

public class Entrenador {
    private int id_Entrenador;
    private String nom_Entrenador;
    private String password;
    private String img_Entrenador;
    private int pokedollars;
    private String tipo_Entrenador;
    
    
    //Todos los parametros
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
    
	//contructor vacío
	public Entrenador() {
		super();
	}

	//Getters and Setters
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
