package pokemon;

import java.util.HashMap;
import java.util.Map;

public enum Tipo {
    NORMAL, FUEGO, AGUA, PLANTA, ELECTRICO, HIELO, 
    LUCHA, VENENO, TIERRA, VOLADOR, PSIQUICO, BICHO, 
    ROCA, FANTASMA, DRAGON;
    
    // Mapa para guardar las efectividades
    private static Map<String, Double> efectividades = new HashMap<>();
    
    // Inicializar efectividades según la tabla de tipos de Gen 1
    static {
        // NORMAL
        efectividades.put("NORMAL-ROCA", 0.5);
        efectividades.put("NORMAL-FANTASMA", 0.0);
        
        // FUEGO
        efectividades.put("FUEGO-FUEGO", 0.5);
        efectividades.put("FUEGO-AGUA", 0.5);
        efectividades.put("FUEGO-PLANTA", 2.0);
        efectividades.put("FUEGO-HIELO", 2.0);
        efectividades.put("FUEGO-BICHO", 2.0);
        efectividades.put("FUEGO-ROCA", 0.5);
        efectividades.put("FUEGO-DRAGON", 0.5);
        
        // AGUA
        efectividades.put("AGUA-FUEGO", 2.0);
        efectividades.put("AGUA-AGUA", 0.5);
        efectividades.put("AGUA-PLANTA", 0.5);
        efectividades.put("AGUA-TIERRA", 2.0);
        efectividades.put("AGUA-ROCA", 2.0);
        efectividades.put("AGUA-DRAGON", 0.5);
        
        // PLANTA
        efectividades.put("PLANTA-FUEGO", 0.5);
        efectividades.put("PLANTA-AGUA", 2.0);
        efectividades.put("PLANTA-PLANTA", 0.5);
        efectividades.put("PLANTA-VENENO", 0.5);
        efectividades.put("PLANTA-TIERRA", 2.0);
        efectividades.put("PLANTA-VOLADOR", 0.5);
        efectividades.put("PLANTA-BICHO", 0.5);
        efectividades.put("PLANTA-ROCA", 2.0);
        efectividades.put("PLANTA-DRAGON", 0.5);
        
        // ELECTRICO
        efectividades.put("ELECTRICO-AGUA", 2.0);
        efectividades.put("ELECTRICO-PLANTA", 0.5);
        efectividades.put("ELECTRICO-ELECTRICO", 0.5);
        efectividades.put("ELECTRICO-TIERRA", 0.0);
        efectividades.put("ELECTRICO-VOLADOR", 2.0);
        efectividades.put("ELECTRICO-DRAGON", 0.5);
        
        // HIELO
        efectividades.put("HIELO-AGUA", 0.5);
        efectividades.put("HIELO-PLANTA", 2.0);
        efectividades.put("HIELO-HIELO", 0.5);
        efectividades.put("HIELO-TIERRA", 2.0);
        efectividades.put("HIELO-VOLADOR", 2.0);
        efectividades.put("HIELO-DRAGON", 2.0);
        
        // LUCHA
        efectividades.put("LUCHA-NORMAL", 2.0);
        efectividades.put("LUCHA-HIELO", 2.0);
        efectividades.put("LUCHA-VENENO", 0.5);
        efectividades.put("LUCHA-VOLADOR", 0.5);
        efectividades.put("LUCHA-PSIQUICO", 0.5);
        efectividades.put("LUCHA-BICHO", 0.5);
        efectividades.put("LUCHA-ROCA", 2.0);
        efectividades.put("LUCHA-FANTASMA", 0.0);
        
        // VENENO
        efectividades.put("VENENO-PLANTA", 2.0);
        efectividades.put("VENENO-VENENO", 0.5);
        efectividades.put("VENENO-TIERRA", 0.5);
        efectividades.put("VENENO-BICHO", 2.0);
        efectividades.put("VENENO-ROCA", 0.5);
        efectividades.put("VENENO-FANTASMA", 0.5);
        
        // TIERRA
        efectividades.put("TIERRA-FUEGO", 2.0);
        efectividades.put("TIERRA-PLANTA", 0.5);
        efectividades.put("TIERRA-ELECTRICO", 2.0);
        efectividades.put("TIERRA-VENENO", 2.0);
        efectividades.put("TIERRA-VOLADOR", 0.0);
        efectividades.put("TIERRA-BICHO", 0.5);
        efectividades.put("TIERRA-ROCA", 2.0);
        
        // VOLADOR
        efectividades.put("VOLADOR-PLANTA", 2.0);
        efectividades.put("VOLADOR-ELECTRICO", 0.5);
        efectividades.put("VOLADOR-LUCHA", 2.0);
        efectividades.put("VOLADOR-BICHO", 2.0);
        efectividades.put("VOLADOR-ROCA", 0.5);
        
        // PSIQUICO
        efectividades.put("PSIQUICO-LUCHA", 2.0);
        efectividades.put("PSIQUICO-VENENO", 2.0);
        efectividades.put("PSIQUICO-PSIQUICO", 0.5);
        
        // BICHO
        efectividades.put("BICHO-FUEGO", 0.5);
        efectividades.put("BICHO-PLANTA", 2.0);
        efectividades.put("BICHO-LUCHA", 0.5);
        efectividades.put("BICHO-VENENO", 2.0);
        efectividades.put("BICHO-VOLADOR", 0.5);
        efectividades.put("BICHO-PSIQUICO", 2.0);
        efectividades.put("BICHO-FANTASMA", 0.5);
        
        // ROCA
        efectividades.put("ROCA-FUEGO", 2.0);
        efectividades.put("ROCA-HIELO", 2.0);
        efectividades.put("ROCA-LUCHA", 0.5);
        efectividades.put("ROCA-TIERRA", 0.5);
        efectividades.put("ROCA-VOLADOR", 2.0);
        efectividades.put("ROCA-BICHO", 2.0);
        
        // FANTASMA
        efectividades.put("FANTASMA-NORMAL", 0.0);
        efectividades.put("FANTASMA-PSIQUICO", 0.0); // Bug Gen 1
        efectividades.put("FANTASMA-FANTASMA", 2.0);
        
        // DRAGON
        efectividades.put("DRAGON-DRAGON", 2.0);
    }
    
    //Método para calcular la efectividad del ataque según el tipo
     
    public double calcularEfectividad(Tipo tipoDefensor) {
        String clave = this.name() + "-" + tipoDefensor.name();
        return efectividades.getOrDefault(clave, 1.0); // 1.0 = neutral por defecto
    }
    
    //Método para calcular la efectividad del ataque si el defensor tiene 2 tipos
    
    public double calcularEfectividadDoble(Tipo tipo1, Tipo tipo2) {
        double efectividad = calcularEfectividad(tipo1);
        if (tipo2 != null && tipo1 != tipo2) {
            efectividad *= calcularEfectividad(tipo2);
        }
        return efectividad;
    }
}