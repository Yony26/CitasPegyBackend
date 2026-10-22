package repository;

import java.util.List;
import java.util.Optional;

    public interface Repositorio<T> {
    void guardar(T item);
    Optional<T> buscarPorId(int id);
    List<T> listar();
    void eliminar(int id);
}