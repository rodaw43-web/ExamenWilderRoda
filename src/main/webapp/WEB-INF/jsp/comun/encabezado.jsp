<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>StockFácil</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<%-- Semana 6: menú sin login. En las semanas 10 y 11 se agregan la sesión y los roles. --%>
<nav class="navbar navbar-expand navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">StockFácil</a>
        <ul class="navbar-nav me-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/categorias">Categorías</a></li>
            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/proveedores">Proveedores</a></li>
        </ul>
    </div>
</nav>
<main class="container">
