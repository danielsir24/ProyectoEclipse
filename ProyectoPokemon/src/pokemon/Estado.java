package pokemon;

// Usamos un Enum para definir los estados alterados que puede tener un Pokémon
public enum Estado {
    NORMAL,          // El Pokémon está perfecto, sin problemas
    ENVENENADO,      // Le va quitando vida en cada turno
    GRAVEMENTE_ENVENENADO, // Como el anterior, pero quita más vida todavía
    QUEMADO,         // Pierde vida cada turno y además pega más flojo
    PARALIZADO,      // A veces el Pokémon se queda quieto y no puede atacar
    DORMIDO,         // El Pokémon se queda frito y no hace nada unos turnos
    CONGELADO;       // No puede hacer nada hasta que el hielo se derrita
    
    /**
     * Este método chequea si el estado actual permite al Pokémon hacer un movimiento
     * @return true si puede atacar normalmente, false si el estado se lo impide
     */
    public boolean puedeAtacar() {
        switch (this) {
            case DORMIDO:
            case CONGELADO:
                // Si está frito o congelado, no puede atacar nunca
                return false;
            case PARALIZADO:
                // Si está paralizado, tiramos un dado: tiene un 25% de probabilidad de fallar
                return Math.random() > 0.25;
            default:
                // En el resto de estados (quemado, envenenado, etc.) sí puede atacar
                return true;
        }
    }
    
    /**
     * Aquí calculamos cuánta vida hay que restarle al Pokémon según su estado al acabar el turno
     * @param vitalidadMaxima Usamos el total de vida para calcular el porcentaje de daño
     * @return Los puntos de vida (PS) que hay que quitarle
     */
    public int calcularDanoPorEstado(int vitalidadMaxima) {
        switch (this) {
            case ENVENENADO:
                return vitalidadMaxima / 8; // Le quita un 12.5% de su vida total
            case GRAVEMENTE_ENVENENADO:
                return vitalidadMaxima / 6; // Le quita bastante más, casi un 17%
            case QUEMADO:
                return vitalidadMaxima / 8; // Igual que el veneno normal
            default:
                // Si está normal, dormido o paralizado, el estado no le quita vida por sí solo
                return 0;
        }
    }
    
    /**
     * Este multiplicador sirve para reducir la fuerza del Pokémon si tiene quemaduras
     * @return 0.5 (mitad de daño) si está quemado, 1.0 (daño normal) si no
     */
    public double modificadorAtaque() {
        // Si está quemado, devolvemos 0.5 para que su ataque valga la mitad
        return this == QUEMADO ? 0.5 : 1.0;
    }
}