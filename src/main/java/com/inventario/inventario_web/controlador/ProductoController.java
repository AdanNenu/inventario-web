/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.controlador;

import com.inventario.inventario_web.modelo.NombrePermiso;
import com.inventario.inventario_web.modelo.Producto;
import com.inventario.inventario_web.modelo.Usuario;
import com.inventario.inventario_web.servicios.ProductoService;
import dto.AltaProductoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/altaProducto")
    public ResponseEntity<?> altaProducto(@RequestBody AltaProductoRequest request) {
        try {
            Producto producto = request.getProducto();
            Usuario usuario = request.getUsuario();

            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.ALTA_PRODUCTO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para dar de alta nuevos productos"
                );
            }

            Producto creado = productoService.altaProducto(producto, usuario.getIdUsuario());
            return ResponseEntity.ok(creado);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{idProducto}/sumar/{nuevasExistencias}")
    public ResponseEntity<?> sumarExistencia(
            @PathVariable int idProducto,
            @PathVariable int nuevasExistencias,
            @RequestBody Usuario usuario) {

        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.SUMAR_EXISTENCIAS.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para agregar existencias al inventario"
                );
            }

            Producto actualizado = productoService.sumarExistencia(
                    idProducto,
                    usuario.getIdUsuario(),
                    nuevasExistencias
            );

            return ResponseEntity.ok(actualizado);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{idProducto}/restar/{cantidadARestar}")
    public ResponseEntity<?> restarExistencia(
            @PathVariable int idProducto,
            @PathVariable int cantidadARestar,
            @RequestBody Usuario usuario) {

        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.RESTAR_EXISTENCIAS.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para registrar salidas de producto"
                );
            }

            Producto actualizado = productoService.restarExistencia(
                    idProducto,
                    cantidadARestar,
                    usuario.getIdUsuario()
            );

            return ResponseEntity.ok(actualizado);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{idProducto}/inactivar")
    public ResponseEntity<?> inactivarProducto(
            @PathVariable int idProducto,
            @RequestBody Usuario usuario) {

        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.INACTIVAR_PRODUCTO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para inactivar productos"
                );
            }

            Producto actualizado = productoService.inactivarProducto(
                    idProducto,
                    usuario.getIdUsuario()
            );

            return ResponseEntity.ok(actualizado);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/{idProducto}/habilitar")
    public ResponseEntity<?> activarProducto(
            @PathVariable int idProducto,
            @RequestBody Usuario usuario) {

        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso()
                    .equalsIgnoreCase(NombrePermiso.REACTIVAR_PRODUCTO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "No tiene permisos para habilitar productos"
                );
            }

            Producto actualizado = productoService.activarProducto(
                    idProducto,
                    usuario.getIdUsuario()
            );

            return ResponseEntity.ok(actualizado);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/listarProductos")
    public ResponseEntity<List<Producto>> listarProductos(
            @RequestBody Usuario usuario) {

        try {
            boolean tienePermiso = usuario.getRol().getLstPermisos().stream()
                    .anyMatch(p -> p.getNombrePermiso().equalsIgnoreCase(NombrePermiso.VER_MODULO_INVENTARIO.getNombre()));

            if (!tienePermiso) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tiene permisos para acceder al módulo de inventario");
            }

            List<Producto> productos = productoService.listarProductos();
            return ResponseEntity.ok(productos);

        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
