package pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    private static final String URL = "jdbc:mysql://localhost:3306/proyecto_pokemon";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // vacío por defecto en XAMPP
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error conectando a la BD: " + e.getMessage());
            return null;
        }
    }
    
    // Método para probar la conexión
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Conexión exitosa a la base de datos!");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Error al conectar con la base de datos");
        }
    }
}