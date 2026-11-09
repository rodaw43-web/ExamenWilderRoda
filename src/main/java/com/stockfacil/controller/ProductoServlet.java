package com.stockfacil.controller;

import com.stockfacil.dao.CategoriaDAO;
import com.stockfacil.dao.ProductoDAO;
import com.stockfacil.model.Producto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                Producto p = new Producto();
                p.setStockMinimo(5);
                mostrarFormulario(p, req, resp);
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(req.getParameter("id"));
                mostrarFormulario(dao.buscarPorId(id), req, resp);
            } else {
                req.setAttribute("productos", dao.listar());
                req.getRequestDispatcher("/WEB-INF/jsp/productos/lista.jsp").forward(req, resp);
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
                resp.sendRedirect("productos");
                return;
            }

            List<String> errores = new ArrayList<>();
            Producto p = new Producto();
            String id = req.getParameter("idProducto");
            p.setIdProducto(id == null || id.isBlank() ? 0 : Integer.parseInt(id));
            p.setCodigo(req.getParameter("codigo").trim().toUpperCase());
            p.setNombre(req.getParameter("nombre").trim());
            p.setIdCategoria(leerEntero(req.getParameter("idCategoria"), "La categoría", errores));
            p.setPrecio(leerDecimal(req.getParameter("precio"), errores));
            p.setStockMinimo(leerEntero(req.getParameter("stockMinimo"), "El stock mínimo", errores));
            p.setActivo(p.getIdProducto() == 0 || req.getParameter("activo") != null);

            if (p.getCodigo().isEmpty()) {
                errores.add("El código es obligatorio.");
            }
            if (p.getNombre().isEmpty()) {
                errores.add("El nombre es obligatorio.");
            }
            if (p.getIdCategoria() <= 0) {
                errores.add("Seleccione una categoría.");
            }
            if (p.getStockMinimo() < 0) {
                errores.add("El stock mínimo no puede ser negativo.");   // RN-02
            }
            if (!p.getCodigo().isEmpty() && dao.existeCodigo(p.getCodigo(), p.getIdProducto())) {
                errores.add("Ya existe un producto con ese código.");                          // RN-01
            }

            if (!errores.isEmpty()) {
                req.setAttribute("errores", errores);
                mostrarFormulario(p, req, resp);
                return;
            }

            if (p.getIdProducto() == 0) {
                dao.insertar(p);
            } else {
                dao.actualizar(p);
            }
            resp.sendRedirect("productos");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void mostrarFormulario(Producto p, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, SQLException {
        req.setAttribute("producto", p);
        req.setAttribute("categorias", categoriaDAO.listarActivas());
        req.getRequestDispatcher("/WEB-INF/jsp/productos/form.jsp").forward(req, resp);
    }

    private int leerEntero(String texto, String campo, List<String> errores) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException | NullPointerException e) {
            errores.add(campo + " debe ser un número entero.");
            return 0;
        }
    }

    private BigDecimal leerDecimal(String texto, List<String> errores) {
        try {
            BigDecimal valor = new BigDecimal(texto.trim());
            if (valor.signum() < 0) {
                errores.add("El precio no puede ser negativo.");   // RN-02
            }
            return valor;
        } catch (NumberFormatException | NullPointerException e) {
            errores.add("El precio debe ser un número, por ejemplo 1250.50");
            return BigDecimal.ZERO;
        }
    }
}
