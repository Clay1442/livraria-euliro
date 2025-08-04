import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { toast } from 'react-toastify';
import CustomConfirmModal from '../components/CustomConfirmModal';
import { confirmAlert } from 'react-confirm-alert'; 
import "./AdminBooksPage.css";


function AdminBooksPage() {
    const [books, setBooks] = useState([]);

    const fetchBooks = () => {
        axios.get('http://localhost:8080/books')
            .then(response => {
                setBooks(response.data);
            })
            .catch(error => console.error("Erro ao buscar livros!", error));
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
                    await axios.delete(`http://localhost:8080/books/${bookId}`);
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

    return (
        <div className="table-container"> 
        <div className="admin-page-container">
            <h1>Gerenciamento de Livros</h1>
            <div className="admin-page-header">
                <Link to="/admin/books/new" className="add-new-button">
                    + Adicionar Novo Livro
                </Link>
            </div>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Título</th>
                        <th>Preço</th>
                        <th>Estoque</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map(book => (
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.title}</td>
                            <td>R$ {book.price.toFixed(2)}</td>
                            <td>{book.stock}</td>
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

