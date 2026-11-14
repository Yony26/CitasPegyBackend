package repository;

import domain.OrdenDelDia;
import domain.enums.EstadoCita;
import database.DatabaseConnection;
import service.outputport.OrdenDelDiaRepositoryPort;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;

public class OrdenDelDiaRepositoryImpl implements OrdenDelDiaRepositoryPort {

    @Override
    public OrdenDelDia guardar(OrdenDelDia orden) {
        String sql = "INSERT INTO dbo.OrdenesDelDia "
                + "(fecha, start_time, estado, booking_code, cliente_id, especialista_id, servicio_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(orden.getFecha()));
            statement.setTime(2, Time.valueOf(orden.getStartTime() + ":00"));
            statement.setString(3, orden.getEstado().name());
            statement.setString(4, orden.getBookingCode());
            statement.setInt(5, orden.getClienteId());
            statement.setInt(6, orden.getEspecialistaId());
            statement.setInt(7, orden.getServicioId());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("SQL Server no devolvió el id de la cita creada.");
                }
                orden.setAgendaId(keys.getInt(1));
            }
            return orden;
        } catch (SQLException e) {
            throw databaseError("guardar la cita", e);
        }
    }

    @Override
    public List<OrdenDelDia> selectAll() {
        String sql = "SELECT agenda_id, fecha, start_time, estado, booking_code, "
                + "cliente_id, especialista_id, servicio_id FROM dbo.OrdenesDelDia "
                + "ORDER BY agenda_id";
        List<OrdenDelDia> ordenes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                ordenes.add(map(results));
            }
            return ordenes;
        } catch (SQLException e) {
            throw databaseError("listar las citas", e);
        }
    }

    @Override
    public OrdenDelDia selectById(int agendaId) {
        String sql = "SELECT agenda_id, fecha, start_time, estado, booking_code, "
                + "cliente_id, especialista_id, servicio_id "
                + "FROM dbo.OrdenesDelDia WHERE agenda_id = ?";
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, agendaId);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? map(results) : null;
            }
        } catch (SQLException e) {
            throw databaseError("buscar la cita", e);
        }
    }

    @Override
    public OrdenDelDia actualizar(OrdenDelDia orden) {
        String sql = "UPDATE dbo.OrdenesDelDia SET fecha = ?, start_time = ?, estado = ?, "
                + "booking_code = ?, cliente_id = ?, especialista_id = ?, servicio_id = ? "
                + "WHERE agenda_id = ?";
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(orden.getFecha()));
            statement.setTime(2, Time.valueOf(orden.getStartTime() + ":00"));
            statement.setString(3, orden.getEstado().name());
            statement.setString(4, orden.getBookingCode());
            statement.setInt(5, orden.getClienteId());
            statement.setInt(6, orden.getEspecialistaId());
            statement.setInt(7, orden.getServicioId());
            statement.setInt(8, orden.getAgendaId());
            return statement.executeUpdate() == 0 ? null : orden;
        } catch (SQLException e) {
            throw databaseError("actualizar la cita", e);
        }
    }

    @Override
    public boolean eliminar(int agendaId) {
        try (Connection connection = DatabaseConnection.open(DatabaseConnection.databaseName());
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM dbo.OrdenesDelDia WHERE agenda_id = ?")) {
            statement.setInt(1, agendaId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw databaseError("eliminar la cita", e);
        }
    }

    private OrdenDelDia map(ResultSet results) throws SQLException {
        String time = results.getString("start_time");
        return new OrdenDelDia(
                results.getInt("agenda_id"),
                results.getDate("fecha").toString(),
                time.substring(0, 5),
                EstadoCita.valueOf(results.getString("estado")),
                results.getString("booking_code"),
                results.getInt("cliente_id"),
                results.getInt("especialista_id"),
                results.getInt("servicio_id"));
    }

    private IllegalStateException databaseError(String action, SQLException cause) {
        return new IllegalStateException("No se pudo " + action + " en SQL Server.", cause);
    }
}