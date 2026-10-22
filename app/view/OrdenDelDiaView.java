package view;

import domain.OrdenDelDia;
import service.inputport.OrdenDelDiaService;
import service.validations.FormTypeValidator;

import java.util.List;

public class OrdenDelDiaView {

    private final OrdenDelDiaService service;

    // Inyección de dependencias: la vista recibe la interfaz del service
    public OrdenDelDiaView(OrdenDelDiaService service) {
        this.service = service;
    }

    public void crear() {
        String fecha = FormTypeValidator.stringValidator("Fecha (yyyy-MM-dd): ");
        String hora = FormTypeValidator.stringValidator("Hora (HH:mm): ");
        int clienteId = FormTypeValidator.intValidator("Id del cliente: ");
        int especialistaId = FormTypeValidator.intValidator("Id del especialista: ");
        int servicioId = FormTypeValidator.intValidator("Id del servicio: ");

        try {
            OrdenDelDia orden = service.crear(fecha, hora, clienteId, especialistaId, servicioId);
            System.out.println("Cita confirmada. Código: " + orden.getBookingCode());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarTodas() {
        List<OrdenDelDia> lista = service.selectAll();
        if (lista.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        for (OrdenDelDia o : lista) {
            System.out.println(o);
        }
    }

    public void cancelar() {
        int id = FormTypeValidator.intValidator("Id de la cita a cancelar: ");
        try {
            service.cancelar(id);
            System.out.println("Cita cancelada. El horario quedó libre.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}