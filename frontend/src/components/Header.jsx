import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../contexts/useCart'; 
import { useAuth } from '../contexts/useAuth';
import './Header.css';

function Header() {
    // Acessamos o estado do carrinho usando nosso hook
    const { cart } = useCart();
    const { isAuthenticated, logout, hasRole } = useAuth(); 
    const navigate = useNavigate();

    // total de itens no carrinho
    const itemCount = cart ? cart.items.reduce((total, item) => total + item.quantity, 0) : 0;

    const handleLogout = () => {
        logout(); // Chama a função de logout do contexto
        navigate('/'); // Redireciona para a home após o logout
    };

    return (
        <header className="main-header">
            <div className="header-content">
                <Link to="/" className="logo">
                    <h1>Livraria Euliro</h1>
                </Link>
                <nav className="main-nav">

                    {isAuthenticated ? (
                        <>
                            {/* Mostra isso se o usuário ESTIVER logado */}
                            {hasRole('ROLE_ADMIN') ? (
                                <Link to="/admin">Painel Admin</Link>
                            ) : (
                                <Link to="/my-account">Minha Conta</Link>
                            )}
                            <button onClick={handleLogout} className="logout-button">Sair</button>
                        </>
                    ) : (
                        <>
                            {/* Mostra isso se o usuário NÃO ESTIVER logado */}
                            <Link to="/login">Login</Link>
                            <Link to="/register">Cadastre-se</Link>
                        </>
                    )}{!hasRole('ROLE_ADMIN') && isAuthenticated && (
                        <Link to="/carts" className="cart-link">
                            <span className="cart-icon">🛒</span>
                            <span className="cart-count">{itemCount}</span>
                        </Link>
                    )}
                </nav>
            </div>
        </header>
    );
}

export default Header;