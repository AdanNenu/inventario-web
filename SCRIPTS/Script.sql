-- =======================================================
--   BASE DE DATOS
-- =======================================================
DROP DATABASE inventarioweb;
CREATE DATABASE inventarioweb;
USE inventarioweb;

-- =======================================================
--   TABLA: rol
-- =======================================================
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL,
    descripcion VARCHAR(150)
);

-- =======================================================
--   TABLA: usuario
--   Un usuario tiene exactamente un rol
-- =======================================================
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(25) NOT NULL,
    id_rol INT,
    estatus TINYINT(1) DEFAULT 1,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

-- =======================================================
--   TABLA: permiso
--   Catálogo de acciones disponibles en el sistema
-- =======================================================
CREATE TABLE permiso (
    id_permiso INT AUTO_INCREMENT PRIMARY KEY,
    nombre_permiso VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200)
);
-- =======================================================
--   TABLA: rol_permiso
--   Relación muchos a muchos → un rol puede tener varios permisos
-- =======================================================
CREATE TABLE rol_permiso (
    id_rol INT NOT NULL,
    id_permiso INT NOT NULL,
    PRIMARY KEY (id_rol, id_permiso),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_permiso) REFERENCES permiso(id_permiso)
);

-- ===========================================
--  TABLA: producto
-- ===========================================
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    existencia TINYINT DEFAULT 0,
    estatus TINYINT DEFAULT 1
);

-- ===========================================
--  TABLA: bitacora
-- ===========================================
CREATE TABLE bitacora (
    id_bitacora INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    tipo_entrada_salida VARCHAR(50) NOT NULL,
    fecha_hora_registro DATETIME NOT NULL,
    
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);



-- =======================================================
--   POBLAR INSERTS DE ROLES
-- =======================================================
INSERT INTO rol (nombre_rol, descripcion)
VALUES
('Administrador', 'Rol administrador con casi todos los permisos, menos salidas'),
('Almacenista', 'Rol orientado al manejo de inventario y salidas');


-- =======================================================
--   INSERTS DE USUARIOS
-- =======================================================
INSERT INTO usuario (nombre, correo, contrasena, id_rol, estatus)
VALUES
('adanadmin', 'adanadmin@inventariocastores.com', 'admin123', 1, 1),
('adanalmacen', 'adanalmacen@inventariocastores.com', 'almacen123', 2, 1);

-- =======================================================
--   INSERTS DE PERMISOS (ACCIONES)
-- =======================================================
INSERT INTO permiso (nombre_permiso, descripcion)
VALUES
('ver_modulo_inventario', 'Permite acceder al módulo de inventario'),
('alta_producto', 'Permite dar de alta productos nuevos'),
('sumar_existencias', 'Permite agregar existencias al inventario'),
('inactivar_producto', 'Permite marcar un producto como inactivo'),
('ver_modulo_salida', 'Permite acceder al módulo de salidas'),
('restar_existencias', 'Permite registrar salidas de producto'),
('ver_modulo_historico', 'Permite acceder al módulo historial de movimientos'),
('reactivar_producto', 'Permite marcar un producto como activo');
-- =======================================================
--   ASIGNACIÓN DE PERMISOS A ROLES
-- =======================================================

-- ADMINISTRADOR
-- Tiene todos los permisos EXCEPTO: ver_modulo_salida (id 5), restar_existencias (id 6) 
INSERT INTO rol_permiso (id_rol, id_permiso)
VALUES
(1, 1),  -- ver_modulo_inventario
(1, 2),  -- alta_producto
(1, 3),  -- sumar_existencias
(1, 4),  -- inactivar_producto
(1, 8),  -- reactivar_producto
(1, 7);  -- ver_modulo_historico

-- ALMACENISTA
-- Solo tiene: ver_modulo_inventario (1), ver_modulo_salida (5), restar_existencias (6)
INSERT INTO rol_permiso (id_rol, id_permiso)
VALUES (2, 1), (2, 5), (2, 6);