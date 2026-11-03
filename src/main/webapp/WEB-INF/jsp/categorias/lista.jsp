<%-- 
    Document   : lista
    Created on : 20 oct 2026, 10:07:52
    Author     : erika
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../comun/encabezado.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h3">Categorías</h1>
    <a class="btn btn-primary" href="categorias?accion=nuevo">Nueva categoría</a>
</div>

<table class="table table-striped bg-white">
    <form method="get" action="categorias" class="d-flex gap-2 mb-3 " style="max-width: 480px">
        <input class="form-control" neme="buscar" placeholder="Buscar por nombre" value="<c:out value='${param.buscar}'/>">
        <button class="btn btn-outline-primary">Buscar</button>
        <a class="btn btn-link" href="categorias">Limpiar</a>
    </form>
    
    <thead>
    <tr><th>Nombre</th><th>Descripción</th><th>Estado</th><th></th></tr>
    </thead>
    <tbody>
    <c:forEach var="cat" items="${categorias}">
        <tr>
            <td><c:out value="${cat.nombre}"/></td>
            <td><c:out value="${cat.descripcion}"/></td>
            <td>${cat.activo ? 'Activa' : 'Inactiva'}</td>
            <td class="text-end">
                <a class="btn btn-sm btn-outline-secondary"
                   href="categorias?accion=editar&id=${cat.idCategoria}">Editar</a>
                <c:if test="${cat.activo}">
                    <form method="post" action="categorias" class="d-inline"
                          onsubmit="return confirm('¿Desactivar esta categoría?')">
                        <input type="hidden" name="accion" value="desactivar">
                        <input type="hidden" name="id" value="${cat.idCategoria}">
                        <button class="btn btn-sm btn-outline-danger">Desactivar</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty categorias}">
        <tr><td colspan="4" class="text-muted">No hay categorias que coinsida con la búsqueda.</td></tr>
    </c:if>    
    </tbody>
</table>

<%@ include file="../comun/pie.jsp" %>

