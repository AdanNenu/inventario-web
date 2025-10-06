/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.servicios;

import com.inventario.inventario_web.modelo.Usuario;
import com.inventario.inventario_web.modelo.Permiso;
import com.inventario.inventario_web.repositorio.AutenticacionRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author link
 */
@Service
public class AutenticacionService {

    @Autowired
    private AutenticacionRepository autenticacionRepository;

    public Usuario validarUsuario(String nombreUsuario, String contrasena) {
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }

        Usuario usuario = autenticacionRepository.obtenerUsuario(nombreUsuario);

        if (usuario == null) {
            throw new NoSuchElementException("Usuario no encontrado: " + nombreUsuario);
        }

        // Validar credenciales 
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new SecurityException("Contraseña incorrecta para el usuario: " + nombreUsuario);
        }

        // Cargar permisos del rol asociado al usuario
        List<Permiso> permisos = obtenerPermisosUsuario(usuario.getIdUsuario());
        usuario.getRol().setLstPermisos(permisos);
        return usuario;
    }

    public List<Permiso> obtenerPermisosUsuario(Integer idUsuario) {
        return autenticacionRepository.obtenerPermisos(idUsuario);
    }
}
