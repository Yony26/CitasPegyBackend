package domain;

    public abstract class Persona {

        private int id;
        private String nombre;
        private String apellido;
        private String documento;
        private String correo;
        private String telefono;

        public Persona(int id, String nombre, String apellido,
                       String documento, String correo, String telefono) {
            this.id = id;
            this.nombre = nombre;
            this.apellido = apellido;
            this.documento = documento;
            this.correo = correo;
            this.telefono = telefono;
        }

        public int getId() { return id; }
        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public String getDocumento() { return documento; }
        public String getCorreo() { return correo; }
        public String getTelefono() { return telefono; }

        public void setNombre(String nombre) { this.nombre = nombre; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public void setCorreo(String correo) { this.correo = correo; }
        public void setTelefono(String telefono) { this.telefono = telefono; }

        public String getNombreCompleto() {
            return nombre + " " + apellido;
        }

        // Cada hijo define su propio rol
        public abstract String getRol();

        @Override
        public String toString() {
            return getRol() + ": " + getNombreCompleto() + " (" + documento + ")";
        }
    }
}
