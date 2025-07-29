import React from 'react';
import { useAuth } from '../contexts/useAuth';

function MyAccountPage() {
    const { user } = useAuth();

    if (!user) {        
        return <div>Carregando informações do usuário...</div>;
    }

    return (
        <div style={{ padding: '40px' }}>
            <h2>Minha Conta</h2>
            <p>Bem-vindo(a) de volta, {user.name}!</p>
            
            <div>
                <h3>Seus Dados:</h3>
                <p><strong>Nome:</strong> {user.name}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Data de Nascimento:</strong> {user.birthDate}</p>
            </div>

            {/* No futuro, aqui listaremos o histórico de pedidos do usuário */}
        </div>
    );
}

export default MyAccountPage;