import repository.OrdenDelDiaRepositoryImpl;
import repository.DatabaseConnection;
import service.OrdenDelDiaServiceImpl;
import service.inputport.OrdenDelDiaService;
import service.validations.FormTypeValidator;
import view.OrdenDelDiaView;

import java.sql.SQLException;
import java.sql.Connection;

public class Application {

    public static void main(String[] args) {
        try (Connection ignored = DatabaseConnection.open()) {
            OrdenDelDiaService service = new OrdenDelDiaServiceImpl(new OrdenDelDiaRepositoryImpl());
            OrdenDelDiaView view = new OrdenDelDiaView(service);
            ejecutarMenu(view);
        } catch (SQLException | IllegalStateException e) {
            System.err.println("No se pudo iniciar la aplicación: " + e.getMessage());
            System.err.println("Verifica SQL Server, la base CitasPegy y la configuración de conexión DB_URL/DB_AUTH.");
            System.exit(1);
        }
    }

    private static void ejecutarMenu(OrdenDelDiaView view) {
        int opcion;
        do {
            System.out.println("\n=== CITAS PEGGY ===");
            System.out.println("1. Listar citas");
            System.out.println("2. Crear cita");
            System.out.println("3. Cancelar cita");
            System.out.println("0. Salir");
            opcion = FormTypeValidator.intValidator("Opción: ");

            switch (opcion) {
                case 1:
                    view.mostrarTodas();
                    break;
                case 2:
                    view.crear();
                    break;
                case 3:
                    view.cancelar();
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}
