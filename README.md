# Inventario Web Castores

Sistema de gestión de inventario desarrollado en **Java** utilizando el framework **Spring Boot**, bajo el patrón de arqitectura **MVC** (Modelo-Vista-Controlador).  
Permite la administración de productos, control de existencias, y manejo de roles y permisos de usuarios.

---
## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Maven** como gestor de dependencias
- **MySQL 8.0** (administrado desde **MySQL Workbench**)
- **NetBeans** como IDE de desarrollo
- **Postman** para pruebas de endpoints
- **GitHub** para control de versiones

---

## Pasos para la ejecución del proyecto

1. **Clonar el reposito**
   git clone https://github.com/tuusuario/inventario-castores.git
2. **Importar proyecto desde tu IDE**
3. **Crear base de datos**
   Ejecutar el script que viene en la carpeta de SCRIPTS en el proyecto. También esta el diagrama E-R.
4. **Valida el archivo application.properties**
   Cambia el puerto de srping y/o basede datossi es necesario.
5. **Correr proyecto**
   Desde tu IDE corre el proyecto.
6. **Probar servicios**
   Abre PosrtMan o SoapUI e importa los XML de las colleciones de prueba. Estan en la carpeta de PRUEBAS.
   
