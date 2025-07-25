import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useCart } from '../contexts/useCart';
import './BookPage.css';

function BookPage() {
    const { id } = useParams(); 
    const [book, setBook] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    
    const { addItemToCart } = useCart();

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
    }, [id]);
   const handleAddToCart = () => {
        if (book) {
           
            const userId = 1; 
            const quantity = 1; 

            addItemToCart(userId, book.id, quantity);
        }
    };


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
             <button onClick={handleAddToCart} className="add-to-cart-button">Adicionar ao Carrinho</button>
        </div>
    );
}

export default BookPage;