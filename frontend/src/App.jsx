import React from 'react';
import { Outlet } from 'react-router-dom'; // Importe o Outlet
import Header from './components/Header'; // Importe nosso novo Header
import './App.css'; // Mantenha o CSS principal se quiser estilos globais


function App() {
  return (
      <div className="App">
        <Header />
        <main>
          <Outlet />  
        </main>
      </div>
  );
}

export default App;
