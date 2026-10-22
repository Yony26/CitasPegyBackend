package service;

import domain.OrdenDelDia;
import domain.enums.EstadoCita;
import service.inputport.OrdenDelDiaService;
import service.outputport.OrdenDelDiaRepositoryPort;

import java.util.List;

public class OrdenDelDiaServiceImpl implements OrdenDelDiaService {

    // Se declara la abstracción (interfaz), no la implementación (inversión de dependencias)
    private final OrdenDelDiaRepositoryPort repository;

    // Inyección de dependencias por constructor
    public OrdenDelDiaServiceImpl(OrdenDelDiaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public OrdenDelDia crear(String fecha, String startTime, int clienteId, int especialistaId, int servicioId) {
        // Validar cruce de horario: mismo especialista, misma fecha y hora, cita no cancelada
        for (OrdenDelDia o : repository.selectAll()) {
            boolean mismoHorario = o.getEspecialistaId() == especialistaId
                    && o.getFecha().equals(fecha)
                    && o.getStartTime().equals(startTime)
                    && o.getEstado() != EstadoCita.CANCELADA;
            if (mismoHorario) {
                throw new IllegalStateException("Horario no disponible: el especialista ya tiene una cita a esa hora.");
            }
        }

        OrdenDelDia orden = new OrdenDelDia();
        orden.setFecha(fecha);
        orden.setStartTime(startTime);
        orden.setClienteId(clienteId);
        orden.setEspecialistaId(especialistaId);
        orden.setServicioId(servicioId);
        orden.setEstado(EstadoCita.PENDIENTE);

        OrdenDelDia guardada = repository.guardar(orden);

        // Confirmación automática con código único
        guardada.setBookingCode("CP-" + String.format("%04d", guardada.getAgendaId()));
        guardada.setEstado(EstadoCita.CONFIRMADA);
        return repository.actualizar(guardada);
    }

    @Override
    public List<OrdenDelDia> selectAll() {
        return repository.selectAll();
    }

    @Override
    public OrdenDelDia selectById(int agendaId) {
        return repository.selectById(agendaId);
    }

    @Override
    public OrdenDelDia cancelar(int agendaId) {
        OrdenDelDia orden = repository.selectById(agendaId);
        if (orden == null) {
            throw new IllegalArgumentException("No existe una cita con id " + agendaId);
        }
        orden.setEstado(EstadoCita.CANCELADA); // libera el horario
        return repository.actualizar(orden);
    }

    @Override
    public boolean eliminar(int agendaId) {
        return repository.eliminar(agendaId);
    }
}