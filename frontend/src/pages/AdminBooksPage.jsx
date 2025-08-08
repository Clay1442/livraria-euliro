import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from 'react-toastify';
import CustomConfirmModal from '../components/CustomConfirmModal';
import { confirmAlert } from 'react-confirm-alert';
import "./AdminBooksPage.css";


function AdminBooksPage() {
    const [books, setBooks] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);

    const fetchBooks = () => {
        axios.get(`${import.meta.env.VITE_API_BASE_URL}/books`)
            .then(response => {
                setBooks(response.data);
                setLoading(false);
            })
            .catch(error => console.error("Erro ao buscar livros!", error));
            setLoading(false);
    };

    useEffect(() => {
        fetchBooks();
    }, []);



    const handleDelete = (bookId) => {
        confirmAlert({
            title: 'Confirmar Exclusão',
            message: 'Tem certeza que deseja deletar este livro?',
            customUI: ({ onClose }) => {
                return (
                    <CustomConfirmModal
                        title="Confirmar Exclusão"
                        message="Tem certeza que deseja deletar este livro?"
                        onClose={onClose}
                        onConfirm={async () => {
                            try {
                                await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/books/${bookId}`);
                                toast.success("Livro deletado com sucesso!");
                                setBooks(books.filter(book => book.id !== bookId));
                            } catch (error) {
                                console.error("Erro ao deletar livro!", error);
                                toast.error("Não foi possível deletar o livro.");
                            }
                            onClose(); // Fechar o modal após a ação
                        }}
                    />
                );
            }
        });
    };


    const filteredBooks = books.filter(book =>
        book.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    if (loading) return <div>Carregando livros...</div>;


    return (
        <div className="table-container">
            <div className="admin-page-container">
                <h1>Gerenciamento de Livros</h1>
                <div className="admin-page-header">
                    <Link to="/admin/books/new" className="add-new-button">
                        + Adicionar Novo Livro
                    </Link>
                </div>

                <div className="search-bar-container">
                    <input
                        type="text"
                        placeholder="Pesquisar por título..."
                        className="search-input"
                        value={searchTerm}
                        onChange={e => setSearchTerm(e.target.value)}
                    />
                </div>

                <table className="admin-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Título</th>
                            <th className="hide-on-mobile">Preço</th>
                            <th className="hide-on-mobile">Estoque</th>
                            <th>Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredBooks.map(book => (
                            <tr key={book.id} className="book-row">
                                <td data-label="ID">{book.id}</td>
                                <td td data-label="Título">{book.title}</td>
                                <td className="hide-on-mobile">R$ {book.price.toFixed(2)}</td>
                                <td className="hide-on-mobile">{book.stock}</td>
                                <td className="actions-cell">
                                    <Link to={`/admin/books/edit/${book.id}`}>
                                        <button className="edit-button">Editar</button>
                                    </Link>
                                    <button className="delete-button" onClick={() => handleDelete(book.id)}>Deletar</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default AdminBooksPage;

