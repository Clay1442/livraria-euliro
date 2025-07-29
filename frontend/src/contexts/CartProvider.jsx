import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { CartContext } from './CartContext';
import { useAuth } from './useAuth';

export function CartProvider({ children }) {
    const [cart, setCart] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { user, isAuthenticated } = useAuth();

    useEffect(() => {
        const loadCart = async () => {
            if (isAuthenticated && user) {
                setIsLoading(true); // Começa a carregar
                await fetchCart(user.id);
                setIsLoading(false); // Termina de carregar
            }
            else {
                setCart(null);
                setIsLoading(false); // Termina de carregar (sem carrinho)
            }
        };
        loadCart();
    }, [isAuthenticated, user]);

    const fetchCart = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8080/carts/users/${userId}`);
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


    const createOrderFromCart = async (userId) => {
        try {
            const response = await axios.post(`http://localhost:8080/orders/from-cart/user/${userId}`);

            fetchCart(userId);
            alert('Pedido criado com sucesso!');
            return response.data;
        } catch (error) {
            console.error("Erro ao finalizar a compra:", error);
            alert('Não foi possível finalizar a compra');
            return null;
        }
    };

    const value = { cart, isLoading,  setCart, fetchCart, addItemToCart, removeItemFromCart, updateItemQuantity, createOrderFromCart };

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
}