package com.ventas.ventasbackend.stripe;

// Importaciones necesarias para interactuar con la API de Stripe.
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

// Anotación que indica que esta clase es un componente de Spring.
@Component
public class StripeClient {

    // Inyecta la clave secreta de Stripe desde las propiedades de la aplicación.
    @Value("${stripe.secret.key}")
    private String secretKey;

    // Método que se ejecuta después de la construcción del bean para inicializar la clave API de Stripe.
    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    // Crea un nuevo cliente en Stripe.
    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    // Obtiene un cliente existente de Stripe por su ID.
    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    // Realiza un cargo a una nueva tarjeta utilizando un token.
    public Charge chargeNewCard(String token, double amount) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100)); // El monto se especifica en centavos.
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

    // Realiza un cargo a una tarjeta existente de un cliente.
    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}