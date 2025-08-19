package com.ventas.ventasbackend.model;

// Importaciones necesarias para la definición de la entidad Venta.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;

import java.time.LocalDateTime;
import java.math.BigDecimal;

// Anotación que marca esta clase como una entidad JPA.
@Entity
// Anotación para ignorar propiedades específicas durante la serialización/deserialización JSON,
// útil para evitar errores con proxies de Hibernate.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Venta {
    // Define la clave primaria de la entidad.
    @Id
    // Configura la estrategia de generación de valores para la clave primaria (identidad).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Mapea la propiedad a la columna 'VentaID' en la base de datos.
    @Column(name = "VentaID")
    private Integer idVenta;
    // Mapea la propiedad a la columna 'FechaVenta' con tipo DATETIME en la base de datos.
    @Column(name = "FechaVenta", columnDefinition = "DATETIME")
    private LocalDateTime fechaVenta;
    // Propiedad para el total de la venta.
    private BigDecimal total;
    // Propiedad para la cantidad de productos en la venta.
    private Integer cantidad;

    // Define una relación muchos a uno con la entidad Producto.
    @ManyToOne
    // Especifica la columna de unión (clave foránea) en la tabla de Venta.
    @JoinColumn(name = "ProductoID")
    private Producto producto;

    // Define una relación muchos a uno con la entidad Vendedor.
    @ManyToOne
    // Especifica la columna de unión (clave foránea) en la tabla de Venta.
    @JoinColumn(name = "VendedorID")
    private Vendedor vendedor;

    // Getters y Setters para acceder y modificar las propiedades de la entidad Venta.

    // Obtiene el ID de la venta.
    public Integer getIdVenta() {
        return idVenta;
    }

    // Establece el ID de la venta.
    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    // Obtiene la fecha y hora de la venta.
    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    // Establece la fecha y hora de la venta.
    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    // Obtiene el total de la venta.
    public BigDecimal getTotal() {
        return total;
    }

    // Establece el total de la venta.
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // Obtiene la cantidad de productos en la venta.
    public Integer getCantidad() {
        return cantidad;
    }

    // Establece la cantidad de productos en la venta.
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    // Obtiene el objeto Producto asociado a esta venta.
    public Producto getProducto() {
        return producto;
    }

    // Establece el objeto Producto asociado a esta venta.
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // Obtiene el objeto Vendedor asociado a esta venta.
    public Vendedor getVendedor() {
        return vendedor;
    }

    // Establece el objeto Vendedor asociado a esta venta.
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
}