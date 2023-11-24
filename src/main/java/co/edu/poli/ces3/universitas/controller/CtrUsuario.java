package co.edu.poli.ces3.universitas.controller;

import co.edu.poli.ces3.universitas.dto.DtoUsuario;
import co.edu.poli.ces3.universitas.model.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrUsuario {

    private Usuario modelUsuario;

    public CtrUsuario() {
        modelUsuario = new Usuario();
    }

    public DtoUsuario addUsuario(DtoUsuario usuario) {
        try {
            Usuario newUsuario = modelUsuario.create(usuario);
            return new DtoUsuario(newUsuario.getId(), newUsuario.getCorreo(), newUsuario.getContraseña(), newUsuario.getNombre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DtoUsuario> getAllUsuarios() {
        try {
            ArrayList<Usuario> usuarios = modelUsuario.all(); // Llama al método 'all' del modelo
            ArrayList<DtoUsuario> dtoUsuarios = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                DtoUsuario dtoUsuario = new DtoUsuario(
                        usuario.getId(),
                        usuario.getCorreo(),
                        usuario.getContraseña(),
                        usuario.getNombre()
                );
                dtoUsuarios.add(dtoUsuario);
            }

            return dtoUsuarios;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DtoUsuario getUsuarioById(int usuarioId) {
        try {
            Usuario usuario = modelUsuario.findById(usuarioId);
            if (usuario != null) {
                return new DtoUsuario(usuario.getId(), usuario.getCorreo(), usuario.getContraseña(), usuario.getNombre());
            } else {
                throw new RuntimeException("NO ESTÁ");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoUsuario updateUsuario(int usuarioId, DtoUsuario updatedUsuario) {
        try {
            Usuario usuario = new Usuario(
                    usuarioId,
                    updatedUsuario.getCorreo(),
                    updatedUsuario.getContraseña(),
                    updatedUsuario.getNombre()
            );

            Usuario updated = modelUsuario.update(usuario);
            return new DtoUsuario(updated.getId(), updated.getCorreo(), updated.getContraseña(), updated.getNombre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUsuario(int usuarioId) {
        try {
            modelUsuario.delete(usuarioId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
