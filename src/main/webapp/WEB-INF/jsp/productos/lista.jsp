<%-- 
    Document   : lista
    Created on : 3 nov 2026, 9:02:41 a.m.
    Author     : User
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../comun/encabezado.jsp" %>
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h3">Productos</h1>
    <a class="btn btn-primary" href="productos?accion=nuevo">Nuevo producto</a>
</div>
<table class="table table-striped bg-white">
    <thead>
        <tr><th>Código</th><th>Nombre</th><th>Categoría</th><th class="text-end">Precio</th>
            <th class="text-end">Stock</th><th>Estado</th><th></th></tr>
    </thead>
    <tbody>
        <c:forEach var="p" items="${productos}">
            <tr class="${p.stockBajo && p.activo ? 'table-warning' : ''}">
                <td><c:out value="${p.codigo}"/></td>
                <td><c:out value="${p.nombre}"/></td>
                <td><c:out value="${p.nombreCategoria}"/></td>
                <td class="text-end">₡${p.precio}</td>
                <td class="text-end">${p.stock} <c:if test="${p.stockBajo}"><span class="badge text-bg-warning">bajo</span></c:if></td>
                <td>${p.activo ? 'Activo' : 'Inactivo'}</td>
                <td class="text-end">
                    <a class="btn btn-sm btn-outline-secondary"
                       href="productos?accion=editar&id=${p.idProducto}">Editar</a>
                    <c:if test="${p.activo}">
                        <form method="post" action="productos" class="d-inline"
                              onsubmit="return confirm('¿Desactivar este producto?')">
                            <input type="hidden" name="accion" value="desactivar">
                            <input type="hidden" name="id" value="${p.idProducto}">
                            <button class="btn btn-sm btn-outline-danger">Desactivar</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@ include file="../comun/pie.jsp" %>
