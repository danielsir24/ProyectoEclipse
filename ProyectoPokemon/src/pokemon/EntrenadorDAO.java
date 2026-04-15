package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrenadorDAO {

    public boolean registrar(Entrenador entrenador) {
        String sql = "INSERT INTO entrenador (nom_Entrenador, password, img_Entrenador, pokedollars, tipo_Entrenador) "
                   + "VALUES (?, ?, ?, ?, ?)";
        Connection con = ConexionBD.getConnection();
        if (con == null) return false;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, entrenador.getNom_Entrenador());
            ps.setString(2, entrenador.getPassword());
            ps.setString(3, entrenador.getImg_Entrenador());
            ps.setInt(4, entrenador.getPokedollars());
            ps.setString(5, entrenador.getTipo_Entrenador());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeNombre(String nombre) {
        String sql = "SELECT id_Entrenador FROM entrenador WHERE nom_Entrenador = ?";
        Connection con = ConexionBD.getConnection();
        if (con == null) return false;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Entrenador login(String nombre, String password) {
        String sql = "SELECT * FROM entrenador WHERE nom_Entrenador = ? AND password = ?";
        Connection con = ConexionBD.getConnection();
        if (con == null) return null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Entrenador e = new Entrenador();
                e.setId_Entrenador(rs.getInt("id_Entrenador"));
                e.setNom_Entrenador(rs.getString("nom_Entrenador"));
                e.setPassword(rs.getString("password"));
                e.setImg_Entrenador(rs.getString("img_Entrenador"));
                e.setPokedollars(rs.getInt("pokedollars"));
                e.setTipo_Entrenador(rs.getString("tipo_Entrenador"));
                return e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}