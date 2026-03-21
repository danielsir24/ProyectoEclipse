package pokemon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class ObjetoDAO {
	private Connection conexion;

	public ObjetoDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	public Objeto buscarPorIdObjeto(int idObjeto) {
		Objeto ob = null;
		String sql = "SELECT * FROM objeto WHERE id_objeto = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idObjeto);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {

					ob = new Objeto();
					ob.setNombre(rs.getString("nom_Objeto"));
					ob.setIdObjeto(rs.getInt("id_Objeto"));
					ob.setBonusAtaque(rs.getInt("bonus_Ataque"));
					ob.setPenalizacionDefensa(rs.getInt("penalizacion_Defensa"));

				}

			}
		} catch (SQLException ex) {
			System.out.println("Error al buscar el Objeto: " + ex.getMessage());
			ex.printStackTrace();
	}
		return ob;
}
}
