package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PokedexDAO {

	private Connection conexion;

	public PokedexDAO() {
		this.conexion = ConexionBD.getConnection();
	}

	public Pokedex buscarPorIdPokedex(int numPokedex) {
		Pokedex p = null;
		String sql = "SELECT * FROM pokedex WHERE num_Pokedex = ?";

		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setInt(1, numPokedex);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					p = new Pokedex();
					p.setNum_Pokedex(rs.getInt("num_Pokedex"));
					p.setNombreEspecia(rs.getString("nombre"));
					p.setTipo1(rs.getString("tipo1"));
					p.setTipo2(rs.getString("tipo2"));
					p.setImg_Back(rs.getString("img_Back"));
					p.setSonido(rs.getString("sonido"));
					p.setImg_Frontal(rs.getString("img_Frontal"));

				}
			}
		} catch (SQLException ex) {
			System.out.println("Error al buscar en la Pokedex: " + ex.getMessage());
			ex.printStackTrace();

		}
		return p;
	}

}
