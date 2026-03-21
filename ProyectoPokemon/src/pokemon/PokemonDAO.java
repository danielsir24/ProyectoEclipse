package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokemonDAO {

	private Connection conexion;

	public PokemonDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	public boolean guardarPokemon(Pokemon pokemon) {
		String sql = "INSERT INTO pokemon (nombre, mote, idPokemon, vitalidad, vitalidadMaxima, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, estamina, nivel, experiencia, fertilidad, sexo, estado, objeto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setString(1, pokemon.getNombre());
			statement.setString(2, pokemon.getMote());
			statement.setInt(3, pokemon.getIdPokemon());
			statement.setInt(4, pokemon.getVitalidad());
			statement.setInt(5, pokemon.getVitalidadMaxima());
			statement.setInt(6, pokemon.getAtaque());
			statement.setInt(7, pokemon.getDefensa());
			statement.setInt(8, pokemon.getAtaqueEspecial());
			statement.setInt(9, pokemon.getDefensaEspecial());
			statement.setInt(10, pokemon.getVelocidad());
			statement.setInt(11, pokemon.getEstamina());
			statement.setInt(12, pokemon.getNivel());
			statement.setInt(13, pokemon.getExperiencia());
			statement.setInt(14, pokemon.getFertilidad());
			statement.setString(15, pokemon.getSexo().name());
			statement.setString(16, pokemon.getEstado().name());
			if (pokemon.getObjeto() != null) {
				statement.setInt(17, pokemon.getObjeto().getIdObjeto());
			} else {
				statement.setNull(1, java.sql.Types.INTEGER);
			}

			int filas = statement.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Pokemon buscarPorIdPokemon(int idBusqueda) {
		Pokemon p = null;
		String sql = "SELECT * FROM pokemon WHERE idPokemon = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idBusqueda);

			try (ResultSet rs = statement.executeQuery()) {

				if (rs.next()) {
					p = new Pokemon();
					int idFichaPokedex = rs.getInt("num_Pokedex");
					PokedexDAO pokedexDAO = new PokedexDAO();
					Pokedex especieCargada = pokedexDAO.buscarPorIdPokedex(idFichaPokedex);
					int idObjeto = rs.getInt("id_Objeto");
					ObjetoDAO objetoDAO = new ObjetoDAO();
					Objeto objetoCargado = objetoDAO.buscarPorIdObjeto(idObjeto);
					//

					p.setIdPokemon(rs.getInt("idPokemon"));
					p.setNombre(rs.getString("nombre"));
					p.setMote(rs.getString("mote"));
					p.setVitalidad(rs.getInt("vitalidad"));
					p.setVitalidadMaxima(rs.getInt("vitalidadMaxima"));
					p.setAtaque(rs.getInt("ataque"));
					p.setDefensa(rs.getInt("defensa"));
					p.setAtaqueEspecial(rs.getInt("ataqueEspecial"));
					p.setDefensaEspecial(rs.getInt("defensaEspecial"));
					p.setVelocidad(rs.getInt("velocidad"));
					p.setEstamina(rs.getInt("estamina"));
					p.setNivel(rs.getInt("nivel"));
					p.setExperiencia(rs.getInt("experiencia"));
					p.setFertilidad(rs.getInt("fertilidad"));
					p.setSexo(Sexo.valueOf(rs.getString("sexo")));
					p.setEstado(Estado.valueOf(rs.getString("estado")));
					p.setInfoPokedex(especieCargada);
					p.setObjeto(objetoCargado);

				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}
}
