package domain.enums;

public enum EstadoCita {
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada");

    private final String value;

    EstadoCita(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
