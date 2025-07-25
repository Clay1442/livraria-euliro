import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../contexts/useCart'; // Importa nosso hook do carrinho
import './Header.css';

function Header() {
    // Acessamos o estado do carrinho usando nosso hook
    const { cart } = useCart();

    // Calculamos o número total de itens no carrinho
    // Usamos .reduce() para somar a 'quantity' de cada item
    const itemCount = cart ? cart.items.reduce((total, item) => total + item.quantity, 0) : 0;

    return (
        <header className="main-header">
            <div className="header-content">
                <Link to="/" className="logo">
                    <h1>Livraria Euliro</h1>
                </Link>
                <nav>
                    <Link to="/carts" className="cart-link">
                        <span className="cart-icon">🛒</span>
                        <span className="cart-count">{itemCount}</span>
                    </Link>
                </nav>
            </div>
        </header>
    );
}

export default Header;