import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Container, Box } from '@mui/material';

const ProductoForm = ({ producto, onSave }) => {
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [precio, setPrecio] = useState('');
  const [stock, setStock] = useState('');

  useEffect(() => {
    if (producto) {
      setNombre(producto.nombre);
      setDescripcion(producto.descripcion);
      setPrecio(producto.precio);
      setStock(producto.stock);
    } else {
      setNombre('');
      setDescripcion('');
      setPrecio('');
      setStock('');
    }
  }, [producto]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newProducto = { nombre, descripcion, precio: parseFloat(precio), stock: parseInt(stock) };

    try {
      if (producto) {
        await axios.put(`http://localhost:8080/api/productos/${producto.idProducto}`, newProducto);
      } else {
        await axios.post('http://localhost:8080/api/productos', newProducto);
      }
      onSave();
    } catch (error) {
      console.error('Error saving product:', error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
        <Typography variant="h5" gutterBottom>
          {producto ? 'Editar Producto' : 'Crear Producto'}
        </Typography>
        <TextField
          label="Nombre"
          fullWidth
          margin="normal"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        <TextField
          label="Descripción"
          fullWidth
          margin="normal"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
        />
        <TextField
          label="Precio"
          fullWidth
          margin="normal"
          type="number"
          step="0.01"
          value={precio}
          onChange={(e) => setPrecio(e.target.value)}
          required
        />
        <TextField
          label="Stock"
          fullWidth
          margin="normal"
          type="number"
          value={stock}
          onChange={(e) => setStock(e.target.value)}
          required
        />
        <Button type="submit" variant="contained" color="primary" sx={{ mt: 3, mb: 2 }}>
          {producto ? 'Actualizar' : 'Crear'}
        </Button>
      </Box>
    </Container>
  );
};

export default ProductoForm;
