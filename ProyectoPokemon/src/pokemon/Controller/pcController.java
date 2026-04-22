package pokemon.Controller;

import pokemon.Main;
import pokemon.Pokemon;
import pokemon.PokedexDAO;
import pokemon.PokemonDAO;

public class pcController {
	public void handleMoverAlEquipo(Pokemon seleccionado) {
	    
	    // 1. Verificamos espacio en el Main (lista en memoria)
	    if (Main.miEquipo.size() >= 6) {
	        System.out.println("El equipo está lleno. Suelta a alguien primero.");
	        return;
	    }

	    // 2. Llamamos al DAO para actualizar la Base de Datos
	    PokemonDAO pDAO = new PokemonDAO();
	    if (pDAO.moverAlEquipo(seleccionado.getIdPokemon())) {
	        
	        // 3. Si la DB dijo que OK, actualizamos la memoria (Main)
	        seleccionado.setUbicacion(1);
	        Main.miEquipo.add(seleccionado);
	        
	        // 4. Refrescamos la interfaz (quitarlo de la lista del PC)
	        System.out.println(seleccionado.getMote() + " se ha unido al equipo.");
	        
	    } else {
	        System.out.println("Error: No se pudo mover el Pokémon.");
	    }
	}

}
