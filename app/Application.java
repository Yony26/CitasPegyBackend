import repository.OrdenDelDiaRepositoryImpl;
import service.AdministracionService;
import service.OrdenDelDiaServiceImpl;
import service.inputport.OrdenDelDiaService;
import service.outputport.OrdenDelDiaRepositoryPort;
import service.validations.FormTypeValidator;
import view.AdministracionView;
import view.OrdenDelDiaView;

public class Application {

    public static void main(String[] args) {
        OrdenDelDiaRepositoryPort ordenRepository = new OrdenDelDiaRepositoryImpl();
        OrdenDelDiaService ordenService = new OrdenDelDiaServiceImpl(ordenRepository);

        OrdenDelDiaView ordenView = new OrdenDelDiaView(ordenService);
        AdministracionView administracionView =
                new AdministracionView(new AdministracionService());

        int opcion;
        do {
            System.out.println("\n=== CITAS PEGY ===");
            System.out.println("1. Administrar citas");
            System.out.println("2. Administrar usuarios");
            System.out.println("0. Salir");
            opcion = FormTypeValidator.intValidator("Opción: ");

            switch (opcion) {
                case 1:
                    mostrarMenuCitas(ordenView);
                    break;
                case 2:
                    administracionView.mostrarMenu();
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenuCitas(OrdenDelDiaView view) {
        int opcion;
        do {
            System.out.println("\n=== ADMINISTRACIÓN DE CITAS ===");
            System.out.println("1. Crear cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Cancelar cita");
            System.out.println("0. Volver");
            opcion = FormTypeValidator.intValidator("Opción: ");

            switch (opcion) {
                case 1:
                    view.crear();
                    break;
                case 2:
                    view.mostrarTodas();
                    break;
                case 3:
                    view.cancelar();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}
