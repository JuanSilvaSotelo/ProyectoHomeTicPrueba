import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ProductoList from './components/ProductoList';
import ProductoForm from './components/ProductoForm';
import VendedorList from './components/VendedorList';
import VendedorForm from './components/VendedorForm';
import VentaList from './components/VentaList';
import VentaForm from './components/VentaForm';
import Login from './components/Login';
import Payment from './components/Payment';
import Register from './components/Register';

import { AppBar, Toolbar, Typography, Button, Container } from '@mui/material';

function App() {
  return (
    <Router>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Ventas App
          </Typography>
          <Button color="inherit" component={Link} to="/productos">
            Productos
          </Button>
          <Button color="inherit" component={Link} to="/vendedores">
            Vendedores
          </Button>
          <Button color="inherit" component={Link} to="/ventas">
            Ventas
          </Button>
          <Button color="inherit" component={Link} to="/login">
            Login
          </Button>
          <Button color="inherit" component={Link} to="/register">
            Register
          </Button>
          <Button color="inherit" component={Link} to="/payment">
            Payment
          </Button>
        </Toolbar>
      </AppBar>
      <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
        <Routes>
          <Route path="/productos" element={<ProductoList />} />
          <Route
            path="/productos/new"
            element={<ProductoForm onSave={() => (window.location.href = '/productos')} />}
          />
          <Route
            path="/productos/edit/:id"
            element={<ProductoForm onSave={() => (window.location.href = '/productos')} />}
          />
          <Route path="/vendedores" element={<VendedorList />} />
          <Route path="/vendedores/new" element={<VendedorForm />} />
          <Route path="/vendedores/edit/:id" element={<VendedorForm />} />
          <Route path="/ventas" element={<VentaList />} />
          <Route path="/ventas/new" element={<VentaForm />} />
          <Route path="/ventas/edit/:id" element={<VentaForm />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/payment" element={<Payment amount={100} />} />
          <Route path="/" element={<ProductoList />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
