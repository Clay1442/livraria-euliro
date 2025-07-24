// src/pages/BookPage.jsx

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './BookPage.css'; // Esta linha importa o estilo do arquivo CSS

function BookPage() {
    const { id } = useParams(); // Pega o 'id' da URL (ex: /books/1)
    const [book, setBook] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const apiUrl = `http://localhost:8080/books/${id}`;
        axios.get(apiUrl)
            .then(response => {
                setBook(response.data);
                setLoading(false);
            })
            .catch(error => {
                setError('Erro ao buscar os detalhes do livro.');
                setLoading(false);
                console.error(error);
            });
    }, [id]); // O efeito roda novamente se o ID na URL mudar

    if (loading) return <div>Carregando...</div>;
    if (error) return <div>{error}</div>;
    if (!book) return <div>Livro não encontrado.</div>;

    return (
        <div className="book-page-container"> 
            <h1>{book.title}</h1>
            <h2>por {book.authors.map(author => author.name + ' ' + author.lastName).join(', ')}</h2>
            <img src={book.imageUrl} alt={book.title} width="100"/> 
            <p><strong>Descrição:</strong> {book.description}</p>
            <p className="price"><strong>Preço:</strong> R$ {book.price.toFixed(2)}</p>
            <p><strong>Estoque:</strong> {book.stock} unidades</p>
            {/* Aqui no futuro teríamos um botão "Adicionar ao Carrinho" */}
        </div>
    );
}

export default BookPage;