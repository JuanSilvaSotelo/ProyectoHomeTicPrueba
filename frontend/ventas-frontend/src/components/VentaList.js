import React, { useEffect, useState } from 'react';
import axios from 'axios';
import VentaForm from './VentaForm';
import {
  Container,
  Typography,
  Button,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Paper,
  Box,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

function VentaList() {
  const [ventas, setVentas] = useState([]);
  const [editingVenta, setEditingVenta] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);

  useEffect(() => {
    fetchVentas();
  }, []);

  const fetchVentas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/ventas');
      setVentas(response.data);
    } catch (error) {
      console.error('There was an error fetching the sales!', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/ventas/${id}`);
      fetchVentas();
    } catch (error) {
      console.error('There was an error deleting the sale!', error);
    }
  };

  const handleEdit = (venta) => {
    setEditingVenta(venta);
    setOpenDialog(true);
  };

  const handleCreate = () => {
    setEditingVenta(null);
    setOpenDialog(true);
  };

  const handleSave = () => {
    setOpenDialog(false);
    fetchVentas();
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingVenta(null);
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Lista de Ventas
        </Typography>
        <Button variant="contained" color="primary" onClick={handleCreate} sx={{ mb: 2 }}>
          Crear Nueva Venta
        </Button>
        <Paper elevation={3}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Fecha</TableCell>
                <TableCell>Total</TableCell>
                <TableCell>Producto</TableCell>
                <TableCell>Vendedor</TableCell>
                <TableCell>Acciones</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {ventas.map((venta) => (
                <TableRow key={venta.idVenta}>
                  <TableCell>
                    {new Date(
                      venta.fechaVenta[0],
                      venta.fechaVenta[1] - 1,
                      venta.fechaVenta[2],
                      venta.fechaVenta[3],
                      venta.fechaVenta[4],
                      venta.fechaVenta[5]
                    ).toLocaleString()}
                  </TableCell>
                  <TableCell>{venta.total}</TableCell>
                  <TableCell>{venta.producto.nombre}</TableCell>
                  <TableCell>{venta.vendedor.nombre}</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      color="primary"
                      size="small"
                      startIcon={<EditIcon />}
                      onClick={() => handleEdit(venta)}
                      sx={{ mr: 1 }}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="outlined"
                      color="secondary"
                      size="small"
                      startIcon={<DeleteIcon />}
                      onClick={() => handleDelete(venta.idVenta)}
                    >
                      Eliminar
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>

        <Dialog open={openDialog} onClose={handleCloseDialog}>
          <DialogTitle>{editingVenta ? 'Editar Venta' : 'Crear Nueva Venta'}</DialogTitle>
          <DialogContent>
            <VentaForm venta={editingVenta} onSave={handleSave} />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleCloseDialog} color="primary">
              Cancelar
            </Button>
          </DialogActions>
        </Dialog>
      </Box>
    </Container>
  );
}

export default VentaList;
