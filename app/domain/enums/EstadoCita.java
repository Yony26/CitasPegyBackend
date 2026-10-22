package domain.enums;

public enum EstadoCita {

    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    CANCELADA("Cancelada"),
    COMPLETADA("Completada");

    private final String valor;

    EstadoCita(String valor) {
        this.valor = valor;
    }

    public String getValue() {
        return valor;
    }
}