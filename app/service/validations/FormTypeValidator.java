package citaspeggy.service.validations;

import java.util.Scanner;

public class FormTypeValidator {
    private final Scanner scanner;
    public FormTypeValidator(Scanner scanner){this.scanner=scanner;}
    public int leerEntero(String mensaje){while(true){System.out.print(mensaje);String v=scanner.nextLine().trim();try{return Integer.parseInt(v);}catch(NumberFormatException e){System.out.println("Ingresa un número entero válido.");}}}
    public double leerDecimal(String mensaje){while(true){System.out.print(mensaje);String v=scanner.nextLine().trim().replace(',','.');try{double n=Double.parseDouble(v);if(n<0)throw new NumberFormatException();return n;}catch(NumberFormatException e){System.out.println("Ingresa un número válido, igual o mayor que cero.");}}}
    public String leerTexto(String mensaje){while(true){System.out.print(mensaje);String v=scanner.nextLine().trim();if(!v.isEmpty())return v;System.out.println("Este campo no puede quedar vacío.");}}
}
