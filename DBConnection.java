import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 🔥 IMPORTANT LINE

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/foodapp",
                    "root",
                    "9938390813"
            );

        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}