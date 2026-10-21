package repository;

import domain.Administracion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdministracionRepository implements Repositorio<Administracion> {

    private final List<Administracion> lista = new ArrayList<>();

    @Override
    public void guardar(Administracion item) {
        lista.add(item);
    }

    @Override
    public Optional<Administracion> buscarPorId(int id) {
        return lista.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    @Override
    public List<Administracion> listar() {
        return new ArrayList<>(lista);
    }

    @Override
    public void eliminar(int id) {
        lista.removeIf(a -> a.getId() == id);
    }
}