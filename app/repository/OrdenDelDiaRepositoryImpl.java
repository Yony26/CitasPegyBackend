package repository;

import domain.OrdenDelDia;
import service.outputport.OrdenDelDiaRepositoryPort;

import java.util.ArrayList;
import java.util.List;

public class OrdenDelDiaRepositoryImpl implements OrdenDelDiaRepositoryPort {

    private final List<OrdenDelDia> ordenes = new ArrayList<>();
    private int siguienteId = 1;

    @Override
    public OrdenDelDia guardar(OrdenDelDia orden) {
        orden.setAgendaId(siguienteId++);
        ordenes.add(orden);
        return orden;
    }

    @Override
    public List<OrdenDelDia> selectAll() {
        return ordenes;
    }

    @Override
    public OrdenDelDia selectById(int agendaId) {
        for (OrdenDelDia o : ordenes) {
            if (o.getAgendaId() == agendaId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public OrdenDelDia actualizar(OrdenDelDia orden) {
        for (int i = 0; i < ordenes.size(); i++) {
            if (ordenes.get(i).getAgendaId() == orden.getAgendaId()) {
                ordenes.set(i, orden);
                return orden;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(int agendaId) {
        return ordenes.removeIf(o -> o.getAgendaId() == agendaId);
    }
}