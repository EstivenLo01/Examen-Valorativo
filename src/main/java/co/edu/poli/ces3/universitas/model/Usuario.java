package co.edu.poli.ces3.universitas.model;

import co.edu.poli.ces3.universitas.dto.DtoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario extends Conexion implements CRUD {
    private int id;
    private String nombre;
    private String correo;
    private String contraseña;

    public Usuario(int id, String nombre, String correo, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public int getId() {
        return this.id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public Usuario create(DtoUsuario usuario) throws SQLException {
        Connection cnn = this.getConexion();
        if (cnn != null) {
            String sql = "INSERT INTO usuario(correo, contraseña, nombre) VALUES(?, ?, ?)";
            this.correo = usuario.getCorreo();
            this.contraseña = usuario.getContraseña();
            this.nombre = usuario.getNombre();

            try (PreparedStatement stmt = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, this.correo);
                stmt.setString(2, this.contraseña);
                stmt.setString(3, this.nombre);
                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        this.id = rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                cnn.close();
            }
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<Usuario> all() {
        Connection cnn = this.getConexion();
        ArrayList<Usuario> usuarios = new ArrayList<>();

        if (cnn != null) {
            String sql = "SELECT * FROM usuario";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String correo = rs.getString("correo");
                        String contraseña = rs.getString("contraseña");
                        String nombre = rs.getString("nombre");
                        Usuario usuario = new Usuario(id, nombre, correo, contraseña);
                        usuarios.add(usuario);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (cnn != null) {
                        cnn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return usuarios;
        }
        return null;
    }

    @Override
    public Usuario findById(int usuarioId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "SELECT id, correo, contraseña, nombre FROM usuario WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, usuarioId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String correo = rs.getString("correo");
                        String contraseña = rs.getString("contraseña");
                        String nombre = rs.getString("nombre");
                        return new Usuario(id, nombre, correo, contraseña);
                    } else {
                        return null;
                    }
                }
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return null;
    }

    @Override
    public Usuario update(Usuario usuario) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "UPDATE usuario SET correo = ?, contraseña = ?, nombre = ? WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, usuario.getCorreo());
                stmt.setString(2, usuario.getContraseña());
                stmt.setString(3, usuario.getNombre());
                stmt.setInt(4, usuario.getId());
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return usuario;
    }

    @Override
    public void delete(int usuarioId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "DELETE FROM usuario WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, usuarioId);
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
    }
}
