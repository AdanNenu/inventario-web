package com.inventario.inventario_web;

// Importaciones necesarias para Spring Boot
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication es una anotación que combina @Configuration, 
// @EnableAutoConfiguration, y @ComponentScan.
// Indica a Spring que esta es la clase principal para la configuración
// y el escaneo de componentes.
@SpringBootApplication
public class InventarioWebApplication {

    // El método main es el punto de entrada de la aplicación Java.
    public static void main(String[] args) {
        // SpringApplication.run(...) inicializa la aplicación Spring Boot.
        // Configura y arranca el contexto de la aplicación.
        SpringApplication.run(InventarioWebApplication.class, args);
    }
    
}