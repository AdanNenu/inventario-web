/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.servicios;

import com.inventario.inventario_web.modelo.Bitacora;
import com.inventario.inventario_web.repositorio.BitacoraRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author link
 */
@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    public boolean registrarMovimiento(Bitacora bitacora) {
        try {
            // Validación funcional 
            if (bitacora == null) {
                throw new IllegalArgumentException("El objeto Bitacora no puede ser nulo.");
            }

            if (bitacora.getTipoEntradaSalida() == null || bitacora.getTipoEntradaSalida().isBlank()) {
                throw new IllegalArgumentException("El tipo de entrada/salida es obligatorio.");
            }

            // Guardar en base de datos
            bitacoraRepository.guardarBitacora(bitacora);
            return true;
        } catch (Exception e) {
            System.err.println("⚠️ Error al registrar movimiento en bitácora: " + e.getMessage());
            return false;
        }
    }

    public List<Bitacora> listarBitacora() {
        try {
            return bitacoraRepository.obtenerBitacora();
        } catch (Exception e) {
            System.err.println("⚠️ Error al listar registros de bitácora: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public List<Bitacora> listarBitacora(String tipoEntradaSalida) {
        try {
              // Acepta solo valores válidos
            if (!tipoEntradaSalida.equalsIgnoreCase("ENTRADA") && !tipoEntradaSalida.equalsIgnoreCase("SALIDA")) {
                throw new IllegalArgumentException("Argumento inválido. Solo se permite 'ENTRADA' o 'SALIDA'.");
            }
            return bitacoraRepository.obtenerBitacora(tipoEntradaSalida.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println(" Error al filtrar bitácora por tipo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}