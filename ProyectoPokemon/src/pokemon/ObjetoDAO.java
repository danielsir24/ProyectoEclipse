package pokemon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObjetoDAO {

    // Método para buscar un objeto específico en la base de datos usando su ID
    public Objeto buscarPorIdObjeto(int idObjeto) {
        Objeto obj = null;
        String sql = "SELECT * FROM objeto WHERE id_Objeto = ?";

        // Conectamos a la base de datos y preparamos la consulta SQL
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            
            statement.setInt(1, idObjeto);
            try (ResultSet rs = statement.executeQuery()) {
                // Si existe el objeto, creamos una instancia y le pasamos todos sus bonos y penalizaciones
                if (rs.next()) {
                    obj = new Objeto();
                    obj.setIdObjeto(rs.getInt("id_Objeto"));
                    obj.setNombre(rs.getString("nom_Objeto"));
                    
                    // Pasamos cada valor de la tabla a los atributos del objeto Java
                    obj.setBonusAtaque(rs.getDouble("bonus_Ataque"));
                    obj.setBonusDefensa(rs.getDouble("bonus_Defensa"));
                    obj.setPenalizacionAtaque(rs.getDouble("penalizacion_Ataque"));
                    obj.setPenalizacionDefensa(rs.getDouble("penalizacion_Defensa"));
                    obj.setBonusAtaqueEspecial(rs.getDouble("bonus_Ataque_Especial"));
                    obj.setBonusDefensaEspecial(rs.getDouble("bonus_Defensa_Especial"));
                    obj.setBonusVelocidad(rs.getDouble("bonus_Velocidad"));
                    obj.setPenalizacionAtaqueEspecial(rs.getDouble("penalizacion_Ataque_Especial"));
                    obj.setPenalizacionDefensaEspecial(rs.getDouble("penalizacion_Defensa_Especial"));
                    obj.setPenalizacionVelocidad(rs.getDouble("penalizacion_Velocidad"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar objeto: " + e.getMessage());
        }
        return obj;
    }

    // Este método devuelve una lista con todos los objetos que existen en el juego (para la tienda, por ejemplo)
    public ArrayList<Objeto> obtenerTodosLosObjetos() {
        ArrayList<Objeto> lista = new ArrayList<>();
        String sql = "SELECT * FROM objeto";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            // Vamos recorriendo la tabla y añadiendo cada objeto a nuestra lista
            while (rs.next()) {
                Objeto obj = new Objeto();
                obj.setIdObjeto(rs.getInt("id_Objeto"));
                obj.setNombre(rs.getString("nom_Objeto"));
                obj.setBonusAtaque(rs.getDouble("bonus_Ataque"));
                obj.setBonusDefensa(rs.getDouble("bonus_Defensa"));
                obj.setPenalizacionAtaque(rs.getDouble("penalizacion_Ataque"));
                obj.setPenalizacionDefensa(rs.getDouble("penalizacion_Defensa"));
                obj.setBonusAtaqueEspecial(rs.getDouble("bonus_Ataque_Especial"));
                obj.setPenalizacionAtaqueEspecial(rs.getDouble("penalizacion_Ataque_Especial"));
                obj.setBonusDefensaEspecial(rs.getDouble("bonus_Defensa_Especial"));
                obj.setPenalizacionDefensaEspecial(rs.getDouble("penalizacion_Defensa_Especial"));
                obj.setBonusVelocidad(rs.getDouble("bonus_Velocidad"));
                obj.setPenalizacionVelocidad(rs.getDouble("penalizacion_Velocidad"));
                
                lista.add(obj);
            }
            System.out.println("Total objetos cargados: " + lista.size());

        } catch (SQLException e) {
            System.err.println("Error al obtener todos los objetos");
            e.printStackTrace();
        }
        return lista;
    }

    // Sirve para sacar todos los objetos que tiene un entrenador concreto en su mochila
    public ArrayList<Object[]> obtenerMochila(int idEntrenador) {
        ArrayList<Object[]> mochila = new ArrayList<>();
        // Usamos un JOIN para saber el nombre y stats del objeto además de la cantidad que tiene el usuario
        String sql = "SELECT o.*, m.cantidad FROM objeto o "
                   + "JOIN mochila m ON o.id_Objeto = m.id_Objeto "
                   + "WHERE m.id_Entrenador = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
             
            statement.setInt(1, idEntrenador);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Objeto obj = new Objeto();
                    obj.setIdObjeto(rs.getInt("id_Objeto"));
                    obj.setNombre(rs.getString("nom_Objeto"));
                    // (Aquí se repite el mapeo de todas las estadísticas...)
                    obj.setBonusAtaque(rs.getDouble("bonus_Ataque"));
                    obj.setBonusDefensa(rs.getDouble("bonus_Defensa"));
                    obj.setPenalizacionAtaque(rs.getDouble("penalizacion_Ataque"));
                    obj.setPenalizacionDefensa(rs.getDouble("penalizacion_Defensa"));
                    obj.setBonusAtaqueEspecial(rs.getDouble("bonus_Ataque_Especial"));
                    obj.setPenalizacionAtaqueEspecial(rs.getDouble("penalizacion_Ataque_Especial"));
                    obj.setBonusDefensaEspecial(rs.getDouble("bonus_Defensa_Especial"));
                    obj.setPenalizacionDefensaEspecial(rs.getDouble("penalizacion_Defensa_Especial"));
                    obj.setBonusVelocidad(rs.getDouble("bonus_Velocidad"));
                    obj.setPenalizacionVelocidad(rs.getDouble("penalizacion_Velocidad"));
                    
                    int cantidad = rs.getInt("cantidad");
                    // Guardamos el objeto y su cantidad en un array de objetos para devolverlo
                    mochila.add(new Object[]{obj, cantidad});
                }
            }
            System.out.println("Mochila del entrenador " + idEntrenador + " cargada: " + mochila.size() + " tipos de objeto.");

        } catch (SQLException e) {
            System.err.println("Error al obtener la mochila del entrenador " + idEntrenador);
            e.printStackTrace();
        }
        return mochila;
    }

    // Este método añade un objeto a la mochila. Si ya existe, simplemente suma la cantidad.
    public boolean añadirAMochila(int idEntrenador, int idObjeto, int cantidad) {
        // Usamos "ON DUPLICATE KEY UPDATE" para que SQL sume si el objeto ya está en la mochila
        String sql = "INSERT INTO mochila (id_Entrenador, id_Objeto, cantidad) VALUES (?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE cantidad = cantidad + ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, idEntrenador);
            statement.setInt(2, idObjeto);
            statement.setInt(3, cantidad);
            statement.setInt(4, cantidad);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al añadir objeto a la mochila");
            e.printStackTrace();
            return false;
        }
    }

    // Método para restar 1 a la cantidad de un objeto cuando se usa. Si solo quedaba uno, borra la fila.
    public boolean usarDeEstaMochila(int idEntrenador, int idObjeto) {
        String sqlSelect = "SELECT cantidad FROM mochila WHERE id_Entrenador = ? AND id_Objeto = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement stSelect = con.prepareStatement(sqlSelect)) {
            stSelect.setInt(1, idEntrenador);
            stSelect.setInt(2, idObjeto);

            try (ResultSet rs = stSelect.executeQuery()) {
                if (rs.next()) {
                    int cantidad = rs.getInt("cantidad");
                    // Si es el último que queda, borramos la entrada de la mochila
                    if (cantidad <= 1) {
                        String sqlDelete = "DELETE FROM mochila WHERE id_Entrenador = ? AND id_Objeto = ?";
                        try (PreparedStatement stDelete = con.prepareStatement(sqlDelete)) {
                            stDelete.setInt(1, idEntrenador);
                            stDelete.setInt(2, idObjeto);
                            return stDelete.executeUpdate() > 0;
                        }
                    } else {
                        // Si tenemos más de uno, simplemente restamos una unidad
                        String sqlUpdate = "UPDATE mochila SET cantidad = cantidad - 1 WHERE id_Entrenador = ? AND id_Objeto = ?";
                        try (PreparedStatement stUpdate = con.prepareStatement(sqlUpdate)) {
                            stUpdate.setInt(1, idEntrenador);
                            stUpdate.setInt(2, idObjeto);
                            return stUpdate.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al usar objeto de la mochila");
            e.printStackTrace();
        }
        return false;
    }
}