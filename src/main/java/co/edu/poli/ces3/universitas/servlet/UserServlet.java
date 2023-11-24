package co.edu.poli.ces3.universitas.servlet;

import co.edu.poli.ces3.universitas.controller.CtrUsuario;
import co.edu.poli.ces3.universitas.dto.DtoUsuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UsuriousServlet", value = "/user")
public class UserServlet extends MyServlet {
    private String message;

    private GsonBuilder gsonBuilder;

    private Gson gson;

    private ArrayList<DtoUsuario> usuarios;

    CtrUsuario ctr = new CtrUsuario();

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        usuarios = new ArrayList<>();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        DtoUsuario usuario = new DtoUsuario(
                body.get("correo").getAsString(),
                body.get("contraseña").getAsString(),
                body.get("nombre").getAsString()
        );

        DtoUsuario newUsuario = ctr.addUsuario(usuario);

        out.print(gson.toJson(newUsuario));
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        String usuarioIdParam = req.getParameter("id");

        if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
            int usuarioId = Integer.parseInt(usuarioIdParam);
            DtoUsuario usuario = ctr.getUsuarioById(usuarioId);
            out.print(gson.toJson(usuario));
        } else {
            ArrayList<DtoUsuario> usuarios = ctr.getAllUsuarios();
            out.print(gson.toJson(usuarios));
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject body = gson.fromJson(stringBuilder.toString(), JsonObject.class);
        int usuarioId = body.get("id").getAsInt();

        DtoUsuario updatedUsuario = new DtoUsuario(
                body.get("correo").getAsString(),
                body.get("contraseña").getAsString(),
                body.get("nombre").getAsString()
        );

        DtoUsuario result = ctr.updateUsuario(usuarioId, updatedUsuario);

        out.print(gson.toJson(result));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        int usuarioId = Integer.parseInt(req.getParameter("id"));

        ctr.deleteUsuario(usuarioId);

        out.print(gson.toJson("Eliminado"));
        out.flush();
    }


}