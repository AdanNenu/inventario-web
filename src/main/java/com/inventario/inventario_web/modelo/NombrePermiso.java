/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.modelo;

/**
 *
 * @author linki
 */
public enum NombrePermiso {

    VER_MODULO_INVENTARIO("ver_modulo_inventario"),
    ALTA_PRODUCTO("alta_producto"),
    SUMAR_EXISTENCIAS("sumar_existencias"),
    INACTIVAR_PRODUCTO("inactivar_producto"),
    REACTIVAR_PRODUCTO("reactivar_producto"),
    VER_MODULO_SALIDA("ver_modulo_salida"),
    RESTAR_EXISTENCIAS("restar_existencias"),
    VER_MODULO_HISTORICO("ver_modulo_historico");

    private final String nombre;

    NombrePermiso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
