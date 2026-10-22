package service.outputport;

import domain.OrdenDelDia;
import java.util.List;

public interface OrdenDelDiaRepositoryPort {
    OrdenDelDia guardar(OrdenDelDia orden);
    List<OrdenDelDia> selectAll();
    OrdenDelDia selectById(int agendaId);
    OrdenDelDia actualizar(OrdenDelDia orden);
    boolean eliminar(int agendaId);
}