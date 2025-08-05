import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import './AdminAuthorsPage.css';
import { toast } from 'react-toastify';
import CustomConfirmModal from '../components/CustomConfirmModal';
import { confirmAlert } from 'react-confirm-alert';

function AdminAuthorsPage() {
    const [authors, setAuthors] = useState([]);

    const fetchAuthors = () => {
        axios.get(`${import.meta.env.VITE_API_BASE_URL}/authors`)
            .then(response => {
                setAuthors(response.data);
            })
            .catch(error => console.error("Erro ao buscar a Lista de Autores!", error));
    };

    useEffect(() => {
        fetchAuthors();
    }, []);


     const handleDelete = (authorId) => {
        confirmAlert({
            customUI: ({ onClose }) => {
                return (
                    <CustomConfirmModal
                        title="Confirmar Exclusão"
                        message="Tem certeza que deseja desativar este autor? Esta ação não pode ser desfeita."
                        onClose={onClose}
                        onConfirm={async () => {
                            try {
                                await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/authors/${authorId}`);
                                toast.success("Autor desativado com sucesso!");
                                setAuthors(authors.filter(author => author.id !== authorId));
                            } catch (error) {
                                console.error("Erro ao desativar Autor!", error);
                                toast.error(error.response?.data?.message || "Não foi possível desativar o Autor.");
                            }
                            onClose();
                        }}
                    />
                );
            }
        });
    };

    return (
        <div className="admin-page-container">
            <h1>Gerenciamento de Autores</h1>
            <div className="admin-page-header">
                <Link to="/admin/authors/new" className="add-new-button">
                    + Adicionar Novo Autor
                </Link>
            </div>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome Completo</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {authors.map(author => (
                        <tr key={author.id}>
                            <td>{author.id}</td>
                            <td>{author.name + ' ' + author.lastName}</td>
                            <td className="actions-cell">
                                <Link to={`/admin/authors/edit/${author.id}`}>
                                    <button className="edit-button">Editar</button>
                                </Link>
                                <button className="delete-button" onClick={() => handleDelete(author.id)}>Deletar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default AdminAuthorsPage;
