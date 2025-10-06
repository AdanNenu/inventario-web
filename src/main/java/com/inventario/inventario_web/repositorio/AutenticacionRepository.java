/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.repositorio;

import com.inventario.inventario_web.modelo.Usuario;
import com.inventario.inventario_web.modelo.Rol;
import com.inventario.inventario_web.modelo.Permiso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Autenticación con SQL puro usando JdbcTemplate (sin Hibernate ni JPA).
 * Se encarga de obtener usuarios, roles y permisos para la validación de autenticación.
 * 
 * Incluye manejo básico de errores con try/catch.
 * 
 * @author link
 */
@Repository
public class AutenticacionRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /* ====================================================
     * CONSULTAS SQL
     * ==================================================== */
    private static final String SQL_OBTENER_USUARIO = """
        SELECT 
            u.id_usuario,
            u.nombre,
            u.correo,
            u.contrasena,
            u.estatus,
            r.id_rol,
            r.nombre_rol,
            r.descripcion AS descripcion_rol
        FROM USUARIO u
        INNER JOIN ROL r ON u.id_rol = r.id_rol
        WHERE u.nombre = ?
    """;

    private static final String SQL_OBTENER_PERMISOS = """
        SELECT 
            p.id_permiso,
            p.nombre_permiso,
            p.descripcion
        FROM PERMISO p
        INNER JOIN ROL_PERMISO rp ON p.id_permiso = rp.id_permiso
        INNER JOIN ROL r ON rp.id_rol = r.id_rol
        INNER JOIN USUARIO u ON u.id_rol = r.id_rol
        WHERE u.id_usuario = ?
    """;

    /* ====================================================
     * MAPEADORES DE RESULTADOS
     * ==================================================== */
    
    // 🔹 Mapeo del usuario y su rol
    private final RowMapper<Usuario> usuarioRowMapper = new RowMapper<>() {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rol rol = new Rol();
            rol.setIdRol(rs.getInt("id_rol"));
            rol.setNombreRol(rs.getString("nombre_rol"));
            rol.setDescripcion(rs.getString("descripcion_rol"));

            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("nombre"));
            u.setCorreo(rs.getString("correo"));
            u.setContrasena(rs.getString("contrasena"));
            u.setEstatus(rs.getInt("estatus"));
            u.setRol(rol);
            return u;
        }
    };

    // 🔹 Mapeo de los permisos
    private final RowMapper<Permiso> permisoRowMapper = new RowMapper<>() {
        @Override
        public Permiso mapRow(ResultSet rs, int rowNum) throws SQLException {
            Permiso p = new Permiso();
            p.setIdPermiso(rs.getInt("id_permiso"));
            p.setNombrePermiso(rs.getString("nombre_permiso"));
            p.setDescripcion(rs.getString("descripcion"));
            return p;
        }
    };

    /* ====================================================
     * MÉTODOS PERSONALIZADOS SQL PURO CON MANEJO DE ERRORES
     * ==================================================== */

    /**
     * Obtiene la información completa del usuario (incluyendo su rol) según el nombre de usuario.
     * 
     * @param nombreUsuario nombre del usuario a consultar
     * @return objeto Usuario o null si no se encuentra
     */
    public Usuario obtenerUsuario(String nombreUsuario) {
        try {
            return jdbc.queryForObject(SQL_OBTENER_USUARIO, usuarioRowMapper, nombreUsuario);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener usuario: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todos los permisos asociados al rol del usuario.
     * 
     * @param idUsuario identificador del usuario
     * @return lista de permisos, puede estar vacía si no tiene asignados
     */
    public List<Permiso> obtenerPermisos(int idUsuario) {
        try {
            return jdbc.query(SQL_OBTENER_PERMISOS, permisoRowMapper, idUsuario);
        } catch (DataAccessException ex) {
            System.err.println("⚠️ Error al obtener permisos del usuario: " + ex.getMessage());
            return new ArrayList<>();
        }
    }
}