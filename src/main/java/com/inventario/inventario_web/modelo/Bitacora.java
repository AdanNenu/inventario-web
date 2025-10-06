/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.modelo;

import java.time.LocalDateTime;

/**
 *
 * @author linki
 */
public class Bitacora {

    private Integer idBitacora;
    private Integer idProducto;
    private Integer idUsuario;
    private String tipoEntradaSalida;
    private LocalDateTime fechaHoraRegistro;

    // Getters y Setters

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoEntradaSalida() {
        return tipoEntradaSalida;
    }

    public void setTipoEntradaSalida(String tipoEntradaSalida) {
        this.tipoEntradaSalida = tipoEntradaSalida;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}