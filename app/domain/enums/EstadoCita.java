package domain.enums;

public enum EstadoCita {
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    ATENDIDA("Atendida"),
    NO_ASISTIO("No asistió"),
    CANCELADA("Cancelada");

    private final String value;

    EstadoCita(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
