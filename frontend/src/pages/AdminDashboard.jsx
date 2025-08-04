import React from 'react';
import { Link } from 'react-router-dom';
import './AdminDashboard.css'; 

function AdminDashboard() {
    return (
        <div className="admin-dashboard">
            <h1>Painel do Administrador</h1>
            <p>Bem-vindo à área de gerenciamento da Livraria Euliro.</p>
            <nav className="dashboard-nav">
                <Link to="/admin/books" className="nav-card">
                    Gerenciar Livros
                </Link>
                <Link to="/admin/authors" className="nav-card">
                    Gerenciar Autores
                </Link>
                <Link to="/admin/users" className="nav-card">
                    Gerenciar Usuários
                </Link>
            </nav>
        </div>
    );
}

export default AdminDashboard;