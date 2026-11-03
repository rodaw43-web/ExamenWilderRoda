package com.stockfacil.controller;

import com.stockfacil.dao.CategoriaDAO;
import com.stockfacil.model.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Semana 6. Mantenimiento de categorías.
 * GET  /categorias                      -> lista
 * GET  /categorias?accion=nuevo         -> formulario vacío
 * GET  /categorias?accion=editar&id=3   -> formulario con datos
 * POST /categorias (accion=guardar)     -> inserta o actualiza
 * POST /categorias (accion=desactivar)  -> desactiva
 */
@WebServlet("/categorias")
public class CategoriaServlet extends HttpServlet {

    private final CategoriaDAO dao = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                req.setAttribute("categoria", new Categoria());
                mostrar("categorias/form", req, resp);
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("categoria", dao.buscarPorId(id));
                mostrar("categorias/form", req, resp);
            } else {
               String buscar = req.getParameter("buscar");
               
               if (buscar != null && !buscar.isBlank()){
                   req.setAttribute("categorias", dao.buscar(buscar.trim()));
               } else {
                   req.setAttribute("categorias", dao.lista());
               }
               mostrar("categorias/lista", req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        try {
            if ("desactivar".equals(accion)) {
                dao.desactivar(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("categorias");
                return;
            }

            // 1. Leer el formulario y pasarlo a un objeto
            Categoria c = new Categoria();
            String id = req.getParameter("idCategoria");
            c.setIdCategoria(id == null || id.isBlank() ? 0 : Integer.parseInt(id));
            c.setNombre(req.getParameter("nombre").trim());
            c.setDescripcion(req.getParameter("descripcion").trim());
            c.setActivo(req.getParameter("activo") != null || c.getIdCategoria() == 0);

            // 2. Validar en el servidor
            List<String> errores = new ArrayList<>();
            if (c.getNombre().isEmpty()) {
                errores.add("El nombre es obligatorio.");
            } else if (dao.existeNombre(c.getNombre(), c.getIdCategoria())) {
                errores.add("Ya existe una categoría con ese nombre.");   // RN-01
            }

            // 3. Si hay errores, volver al formulario con los datos escritos
            if (!errores.isEmpty()) {
                req.setAttribute("errores", errores);
                req.setAttribute("categoria", c);
                mostrar("categorias/form", req, resp);
                return;
            }

            // 4. Guardar y volver a la lista
            if (c.getIdCategoria() == 0) {
                dao.insertar(c);
            } else {
                dao.actualizar(c);
            }
            resp.sendRedirect("categorias");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void mostrar(String vista, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/" + vista + ".jsp").forward(req, resp);
    }
}

