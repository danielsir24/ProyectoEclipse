package pokemon;

public enum Estado {
    NORMAL,          // Sin alteración
    ENVENENADO,      // Pierde vida cada turno
    GRAVEMENTE_ENVENENADO, // Pierde más vida progresivamente
    QUEMADO,         // Pierde vida cada turno y reduce ataque
    PARALIZADO,      // Puede no atacar (25% probabilidad)
    DORMIDO,         // No puede atacar durante 1-3 turnos
    CONGELADO;       // No puede atacar hasta que se descongele
    
    /**
     * Verifica si el Pokémon puede atacar con este estado
     * @return true si puede atacar, false si no
     */
    public boolean puedeAtacar() {
        switch (this) {
            case DORMIDO:
            case CONGELADO:
                return false;
            case PARALIZADO:
                // 25% de probabilidad de no poder atacar
                return Math.random() > 0.25;
            default:
                return true;
        }
    }
    
    /**
     * Calcula el daño que causa el estado al final del turno
     * @param vitalidadMaxima La vitalidad máxima del Pokémon
     * @return El daño a aplicar
     */
    public int calcularDanoPorEstado(int vitalidadMaxima) {
        switch (this) {
            case ENVENENADO:
                return vitalidadMaxima / 8; // 12.5% de daño
            case GRAVEMENTE_ENVENENADO:
                return vitalidadMaxima / 6; // ~16.6% de daño (simplificado)
            case QUEMADO:
                return vitalidadMaxima / 8; // 12.5% de daño
            default:
                return 0;
        }
    }
    
    /**
     * Multiplicador de ataque si el Pokémon está quemado
     * @return 0.5 si está quemado, 1.0 en caso contrario
     */
    public double modificadorAtaque() {
        return this == QUEMADO ? 0.5 : 1.0;
    }
}