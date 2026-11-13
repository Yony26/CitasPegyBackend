package view;

import domain.Administracion;
import service.AdministracionService;
import service.validations.FormTypeValidator;
import java.util.List;

public class AdministracionView {

    private final AdministracionService service;

    public AdministracionView(AdministracionService service) {
        this.service = service;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== ADMINISTRACION ===");
            System.out.println("1. Registrar administrador");
            System.out.println("2. Listar administradores");
            System.out.println("3. Buscar por id");
            System.out.println("4. Eliminar por id");
            System.out.println("0. Salir");
            opcion = FormTypeValidator.intValidator("Opción: ");

            switch (opcion) {
                case 1: registrar(); break;
                case 2: listar(); break;
                case 3: buscar(); break;
                case 4: eliminar(); break;
                case 0: System.out.println("Hasta luego"); break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        try {
            int id = FormTypeValidator.intValidator("Id: ");
            String nombre = FormTypeValidator.stringValidator("Nombre: ");
            String apellido = FormTypeValidator.stringValidator("Apellido: ");
            String documento = FormTypeValidator.stringValidator("Documento: ");
            String correo = FormTypeValidator.stringValidator("Correo: ");
            String telefono = FormTypeValidator.stringValidator("Teléfono: ");
            String cargo = FormTypeValidator.stringValidator("Cargo: ");
            String usuario = FormTypeValidator.stringValidator("Usuario: ");

            service.registrar(new Administracion(id, nombre, apellido,
                    documento, correo, telefono, cargo, usuario));
            System.out.println("Administrador registrado");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<Administracion> lista = service.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay administradores registrados");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void buscar() {
        int id = FormTypeValidator.intValidator("Id a buscar: ");
        service.buscarPorId(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se encontro"));
    }

    private void eliminar() {
        try {
            int id = FormTypeValidator.intValidator("Id a eliminar: ");
            service.eliminar(id);
            System.out.println("Eliminado");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}