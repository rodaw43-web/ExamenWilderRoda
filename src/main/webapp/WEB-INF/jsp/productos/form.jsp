<%-- 
    Document   : form
    Created on : 3 nov 2026, 10:16:07 a.m.
    Author     : User
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../comun/encabezado.jsp" %>
<h1 class="h3 mb-3">${producto.idProducto == 0 ? 'Nuevo producto' : 'Editar producto'}</h1>
<c:if test="${not empty errores}">
    <div class="alert alert-danger">
        <c:forEach var="e" items="${errores}"><div><c:out value="${e}"/></div></c:forEach>
        </div>
</c:if>
<form method="post" action="productos" class="card card-body" style="max-width: 560px">
    <input type="hidden" name="accion" value="guardar">
    <input type="hidden" name="idProducto" value="${producto.idProducto}">
    <label class="form-label">Código</label>
    <input class="form-control mb-3" name="codigo" maxlength="20" required
           value="<c:out value='${producto.codigo}'/>">
    <label class="form-label">Nombre</label>
    <input class="form-control mb-3" name="nombre" maxlength="100" required
           value="<c:out value='${producto.nombre}'/>">
    
    <%-- El combo se llena con las categorías activas; se marca la del producto --%>
    <label class="form-label">Categoría</label>
    <select class="form-select mb-3" name="idCategoria" required>
        <option value="">Seleccione…</option>
        <c:forEach var="cat" items="${categorias}">
            <option value="${cat.idCategoria}" ${cat.idCategoria == producto.idCategoria ? 'selected' : ''}>
                <c:out value="${cat.nombre}"/>
            </option>
        </c:forEach>
    </select>
    <label class="form-label">Precio (₡)</label>
    <input class="form-control mb-3" name="precio" type="number" step="0.01" min="0" required
           value="${producto.precio}">
    <label class="form-label">Stock mínimo</label>
    <input class="form-control mb-3" name="stockMinimo" type="number" min="0" required
           value="${producto.stockMinimo}">
    <c:if test="${producto.idProducto != 0}">
        <p class="text-muted">Stock actual: ${producto.stock}. Se cambia registrando una entrada o salida.</p>
        <div class="form-check mb-3">
            <input class="form-check-input" type="checkbox" name="activo" id="activo"
                   ${producto.activo ? 'checked' : ''}>
            <label class="form-check-label" for="activo">Activo</label>
        </div>
    </c:if>
    <div>
        <button class="btn btn-primary">Guardar</button>
        <a class="btn btn-link" href="productos">Cancelar</a>
    </div>
</form>
<%@ include file="../comun/pie.jsp" %>
