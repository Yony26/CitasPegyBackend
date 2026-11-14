package repository;

import domain.OrdenDelDia;
import domain.enums.EstadoCita;
import service.outputport.OrdenDelDiaRepositoryPort;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenDelDiaRepositoryImpl implements OrdenDelDiaRepositoryPort {

    private static final String SELECT_COLUMNS =
            "AppointmentId, BookingCode, ClientUserId, SpecialistUserId, ServiceId, "
                    + "AppointmentDate, StartTime, Status";

    @Override
    public OrdenDelDia guardar(OrdenDelDia orden) {
        Date fecha;
        Time hora;
        try {
            fecha = Date.valueOf(LocalDate.parse(orden.getFecha()));
            hora = Time.valueOf(LocalTime.parse(orden.getStartTime()));
        } catch (DateTimeException | IllegalArgumentException e) {
            throw new IllegalArgumentException("La fecha o la hora de la cita no tienen un formato válido.", e);
        }

        String sql = "INSERT INTO dbo.Appointments "
                + "(ClientUserId, SpecialistUserId, ServiceId, AppointmentDate, StartTime, Status, "
                + "ServiceNameSnapshot, DurationSnapshotMinutes, PriceSnapshot) "
                + "OUTPUT INSERTED.AppointmentId, INSERTED.BookingCode "
                + "SELECT ?, ?, s.ServiceId, ?, ?, ?, s.Name, s.DurationMinutes, s.Price "
                + "FROM dbo.Services s "
                + "INNER JOIN dbo.Specialists sp ON sp.BusinessId = s.BusinessId "
                + "WHERE s.ServiceId = ? AND sp.UserId = ?";

        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orden.getClienteId());
            statement.setInt(2, orden.getEspecialistaId());
            statement.setDate(3, fecha);
            statement.setTime(4, hora);
            statement.setString(5, orden.getEstado().getValue());
            statement.setInt(6, orden.getServicioId());
            statement.setInt(7, orden.getEspecialistaId());

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException(
                            "El servicio o el especialista no existe, o no pertenecen al mismo negocio.");
                }
                orden.setAgendaId(result.getInt("AppointmentId"));
                orden.setBookingCode(result.getString("BookingCode"));
                return orden;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 2601 || e.getErrorCode() == 2627) {
                throw new IllegalStateException(
                        "Horario no disponible: el especialista ya tiene una cita a esa hora.", e);
            }
            throw new IllegalStateException("No se pudo guardar la cita en SQL Server.", e);
        }
    }

    @Override
    public List<OrdenDelDia> selectAll() {
        String sql = "SELECT " + SELECT_COLUMNS + " FROM dbo.Appointments ORDER BY AppointmentDate, StartTime";
        List<OrdenDelDia> ordenes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                ordenes.add(map(result));
            }
            return ordenes;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron consultar las citas en SQL Server.", e);
        }
    }

    @Override
    public OrdenDelDia selectById(int agendaId) {
        String sql = "SELECT " + SELECT_COLUMNS + " FROM dbo.Appointments WHERE AppointmentId = ?";

        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, agendaId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? map(result) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo consultar la cita en SQL Server.", e);
        }
    }

    @Override
    public OrdenDelDia actualizar(OrdenDelDia orden) {
        String sql = "UPDATE dbo.Appointments SET Status = ? WHERE AppointmentId = ?";

        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, orden.getEstado().getValue());
            statement.setInt(2, orden.getAgendaId());
            return statement.executeUpdate() == 0 ? null : orden;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar la cita en SQL Server.", e);
        }
    }

    @Override
    public boolean eliminar(int agendaId) {
        String sql = "DELETE FROM dbo.Appointments WHERE AppointmentId = ?";

        try (Connection connection = DatabaseConnection.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, agendaId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo eliminar la cita en SQL Server.", e);
        }
    }

    private OrdenDelDia map(ResultSet result) throws SQLException {
        Date date = result.getDate("AppointmentDate");
        Time time = result.getTime("StartTime");
        String status = result.getString("Status");
        return new OrdenDelDia(
                result.getInt("AppointmentId"),
                date.toLocalDate().toString(),
                time.toLocalTime().toString(),
                estadoDesde(status),
                result.getString("BookingCode"),
                result.getInt("ClientUserId"),
                result.getInt("SpecialistUserId"),
                result.getInt("ServiceId"));
    }

    private EstadoCita estadoDesde(String status) throws SQLException {
        for (EstadoCita estado : EstadoCita.values()) {
            if (estado.getValue().equals(status)) {
                return estado;
            }
        }
        throw new SQLException("Estado de cita no reconocido en SQL Server: " + status);
    }

}
