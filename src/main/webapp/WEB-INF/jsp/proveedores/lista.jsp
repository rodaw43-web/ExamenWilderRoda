<%-- 
    Document   : lista
    Created on : 20 oct 2026, 10:07:52
    Author     : erika
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../comun/encabezado.jsp" %>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h1 class="h3">Proveedores</h1>
    <a class="btn btn-primary" href="proveedores?accion=nuevo">Nuevo proveedor</a>
</div>

<table class="table table-striped bg-white">
    <form method="get" action="proveedores" class="d-flex gap-2 mb-3 " style="max-width: 480px">
        <input class="form-control" neme="buscar" placeholder="Buscar por nombre" value="<c:out value='${param.buscar}'/>">
        <button class="btn btn-outline-primary">Buscar</button>
        <a class="btn btn-link" href="proveedores">Limpiar</a>
    </form>

    <thead>
        <tr><th>Nombre</th><th>CedulaJuridica</th><th>Telefono</th><th>Correo</th><th>Estado</th><th></th></tr>
    </thead>
    <tbody>
        <c:forEach var="cat" items="${proveedores}">
            <tr>
                <td><c:out value="${cat.nombre}"/></td>
                <td><c:out value="${cat.cedulaJuridica}"/></td>
                <td><c:out value="${cat.telefono}"/></td>
                <td><c:out value="${cat.correo}"/></td>
                <td>${cat.activo ? 'Activa' : 'Inactiva'}</td>
                <td class="text-end">
                    <a class="btn btn-sm btn-outline-secondary"
                       href="proveedores?accion=editar&id=${cat.idProveedor}">Editar</a>
                    <c:if test="${cat.activo}">
                        <form method="post" action="proveedores" class="d-inline"
                              onsubmit="return confirm('¿Desactivar este proveedor?')">
                            <input type="hidden" name="accion" value="desactivar">
                            <input type="hidden" name="id" value="${cat.idProveedor}">
                            <button class="btn btn-sm btn-outline-danger">Desactivar</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty proveedores}">
            <tr><td colspan="4" class="text-muted">No hay un proveedor que coinsida con la búsqueda.</td></tr>
        </c:if>    
    </tbody>
</table>

<%@ include file="../comun/pie.jsp" %>

