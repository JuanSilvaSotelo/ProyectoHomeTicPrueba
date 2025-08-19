package com.ventas.ventasbackend;

import com.ventas.ventasbackend.controller.AuthController;
import com.ventas.ventasbackend.model.User;
import com.ventas.ventasbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123"); // La contraseña no está codificada en el modelo
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password123");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("¡Usuario ha iniciado sesión exitosamente!", response.getBody());
        assertEquals(0, user.getFailedLoginAttempts());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoginUser_UserNotFound() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        User loginUser = new User();
        loginUser.setUsername("nonexistentuser");
        loginUser.setPassword("password123");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("¡Nombre de usuario o contraseña inválidos!", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginUser_IncorrectPassword_AttemptsIncremented() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("wrongPassword");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("¡Nombre de usuario o contraseña inválidos!", response.getBody());
        assertEquals(1, user.getFailedLoginAttempts());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoginUser_AccountLocked() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setAccountLocked(true);
        user.setLockTime(System.currentTimeMillis()); // Locked now

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password123");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("¡Cuenta bloqueada! Intente de nuevo más tarde.", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginUser_AccountLocked_LockTimeExpired() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setAccountLocked(true);
        user.setLockTime(System.currentTimeMillis() - (5 * 60 * 1000) - 1000); // Locked 5 minutes and 1 second ago (expired)
        user.setFailedLoginAttempts(5); // Max attempts reached before lock

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("password123");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("¡Usuario ha iniciado sesión exitosamente!", response.getBody());
        assertEquals(0, user.getFailedLoginAttempts());
        assertEquals(false, user.isAccountLocked());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testLoginUser_AccountBecomesLocked() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(4); // 4 failed attempts, 1 more will lock

        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("wrongPassword");
        ResponseEntity<String> response = authController.loginUser(loginUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("¡Demasiados intentos fallidos! Su cuenta ha sido bloqueada!", response.getBody());
        assertEquals(5, user.getFailedLoginAttempts());
        assertEquals(true, user.isAccountLocked());
        verify(userRepository, times(1)).save(user);
    }
}