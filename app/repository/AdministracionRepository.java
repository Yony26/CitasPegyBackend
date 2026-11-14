package repository;

import domain.Administracion;
import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class AdministracionRepository implements Repositorio<Administracion> {

    @Override
    public void guardar(Administracion item) {
        String sql = "INSERT INTO dbo.Administradores "
                + "(id, nombre, apellido, documento, correo, telefono, cargo, usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getId());
            statement.setString(2, item.getNombre());
            statement.setString(3, item.getApellido());
            statement.setString(4, item.getDocumento());
            statement.setString(5, item.getCorreo());
            statement.setString(6, item.getTelefono());
            statement.setString(7, item.getCargo());
            statement.setString(8, item.getUsuario());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("guardar el administrador", e);
        }
    }

    @Override
    public Optional<Administracion> buscarPorId(int id) {
        String sql = "SELECT id, nombre, apellido, documento, correo, telefono, cargo, usuario "
                + "FROM dbo.Administradores WHERE id = ?";
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? Optional.of(map(results)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw databaseError("buscar el administrador", e);
        }
    }

    @Override
    public List<Administracion> listar() {
        String sql = "SELECT id, nombre, apellido, documento, correo, telefono, cargo, usuario "
                + "FROM dbo.Administradores ORDER BY id";
        List<Administracion> administradores = new ArrayList<>();
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                administradores.add(map(results));
            }
            return administradores;
        } catch (SQLException e) {
            throw databaseError("listar los administradores", e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM dbo.Administradores WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw databaseError("eliminar el administrador", e);
        }
    }

    private Administracion map(ResultSet results) throws SQLException {
        return new Administracion(
                results.getInt("id"),
                results.getString("nombre"),
                results.getString("apellido"),
                results.getString("documento"),
                results.getString("correo"),
                results.getString("telefono"),
                results.getString("cargo"),
                results.getString("usuario"));
    }

    private IllegalStateException databaseError(String action, SQLException cause) {
        return new IllegalStateException("No se pudo " + action + " en SQL Server.", cause);
    }
}
