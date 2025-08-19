package com.ventas.ventasbackend.controller;

// Importaciones necesarias para el controlador de pagos.
import com.stripe.model.Charge;
import com.ventas.ventasbackend.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Anotación que indica que esta clase es un controlador REST.
@RestController
// Anotación para permitir solicitudes de cualquier origen (CORS).
@CrossOrigin("*")
// Anotación para mapear las solicitudes a la ruta /api/payment.
@RequestMapping("/api/payment")
public class PaymentController {

    // Cliente de Stripe para interactuar con la API de pagos.
    private StripeClient stripeClient;

    // Constructor que inyecta la dependencia de StripeClient.
    @Autowired
    PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    // Método para procesar un cargo de tarjeta.
    // Mapea las solicitudes POST a la ruta /charge.
    @PostMapping("/charge")
    public Charge chargeCard(
            // Encabezado de la solicitud que contiene el token de la tarjeta.
            @RequestHeader(value="token") String token,
            // Encabezado de la solicitud que contiene el monto a cobrar.
            @RequestHeader(value="amount") Double amount) throws Exception {
        // Llama al método chargeNewCard del cliente de Stripe para procesar el pago.
        return this.stripeClient.chargeNewCard(token, amount);
    }
}