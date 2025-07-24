import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import App from './App.jsx'
import BookPage from './pages/BookPage.jsx'; // A página de detalhes que criamos
import './index.css'

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        {/* Rota para a página inicial, que renderiza nosso App principal */}
        <Route path="/" element={<App />} />
        
        {/* Rota para a página de detalhes de um livro específico */}
        <Route path="/books/:id" element={<BookPage />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
