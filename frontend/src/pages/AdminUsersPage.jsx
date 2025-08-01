import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom"; 

function AdminUsersPage() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get('http://localhost:8080/users')
            .then(response => {
                setUsers(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error("Erro ao buscar usuários!", error);
                alert('Você não tem permissão para acessar esta página.');
                setLoading(false);
            });
    }, []);

    const handleDelete = async (userId) => {
        if (window.confirm("Tem certeza que deseja desativar este usuário?")) {
            try {
                await axios.delete(`http://localhost:8080/users/${userId}`);
                alert("Usuário desativado com sucesso!");
                setUsers(users.filter(user => user.id !== userId));
            } catch (error) {
                console.error("Erro ao desativar usuario!", error);
                alert("Não foi possível desativar o usuário.");
            }
        }
    };

    if (loading) return <div>Carregando usuários...</div>;

    return (
        <div style={{ padding: '40px' }}>
            <h1>Gerenciamento de Usuários</h1>
            <table border="1" style={{ width: '100%', marginTop: '20px', borderCollapse: 'collapse' }}>
                <thead>
                    <tr>
                        <th style={{padding: '8px'}}>ID</th>
                        <th style={{padding: '8px'}}>Nome</th>
                        <th style={{padding: '8px'}}>Email</th>
                        <th style={{padding: '8px'}}>Papéis (Roles)</th>
                        <th style={{padding: '8px'}}>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(user => (
                        <tr key={user.id}>
                            <td style={{padding: '8px'}}>{user.id}</td>
                            <td style={{padding: '8px'}}>{user.name}</td>
                            <td style={{padding: '8px'}}>{user.email}</td>
                            <td style={{padding: '8px'}}>{user.roles.join(', ')}</td>
                            <td style={{padding: '8px'}}>
                                <Link to={`/admin/users/edit/${user.id}`}>
                                    <button>Editar</button>
                                </Link>
                                <button onClick={() => handleDelete(user.id)}>Desativar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminUsersPage;