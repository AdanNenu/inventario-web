/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.controlador;

import com.inventario.inventario_web.modelo.Bitacora;
import com.inventario.inventario_web.modelo.NombrePermiso;
import com.inventario.inventario_web.modelo.Usuario;
import com.inventario.inventario_web.servicios.BitacoraService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author link
 */
@RestController
@CrossOrigin(origins = "*")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @PostMapping("/registro")
    public ResponseEntity<Boolean> registrarMovimiento(@RequestBody Bitacora bitacora) {
        try {
            boolean exito = bitacoraService.registrarMovimiento(bitacora);
            return ResponseEntity.ok(exito);
        } catch (Exception e) {
            System.err.println("⚠️ Error al registrar movimiento en bitácora: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping("/listarHistorico")
    public ResponseEntity<?> listarBitacora(@RequestBody Usuario usuario) {
        try {
            // 🟢 Validar permiso 'ver_modulo_historico'
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.VER_MODULO_HISTORICO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para visualizar el historial de movimientos"
                );
            }

            List<Bitacora> lista = bitacoraService.listarBitacora();
            return ResponseEntity.ok(lista);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/listar/{tipoEntradaSalida}")
    public ResponseEntity<List<Bitacora>> listarBitacora(
            @PathVariable String tipoEntradaSalida,
            @RequestBody Usuario usuario) {
        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.VER_MODULO_HISTORICO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para visualizar el historial de movimientos"
                );
            }

            List<Bitacora> lista = bitacoraService.listarBitacora(tipoEntradaSalida);
            return ResponseEntity.ok(lista);

        } catch (IllegalArgumentException ex) {
            System.err.println("⚠️ Argumento inválido al filtrar bitácora: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            System.err.println("⚠️ Error al filtrar bitácora: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
