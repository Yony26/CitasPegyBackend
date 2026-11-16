package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:sqlserver://localhost:1433;databaseName=CitasPegy;encrypt=true;trustServerCertificate=true";

    private DatabaseConnection() {
    }

    public static Connection open() throws SQLException {
        String url = setting("DB_URL", DEFAULT_URL);
        if ("windows".equalsIgnoreCase(setting("DB_AUTH", "sql"))) {
            Properties properties = new Properties();
            properties.setProperty("integratedSecurity", "true");
            return DriverManager.getConnection(url, properties);
        }

        String user = setting("DB_USER", null);
        String password = setting("DB_PASSWORD", null);

        if (user == null || user.trim().isEmpty()) {
            throw new IllegalStateException("Falta configurar la variable de entorno DB_USER.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalStateException("Falta configurar la variable de entorno DB_PASSWORD.");
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
