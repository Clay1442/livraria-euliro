import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import App from './App.jsx'
import BookList from './components/BookList.jsx';  
import BookPage from './pages/BookPage.jsx'; 
import './index.css'
import { CartProvider } from './contexts/CartProvider';
import CartPage from './pages/CartPage.jsx'; 
import { AuthProvider } from './contexts/AuthProvider'; // Importe o AuthProvider
import LoginPage from './pages/LoginPage.jsx';
import RegisterPage from './pages/RegisterPage.jsx';
import ProtectedRoute from './components/ProtectedRoute'; 
import MyAccountPage from './pages/MyAccountPage';  
import OrderHistoryPage from './pages/OrderHistoryPage.jsx';
import OrderConfirmationPage from './pages/OrderConfirmationPage';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <AuthProvider>
    <CartProvider>
    <BrowserRouter>
      <Routes>
        {/* Rota para a página inicial, que renderiza nosso App principal */}
        <Route path="/" element={<App />}>

        {/* Se o caminho for exatamente "/", mostre o BookList */}
        <Route index element={<BookList />} /> 
        
        {/* Rota para a página de detalhes de um livro específico */}
        <Route path="/books/:id" element={<BookPage />} />

        <Route path="carts" element={<CartPage />} />

        <Route path="login" element={<LoginPage />} />

        <Route path="register" element={<RegisterPage />} />
        
        <Route path="order-confirmation/:orderId" element={<OrderConfirmationPage />} />

  <Route element={<ProtectedRoute />}>
            <Route path="my-account" element={<MyAccountPage />} />
            <Route path="my-orders" element={<OrderHistoryPage />} />
        </Route>

        </Route>
      </Routes>
    </BrowserRouter>
    </CartProvider>
    </AuthProvider>
  </React.StrictMode>
);
