package repository;

import domain.Administracion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class AdministracionRepository implements Repositorio<Administracion> {

    private final Map<Integer, Administracion> administradores = new TreeMap<>();

    @Override
    public void guardar(Administracion item) {
        if (administradores.containsKey(item.getId())) {
            throw new IllegalArgumentException("Ya existe un administrador con ese id");
        }
        administradores.put(item.getId(), copiar(item));
    }

    @Override
    public Optional<Administracion> buscarPorId(int id) {
        Administracion administrador = administradores.get(id);
        return administrador == null ? Optional.empty() : Optional.of(copiar(administrador));
    }

    @Override
    public List<Administracion> listar() {
        List<Administracion> resultado = new ArrayList<>();
        for (Administracion administrador : administradores.values()) {
            resultado.add(copiar(administrador));
        }
        return resultado;
    }

    @Override
    public void eliminar(int id) {
        administradores.remove(id);
    }

    private Administracion copiar(Administracion administrador) {
        return new Administracion(administrador.getId(), administrador.getNombre(),
                administrador.getApellido(), administrador.getDocumento(), administrador.getCorreo(),
                administrador.getTelefono(), administrador.getCargo(), administrador.getUsuario());
    }
}
