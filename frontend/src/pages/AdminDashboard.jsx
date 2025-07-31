import React from 'react';
import { Link } from 'react-router-dom'; 

function AdminDashboard() {
    return (
        <div style={{ padding: '40px' }}>
            <h1>Painel do Administrador</h1>
            <p>Bem-vindo à área de gerenciamento da Livraria Euliro.</p>
            <nav>
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    <li style={{ margin: '10px 0' }}>
                        <Link to="/admin/books">Gerenciar Livros</Link>
                    </li>
                    <li style={{ margin: '10px 0' }}>
                        <Link to="/admin/authors">Gerenciar Autores</Link>
                    </li>
                    <li style={{ margin: '10px 0' }}>
                        <Link to="/admin/users">Gerenciar Usuários</Link> 
                    </li>
                </ul>
            </nav>
        </div>
    );
}

export default AdminDashboard;