package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pokemon.Movimiento;

public class MovimientoDAO {

	private Connection conexion;

	public MovimientoDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	// Busca un movimiento por su id
	public Movimiento buscarPorIdMovimiento(int idMovimiento) {
		Movimiento mov = null;
		String sql = "SELECT * FROM movimiento WHERE id_Movimiento = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idMovimiento);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					mov = new Movimiento(rs.getString("nom_Movimiento"), rs.getInt("potencia"), Tipo.NORMAL, "ATAQUE",
							0, rs.getInt("id_Movimiento"));
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al buscar movimiento con id " + idMovimiento);
			e.printStackTrace();
		}
		return mov;
	}

	// Devuelve la lista de movimientos de un pokemon
	public ArrayList<Movimiento> obtenerMovimientosDePokemon(int idPokemon) {
		ArrayList<Movimiento> movimientos = new ArrayList<>();
		String sql = "SELECT m.* FROM movimiento m "
				+ "JOIN pokedex_movimiento pm ON m.id_Movimiento = pm.id_Movimiento " + "WHERE pm.num_Pokedex = ?";
		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Movimiento mov = new Movimiento(rs.getString("nom_Movimiento"), rs.getInt("potencia"),
							Tipo.valueOf(rs.getString("tipo")), rs.getString("clase_Movimiento"),
							rs.getInt("coste_estamina"), rs.getInt("id_Movimiento"));
					movimientos.add(mov);
				}
			}
			System.out.println("Movimientos del pokemon " + idPokemon + ": " + movimientos.size() + " encontrados.");

		} catch (SQLException e) {
			System.err.println("Error al obtener movimientos del pokemon " + idPokemon);
			e.getMessage();
			e.printStackTrace();
		}
		return movimientos;
	}

	// Asigna un movimiento a un pokemon
	public boolean asignarMovimiento(int idPokemon, int idMovimiento) {
		String sql = "INSERT INTO pokemon_movimiento (id_Pokemon, id_Movimiento, activo, puntos_Poder) "
				+ "SELECT ?, ?, 1, m.puntos_Poder FROM movimiento m WHERE m.id_Movimiento = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			statement.setInt(2, idMovimiento);
			statement.setInt(3, idMovimiento);

			int filas = statement.executeUpdate();
			return filas > 0;

		} catch (SQLException e) {
			System.err.println("Error al asignar movimiento " + idMovimiento + " al pokemon " + idPokemon);
			e.printStackTrace();
			return false;
		}
	}

	// Metodo para que un pokemon aprenda un movimiento, borrrando el anterior
	public boolean aprenderMovimientoManual(int idPokemon, int idMovNuevo, int idMovOlvidado) {
		// Si idMovOlvidado > 0, significa que el usuario eligió uno para sustituir
		String sqlDelete = "DELETE FROM pokemon_movimientos WHERE id_Pokemon = ? AND id_Movimiento = ?";
		String sqlInsert = "INSERT INTO pokemon_movimientos (id_Pokemon, id_Movimiento) VALUES (?, ?)";

		try {
			conexion.setAutoCommit(false);

			// Si hay un movimiento que olvidar, lo borramos primero
			if (idMovOlvidado > 0) {
				try (PreparedStatement psDel = conexion.prepareStatement(sqlDelete)) {
					psDel.setInt(1, idPokemon);
					psDel.setInt(2, idMovOlvidado);
					psDel.executeUpdate();
				}
			}

			// Insertamos el nuevo movimiento seleccionado de la lista
			try (PreparedStatement psIns = conexion.prepareStatement(sqlInsert)) {
				psIns.setInt(1, idPokemon);
				psIns.setInt(2, idMovNuevo);
				psIns.executeUpdate();
			}

			conexion.commit();
			return true;
		} catch (SQLException e) {
			try {
				conexion.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				conexion.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Metodo para filtrar los tipos, esto lo usaremos para dar una lista de los
	// ataques le podamos asignar al pokemon
	public ArrayList<Movimiento> obtenerMovimientosPorTipo(String tipoBusqueda) {
		ArrayList<Movimiento> lista = new ArrayList<>();
		String sql = "SELECT * FROM movimiento WHERE tipo = ?";

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {
			ps.setString(1, tipoBusqueda);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				// Pillamos los datos desde la base de datos
				int idMov = rs.getInt("id_Movimiento");
				String nom = rs.getString("nombre");
				int pot = rs.getInt("potencia");
				String tipoString = rs.getString("tipo");
				String cat = rs.getString("categoria");
				int tur = rs.getInt("turnos");

				// Convertimos el string del tipo a Enum pa que pille bien el numero
				Tipo tipoEnum = Tipo.valueOf(tipoString.toUpperCase());

				//Llamamos al constructor
				Movimiento m = new Movimiento(nom, pot, tipoEnum, cat, tur, idMov);

				lista.add(m);
			}
			//Debug
		} catch (SQLException e) {
			System.err.println("Error al cargar movimientos: " + e.getMessage());
			e.printStackTrace();
			//Debug
		} catch (IllegalArgumentException e) {
			System.err.println("Error: El tipo en la DB no coincide con el Enum Tipo");
		}
		return lista;
	}

	// Desactiva un movimiento de un pokemon
	public boolean desactivarMovimiento(int idPokemon, int idMovimiento) {
		String sql = "UPDATE pokemon_movimiento SET activo = 0 " + "WHERE id_Pokemon = ? AND id_Movimiento = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			statement.setInt(2, idMovimiento);

			int filas = statement.executeUpdate();
			return filas > 0;

		} catch (SQLException e) {
			System.err.println("Error al desactivar movimiento");
			e.printStackTrace();
			return false;
		}
	}

	public boolean gastarEstamina(int idPokemon, int idMovimiento) {
		String sql = "UPDATE pokemon_movimiento SET puntos_Poder = puntos_Poder - 1 "
				+ "WHERE id_Pokemon = ? AND id_Movimiento = ? AND puntos_Poder > 0";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);
			statement.setInt(2, idMovimiento);

			int filas = statement.executeUpdate();
			if (filas == 0) {
				System.out.println("El movimiento no tiene estamina.");
			}
			return filas > 0;

		} catch (SQLException e) {
			System.err.println("Error");
			e.printStackTrace();
			return false;
		}
	}

	public boolean restaurarEstamina(int idPokemon) {
		String sql = "UPDATE pokemon_movimiento pm " + "JOIN movimiento m ON pm.id_Movimiento = m.id_Movimiento "
				+ "SET pm.puntos_Poder = m.puntos_Poder " + "WHERE pm.id_Pokemon = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idPokemon);

			int filas = statement.executeUpdate();
			System.out.println("Estamina recargada para el pokemon " + idPokemon);
			return filas > 0;

		} catch (SQLException e) {
			System.err.println("Error al restaurar la estamina del pokemon " + idPokemon);
			e.printStackTrace();
			return false;
		}
	}
}
