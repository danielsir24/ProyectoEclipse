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
        String sql = "INSERT INTO pokemon (id_especie, nombre, nivel, hp_actual, id_entrenador) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
        	statement.setString(1, pokemon.getNombre());
        	statement.setString(2, pokemon.getMote());
        	statement.setInt(3, pokemon.getVitalidad());
        	statement.setInt(4, pokemon.getVitalidadMaxima());
        	statement.setInt(5, pokemon.getAtaque());
        	statement.setInt(6, pokemon.getDefensa());
        	statement.setInt(7, pokemon.getAtaque());
        	statement.setInt(8, pokemon.getAtaqueEspecial());
        	statement.setInt(9, pokemon.getDefensaEspecial());
        	statement.setInt(10, pokemon.getVelocidad());
        	statement.setInt(11, pokemon.getEstamina());
        	statement.setInt(12, pokemon.getNivel());
        	statement.setInt(13, pokemon.getExperiencia());
        	statement.setInt(14, pokemon.getFertilidad());
        	statement.setString(15, pokemon.getSexo().name());
        	statement.setInt(16, pokemon.getMovimientos());
        	
        	
        }
}
}
