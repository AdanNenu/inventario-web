/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.servicios;

import com.inventario.inventario_web.modelo.Bitacora;
import com.inventario.inventario_web.modelo.Producto;
import com.inventario.inventario_web.repositorio.BitacoraRepository;
import com.inventario.inventario_web.repositorio.ProductoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de productos con manejo genérico de errores mediante try/catch.
 * Aplica reglas de negocio y registra movimientos en bitácora.
 *
 * Nivel medio: claro, seguro y suficientemente simple.
 *
 * @author link
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    // Alta de producto (existencia inicia en 0 por defecto)
    public Producto altaProducto(Producto producto, int idUsuario) {
            producto.setExistencia(0);
            productoRepository.guardarProducto(producto);
            return producto;
    }

    // Sumar existencia al producto
    public Producto sumarExistencia(int idProducto, int idUsuario, int nuevasExistencias) {
            Producto producto = productoRepository.buscarProductoPorId(idProducto)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (nuevasExistencias < producto.getExistencia()) {
                throw new RuntimeException("Solo se puede sumar existencias, no disminuir. "
                        + "Existencias actuales: " + producto.getExistencia());
            }

            producto.setExistencia(nuevasExistencias);

            // Registrar en bitácora
            Bitacora bitacora = new Bitacora();
            bitacora.setIdProducto(idProducto);
            bitacora.setIdUsuario(idUsuario);
            bitacora.setTipoEntradaSalida("ENTRADA");
            bitacora.setFechaHoraRegistro(LocalDateTime.now());
            bitacoraRepository.guardarBitacora(bitacora);

            productoRepository.guardarProducto(producto);
            return producto;
    }

    // Restar existencia al producto
    public Producto restarExistencia(int idProducto, int cantidadARestar, int idUsuario) {
            Producto producto = productoRepository.buscarProductoPorId(idProducto)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            int existenciasActuales = producto.getExistencia();

            if (cantidadARestar <= 0) {
                throw new RuntimeException("La cantidad a restar debe ser mayor a cero.");
            }

            if (cantidadARestar > existenciasActuales) {
                throw new RuntimeException("No hay existencias suficientes. Existencias actuales: "
                        + existenciasActuales + ", cantidad solicitada: " + cantidadARestar);
            }

            producto.setExistencia(existenciasActuales - cantidadARestar);

            Bitacora bitacora = new Bitacora();
            bitacora.setIdProducto(idProducto);
            bitacora.setIdUsuario(idUsuario);
            bitacora.setTipoEntradaSalida("SALIDA");
            bitacora.setFechaHoraRegistro(LocalDateTime.now());
            bitacoraRepository.guardarBitacora(bitacora);

            productoRepository.guardarProducto(producto);
            return producto;
    }

    // Inactivar producto
    public Producto inactivarProducto(int idProducto, int idUsuario) {
            Producto producto = productoRepository.buscarProductoPorId(idProducto)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if ("0".equals(producto.getEstatus())) {
                throw new RuntimeException("El producto ya está dado de baja.");
            }

            producto.setEstatus("0");
            productoRepository.guardarProducto(producto);
            return producto;
         }

    // Habilitar producto
    public Producto activarProducto(int idProducto, int idUsuario) {
            Producto producto = productoRepository.buscarProductoPorId(idProducto)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if ("1".equals(producto.getEstatus())) {
                throw new RuntimeException("El producto ya está activo.");
            }

            producto.setEstatus("1");
            productoRepository.guardarProducto(producto);
            return producto;
    }

    // Listar productos (por estatus o todos)
    public List<Producto> listarProductos(int estatus) {
            // Validar que el estatus sea numérico y válido (0 o 1)
            if (estatus != 0 && estatus !=1 ) {
                throw new IllegalArgumentException("El parámetro 'estatus' debe ser 0 (inactivo) o 1 (activo). Valor recibido: " + estatus);
            }
            return productoRepository.obtenerProductos(estatus);
    }

    // Listar productos (por estatus o todos)
    public List<Producto> listarProductos() {
            return productoRepository.obtenerProductos();
    }
}
