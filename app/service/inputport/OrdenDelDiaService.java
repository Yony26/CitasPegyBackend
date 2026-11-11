package service.inputport;

import domain.OrdenDelDia;
import java.util.List;

public interface OrdenDelDiaService {
    OrdenDelDia crear(String fecha, String startTime, int clienteId, int especialistaId, int servicioId);
    List<OrdenDelDia> selectAll();
    OrdenDelDia selectById(int agendaId);
    OrdenDelDia cancelar(int agendaId);
    boolean eliminar(int agendaId);
}