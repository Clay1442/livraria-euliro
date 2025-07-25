import React from 'react';
import { useCart } from '../contexts/useCart';
import './CartPage.css';

function CartPage() {
      const { cart, removeItemFromCart } = useCart();
    
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
            const userId = 1;
            removeItemFromCart(userId, itemId);
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
                                {/* Futuramente, botões para +/- aqui */}
                            </div>
                        </div>
                        <div className="cart-item-subtotal">
                            <p>Subtotal: R$ {(item.unitPrice * item.quantity).toFixed(2)}</p>
                            <button onClick={() => handleRemoveFromCart(item.id)} className="remove-item-button">Remover</button>
                        </div>
                    </div>
                ))}
            </div>
            <div className="cart-summary">
                <h3>Total: R$ {totalPrice.toFixed(2)}</h3>
                <button className="checkout-button">Finalizar Compra</button>
            </div>
        </div>
    );
}

export default CartPage;