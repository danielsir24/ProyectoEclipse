package pokemon.Controller;

import javafx.scene.control.Alert;
import pokemon.Main;
import pokemon.Pokemon;
import pokemon.PokedexDAO;
import pokemon.PokemonDAO;

public class pcController {

	// Metodo para mover el Pokemon del PC al equipo

	public void handleMoverAlEquipo(Pokemon seleccionado) {

		// Verificamos espacio en el main
		if (Main.miEquipo.size() >= 6) {
			System.out.println("El equipo está lleno. Suelta a alguien primero.");
			return;
		}

		// Llamamos al DAO para actualizar la DB
		PokemonDAO pDAO = new PokemonDAO();
		if (pDAO.moverAlEquipo(seleccionado.getIdPokemon())) {

			// Actualizamos el main si todo está correcto en la BD
			seleccionado.setUbicacion(1);
			Main.miEquipo.add(seleccionado);

			// Refrescamos la interfaz (quitarlo de la lista del PC)
			System.out.println(seleccionado.getMote() + " se ha unido al equipo.");

		} else {
			System.out.println("Error: No se pudo mover el Pokémon.");
		}
	}

	public void handleMoverAlPC(Pokemon seleccionado) {
		// Llamamos al DAO para actualizar la DB
		PokemonDAO pDAO = new PokemonDAO();
		if (pDAO.moverAlEquipo(seleccionado.getIdPokemon())) {

			// Actualizamos el main si todo está correcto en la BD
			seleccionado.setUbicacion(1);
			Main.miEquipo.add(seleccionado);

			// Refrescamos la interfaz (quitarlo de la lista del PC)
			System.out.println(seleccionado.getMote() + " se ha unido al equipo.");

		} else {
			System.out.println("Error: No se pudo mover el Pokémon.");
		}
	}

}
