package co.edu.poli.ces3.universitas.dto;

public class DtoUsuario {
    private int id;
    private String correo;
    private String contraseña;
    private String nombre;

    public DtoUsuario(int id, String correo, String contraseña, String nombre) {
        this.id = id;
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    public DtoUsuario(String correo, String contraseña, String nombre) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    public DtoUsuario() {
    }

    public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "El usuario se llama: " + this.nombre +
                " su correo es: " + this.correo;
    }
}
