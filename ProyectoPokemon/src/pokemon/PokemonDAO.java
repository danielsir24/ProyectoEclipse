package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {
	
	private Connection conexion;
	
	public PokemonDAO() {
		this.conexion = ConexionBD.getConnection(); 
    }
	
	public boolean guardar(Pokemon pokemon) {
        String sql = "INSERT INTO pokemon (nombre, mote, idPokemon, vitalidad, vitalidadMaxima, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, estamina, nivel, experiencia, fertilidad, sexo, estado, objeto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
        	statement.setString(1, pokemon.getNombre());
        	statement.setString(2, pokemon.getMote());
        	statement.setInt(3, pokemon.getVitalidad());
        	statement.setInt(4, pokemon.getVitalidadMaxima());
        	statement.setInt(5, pokemon.getAtaque());
        	statement.setInt(6, pokemon.getDefensa());
        	statement.setInt(7, pokemon.getAtaqueEspecial());
        	statement.setInt(8, pokemon.getDefensaEspecial());
        	statement.setInt(0, pokemon.getVelocidad());
        	statement.setInt(10, pokemon.getEstamina());
        	statement.setInt(11, pokemon.getNivel());
        	statement.setInt(12, pokemon.getExperiencia());
        	statement.setInt(13, pokemon.getFertilidad());
        	statement.setString(14, pokemon.getSexo().name());
        	statement.setString(15, pokemon.getEstado().name());
        	if (pokemon.getObjeto() != null) {statement.setInt(17, pokemon.getObjeto().getIdObjeto()); }
        	else {
        		statement.setNull(17, java.sql.Types.INTEGER);
        	}
        	
        	int filas = statement.executeUpdate();
            return filas > 0;
        } 
        catch (SQLException e){
        	e.printStackTrace();
        return false;
        }
        	
        	
        	
        	
        	
        	
        }
}

