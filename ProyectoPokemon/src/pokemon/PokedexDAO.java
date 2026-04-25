package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokedexDAO {

	private Connection conexion;

	// El constructor prepara la conexión para poder hablar con la base de datos
	public PokedexDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	// Método para buscar los datos de una especie concreta usando su número de la Pokédex
	public Pokedex buscarPorIdPokedex(int numPokedex) {
		Pokedex px = null;
		String sql = "SELECT * FROM pokedex WHERE num_Pokedex = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			// Ponemos el número que queremos buscar en la consulta
			statement.setInt(1, numPokedex);

			try (ResultSet rs = statement.executeQuery()) {
				// Si la base de datos encuentra la especie, rellenamos el objeto Pokedex
				if (rs.next()) {
					px = new Pokedex();
					px.setNum_Pokedex(rs.getInt("num_Pokedex"));
					px.setNombreEspecia(rs.getString("nombre"));
					px.setTipo1(rs.getString("tipo1"));
					px.setTipo2(rs.getString("tipo2"));
					px.setImg_Back(rs.getString("img_Back"));
					px.setSonido(rs.getString("sonido"));
					px.setImg_Frontal(rs.getString("img_Frontal"));
				}
			}
		} catch (SQLException ex) {
			// Si falla la consulta, avisamos del error por la consola
			System.out.println("Error al buscar en la Pokedex: " + ex.getMessage());
			ex.printStackTrace();
		}
		return px;
	}
	
	// Este método sirve para sacar un número de Pokémon al azar de la base de datos
	public int generarIdPokedexAleatorio() {
		
		// Comprobamos primero si la conexión funciona correctamente
		if (this.conexion == null) {
	        System.out.println("Error: No hay conexión a la DB");
	        return 1;
		} else {
	    int id = -1;
	    // Usamos ORDER BY RAND() para que SQL nos dé una fila aleatoria
	    String sql = "SELECT num_Pokedex FROM pokedex ORDER BY RAND() LIMIT 1";
	    
	    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                // Guardamos el número que ha salido
	                id = rs.getInt("num_Pokedex");
	            }
	        }
	    } catch (SQLException ex) {
	        System.out.println("Error al generar ID aleatorio: " + ex.getMessage());
	    }
	    return id;
	    }
	}
}

