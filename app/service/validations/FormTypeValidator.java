package service.validations;

import java.util.Scanner;

public class FormTypeValidator {

    // Única instancia de Scanner para todo el proyecto, para evitar
    // conflictos por abrir varios Scanner sobre el mismo System.in.
    private static final Scanner scanner = new Scanner(System.in);

    // Todos los métodos son estáticos, así que no se debe instanciar esta clase.
    private FormTypeValidator() {
    }

    // Pide un número entero y repite la pregunta hasta que sea válido.
    public static int intValidator(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Dato inválido. Debe ingresar un número entero. Intente de nuevo.");
            }
        }
    }

    // Pide un número decimal y repite la pregunta hasta que sea válido.
    public static double doubleValidator(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Dato inválido. Debe ingresar un número decimal. Intente de nuevo.");
            }
        }
    }

    // Pide un texto y no deja continuar si queda vacío.
    public static String stringValidator(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("El campo no puede estar vacío. Intente de nuevo.");
        }
    }
}