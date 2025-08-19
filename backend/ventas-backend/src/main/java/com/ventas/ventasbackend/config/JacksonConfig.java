package com.ventas.ventasbackend.config;

// Importaciones necesarias para la configuración de Jackson.
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Anotación que indica que esta clase proporciona beans de configuración.
@Configuration
public class JacksonConfig {

    // Define un bean para ObjectMapper, que se utiliza para serializar y deserializar objetos JSON.
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Registra el módulo Jdk8Module para soportar tipos de Java 8 (Optional, etc.).
        objectMapper.registerModule(new Jdk8Module());
        // Registra el módulo JavaTimeModule para soportar tipos de fecha y hora de Java 8 (LocalDate, LocalDateTime, etc.).
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}