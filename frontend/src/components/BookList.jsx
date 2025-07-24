import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'; // Importe o Link
import axios from 'axios';
import './BookList.css'; // Esta linha importa o estilo do arquivo CSS

function BookList() {
    const [books, setBooks] = useState([]);

    useEffect(() => {
        const apiUrl = 'http://localhost:8080/books';
        axios.get(apiUrl)
            .then(response => {
                setBooks(response.data);
            })
            .catch(error => {
                console.error('Houve um erro ao buscar os livros:', error);
            });
    }, []);

    return (
        <div className="book-list-container">
            <h2>Nossos Livros</h2>
            <div className="book-grid">
                {books.map(book => (<Link to={`/books/${book.id}`} key={book.id} className="book-card-link">
                    <div className="book-card" key={book.id}>
                        <h3>{book.title}</h3>
                        <p>{book.authors.map(author => author.name + ' ' + author.lastName).join(', ')}</p>
                        <p className="price">R$ {book.price.toFixed(2)}</p>
                    </div>
                </Link>
                ))}
            </div>
        </div>
    );
}

export default BookList;