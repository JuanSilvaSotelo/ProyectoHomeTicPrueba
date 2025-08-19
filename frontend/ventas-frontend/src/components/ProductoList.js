import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import {
  Container,
  Typography,
  List,
  ListItem,
  ListItemText,
  Button,
  Box,
  Paper,
} from '@mui/material';

function ProductoList() {
  const [productos, setProductos] = useState([]);

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/productos')
      .then((response) => {
        setProductos(response.data);
      })
      .catch((error) => {
        console.error('There was an error fetching the products!', error);
      });
  }, []);

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Lista de Productos
        </Typography>
        <Button
          variant="contained"
          color="primary"
          component={Link}
          to="/productos/new"
          sx={{ mb: 2 }}
        >
          Crear Nuevo Producto
        </Button>
        <Paper elevation={3} sx={{ p: 2 }}>
          <List>
            {productos.map((producto) => (
              <ListItem key={producto.idProducto}>
                <ListItemText
                  primary={`${producto.nombre} - $${producto.precio}`}
                  secondary={`Descripción: ${producto.descripcion}, Stock: ${producto.stock}`}
                />
              </ListItem>
            ))}
          </List>
        </Paper>
      </Box>
    </Container>
  );
}

export default ProductoList;
