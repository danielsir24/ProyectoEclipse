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
		// Insert que se ejecutará en la base de datos una vez capturemos al pokemon
		String sql = "INSERT INTO pokemon (nombre, mote, vitalidad, vitalidadMaxima, ataque, defensa, ataq_Especial, def_Especial, velocidad, nivel, experiencia, fertilidad, sexo, estado, id_Objeto, ubicacion, id_Entrenador, num_Pokedex) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		// Preparamos la conexion
		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setString(1, pokemon.getNombre());
			statement.setString(2, pokemon.getMote());
			statement.setInt(3, pokemon.getVitalidad());
			statement.setInt(4, pokemon.getVitalidadMaxima());
			statement.setInt(5, pokemon.getAtaque());
			statement.setInt(6, pokemon.getDefensa());
			statement.setInt(7, pokemon.getAtaqueEspecial());
			statement.setInt(8, pokemon.getDefensaEspecial());
			statement.setInt(9, pokemon.getVelocidad());
			statement.setInt(10, pokemon.getNivel());
			statement.setInt(11, pokemon.getExperiencia());
			statement.setInt(12, pokemon.getFertilidad());
			statement.setString(13, pokemon.getSexo().name());
			statement.setString(14, pokemon.getEstado().name());
			// esto es pa saber si tiene objeto on y si lo siente lo almacena, pero vaya
			// que los pokemon de normal se van a generar sin objetos 
			if (pokemon.getObjeto() != null) {
				statement.setInt(15, pokemon.getObjeto().getIdObjeto());
			} else {
				statement.setNull(15, java.sql.Types.INTEGER);
			}
			statement.setInt(16, ubicacion);
			statement.setInt(17, idEntrenador);
			statement.setInt(18, pokemon.getInfoPokedex().getNum_Pokedex());

			

			int filas = statement.executeUpdate();
			return filas > 0;
			
			
		} catch (SQLException e) {
			System.out.println("ERROR EN EL INSERT");
		    System.out.println("Estado SQL: " + e.getSQLState());
		    System.out.println("Código de error: " + e.getErrorCode());
		    System.out.println("Mensaje: " + e.getMessage());
		    e.printStackTrace(); 
		    return false;
		}
		
	}

	public Pokemon buscarPorIdPokemon(int idBusqueda) {
		Pokemon p = null;
		// Select que se ejecutará en la base de datos
		String sql = "SELECT * FROM pokemon WHERE idPokemon = ?";

		// Preparamosla conexion
		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idBusqueda);

			try (ResultSet rs = statement.executeQuery()) {

				if (rs.next()) {
					// Declaramos los atributos que vamos a seleccionar
					p = new Pokemon();
					int idFichaPokedex = rs.getInt("num_Pokedex");
					PokedexDAO pokedexDAO = new PokedexDAO();
					Pokedex especieCargada = pokedexDAO.buscarPorIdPokedex(idFichaPokedex);
					int idObjeto = rs.getInt("id_Objeto");
					ObjetoDAO objetoDAO = new ObjetoDAO();
					Objeto objetoCargado = objetoDAO.buscarPorIdObjeto(idObjeto);
					//

					// Hacemos los sets
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

	public ArrayList<Pokemon> obtenerPokemonPC(int idEntrenador) {
		ArrayList<Pokemon> listaPC = new ArrayList<>();

		String sql = "SELECT * FROM pokemon WHERE id_eNTRENADOR = ? AND ubicacion = 0";

		// Preparamos la cnexion para recoger los datos
		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {

			statement.setInt(1, idEntrenador);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {

				Pokemon p = new Pokemon();

				// Hacemos los sets de la conexion para que se modifiquen en la base de datos
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

				// Aquivann a ir los movimientos pero rimero hay que hacer MovimientoDAO

				// Aqui van a ir los objetosperofalta hacer objeto DAO

				listaPC.add(p);
			}

		} catch (SQLException e) {
			System.err.println("Error al recuperar el PC del entrenador " + idEntrenador);
			e.printStackTrace();
		}

		return listaPC;
	}

	// Metodo paramover del PC al equipo
	public boolean moverAlEquipo(int idPokemon) {
		// Primero ponemos el select que se hara en el sql
		String sql = "UPDATE pokemon SET ubicacion = 1 WHERE id_Pokemon = ?";

		// Como siempre hacemos la conexion con la base de datos
		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			
			// Esto es para comprobar que haya funcionado, si es true, es que el pokemon se
			// ha movido
			int filasAfectadas = statement.executeUpdate();
			return filasAfectadas > 0;
			

		} catch (SQLException e) { // Y aqui la expecion
			System.err.println("Error al mover al equipo: " + e.getMessage());
			return false;

		}

	}

	// Metodo para mover al PC, es identico al del equipo pero con la ubicacion
	// cambiada
	public boolean moverAlPC(int idPokemon) {
		String sql = "UPDATE pokemon SET ubicacion = 0 WHERE id_Pokemon = ?";

		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			

			int filasAfectadas = statement.executeUpdate();
			return filasAfectadas > 0;

		} catch (SQLException e) {
			System.err.println("Error al mover al PC: " + e.getMessage());
			return false;

		}
		
		

	}
	
	public ArrayList<Pokemon> obtenerEquipo(int idEntrenador) {
	    ArrayList<Pokemon> equipoRecuperado = new ArrayList<>();
	    String sql = "SELECT * FROM pokemon WHERE id_Entrenador = ? AND ubicacion = 1";

	    try (Connection conexion = ConexionBD.getConnection();
	         PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        
	        pstmt.setInt(1, idEntrenador);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Pokemon p = new Pokemon();
	            p.setIdPokemon(rs.getInt("id_Pokemon"));
	            p.setMote(rs.getString("mote"));
	            p.setNivel(rs.getInt("nivel"));
	            p.setIdEntrenador(rs.getInt("id_Entrenador"));
	            
	            int numPokedex = rs.getInt("num_Pokedex");
	            PokedexDAO pokedexDAO = new PokedexDAO();
	            p.setInfoPokedex(pokedexDAO.buscarPorIdPokedex(numPokedex));
	            p.setNombre(p.getInfoPokedex().getNombreEspecie());
	            System.out.println("DAO: He encontrado a " + p.getMote() + " en la base de datos.");
	            
	            equipoRecuperado.add(p);
	        }
	        System.out.println("Total recuperados: " + equipoRecuperado.size());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return equipoRecuperado;
	}

}
