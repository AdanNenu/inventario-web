/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.repositorio;

import com.inventario.inventario_web.modelo.Bitacora;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Bitácora con SQL puro usando JdbcTemplate (sin Hibernate ni JPA).
 * Incluye manejo básico de errores con try/catch.
 * 
 * @author link
 */
@Repository
public class BitacoraRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // 🔹 Mapeo del ResultSet a la entidad Bitacora
    private final RowMapper<Bitacora> rowMapper = new RowMapper<>() {
        @Override
        public Bitacora mapRow(ResultSet rs, int rowNum) throws SQLException {
            Bitacora b = new Bitacora();
            b.setIdBitacora(rs.getInt("id_bitacora"));
            b.setIdProducto(rs.getInt("id_producto"));
            b.setIdUsuario(rs.getInt("id_usuario"));
            b.setTipoEntradaSalida(rs.getString("tipo_entrada_salida"));
            b.setFechaHoraRegistro(rs.getTimestamp("fecha_hora_registro").toLocalDateTime());
            return b;
        }
    };

    /* ====================================================
     * MÉTODOS PERSONALIZADOS SQL PURO CON MANEJO DE ERRORES
     * ==================================================== */

    /**
     * Guarda un nuevo registro en la bitácora.
     *
     * @param b objeto Bitacora a registrar
     */
    public void guardarBitacora(Bitacora b) {
        String sql = """
            INSERT INTO bitacora (id_producto, id_usuario, tipo_entrada_salida, fecha_hora_registro)
            VALUES (?, ?, ?, ?)
        """;
        try {
            jdbc.update(sql,
                    b.getIdProducto(),
                    b.getIdUsuario(),
                    b.getTipoEntradaSalida(),
                    b.getFechaHoraRegistro() != null ? b.getFechaHoraRegistro() : LocalDateTime.now()
            );
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al guardar registro en bitácora: " + ex.getMessage());
        }
    }

    /**
     * Lista registros de bitácora filtrando opcionalmente por tipo de entrada o salida.
     * Si tipoEntradaSalida es null, vacío o "TODOS", devuelve todos los registros.
     *
     * @param tipoEntradaSalida filtro opcional ("ENTRADA", "SALIDA" o "TODOS")
     * @return lista de registros de bitácora
     */
    public List<Bitacora> obtenerBitacora(String tipoEntradaSalida) {
        try {
            if (tipoEntradaSalida == null || tipoEntradaSalida.isEmpty() || tipoEntradaSalida.equalsIgnoreCase("TODOS")) {
                String sql = "SELECT * FROM bitacora";
                return jdbc.query(sql, rowMapper);
            }

            String sql = "SELECT * FROM bitacora WHERE tipo_entrada_salida = ?";
            return jdbc.query(sql, rowMapper, tipoEntradaSalida);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener registros de bitácora: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista todos los registros de bitácora sin filtro.
     *
     * @return lista completa de registros de bitácora
     */
    public List<Bitacora> obtenerBitacora() {
        try {
            String sql = "SELECT * FROM bitacora";
            return jdbc.query(sql, rowMapper);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener todos los registros de bitácora: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
}