package repository;

import domain.OrdenDelDia;
import service.outputport.OrdenDelDiaRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrdenDelDiaRepositoryImpl implements OrdenDelDiaRepositoryPort {

    private final Map<Integer, OrdenDelDia> ordenes = new TreeMap<>();
    private int siguienteId = 1;

    @Override
    public OrdenDelDia guardar(OrdenDelDia orden) {
        orden.setAgendaId(siguienteId++);
        ordenes.put(orden.getAgendaId(), copiar(orden));
        return orden;
    }

    @Override
    public List<OrdenDelDia> selectAll() {
        List<OrdenDelDia> resultado = new ArrayList<>();
        for (OrdenDelDia orden : ordenes.values()) {
            resultado.add(copiar(orden));
        }
        return resultado;
    }

    @Override
    public OrdenDelDia selectById(int agendaId) {
        OrdenDelDia orden = ordenes.get(agendaId);
        return orden == null ? null : copiar(orden);
    }

    @Override
    public OrdenDelDia actualizar(OrdenDelDia orden) {
        if (!ordenes.containsKey(orden.getAgendaId())) {
            return null;
        }
        ordenes.put(orden.getAgendaId(), copiar(orden));
        return orden;
    }

    @Override
    public boolean eliminar(int agendaId) {
        return ordenes.remove(agendaId) != null;
    }

    private OrdenDelDia copiar(OrdenDelDia orden) {
        return new OrdenDelDia(orden.getAgendaId(), orden.getFecha(), orden.getStartTime(),
                orden.getEstado(), orden.getBookingCode(), orden.getClienteId(),
                orden.getEspecialistaId(), orden.getServicioId());
    }
}
