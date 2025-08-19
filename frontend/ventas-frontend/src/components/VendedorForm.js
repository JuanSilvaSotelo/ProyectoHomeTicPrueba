import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { TextField, Button, Typography, Container, Box } from '@mui/material';

const VendedorForm = ({ vendedor, onSave }) => {
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [telefono, setTelefono] = useState('');

  useEffect(() => {
    if (vendedor) {
      setNombre(vendedor.nombre);
      setEmail(vendedor.email);
      setTelefono(vendedor.telefono);
    } else {
      setNombre('');
      setEmail('');
      setTelefono('');
    }
  }, [vendedor]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newVendedor = { nombre, email, telefono };

    try {
      if (vendedor) {
        await axios.put(`http://localhost:8080/api/vendedores/${vendedor.idVendedor}`, newVendedor);
      } else {
        await axios.post('http://localhost:8080/api/vendedores', newVendedor);
      }
      onSave();
    } catch (error) {
      console.error('Error saving vendor:', error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
        <Typography variant="h5" gutterBottom>
          {vendedor ? 'Editar Vendedor' : 'Crear Vendedor'}
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
          label="Email"
          fullWidth
          margin="normal"
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <TextField
          label="Teléfosasao"
          fullWidth
          margin="normal"
          value={telefono}
          onChange={(e) => setTelefono(e.target.value)}
        />
        <Button type="submit" variant="contained" color="primary" sx={{ mt: 3, mb: 2 }}>
          {vendedor ? 'Actualizar' : 'Crear'}
        </Button>
      </Box>
    </Container>
  );
};

export default VendedorForm;
