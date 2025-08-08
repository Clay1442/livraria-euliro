import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from 'react-toastify';
import CustomConfirmModal from '../components/CustomConfirmModal';
import { confirmAlert } from 'react-confirm-alert';
import './AdminUsersPage.css';

function AdminUsersPage() {
    const [users, setUsers] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_API_BASE_URL}/users`)
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
        confirmAlert({
            customUI: ({ onClose }) => {
                return (
                    <CustomConfirmModal
                        title="Confirmar Exclusão"
                        message="Tem certeza que deseja desativar este usuário? Esta ação não pode ser desfeita."
                        onClose={onClose}
                        onConfirm={async () => {
                            try {
                                await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/users/${userId}`);
                                toast.success("Usuário desativado com sucesso!");
                                setUsers(users.filter(user => user.id !== userId));
                            } catch (error) {
                                console.error("Erro ao desativar usuário!", error);
                                toast.error(error.response?.data?.message || "Não foi possível desativar o usuário.");
                            }
                            onClose();
                        }}
                    />
                );
            }
        });
    }

    const filteredUsers = users.filter(user =>
        user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        user.email.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) return <div>Carregando usuários...</div>;

    return (
        <div className="admin-page-container">
            <div className="admin-page-header">
                <h1>Gerenciamento de Usuários</h1>
            </div>

            <div className="search-bar-container">
                <input
                    type="text"
                    placeholder="Pesquisar por Nome..."
                    className="search-input"
                    value={searchTerm}
                    onChange={e => setSearchTerm(e.target.value)}
                />
            </div>

            <table className="admin-table">
                <thead>
                    <tr>
                        <th className="hide-on-mobile">ID</th>
                        <th className="hide-on-mobile">Nome</th>
                        <th>Email</th>
                        <th>Papéis (Roles)</th>
                        <th >Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredUsers.map(user => (
                        <tr key={user.id}>
                            <td className="hide-on-mobile">{user.id}</td>
                            <td className="hide-on-mobile">{user.name}</td>
                            <td data-label="Email">{user.email}</td>
                            <td data-label="Papéis">{user.roles.join(', ')}</td>
                            <td className="actions-cell">
                                <Link to={`/admin/users/edit/${user.id}`}>
                                    <button className="edit-button">Editar</button>
                                </Link>
                                <button className="delete-button" onClick={() => handleDelete(user.id)}>Desativar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminUsersPage;