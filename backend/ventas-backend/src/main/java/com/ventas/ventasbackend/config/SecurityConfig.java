package com.ventas.ventasbackend.config;

// Importaciones necesarias para la configuración de seguridad.
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

// Anotación que indica que esta clase proporciona beans de configuración.
@Configuration
// Anotación para habilitar la seguridad web de Spring Security.
@EnableWebSecurity
public class SecurityConfig {

    // Logger para registrar mensajes de la configuración de seguridad.
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    // Define un bean para la cadena de filtros de seguridad.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configurando la cadena de filtros de seguridad.");
        http
                // Deshabilita la protección CSRF (Cross-Site Request Forgery).
                .csrf(AbstractHttpConfigurer::disable)
                // Configura las reglas de autorización de solicitudes HTTP.
                .authorizeHttpRequests(auth -> {
                    logger.info("Aplicando reglas de autorización.");
                    // Permite todas las solicitudes sin autenticación.
                    auth.anyRequest().permitAll();
                })
                // Añade un filtro personalizado antes del filtro de autenticación de nombre de usuario y contraseña.
                .addFilterBefore(new RequestLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RateLimitingFilter(), RequestLoggingFilter.class);
        logger.info("Cadena de filtros de seguridad configurada.");
        return http.build();
    }

    // Clase interna estática para un filtro de registro de solicitudes.
    private static class RequestLoggingFilter implements Filter {
        // Logger para registrar mensajes del filtro.
        private static final Logger filterLogger = LoggerFactory.getLogger(RequestLoggingFilter.class);

        @Override
        // Método principal del filtro que se ejecuta para cada solicitud.
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            // Registra el método HTTP y la URI de la solicitud entrante.
            filterLogger.info("Solicitud entrante: {} {}", req.getMethod(), req.getRequestURI());
            // Continúa con la cadena de filtros.
            chain.doFilter(request, response);
        }
    }

    // Clase interna estática para un filtro de limitación de tasas (Rate Limiting).
    private static class RateLimitingFilter implements Filter {
        private static final Logger filterLogger = LoggerFactory.getLogger(RateLimitingFilter.class);
        private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
        private final Map<String, Long> lastRequestTimes = new ConcurrentHashMap<>();
        private final int MAX_REQUESTS = 10; // Máximo de solicitudes permitidas en un intervalo
        private final long TIME_INTERVAL_SECONDS = 60; // Intervalo de tiempo en segundos

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            String clientIp = request.getRemoteAddr();

            long currentTime = System.currentTimeMillis();

            // Eliminar entradas antiguas
            lastRequestTimes.entrySet().removeIf(entry ->
                    currentTime - entry.getValue() > TimeUnit.SECONDS.toMillis(TIME_INTERVAL_SECONDS));
            requestCounts.entrySet().removeIf(entry ->
                    currentTime - lastRequestTimes.getOrDefault(entry.getKey(), 0L) > TimeUnit.SECONDS.toMillis(TIME_INTERVAL_SECONDS));

            requestCounts.put(clientIp, requestCounts.getOrDefault(clientIp, 0L) + 1);
            lastRequestTimes.put(clientIp, currentTime);

            if (requestCounts.get(clientIp) > MAX_REQUESTS) {
                filterLogger.warn("Solicitud bloqueada por límite de tasa para IP: {}", clientIp);
                ((HttpServletResponse) response).setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Demasiadas solicitudes. Por favor, intente de nuevo más tarde.");
                return;
            }
            chain.doFilter(request, response);
        }
    }
}