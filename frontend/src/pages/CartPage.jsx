import React from 'react';
import { useCart } from '../contexts/useCart';
import { useNavigate } from 'react-router-dom';
import './CartPage.css';
import { useAuth } from '../contexts/useAuth';

function CartPage() {
    const { cart, removeItemFromCart, updateItemQuantity, createOrderFromCart, isLoading } = useCart();
    const { user, isAuthenticated } = useAuth();
    const navigate = useNavigate();
    
    // Função de guarda para verificar se o usuário está logado antes de qualquer ação
    const ensureAuthenticated = () => {
        if (!isAuthenticated) {
            alert('Você precisa estar logado para gerenciar o carrinho.');
            navigate('/login');
            return false;
        }
        return true;
    };

    if (isLoading) {
        return (
            <div className="cart-container">
                <h2>Seu Carrinho de Compras</h2>
                <p>Seu carrinho está vazio.</p>
            </div>
        );
    }


      if (!cart || cart.items.length === 0) {
        return (
            <div className="cart-container">
                <h2>Seu Carrinho de Compras</h2>
                <p>Seu carrinho está vazio.</p>
            </div>
        );
    }


    const totalPrice = cart.items.reduce((total, item) => {
        return total + item.subtotal;
    }, 0);

    const handleRemoveFromCart = (itemId) => {
      if (ensureAuthenticated()) {
            removeItemFromCart(user.id, itemId);
        }
    };


    const handleIncreaseQuantity = (item) => {
         if (ensureAuthenticated()) {
            updateItemQuantity(user.id, item.id, item.quantity + 1);
        }
    };

    const handleDecreaseQuantity = (item) => {
        if (ensureAuthenticated()) {
            updateItemQuantity(user.id, item.id, item.quantity - 1);
        }
    };

    const handleCheckout = async () => {
      if (ensureAuthenticated()) {
            const newOrder = await createOrderFromCart(user.id);
        if (!user) {
            alert('Você precisa estar logado para finalizar a compra.');
            navigate('/login');
            return;
        }
        if (newOrder) {
            navigate('/');
        }
    }
    };


    return (
        <div className="cart-container">
            <h2>Seu Carrinho de Compras</h2>
            <div className="cart-items-list">
                {cart.items.map(item => (
                    <div className="cart-item" key={item.id}>
                        <img src={item.book.imageUrl} alt={item.book.title} className="cart-item-image" />
                        <div className="cart-item-details">
                            <h3>{item.book.title}</h3>
                            <p>Preço Unitário: R$ {item.unitPrice.toFixed(2)}</p>
                            <div className="cart-item-quantity">
                                <p>Quantidade: {item.quantity}</p>
                            </div>
                            <div className="cart-item-quantity">
                                <button onClick={() => handleDecreaseQuantity(item)}>-</button>
                                <span>{item.quantity}</span>
                                <button onClick={() => handleIncreaseQuantity(item)}>+</button>
                            </div>

                        </div>

                        <div className="cart-item-subtotal">
                            <p>Subtotal: R$ {Number(item.subtotal).toFixed(2)}</p>
                            <button onClick={() => handleRemoveFromCart(item.id)} className="remove-item-button">Remover</button>
                        </div>
                    </div>
                ))}
            </div>
            <div className="cart-summary">
                <h3>Total: R$ {totalPrice.toFixed(2)}</h3>
                <button onClick={handleCheckout} className="checkout-button">Finalizar Compra</button>
            </div>
        </div>
    );
}

export default CartPage;