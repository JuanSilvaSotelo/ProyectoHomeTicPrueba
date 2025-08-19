package com.ventas.ventasbackend.controller;

// Importaciones necesarias para el controlador de autenticación.
import com.ventas.ventasbackend.model.User;
import com.ventas.ventasbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Anotación que indica que esta clase es un controlador REST.
@RestController
// Anotación para mapear las solicitudes a la ruta /api.
@RequestMapping("/api")
public class AuthController {

    // Inyección de dependencia del repositorio de usuarios.
    @Autowired
    private UserRepository userRepository;

    // Método para registrar un nuevo usuario.
    // Mapea las solicitudes POST a la ruta /api/register.
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Verifica si el nombre de usuario ya está en uso.
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("¡El nombre de usuario ya está en uso!");
        }
        // Guarda el nuevo usuario en la base de datos.
        userRepository.save(user);
        return ResponseEntity.ok("¡Usuario registrado exitosamente!");
    }

    // Método para autenticar un usuario.
    // Mapea las solicitudes POST a la ruta /api/login.
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Busca el usuario por nombre de usuario.
        User existingUser = userRepository.findByUsername(user.getUsername());

        final int MAX_FAILED_ATTEMPTS = 5; // Número máximo de intentos fallidos
        final long LOCK_TIME_DURATION = 5 * 60 * 1000; // 5 minutos en milisegundos

        if (existingUser == null) {
            return ResponseEntity.badRequest().body("¡Nombre de usuario o contraseña inválidos!");
        }

        // Si la cuenta está bloqueada, verifica si el tiempo de bloqueo ha expirado
        if (existingUser.isAccountLocked()) {
            if (System.currentTimeMillis() < existingUser.getLockTime() + LOCK_TIME_DURATION) {
                return ResponseEntity.badRequest().body("¡Cuenta bloqueada! Intente de nuevo más tarde.");
            } else {
                // El tiempo de bloqueo ha expirado, desbloquea la cuenta
                existingUser.setAccountLocked(false);
                existingUser.setFailedLoginAttempts(0);
                existingUser.setLockTime(null);
            }
        }

        // Verifica si la contraseña es correcta
        if (!existingUser.getPassword().equals(user.getPassword())) {
            existingUser.setFailedLoginAttempts(existingUser.getFailedLoginAttempts() + 1);
            if (existingUser.getFailedLoginAttempts() >= MAX_FAILED_ATTEMPTS) {
                existingUser.setAccountLocked(true);
                existingUser.setLockTime(System.currentTimeMillis());
                userRepository.save(existingUser);
                return ResponseEntity.badRequest().body("¡Demasiados intentos fallidos! Su cuenta ha sido bloqueada!");
            }
            userRepository.save(existingUser);
            return ResponseEntity.badRequest().body("¡Nombre de usuario o contraseña inválidos!");
        } else {
            // Contraseña correcta, resetea los intentos fallidos
            existingUser.setFailedLoginAttempts(0);
            existingUser.setAccountLocked(false);
            existingUser.setLockTime(null);
            userRepository.save(existingUser);
            return ResponseEntity.ok("¡Usuario ha iniciado sesión exitosamente!");
        }
    }
}