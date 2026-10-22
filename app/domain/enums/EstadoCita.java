package domain;

import domain.enums.EstadoCita;

public class OrdenDelDia {

    private int agendaId;
    private String fecha;        // formato yyyy-MM-dd
    private String startTime;    // formato HH:mm
    private EstadoCita estado;
    private String bookingCode;
    private int clienteId;
    private int especialistaId;
    private int servicioId;

    public OrdenDelDia() {
    }

    public OrdenDelDia(int agendaId, String fecha, String startTime, EstadoCita estado,
                       String bookingCode, int clienteId, int especialistaId, int servicioId) {
        this.agendaId = agendaId;
        this.fecha = fecha;
        this.startTime = startTime;
        this.estado = estado;
        this.bookingCode = bookingCode;
        this.clienteId = clienteId;
        this.especialistaId = especialistaId;
        this.servicioId = servicioId;
    }

    public int getAgendaId() { return agendaId; }
    public void setAgendaId(int agendaId) { this.agendaId = agendaId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public EstadoCita getEstado() { return estado; }
    public void setEstado(EstadoCita estado) { this.estado = estado; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getEspecialistaId() { return especialistaId; }
    public void setEspecialistaId(int especialistaId) { this.especialistaId = especialistaId; }

    public int getServicioId() { return servicioId; }
    public void setServicioId(int servicioId) { this.servicioId = servicioId; }

    @Override
    public String toString() {
        return "Cita " + bookingCode + " | " + fecha + " " + startTime
                + " | Estado: " + estado.getValue()
                + " | Cliente: " + clienteId
                + " | Especialista: " + especialistaId
                + " | Servicio: " + servicioId;
    }
}