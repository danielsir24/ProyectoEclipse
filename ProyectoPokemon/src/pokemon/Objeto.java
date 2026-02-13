package pokemon;

public class Objeto {

    private String nombre;

    private int bonusAtaque;
    private int penalizacionDefensa;

    public Objeto(String nombre, int bonusAtaque, int penalizacionDefensa) {
        this.nombre = nombre;
        this.bonusAtaque = bonusAtaque;
        this.penalizacionDefensa = penalizacionDefensa;
    }

    public int getBonusAtaque() {
        return bonusAtaque;
    }

    public int getPenalizacionDefensa() {
        return penalizacionDefensa;
    }
}

