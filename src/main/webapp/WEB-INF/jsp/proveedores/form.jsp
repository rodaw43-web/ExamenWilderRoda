<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="p" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../comun/encabezado.jsp" %>

<h1 class="h3 mb-3">${proveedor.idProveedor == 0 ? 'Nuevo proveedor' : 'Editar proveedor'}</h1>

<c:if test="${not empty errores}">
    <div class="alert alert-danger">
        <c:forEach var="e" items="${errores}"><div><c:out value="${e}"/></div></c:forEach>
    </div>
</c:if>

<form method="post" action="proveedores" class="card card-body" style="max-width: 560px">
    <input type="hidden" name="accion" value="guardar">
    <input type="hidden" name="idProveedor" value="${proveedor.idProveedor}">

    <label class="form-label">Nombre</label>
    <input class="form-control mb-3" name="nombre" maxlength="60" required
           value="<c:out value='${proveedor.nombre}'/>">

    <label class="form-label">Cedula Juridica</label>
    <input class="form-control mb-3" name="cedula juridica" maxlength="100"
           value="<c:out value='${proveedor.cedulaJuridica}'/>">
    
    <label class="form-label">Telefono</label>
    <input class="form-control mb-3" name="telefono" maxlength="8"
           value="<c:out value='${proveedor.telefono}'/>">
    
    <label class="form-label">Correo</label>
    <input class="form-control mb-3" name="correo" maxlength="100"
           value="<c:out value='${proveedor.correo}'/>">

    <c:if test="${proveedor.idProveedor != 0}">
        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" name="activo" id="activo"
                   ${proveedor.activo ? 'checked' : ''}>
            <label class="form-check-label" for="activo">Activa</label>
        </div>
    </c:if>

    <div>
        <button class="btn btn-primary">Guardar</button>
        <a class="btn btn-link" href="proveedores">Cancelar</a>
    </div>
</form>

<%@ include file="../comun/pie.jsp" %>
