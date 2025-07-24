// src/App.jsx

import React from 'react';
import BookList from './components/BookList'; // Importa nosso novo componente
import './App.css'; // Mantenha o CSS principal se quiser estilos globais

function App() {
  return (
      <div className="App">
        <header className="App-header">
          <h1>Livraria Euliro</h1>
        </header>
        <main>
          <BookList /> {/* Renderiza o componente da lista de livros */}
        </main>
      </div>
  );
}

export default App;