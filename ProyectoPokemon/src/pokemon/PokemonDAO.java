package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {

	private Connection conexion;

	// El constructor se encarga de pillar la conexión a la base de datos nada más empezar
	public PokemonDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	// Este método sirve para registrar un Pokémon nuevo en la base de datos (por ejemplo, al capturarlo)
	public boolean guardarPokemon(Pokemon pokemon, int idEntrenador, int ubicacion) {
	    
	    String sql = "INSERT INTO pokemon (num_Pokedex, id_Entrenador, id_Objeto, mote, vitalidad, "
	               + "vitalidadMaxima, ataque, defensa, ataq_Especial, def_Especial, velocidad, "
	               + "fertilidad, nivel, estado, ubicacion, sexo) "
	               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement statement = conexion.prepareStatement(sql)) {
	        // Vamos metiendo todos los datos del objeto Pokémon en los huecos de la consulta
	        statement.setInt(1, pokemon.getInfoPokedex().getNum_Pokedex());
	        statement.setInt(2, idEntrenador);
	        
	        // Si el Pokémon no lleva ningún objeto equipado, ponemos un nulo en la base de datos
	        if (pokemon.getObjeto() != null) {
	            statement.setInt(3, pokemon.getObjeto().getIdObjeto());
	        } else {
	            statement.setNull(3, java.sql.Types.INTEGER);
	        }

	        // Ponemos el mote y todas las estadísticas de combate
	        statement.setString(4, pokemon.getMote());
	        statement.setInt(5, pokemon.getVitalidad());
	        statement.setInt(6, pokemon.getVitalidadMaxima());
	        statement.setInt(7, pokemon.getAtaque());
	        statement.setInt(8, pokemon.getDefensa());
	        statement.setInt(9, pokemon.getAtaqueEspecial());
	        statement.setInt(10, pokemon.getDefensaEspecial());
	        statement.setInt(11, pokemon.getVelocidad());
	        
	        // También guardamos su nivel, su estado de salud y si es macho o hembra
	        statement.setInt(12, pokemon.getFertilidad()); 
	        statement.setInt(13, pokemon.getNivel()); 
	        statement.setString(14, pokemon.getEstado() != null ? pokemon.getEstado().name() : "NORMAL");
	        statement.setInt(15, ubicacion); // 1 si va al equipo, 0 si va al PC
	        statement.setString(16, pokemon.getSexo() != null ? pokemon.getSexo().name() : "MACHO");

	        int filas = statement.executeUpdate();
	        return filas > 0;

	    } catch (SQLException e) {
	        System.out.println("ERROR EN EL INSERT DE CAPTURA");
	        e.printStackTrace();
	        return false;
	    }
	}

	// Método para buscar un Pokémon concreto usando su ID de la base de datos
	public Pokemon buscarPorIdPokemon(int idBusqueda) {
		Pokemon p = null;
		String sql = "SELECT * FROM pokemon WHERE id_Pokemon = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idBusqueda);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					// Si lo encuentra, creamos el objeto y vamos rellenando sus datos
					p = new Pokemon();
					System.out.println("OJO: " + p.getNombre() + " tiene de vida MAX: " + p.getVitalidadMaxima());
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

					// Pasamos el texto de la base de datos a los Enums de Java
					String sexoStr = rs.getString("sexo");
					p.setSexo(sexoStr != null ? Sexo.valueOf(sexoStr) : Sexo.MACHO);

					String estadoStr = rs.getString("estado");
					p.setEstado(estadoStr != null ? Estado.valueOf(estadoStr) : Estado.NORMAL);

					p.setInfoPokedex(especieCargada);
					if (especieCargada != null) {
						p.setNombre(especieCargada.getNombreEspecie());
					}

					// Si tenía un objeto, lo buscamos también usando su DAO
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

	// Recupera todos los Pokémon que el entrenador tiene guardados en el PC (ubicación 0)
	public ArrayList<Pokemon> obtenerPokemonPC(int idEntrenador, int posicionCaja) {
		ArrayList<Pokemon> listaPC = new ArrayList<>();
		String sql = "SELECT * FROM pokemon WHERE id_Entrenador = ? AND ubicacion = 0";

		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement statement = conexion.prepareStatement(sql)) {

			statement.setInt(1, idEntrenador);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				// Vamos creando la lista de Pokémon que están en el almacenamiento
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

				// Cargamos también la info de la Pokédex para tener los tipos y el nombre real
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

	// Este método actualiza los datos de un Pokémon tras un combate o entrenamiento
	public boolean actualizarPokemon(Pokemon p) {
		String sql = "UPDATE pokemon SET vitalidad = ?, experiencia = ?, nivel = ?, estado = ?, ubicacion = ?, vitalidadMaxima = ?, ataque = ?, defensa = ?, ataq_Especial = ?, def_Especial = ?, velocidad = ? WHERE id_Pokemon = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, p.getVitalidad());
			ps.setInt(2, p.getExperiencia());
			ps.setInt(3, p.getNivel());
			ps.setString(4, p.getEstado() != null ? p.getEstado().name() : "NORMAL");
			ps.setInt(5, p.getUbicacion());

			// Actualizamos las estadísticas por si ha subido de nivel
			ps.setInt(6, p.getVitalidadMaxima());
			ps.setInt(7, p.getAtaque());
			ps.setInt(8, p.getDefensa());
			ps.setInt(9, p.getAtaqueEspecial());
			ps.setInt(10, p.getDefensaEspecial());
			ps.setInt(11, p.getVelocidad());

			// Usamos el ID para saber qué Pokémon estamos editando
			ps.setInt(12, p.getIdPokemon());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error al actualizar el Pokemon " + p.getIdPokemon());
			e.printStackTrace();
			return false;
		}
	}

	// Cambia la ubicación del Pokémon al equipo (ubicación 1)
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

	// Cambia la ubicación del Pokémon al PC (ubicación 0)
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

	// Borra definitivamente un Pokémon de la base de datos (lo libera)
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
		}
	}

	// Obtiene los 6 (o menos) Pokémon que el entrenador lleva encima para combatir
	public ArrayList<Pokemon> obtenerEquipo(int idEntrenador) {
		ArrayList<Pokemon> equipoRecuperado = new ArrayList<>();
		String sql = "SELECT * FROM pokemon WHERE id_Entrenador = ? AND ubicacion = 1";

		try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement pstmt = conexion.prepareStatement(sql)) {

			pstmt.setInt(1, idEntrenador);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				// Rellenamos el objeto con toda la información de combate necesaria
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

				// Manejo de Enums con control de errores por si hay datos raros en la BD
				String sexoStr = rs.getString("sexo");
				if (sexoStr != null) {
					try {
						p.setSexo(Sexo.valueOf(sexoStr));
					} catch (IllegalArgumentException e) {
						p.setSexo(Sexo.MACHO);
					}
				}

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

	// Este método asigna los ataques básicos de la especie a un Pokémon recién capturado
	public void asignarAtaquesPredetermiandos(int idPokemon, int numPokedex) {
		String sqlSelect = "SELECT id_Movimiento FROM pokedex_Movimiento WHERE num_Pokedex = ? LIMIT 4";

		// Y con esto se los insertamos al Pokemon concreto
		String sqlInsert = "INSERT INTO pokemon_movimento (id_Pokemon, id_Movimiento, activo, puntos_Poder) VALUES (?, ?, 1, 20)";


		try (PreparedStatement psSelect = conexion.prepareStatement(sqlSelect)) {
			psSelect.setInt(1, numPokedex);
			ResultSet rs = psSelect.executeQuery();

			try (PreparedStatement psInsert = conexion.prepareStatement(sqlInsert)) {
				// Recorremos los ataques encontrados y los vamos insertando para ese Pokémon
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

	// Busca el ID más alto de la tabla Pokémon para saber cuál ha sido el último en crearse
	public int obtenerUltimoIdGenerado() {
		// Esta consulta busca el número más alto en id_Pokemon
		// para saber ccual es el ultimo que s eha creado y asi meterle los movimientos

		//Al final no ha hecho falta exterminar a los Pokemon

		String sql = "SELECT MAX(id_Pokemon) FROM pokemon";

		try (PreparedStatement st = conexion.prepareStatement(sql); ResultSet rs = st.executeQuery(sql)) {

			if (rs.next()) {
				// Al ser AUTOINCREMENT, el último ID creado siempre será el número más grande
				return rs.getInt(1); 
			}
		} catch (SQLException e) {
			System.out.println("Error al recuperar el último ID: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}
	
	//Añadimos el objeto del pokemon a la bbdd
	public boolean equiparObjeto(int idPokemon, int idObjeto) {
	    String sql = "UPDATE pokemon SET id_Objeto = ? WHERE id_Pokemon = ?";
		  try (Connection conexion = ConexionBD.getConnection();
				PreparedStatement st = conexion.prepareStatement(sql)) {
			  if (idObjeto == 0) {
		            st.setNull(1, java.sql.Types.INTEGER);
		        } else {
		            st.setInt(1, idObjeto);
		        }
		        st.setInt(2, idPokemon);
		        return st.executeUpdate() > 0;
		    } catch (SQLException e) {
		        System.err.println("Error al equipar objeto: " + e.getMessage());
		        return false;
		    }
		}
}