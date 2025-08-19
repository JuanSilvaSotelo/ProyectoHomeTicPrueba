import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  TextField,
  Button,
  Typography,
  Container,
  Box,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
} from '@mui/material';

const VentaForm = ({ venta, onSave }) => {
  const [fecha, setFecha] = useState('');
  const [total, setTotal] = useState('');
  const [idProducto, setIdProducto] = useState('');
  const [idVendedor, setIdVendedor] = useState('');
  const [productos, setProductos] = useState([]);
  const [vendedores, setVendedores] = useState([]);

  useEffect(() => {
    if (venta) {
      setFecha(venta.fecha);
      setTotal(venta.total);
      setIdProducto(venta.producto ? venta.producto.idProducto : '');
      setIdVendedor(venta.vendedor ? venta.vendedor.idVendedor : '');
    } else {
      setFecha('');
      setTotal('');
      setIdProducto('');
      setIdVendedor('');
    }
    fetchProductos();
    fetchVendedores();
  }, [venta]);

  const fetchProductos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/productos');
      setProductos(response.data);
    } catch (error) {
      console.error('Error fetching products:', error);
    }
  };

  const fetchVendedores = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/vendedores');
      setVendedores(response.data);
    } catch (error) {
      console.error('Error fetching vendors:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newVenta = {
      fecha,
      total: parseFloat(total),
      producto: { idProducto: parseInt(idProducto) },
      vendedor: { idVendedor: parseInt(idVendedor) },
    };

    try {
      if (venta) {
        await axios.put(`http://localhost:8080/api/ventas/${venta.idVenta}`, newVenta);
      } else {
        await axios.post('http://localhost:8080/api/ventas', newVenta);
      }
      onSave();
    } catch (error) {
      console.error('Error saving venta:', error);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
        <Typography variant="h5" gutterBottom>
          {venta ? 'Editar Venta' : 'Crear Venta'}
        </Typography>
        <TextField
          label="Fecha"
          fullWidth
          margin="normal"
          type="date"
          value={fecha}
          onChange={(e) => setFecha(e.target.value)}
          InputLabelProps={{
            shrink: true,
          }}
          required
        />
        <TextField
          label="Total"
          fullWidth
          margin="normal"
          type="number"
          value={total}
          onChange={(e) => setTotal(e.target.value)}
          required
        />
        <FormControl fullWidth margin="normal" required>
          <InputLabel>Producto</InputLabel>
          <Select
            value={idProducto}
            onChange={(e) => setIdProducto(e.target.value)}
            label="Producto"
          >
            <MenuItem value="">Seleccione un producto</MenuItem>
            {productos.map((producto) => (
              <MenuItem key={producto.idProducto} value={producto.idProducto}>
                {producto.nombre}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth margin="normal" required>
          <InputLabel>Vendedor</InputLabel>
          <Select
            value={idVendedor}
            onChange={(e) => setIdVendedor(e.target.value)}
            label="Vendedor"
          >
            <MenuItem value="">Seleccione un vendedor</MenuItem>
            {vendedores.map((vendedor) => (
              <MenuItem key={vendedor.idVendedor} value={vendedor.idVendedor}>
                {vendedor.nombre}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Button type="submit" variant="contained" color="primary" sx={{ mt: 3, mb: 2 }}>
          {venta ? 'Actualizar' : 'Crear'}
        </Button>
      </Box>
    </Container>
  );
};

export default VentaForm;
