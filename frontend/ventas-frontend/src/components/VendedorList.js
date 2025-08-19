import React, { useEffect, useState } from 'react';
import axios from 'axios';
import VendedorForm from './VendedorForm';
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

function VendedorList() {
  const [vendedores, setVendedores] = useState([]);
  const [editingVendedor, setEditingVendedor] = useState(null);
  const [openDialog, setOpenDialog] = useState(false);

  useEffect(() => {
    fetchVendedores();
  }, []);

  const fetchVendedores = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/vendedores');
      setVendedores(response.data);
    } catch (error) {
      console.error('There was an error fetching the vendors!', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/vendedores/${id}`);
      fetchVendedores();
    } catch (error) {
      console.error('There was an error deleting the vendor!', error);
    }
  };

  const handleEdit = (vendedor) => {
    setEditingVendedor(vendedor);
    setOpenDialog(true);
  };

  const handleCreate = () => {
    setEditingVendedor(null);
    setOpenDialog(true);
  };

  const handleSave = () => {
    setOpenDialog(false);
    fetchVendedores();
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setEditingVendedor(null);
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Lista de Vendedores
        </Typography>
        <Button variant="contained" color="primary" onClick={handleCreate} sx={{ mb: 2 }}>
          Crear Nuevo Vendedor
        </Button>
        <Paper elevation={3}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nombre</TableCell>
                <TableCell>Email</TableCell>
                <TableCell>Teléfono</TableCell>
                <TableCell>Acciones</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {vendedores.map((vendedor) => (
                <TableRow key={vendedor.idVendedor}>
                  <TableCell>{vendedor.nombre}</TableCell>
                  <TableCell>{vendedor.email}</TableCell>
                  <TableCell>{vendedor.telefono}</TableCell>
                  <TableCell>
                    <Button
                      variant="outlined"
                      color="primary"
                      size="small"
                      startIcon={<EditIcon />}
                      onClick={() => handleEdit(vendedor)}
                      sx={{ mr: 1 }}
                    >
                      Editar
                    </Button>
                    <Button
                      variant="outlined"
                      color="secondary"
                      size="small"
                      startIcon={<DeleteIcon />}
                      onClick={() => handleDelete(vendedor.idVendedor)}
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
          <DialogTitle>{editingVendedor ? 'Editar Vendedor' : 'Crear Nuevo Vendedor'}</DialogTitle>
          <DialogContent>
            <VendedorForm vendedor={editingVendedor} onSave={handleSave} />
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

export default VendedorList;
