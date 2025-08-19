package com.ventas.ventasbackend;

// Importaciones necesarias para la aplicación Spring Boot.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Anotación que marca esta clase como una aplicación Spring Boot.
@SpringBootApplication
public class VentasBackendApplication {

	// Método principal que inicia la aplicación Spring Boot.
	public static void main(String[] args) {
		SpringApplication.run(VentasBackendApplication.class, args);
	}

	// Bean para configurar CORS (Cross-Origin Resource Sharing).
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			// Configura los mapeos de CORS.
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**") // Aplica la configuración CORS a todas las rutas bajo /api.
						.allowedOrigins("http://localhost:3000") // Permite solicitudes desde http://localhost:3000.
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite los métodos HTTP especificados.
						.allowedHeaders("*"); // Permite todos los encabezados.
			}
		};
	}

}