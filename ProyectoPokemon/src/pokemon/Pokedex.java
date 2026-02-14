package pokemon;

public class Pokedex {
	
	
	private int num_Pokedex;
	private String tipo1;
	private String tipo2;
	private String img_Back;
	private String sonido;
	private String img_Frontal;
	
	
	//constructor todos los parámetros
	public Pokedex(int num_Pokedex, String tipo1, String tipo2, String img_Back, String sonido, String img_Frontal) {
		super();
		this.num_Pokedex = num_Pokedex;
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.img_Back = img_Back;
		this.sonido = sonido;
		this.img_Frontal = img_Frontal;
	}
	
	//constructor vacío
	public Pokedex() {
		super();
		
	}

	public int getNum_Pokedex() {
		return num_Pokedex;
	}

	public void setNum_Pokedex(int num_Pokedex) {
		this.num_Pokedex = num_Pokedex;
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