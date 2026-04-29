package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona el acceso a datos de los objetos Pokemon. Permite realizar
 * operaciones CRUD sobre la base de datos.
 * 
 * @author Alejandro Varela, Daniel Sirbu, Adrían Rodriguez, Juan Carlos Benitez
 * 
 * @version 1.0
 */
public class PokemonDAO {

	/** Conexión a la base de datos. */
	private Connection conexion;

	/**
	 * El constructor se encarga de realizar la conexión a la base de datos nada más
	 * empezar, usando la clase ConexionBD
	 * 
	 */
	public PokemonDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	/**
	 * Este método sirve para registrar un Pokémon nuevo en la base de datos (por
	 * ejemplo, al capturarlo), realizando una consulta SQL de tipo INSERT, que
	 * recoge todos los datos del pokemon, lo cera en la base de datos con un nuevo
	 * id, y mete las estadisticas recoidas en los huecos en la base de datos
	 * utilizando los statements de la conexion tanto para recogerlos como para
	 * establecerlos
	 * 
	 * @param pokemon      Este es el pokemon que ha sido capturado, utilizando
	 *                     getters y setters se recogerran las estadisticas y se
	 *                     establecerán en la base de datos
	 * 
	 * @param idEntrenador Este parametro sirve para que el metodo sepa en que
	 *                     sesión del entreador debe almacenar el pokemon, según la
	 *                     id de entrenador que lo haya capturado
	 * 
	 * @param ubicacion    Este parametro sirve para saber si el pokemon se
	 *                     encuentra en el PC o en el qeuipo. Si en el momento de
	 *                     guardarlo, el espacion del equipo está lleno, se le
	 *                     asiganra a la ubicacion un valor de 0, pero si no lo
	 *                     está, se le asignará un 1
	 * 
	 * @return Este return nos devuelve un booleano de filas afectadas por la
	 *         consulta, a modode DEBUG,para saber si se ha ejecutado correctamente
	 *         el numero total de consultas
	 * 
	 * @throws SQLException Por si la cosulta de SQL falla al insertar los datos, o
	 *                      la base de datos no responde
	 */
	public boolean guardarPokemon(Pokemon pokemon, int idEntrenador, int ubicacion) {

		String sql = "INSERT INTO pokemon (num_Pokedex, id_Entrenador, id_Objeto, mote, vitalidad, "
				+ "vitalidadMaxima, ataque, defensa, ataq_Especial, def_Especial, velocidad, "
				+ "fertilidad, nivel, estado, ubicacion, sexo, experiencia) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			// Vamos metiendo todos los datos del objeto Pokémon en los huecos de la
			// consulta
			statement.setInt(1, pokemon.getInfoPokedex().getNum_Pokedex());
			statement.setInt(2, idEntrenador);

			// Si el Pokémon no lleva ningún objeto equipado, ponemos un nulo en la base de
			// datos
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
			statement.setInt(17, pokemon.getExperiencia());

			int filas = statement.executeUpdate();
			return filas > 0;

		} catch (SQLException e) {
			System.out.println("ERROR EN EL INSERT DE CAPTURA");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Este metodo sirve, como indica su nombre, para buscar a un pokemon que haya
	 * sido capturado por el entrenador, usando como metodo de busqieda su ID, el
	 * cual es único. Esto con la finalidad de poder hacer cambios en este pokemon
	 * desde Java, usando los metodos asociados a esta clase o a la clase Pokemon
	 * 
	 * @param idBusqueda Usamos este parametro para saber con que pokemon estamos
	 *                   "operando" por así decirlo. Este parametro lo intoduciremos
	 *                   en el parentesis del metodo
	 * 
	 * @return Un objeto de la clase Pokemon con toda su información y devuelve null
	 *         si el ID no existe o no se encuentra
	 * 
	 * @throws SQLException: Si la consulta SELECT falla o la base de datos no
	 *                       responde
	 */
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

	/**
	 * Este metodo busca los pokemon en la base de datos según el id de su
	 * entrenador, y a continuación se fija en el valor de su atributo "ubicación"
	 * para saber si está en la caja del PC, lo cual sería 0. Este metodo lo usamos
	 * a la hora de cargar los pokemon que hay en el PC en la vista del PC
	 * 
	 * @param idEntrenador El id del entrenador, en el que se fijará el metodo para
	 *                     saber cuales son los pokemon que tiene que buscar
	 * 
	 * @param posicionCaja La posición de la caja le servirá al metodo para
	 *                     determinar si el Pokemon realmente está en el PC
	 * 
	 * @return Un ArrayList con los Pokémon del entrenador que están en el PC. Si no
	 *         tiene ninguno, la lista se devolverá vacía pero no null
	 * 
	 * @throws SQLException por si la consulta falla al buscar los Pokemon o la base
	 *                      de datos no responde
	 */
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
	/**
	 * Este metodo nos sirve para actualizar los parametros de un Pokemon en la base
	 * de datos tras completar un combate,unentrenamiento, cambiar un
	 * movimeinto...etc
	 * 
	 * @param p Es el Pokemon del cual vamos a actualizar los datos
	 * 
	 * @return Devuelve true si la consulta y la actualizacion e los datos ha sido
	 *         exitosa, si falla,devuelve false
	 * 
	 * @throws SQLException por si la consulta UPDATE falla o la base de datos no
	 *                      responde
	 */
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
	/**
	 * Con este metodo cambiamosla ubicaión del Pokemon a,donde esta era 0, así, la
	 * base de datos lo leerá y sabrá que el pokemon ha pasado a estar en el equipo
	 * siempre y cuando haya espacio
	 * 
	 * @param idPokemon El id del pokemon cuya ubicación va a ser cambiada
	 * 
	 * @return Devuelve true si la consulta ha sido exitosa, si no, devuelve false
	 * 
	 * @throws SQLException por si la consulta falla al cambiar la ubicación del
	 *                      pokemon o la base de datos no responde
	 */
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

	/**
	 * Este metodo es igual al anterior, pero al revés. Cambia la ubicación del
	 * Pokemon a 0 donde era 1, para que su nueva ubiación sea el PC
	 * 
	 * @param idPokemon El id del pokemon cuya ubicación va a ser cambiada
	 * 
	 * @return Devuelve true si la consulta ha sido exitosa, si no, devuelve false
	 * 
	 * @throws SQLException por si la consulta falla al cambiar la ubicación del
	 *                      pokemon o la base de datos no responde
	 * 
	 */
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

	/**
	 * Este metodo sirve para borrar un Pokemon de la base de datos ejecutando una
	 * consulta DELETE de la fila de dicho Pokemon usando su id
	 * 
	 * @param idPokemon eL ID del Pokemon que va a ser liberado (borrado de la base
	 *                  de datos)
	 * 
	 * @return Devuelve true si la elimiación ha sido exitosa, si no, devuelve false
	 * 
	 * @throws SQLException por si la consulta falla al eliminar al pokemon o la
	 *                      base de datos no responde
	 */
	public boolean liberarPokemon(int idPokemon) {
		try {
			// Primero borramos los movimientos del pokemon
			// si no la foreign key no nos deja borrar el pokemon
			String sqlMovimientos = "DELETE FROM pokemon_movimiento WHERE id_Pokemon = ?";
			try (PreparedStatement stMov = conexion.prepareStatement(sqlMovimientos)) {
				stMov.setInt(1, idPokemon);
				stMov.executeUpdate();
			}

			// Ahora ya podemos borrar el pokemon
			String sqlPokemon = "DELETE FROM pokemon WHERE id_Pokemon = ?";
			try (PreparedStatement stPok = conexion.prepareStatement(sqlPokemon)) {
				stPok.setInt(1, idPokemon);
				return stPok.executeUpdate() > 0;
			}

		} catch (SQLException e) {
			System.out.println("ERROR en el metodo nene");
			System.out.println("Mensaje: " + e.getMessage());
			System.out.println("Codigo de error SQL: " + e.getErrorCode());
			e.printStackTrace();
			return false;
		}
	}

	// Obtiene los 6 (o menos) Pokémon que el entrenador lleva encima para combatir

	/**
	 * Este metidi sirve para obtener un ArrayList de los Poekmon que estén en el
	 * equipo, cuya ubicación será 1. Es igual al de obtener los Pokemon del PC pero
	 * con la ubicación cambiada
	 * 
	 * @param idEntrenador El id del entrenador cuyo equipo será buscado en la
	 *                     consulta de la base de datos
	 * 
	 * @return Devuelve un ArrayList con los pokemon de ese entrenador cuya
	 *         ubicación sea 1.
	 * 
	 * @throws SQLException por si la consulta falla al buscar los Pokemon o la base
	 *                      de datos no responde
	 */
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

				List<Movimiento> movimientos = obtenerMovimientosPokemon(p.getIdPokemon(), conexion);
				p.setMovimientos(movimientos);

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

	// Este método asigna los ataques básicos de la especie a un Pokémon recién
	// capturado
	/**
	 * Este metodo se encarga de buscar en la base de datos la información de la
	 * Pokedex de ese pokemon segun su numero de la pokedex, e insertar en el
	 * Pokemon en concreto los ataques basicos dada su especie.
	 * 
	 * @param idPokemon  El id del Pokemon en el que se va a aintroducir la lista de
	 *                   ataques
	 * 
	 * @param numPokedex El numero de la Pokedex en el que se buscará la lista de
	 *                   ataques que se vaa introducir en el pokemon.
	 * 
	 * @throws SQLException por si la consulta falla al buscar el id del Pokemon o
	 *                      introducir la lista de atques en él, o la base de datos
	 *                      no responde
	 */
	public void asignarAtaquesPredetermiandos(int idPokemon, int numPokedex) {
		String sqlSelect = "SELECT id_Movimiento FROM pokedex_Movimiento WHERE num_Pokedex = ? LIMIT 4";

		// Y con esto se los insertamos al Pokemon concreto
		String sqlInsert = "INSERT INTO pokemon_movimiento (id_Pokemon, id_Movimiento, activo, puntos_Poder) VALUES (?, ?, 1, 20)";

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

	// Metodo para ganar experiencia
	/**
	 * Este metodo sirve para actualizar en la base de datos los puntos de
	 * experiencia totales que tiene un pokemon através de un UPDATE
	 * 
	 * @param p                 El pokemon al que van a ser sumados esos puntos de
	 *                          experiencia
	 * 
	 * @param experienciaGanada La experiencia que va a ser sumada a la que ya
	 *                          existe en la base de datos
	 * 
	 * @return Si la actualizacion de experiencia ha sido exitosa, devuelve true, en
	 *         caso de nos serlo, devuleve false
	 * 
	 * @throws SQLException Por si la consulta falla al actualizar la experienciadel
	 *                      Pokemon
	 */
	public boolean actualizarExperiencia(Pokemon p, int experienciaGanada) {
		p.ganarExperiencia(experienciaGanada);

		// Con esta query actualizamos las estadisticas del pokemon si sube de nivel
		String sql = "UPDATE pokemon SET experiencia = ?, nivel = ?, vitalidadMaxima = ?, vitalidad = ?, "
				+ "ataque = ?, defensa = ?, ataq_Especial = ?, def_Especial = ?, velocidad = ? "
				+ "WHERE id_Pokemon = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, p.getExperiencia());
			ps.setInt(2, p.getNivel());
			ps.setInt(3, p.getVitalidadMaxima());
			ps.setInt(4, p.getVitalidad());
			ps.setInt(5, p.getAtaque());
			ps.setInt(6, p.getDefensa());
			ps.setInt(7, p.getAtaqueEspecial());
			ps.setInt(8, p.getDefensaEspecial());
			ps.setInt(9, p.getVelocidad());
			ps.setInt(10, p.getIdPokemon());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al dar experiencia al Pokemon: " + p.getIdPokemon());
			e.printStackTrace();
			return false;
		}
	}

	// Busca el ID más alto de la tabla Pokémon para saber cuál ha sido el último en
	// crearse

	/**
	 * Este metodo sirve para buscar en la base de datos el ultimo id que ha sido
	 * generado, con la finalidad de encontrar al ultimo pokemonañadido a ella
	 * 
	 * @return Devuelve el ultimo id que ha generado la base de datos
	 * 
	 * @throws SQLException Por si la consulta falla al buscar el ultimo id
	 */
	public int obtenerUltimoIdGenerado() {
		// Esta consulta busca el número más alto en id_Pokemon
		// para saber ccual es el ultimo que s eha creado y asi meterle los movimientos

		// Al final no ha hecho falta exterminar a los Pokemon

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

	// Añadimos el objeto del pokemon a la bbdd
	/**
	 * Este metodo sirve para actualizar mediante un UPDATE el parametro de Objeto
	 * de un pokemon, con la finalidadde equiparle un objeto
	 * 
	 * @param idPokemon El id del Pokemon al que se le va a añadir el objeto
	 * 
	 * @param idObjeto  El id del objeto que va a ser añadido
	 * 
	 * @return Devuelve true si el objeto ha sido añadido exitosamente mediante la
	 *         consulta, si no, devuelve un false
	 * 
	 * @throws SQLException Por si la consulta falla al añadirel objeto o la base de
	 *                      datos no responde
	 */
	public boolean equiparObjeto(int idPokemon, int idObjeto) {
		String sql = "UPDATE pokemon SET id_Objeto = ? WHERE id_Pokemon = ?";
		try (Connection conexion = ConexionBD.getConnection(); PreparedStatement st = conexion.prepareStatement(sql)) {
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

	/**
	 * Este metodo recoge una List de los movimientos que tenga un Pokemon según el
	 * id que recoja, este lo usamos para añadir su ista de movimientos en la
	 * pantalla del PC o la pantalla del COMBATE. No confunir con el anterior metodo
	 * relacionado con los movimeintos, pues en este caso, si los movimientos de un
	 * pokemon han sido cambiados, este recoge los movimientos que tiene el pokemon
	 * y no los predeterminados de su especie
	 * 
	 * @param idPokemon El id del Pokemon cuyos movimientos van a ser obtenidos
	 * 
	 * @param conexion  La conexion con la base de datos
	 * 
	 * @return Devuelve una lista con los movimentos del Pokemon en concreto según
	 *         su id
	 * @throws SQLException Por si la consulta falla al buscar los movimientos del
	 *                      pokemon según su id
	 */
	private List<Movimiento> obtenerMovimientosPokemon(int idPokemon, Connection conexion) {
		List<Movimiento> movimientos = new ArrayList<>();

		String sql = "SELECT m.id_Movimiento, m.nom_Movimiento, m.potencia, m.tipo, "
				+ "m.clase_Movimiento, m.coste_estamina, pm.puntos_Poder " + "FROM pokemon_movimiento pm "
				+ "JOIN movimiento m ON pm.id_Movimiento = m.id_Movimiento "
				+ "WHERE pm.id_Pokemon = ? AND pm.activo = 1";

		try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
			pstmt.setInt(1, idPokemon);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Tipo tipo;
				try {
					tipo = Tipo.valueOf(rs.getString("tipo").toUpperCase());
				} catch (Exception e) {
					tipo = Tipo.NORMAL;
				}

				Movimiento mov = new Movimiento(rs.getString("nom_Movimiento"), rs.getInt("potencia"), tipo,
						rs.getString("clase_Movimiento"), rs.getInt("coste_estamina"), rs.getInt("puntos_Poder"));

				movimientos.add(mov);
			}
			System.out.println("DAO: " + movimientos.size() + " movimientos cargados para pokemon " + idPokemon);

		} catch (SQLException e) {
			System.err.println("Error al cargar movimientos: " + e.getMessage());
		}

		return movimientos;
	}
}