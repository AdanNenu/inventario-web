/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.repositorio;

import com.inventario.inventario_web.modelo.Producto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de productos usando SQL puro (sin Hibernate).
 * Implementado con JdbcTemplate de Spring y manejo de errores básico.
 * 
 * @author link
 */
@Repository
public class ProductoRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Mapeo de ResultSet a entidad Producto
    private final RowMapper<Producto> rowMapper = new RowMapper<>() {
        @Override
        public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
            Producto p = new Producto();
            p.setIdProducto(rs.getInt("id_producto"));
            p.setNombre(rs.getString("nombre"));
            p.setCosto(rs.getDouble("costo"));
            p.setExistencia(rs.getInt("existencia"));
            p.setEstatus(rs.getString("estatus"));
            return p;
        }
    };

    /* =====================================================
     * MÉTODOS CRUD CON MANEJO SIMPLE DE EXCEPCIONES
     * ===================================================== */

    // Alta o actualización de producto
    public void guardarProducto(Producto p) {
        String sql = """
            INSERT INTO producto (id_producto, nombre, costo, existencia, estatus)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                nombre = VALUES(nombre),
                costo = VALUES(costo),
                existencia = VALUES(existencia),
                estatus = VALUES(estatus)
        """;

        try {
            jdbc.update(sql,
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getCosto(),
                    p.getExistencia(),
                    p.getEstatus());
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al guardar producto: " + ex.getMessage());
        }
    }

    // Buscar producto por ID
    public Optional<Producto> buscarProductoPorId(int idProducto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        try {
            List<Producto> resultados = jdbc.query(sql, rowMapper, idProducto);
            return resultados.stream().findFirst();
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al buscar producto por ID (" + idProducto + "): " + ex.getMessage());
            return Optional.empty();
        }
    }

    // Obtener todos los productos
    public List<Producto> obtenerProductos() {
        String sql = "SELECT * FROM producto";
        try {
            return jdbc.query(sql, rowMapper);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener todos los productos: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // Obtener productos por estatus
    public List<Producto> obtenerProductos(int estatus) {
        String sql = "SELECT * FROM producto WHERE estatus = ?";
        try {
            return jdbc.query(sql, rowMapper, estatus);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener productos por estatus (" + estatus + "): " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // Actualizar existencias
    public void actualizarExistencia(int idProducto, int nuevaExistencia) {
        String sql = "UPDATE producto SET existencia = ? WHERE id_producto = ?";
        try {
            jdbc.update(sql, nuevaExistencia, idProducto);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al actualizar existencia para producto ID " + idProducto + ": " + ex.getMessage());
        }
    }

    // Inactivar producto
    public void inactivarProducto(int idProducto) {
        String sql = "UPDATE producto SET estatus = 'INACTIVO' WHERE id_producto = ?";
        try {
            jdbc.update(sql, idProducto);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al inactivar producto ID " + idProducto + ": " + ex.getMessage());
        }
    }

    // Habilitar producto
    public void habilitarProducto(int idProducto) {
        String sql = "UPDATE producto SET estatus = 'ACTIVO' WHERE id_producto = ?";
        try {
            jdbc.update(sql, idProducto);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al habilitar producto ID " + idProducto + ": " + ex.getMessage());
        }
    }
}