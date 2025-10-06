/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventario.inventario_web.modelo;

import java.util.List;

/**
 *
 * @author linki
 */
public class Rol {

    private Integer idRol;
    private String nombreRol;
    private String descripcion;
    private List<Permiso> lstPermisos;

    // Getters y Setters
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Permiso> getLstPermisos() {
        return lstPermisos;
    }

    public void setLstPermisos(List<Permiso> lstPermisos) {
        this.lstPermisos = lstPermisos;
    }
}
