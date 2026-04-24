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

	public boolean guardarPokemon(Pokemon pokemon, int idEntrenador, int ubicacion) {
		// Columnas de la BD
		// id_Pokemon, num_Pokedex, id_Entrenador, id_Objeto, mote,
		// vitalidad, ataque, defensa, ataq_Especial, velocidad,
		// def_Especial, fertilidad, nivel, estado, ubicacion, sexo

		// Falta la experiencia y la experiencia necesariopara subir d nivel, pero esta
		// utima nisiquiera existe en ninguna parte del codigo, asi que me tocará
		// hacerla, pero ya mañana
		String sql = "INSERT INTO pokemon "
				+ "(num_Pokedex, id_Entrenador, id_Objeto, mote, vitalidad, vitalidadMaxima, "
				+ "ataque, defensa, ataq_Especial, def_Especial, velocidad, "
				+ "fertilidad, nivel, estado, ubicacion, sexo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, pokemon.getInfoPokedex().getNum_Pokedex());
			statement.setInt(2, idEntrenador);
			if (pokemon.getObjeto() != null) {
				statement.setInt(3, pokemon.getObjeto().getIdObjeto());
			} else {
				statement.setNull(3, java.sql.Types.INTEGER);
			}
			statement.setString(4, pokemon.getMote());
			statement.setInt(5, pokemon.getVitalidad());
			statement.setInt(6, pokemon.getVitalidadMaxima());
			statement.setInt(7, pokemon.getAtaque());
			statement.setInt(8, pokemon.getDefensa());
			statement.setInt(9, pokemon.getAtaqueEspecial());
			statement.setInt(10, pokemon.getDefensaEspecial());
			statement.setInt(11, pokemon.getVelocidad());
			statement.setInt(12, pokemon.getFertilidad());
			statement.setInt(13, pokemon.getNivel());
			statement.setString(14, pokemon.getEstado() != null ? pokemon.getEstado().name() : "NORMAL");
			statement.setInt(15, ubicacion);
			statement.setString(16, pokemon.getSexo() != null ? pokemon.getSexo().name() : "MACHO");

			int filas = statement.executeUpdate();
			return filas > 0;

		} catch (SQLException e) {
			System.out.println("ERROR EN EL INSERT");
			System.out.println("Estado SQL: " + e.getSQLState());
			System.out.println("Codigo de error: " + e.getErrorCode());
			System.out.println("Mensaje: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public Pokemon buscarPorIdPokemon(int idBusqueda) {
		Pokemon p = null;
		String sql = "SELECT * FROM pokemon WHERE id_Pokemon = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idBusqueda);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					p = new Pokemon();
					int idFichaPokedex = rs.getInt("num_Pokedex");
					PokedexDAO pokedexDAO = new PokedexDAO();
					Pokedex especieCargada = pokedexDAO.buscarPorIdPokedex(idFichaPokedex);

					p.setIdPokemon(rs.getInt("id_Pokemon"));
					p.setMote(rs.getString("mote"));
					p.setVitalidad(rs.getInt("vitalidad"));
					p.setVitalidadMaxima(rs.getInt("vitalidadMaxima"));
					p.setAtaque(rs.getInt("ataque"));
					p.setDefensa(rs.getInt("defensa"));
					p.setAtaqueEspecial(rs.getInt("ataq_Especial"));
					p.setDefensaEspecial(rs.getInt("def_Especial"));
					p.setVelocidad(rs.getInt("velocidad"));
					p.setNivel(rs.getInt("nivel"));
					p.setFertilidad(rs.getInt("fertilidad"));

					String sexoStr = rs.getString("sexo");
					p.setSexo(sexoStr != null ? Sexo.valueOf(sexoStr) : Sexo.MACHO);

					String estadoStr = rs.getString("estado");
					p.setEstado(estadoStr != null ? Estado.valueOf(estadoStr) : Estado.NORMAL);

					p.setInfoPokedex(especieCargada);
					if (especieCargada != null) {
						p.setNombre(especieCargada.getNombreEspecie());
					}

					int idObjeto = rs.getInt("id_Objeto");
					if (!rs.wasNull()) {
						ObjetoDAO objetoDAO = new ObjetoDAO();
						p.setObjeto(objetoDAO.buscarPorIdObjeto(idObjeto));
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}

	public ArrayList<Pokemon> obtenerPokemonPC(int idEntrenador, int posicionCaja) {
		ArrayList<Pokemon> listaPC = new ArrayList<>();
		String sql = "SELECT * FROM pokemon WHERE id_Entrenador = ? AND ubicacion = 0";

		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {

			statement.setInt(1, idEntrenador);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Pokemon p = new Pokemon();
				p.setIdPokemon(rs.getInt("id_Pokemon"));
				p.setMote(rs.getString("mote"));
				p.setNivel(rs.getInt("nivel"));
				p.setVitalidad(rs.getInt("vitalidad"));
				p.setVitalidadMaxima(rs.getInt("vitalidadMaxima"));
				p.setAtaque(rs.getInt("ataque"));
				p.setDefensa(rs.getInt("defensa"));
				p.setAtaqueEspecial(rs.getInt("ataq_Especial"));
				p.setDefensaEspecial(rs.getInt("def_Especial"));
				p.setVelocidad(rs.getInt("velocidad"));
				p.setFertilidad(rs.getInt("fertilidad"));

				String sexoStr = rs.getString("sexo");
				p.setSexo(sexoStr != null ? Sexo.valueOf(sexoStr) : Sexo.MACHO);

				String estadoStr = rs.getString("estado");
				p.setEstado(estadoStr != null ? Estado.valueOf(estadoStr) : Estado.NORMAL);

				int numPokedex = rs.getInt("num_Pokedex");
				PokedexDAO pxDAO = new PokedexDAO();
				Pokedex info = pxDAO.buscarPorIdPokedex(numPokedex);
				p.setInfoPokedex(info);
				if (info != null) {
					p.setNombre(info.getNombreEspecie());
					List<Tipo> listaTipos = new ArrayList<>();
					if (info.getTipo1() != null) {
						try {
							listaTipos.add(Tipo.valueOf(info.getTipo1().toUpperCase()));
						} catch (Exception ignored) {
						}
					}
					if (info.getTipo2() != null) {
						try {
							listaTipos.add(Tipo.valueOf(info.getTipo2().toUpperCase()));
						} catch (Exception ignored) {
						}
					}
					p.setTipos(listaTipos);
				}
				listaPC.add(p);
			}

		} catch (SQLException e) {
			System.err.println("Error al recuperar el PC del entrenador " + idEntrenador);
			e.printStackTrace();
		}
		return listaPC;
	}

	// Método obligatorio para guardar el Entrenamiento y los Combates
	public boolean actualizarPokemon(Pokemon p) {
		String sql = "UPDATE pokemon SET vitalidad = ?, experiencia = ?, nivel = ?, estado = ?, ubicacion = ?, vitalidadMaxima = ?, ataque = ?, defensa = ?, ataq_Especial = ?, def_Especial = ?, velocidad = ? WHERE id_Pokemon = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, p.getVitalidad());
			ps.setInt(2, p.getExperiencia());
			ps.setInt(3, p.getNivel());
			ps.setString(4, p.getEstado() != null ? p.getEstado().name() : "NORMAL");
			ps.setInt(5, p.getUbicacion());

			// Estadísticas que mejoran con el entrenamiento
			ps.setInt(6, p.getVitalidadMaxima());
			ps.setInt(7, p.getAtaque());
			ps.setInt(8, p.getDefensa());
			ps.setInt(9, p.getAtaqueEspecial());
			ps.setInt(10, p.getDefensaEspecial());
			ps.setInt(11, p.getVelocidad());

			// Condición del WHERE
			ps.setInt(12, p.getIdPokemon());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error al actualizar el Pokemon " + p.getIdPokemon());
			e.printStackTrace();
			return false;
		}
	}

	public boolean moverAlEquipo(int idPokemon) {
		String sql = "UPDATE pokemon SET ubicacion = 1 WHERE id_Pokemon = ?";
		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error al mover al equipo: " + e.getMessage());
			return false;
		}
	}

	public boolean moverAlPC(int idPokemon) {
		String sql = "UPDATE pokemon SET ubicacion = 0 WHERE id_Pokemon = ?";
		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error al mover al PC: " + e.getMessage());
			return false;
		}

	}

	public boolean liberarPokemon(int idPokemon) {
		String sql = "DELETE FROM pokemon WHERE id_pokemon = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, idPokemon);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("DEBUG: Pokemon con ID: " + idPokemon + " ha sido eliminado");
				return true;
			} else {
				System.out.println("DEBUG: Pokemon no encontrado");
				return false;
			}

		} catch (SQLException e) {
			System.err.println("ERROR en el metodo nene");
			System.err.println("Mensaje: " + e.getMessage());
			System.err.println("Código de error SQL: " + e.getErrorCode());
			e.printStackTrace();
			return false;
			// Te quiero mucho señor debug

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
				p.setVitalidad(rs.getInt("vitalidad"));
				p.setVitalidadMaxima(rs.getInt("vitalidadMaxima"));
				p.setAtaque(rs.getInt("ataque"));
				p.setDefensa(rs.getInt("defensa"));
				p.setAtaqueEspecial(rs.getInt("ataq_Especial"));
				p.setDefensaEspecial(rs.getInt("def_Especial"));
				p.setVelocidad(rs.getInt("velocidad"));
				p.setFertilidad(rs.getInt("fertilidad"));

				// Sexo
				String sexoStr = rs.getString("sexo");
				if (sexoStr != null) {
					try {
						p.setSexo(Sexo.valueOf(sexoStr));
					} catch (IllegalArgumentException e) {
						p.setSexo(Sexo.MACHO);
					}
				}

				// Estado
				String estadoStr = rs.getString("estado");
				if (estadoStr != null) {
					try {
						p.setEstado(Estado.valueOf(estadoStr));
					} catch (IllegalArgumentException e) {
						p.setEstado(Estado.NORMAL);
					}
				}

				int numPokedex = rs.getInt("num_Pokedex");
				PokedexDAO pokedexDAO = new PokedexDAO();
				Pokedex info = pokedexDAO.buscarPorIdPokedex(numPokedex);
				p.setInfoPokedex(info);
				if (info != null) {
					p.setNombre(info.getNombreEspecie());
				}

				System.out
						.println("DAO: He encontrado a " + p.getMote() + " (" + p.getSexo() + ") en la base de datos.");
				equipoRecuperado.add(p);
			}
			System.out.println("Total recuperados: " + equipoRecuperado.size());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return equipoRecuperado;
	}

	public void asiganrAtaquesPredetermiandos(int idPokemon, int numPokedex) {
		// Select pa pillar los ataques de cada especie buscando su numero en la pokedex
		String sqlSelect = "SELECT id_Movimiento FROM pokedex_Movimiento WHERE num_Pokedex = ? LIMIT 4";
		// Y con esto se los insertamos al Pokemon concreto
		String sqlInsert = "INSERT INTO pokemon_movimento (id_Pokemon, id_Movimiento, activo, puntos_Poder) VALUER (?, ?, 1; 20)";

		try (PreparedStatement psSelect = conexion.prepareStatement(sqlSelect)) {
			psSelect.setInt(1, numPokedex);
			ResultSet rs = psSelect.executeQuery();

			try (PreparedStatement psInsert = conexion.prepareStatement(sqlInsert)) {
				// Nunca he puesto un coentario en estos bucles pero vaya que sirven para que
				// vaya recorriendo todos los statements mientras el rs este activo que es
				// basicamente la ejecucion de la consulta
				while (rs.next()) {
					psInsert.setInt(1, idPokemon);
					psInsert.setInt(2, rs.getInt("id_Movimiento"));
					psInsert.executeUpdate();
				}

			}
			System.out.println("DEBUG: Ataques asignados correctamente");

		} catch (SQLException e) {
			System.out.println("Error al asignar ataques: " + e.getMessage());
			e.printStackTrace();

		}

	}

	public int obtenerUltimoIdGenerado() {
		// Esta consulta busca el número más alto en id_Pokemon
		// para saber ccual es el ultimo que s eha creado y asi meterle los movimientos

		// Lo malo es que cualquier pokemon que hubiera hasta la creacion de este
		// metodo debe ser extermianadopq nova a tenermovimientos, siempre
		// os recordaremos como unos grandes
		String sql = "SELECT MAX(id_Pokemon) FROM pokemon";

		// Hace falta que siga cometandocada vez que hago una conexion con la base de
		// datos?
		try (PreparedStatement st = conexion.prepareStatement(sql); ResultSet rs = st.executeQuery(sql)) {

			if (rs.next()) {
				return rs.getInt(1); // Y aqui devuelve el id mas alto que ha encontrado que al estar en
										// AUTOINCREMENT en la base de datos pues el ultimo siempre será el mas alto
										// (espero que funcione)
			}
		} catch (SQLException e) {
			System.out.println("Error al recuperar el último ID: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
}
