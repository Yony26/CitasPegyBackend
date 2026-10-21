package domain;

public class Administracion { extends Persona

        private String cargo;
        private String usuario;

        public Administracion(int id, String nombre, String apellido,
                              String documento, String correo, String telefono,
                              String cargo, String usuario) {
            super(id,nombre,apellido,documento,correo,telefono);
            this.cargo = cargo;
            this.usuario = usuario;
        }

        public String getCargo() { return cargo; }
        public String getUsuario() { return usuario; }
        public void setCargo(String cargo) { this.cargo = cargo; }

        @Override
        public String getRol() {
            return "ADMINISTRACION";
        }
    }
