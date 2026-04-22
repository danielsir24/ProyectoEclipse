package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import pokemon.ConexionBD;
import java.sql.Connection;

import pokemon.Pokemon;
import java.util.ArrayList;
import java.util.List;
import pokemon.Tipo;



public class PokemonDAO {

	private Connection conexion;

	public PokemonDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	public boolean guardarPokemon(Pokemon pokemon, int idEntrenador, int ubicacion) {
		String sql = "INSERT INTO pokemon (nombre, mote, id_Pokemon, vitalidad, vitalidadMaxima, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad, estamina, nivel, experiencia, fertilidad, sexo, estado, id_Objeto, ubicacion, id_Entrenador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
			statement.setString(18, pokemon.getEstado().name());
			statement.setInt(19, pokemon.getIdEntrenador());

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
	
	public ArrayList<Pokemon> obtenerPokemonPC(int idEntrenador){
		ArrayList<Pokemon> listaPC = new ArrayList<>();
		
		String sql = "SELECR * FROM pokemon WHERE id_eNTRENADOR = ? AND ubicacion = 0";
		
		//Preparamos la cnexion para recoger los datos
		try (Connection conexion = ConexionBD.getConnection();
			PreparedStatement statement = conexion.prepareStatement(sql)){
			
			statement.setInt(1, idEntrenador);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				
				Pokemon p = new Pokemon();
				
				String mote = rs.getString("mote");
				int nivel = rs.getInt("nivel");
				int idPokemon = rs.getInt("idPokemon");
				int ubicacion = rs.getInt("ubicacion");
				
				int numPokedex = rs.getInt("num_Pokedex");
				PokedexDAO pxDAO = new PokedexDAO();
				Pokedex info = pxDAO.buscarPorIdPokedex(numPokedex);
				p.setInfoPokedex(info);
				p.setNombre(mote);
				
				List<Tipo> listaTipos = new ArrayList<>();
				
				if (info.getTipo1() != null) {
					listaTipos.add(Tipo.valueOf(info.getTipo1().toUpperCase()));
				}
				if (info.getTipo2() != null) {
					listaTipos.add(Tipo.valueOf(info.getTipo1().toUpperCase()));
				}
				p.setTipos(listaTipos);
				
			}
			
		}catch(SQLException e) {
			
		}
				
				
				
		
	}
}
