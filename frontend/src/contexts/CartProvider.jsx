import React, { useState } from 'react';
import axios from 'axios';
import { CartContext } from './CartContext'; 
export function CartProvider({ children }) {
    const [cart, setCart] = useState(null);

     const fetchCart = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8080/carts/user/${userId}`);
            setCart(response.data);
            return response.data;
        } catch (error) {
            console.error("Erro ao buscar o carrinho:", error);
        }
    };

    const addItemToCart = async (userId, bookId, quantity) => {
        try {
            const response = await axios.post(`http://localhost:8080/carts/${userId}/items`, {
                bookId,
                quantity
            });
            setCart(response.data);
            alert('Livro adicionado ao carrinho!');
        } catch (error) {
            console.error("Erro ao adicionar item ao carrinho:", error);
            alert('Não foi possível adicionar o livro ao carrinho.');
        }
    };
    
    const removeItemFromCart = async (userId, itemId) => {
             try {
            const response = await axios.delete(`http://localhost:8080/carts/${userId}/items/${itemId}`);
            setCart(response.data);
            alert('Item removido do carrinho!');
        } catch (error) {
               console.error("Erro ao remover item do carrinho:", error); 
               alert('Não foi possível remover o item do carrinho.');
     }
    };

     const updateItemQuantity = async (userId, itemId, quantity) => {
        try {
            const response = await axios.patch(`http://localhost:8080/carts/user/${userId}/items/${itemId}`, {
                quantity // Envia o JSON { "quantity": X }
            });
            setCart(response.data); // Atualiza o estado com o carrinho retornado
        } catch (error) {
            console.error("Erro ao atualizar a quantidade:", error);
            alert('Não foi possível atualizar a quantidade.');
        }
    };



    const value = { cart, fetchCart, addItemToCart, removeItemFromCart, updateItemQuantity};

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
}