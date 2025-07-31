import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

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


    const handleDelete = async (bookId) => {
        if (window.confirm("Tem certeza que deseja deletar este livro?")) {
            try {
                await axios.delete(`http://localhost:8080/books/${bookId}`)
                alert("Livro deletado com sucesso!");
                //Remover da lista localmente
                setBooks(books.filter(book => book.id !== bookId));
            }
            catch (error) {
                console.error("Erro ao deletar livro!", error);
                alert("Não foi possível deletar o livro.");
            }
        }
    };


    return (
        
        <div style={{ padding: '40px' }}>
            <h1>Gerenciamento de Livros</h1>
             <Link to="/admin/books/new">
                    <button style={{ padding: '10px 15px', fontSize: '1em' }}>+ Adicionar Novo Livro</button>
                </Link>
            <table border="1" style={{ width: '100%', marginTop: '20px' }}>
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
                            <td>
                                <Link to={`/admin/books/edit/${book.id}`}>
                                <button>Editar</button>
                                </Link>
                                <button onClick={() => handleDelete(book.id)}>Deletar</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

}

export default AdminBooksPage;




