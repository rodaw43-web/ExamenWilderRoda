/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockfacil.dao;

import com.stockfacil.config.Conexion;
import com.stockfacil.model.Proveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public List<Proveedor> lista() throws SQLException {
        String sql = "SELECT * FROM proveedor ORDER BY nombre";
        List<Proveedor> lista = new ArrayList<>();

        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public List<Proveedor> buscar(String texto) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE nombre LIKE ? ORDER BY nombre";
        List<Proveedor> lista = new ArrayList<>();

        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }
    
    
    private Proveedor mapear(ResultSet rs) throws SQLException{
        Proveedor p = new Proveedor();
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setNombre(rs.getString("nombre"));
        p.setCedulaJuridica(rs.getString("cedula_juridica"));
        p.setTelefono(rs.getString("telefono"));
        p.setCorreo(rs.getString("correo"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
    
    public Proveedor buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM proveedor WHERE id_proveedor = ?";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next() ? mapear(rs) : null;
            }
        }
    }
    
     /** RN-01: true si otro registro ya usa ese nombre. */
    public boolean existeNombre(String nombre, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM proveedor WHERE nombre = ? AND id_proveedor <> ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }
    
    public boolean existeCedulaJuridica(String cedula, int idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM proveedor WHERE cedula_juridica = ? AND id_proveedor <> ?";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, cedula);
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()){
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void insertar(Proveedor p) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre, cedula_juridica, telefono, correo, activo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCedulaJuridica());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setBoolean(5, p.isActivo());
            ps.executeUpdate();
        }
    }

    public void actualizar(Proveedor p) throws SQLException {
        String sql = "UPDATE proveedor SET nombre = ?, cedula_juridica = ?, telefono = ?, correo = ?, activo = ? WHERE id_proveedor = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCedulaJuridica());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getCorreo());
            ps.setBoolean(5, p.isActivo());
            ps.setInt(6, p.getIdProveedor());
            ps.executeUpdate();
        }
    }

    /** RN-05: no se borra, se desactiva. */
    public void desactivar(int id) throws SQLException {
        String sql = "UPDATE proveedor SET activo = 0 WHERE id_proveedor = ?";
        try (Connection con = Conexion.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
}
