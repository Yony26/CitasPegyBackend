package view;

import domain.Administracion;
import service.AdministracionService;
import java.util.List;
import java.util.Scanner;

public class AdministracionView {

    private final AdministracionService service;
    private final Scanner scanner = new Scanner(System.in);

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
            System.out.print("Opcion: ");
            opcion = leerEntero();

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
            System.out.print("Id: ");
            int id = leerEntero();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Documento: ");
            String documento = scanner.nextLine();
            System.out.print("Correo: ");
            String correo = scanner.nextLine();
            System.out.print("Telefono: ");
            String telefono = scanner.nextLine();
            System.out.print("Cargo: ");
            String cargo = scanner.nextLine();
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();

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
        System.out.print("Id a buscar: ");
        int id = leerEntero();
        service.buscarPorId(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No se encontro"));
    }

    private void eliminar() {
        try {
            System.out.print("Id a eliminar: ");
            service.eliminar(leerEntero());
            System.out.println("Eliminado");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}