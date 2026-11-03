package com.stockfacil.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Semana 6. Abre conexiones a la base stockfacil.
 * Cambie USUARIO y CLAVE por los de su MySQL.
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/stockfacil";
    private static final String USUARIO = "root";
    private static final String CLAVE = "AdmIn/1708";

    static {
        try {
            // Tomcat necesita que el driver se cargue a mano una vez.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de MySQL", e);
        }
    }

    public static Connection obtener() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
