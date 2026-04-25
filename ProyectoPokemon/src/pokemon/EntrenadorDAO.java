package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Esta clase se encarga de todas las operaciones de la base de datos relacionadas con el Entrenador
public class EntrenadorDAO {

    // Método para insertar un nuevo entrenador en la base de datos al registrarse
    public boolean registrar(Entrenador entrenador) {
        // Consulta SQL para insertar los datos en la tabla entrenador
        String sql = "INSERT INTO entrenador (nom_Entrenador, password, img_Entrenador, pokedollars, tipo_Entrenador) "
                   + "VALUES (?, ?, ?, ?, ?)";
        
        // Abrimos la conexión y preparamos la consulta
        try (Connection con = ConexionBD.getConnection();
        	PreparedStatement ps = con.prepareStatement(sql)) {
        	
            // Sustituimos las interrogaciones por los datos reales del objeto entrenador
            ps.setString(1, entrenador.getNom_Entrenador());
            ps.setString(2, entrenador.getPassword());
            ps.setString(3, entrenador.getImg_Entrenador());
            ps.setInt(4, entrenador.getPokedollars());
            ps.setString(5, entrenador.getTipo_Entrenador());
            
            // Ejecutamos la inserción
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Si algo falla, mostramos el error por consola
        	System.err.println("Error al registrar entrenador: " + e.getMessage());
            return false;
        }
    }

    // Sirve para comprobar si un nombre de usuario ya está cogido en la base de datos
    public boolean existeNombre(String nombre) {
        String sql = "SELECT id_Entrenador FROM entrenador WHERE nom_Entrenador = ?";

        try (Connection con = ConexionBD.getConnection();
        	PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            // Si la consulta devuelve algún resultado, es que el nombre ya existe
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para validar el acceso comprobando nombre y contraseña
    public Entrenador login(String nombre, String password) {
        String sql = "SELECT * FROM entrenador WHERE nom_Entrenador = ? AND password = ?";
      
        try (Connection con = ConexionBD.getConnection();
        	PreparedStatement ps = con.prepareStatement(sql)) {
        	
            ps.setString(1, nombre);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                // Si encontramos al entrenador, creamos el objeto y le metemos sus datos de la BD
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
            }
        } catch (SQLException e) {
        	System.err.println("Error en el login: " + e.getMessage());
        }
        return null;
    }

    // Este método lo usamos para guardar en la base de datos el dinero nuevo tras jugar o combatir
    public boolean actualizarPokedollars(Entrenador entrenador) {
        String sql = "UPDATE entrenador SET pokedollars = ? WHERE id_Entrenador = ?";
        
        try (Connection con = ConexionBD.getConnection();
        	PreparedStatement ps = con.prepareStatement(sql)) {
            // Obtenemos los pokedollars actualizados del objeto entrenador
            ps.setInt(1, entrenador.getPokedollars());
            // Usamos el ID para saber a qué entrenador exactamente hay que actualizarle el dinero
            ps.setInt(2, entrenador.getId_Entrenador());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Si se ha modificado al menos una fila, ha ido bien
        } catch (SQLException e) {
        	System.err.println("Error al actualizar pokédollars: " + e.getMessage());
            return false;
        }
    }
}