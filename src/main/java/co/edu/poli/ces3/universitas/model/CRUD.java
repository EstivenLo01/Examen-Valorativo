package co.edu.poli.ces3.universitas.model;

import co.edu.poli.ces3.universitas.dto.DtoUsuario;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {
    Usuario create(DtoUsuario usuario) throws SQLException;

    public ArrayList<Usuario> all();

    public Usuario findById(int id) throws SQLException;

    Usuario update(Usuario usuario) throws SQLException;

    void delete(int usuarioId) throws SQLException;
}