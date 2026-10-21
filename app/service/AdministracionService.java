package service;

import domain.Administracion;
import repository.AdministracionRepository;
import java.util.List;
import java.util.Optional;

public class AdministracionService {

    private final AdministracionRepository repo = new AdministracionRepository();

    public void registrar(Administracion admin) {
        if (repo.buscarPorId(admin.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un administrador con ese id");
        }
        repo.guardar(admin);
    }

    public List<Administracion> listar() {
        return repo.listar();
    }

    public Optional<Administracion> buscarPorId(int id) {
        return repo.buscarPorId(id);
    }

    public void eliminar(int id) {
        if (repo.buscarPorId(id).isEmpty()) {
            throw new IllegalArgumentException("No existe un administrador con ese id");
        }
        repo.eliminar(id);
    }
}