package pokemon;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ObjetoDAO {
	private Connection conexion;

	public ObjetoDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	public Pokedex buscarPorIdObjeto(int idObjeto) {
		Objeto ob = null;
		String sql = "SELECT * FROM objeto WHERE id_objeto = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, idObjeto);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {

					ob = new Objeto();
					

				}

			}
		}
	}
}
