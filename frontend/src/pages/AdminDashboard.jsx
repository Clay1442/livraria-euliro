import React from 'react';

function AdminDashboard() {
    return (
        <div style={{ padding: '40px' }}>
            <h1>Painel do Administrador</h1>
            <p>Bem-vindo à área de gerenciamento da Livraria Euliro.</p>
            <div>gerenciamento de livros</div>
            {/* No futuro, aqui teremos links para "Gerenciar Livros", "Gerenciar Pedidos", etc. */}
        </div>
    );
}

export default AdminDashboard;