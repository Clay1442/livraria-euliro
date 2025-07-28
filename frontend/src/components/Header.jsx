import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../contexts/useCart'; // Importa nosso hook do carrinho
import { useAuth } from '../contexts/useAuth';
import './Header.css';

function Header() {
    // Acessamos o estado do carrinho usando nosso hook
    const { cart } = useCart();
    const { isAuthenticated, logout } = useAuth(); // 2. Pegar o estado e a função do AuthContext
    const navigate = useNavigate();

    // Calculamos o número total de itens no carrinho
    // Usamos .reduce() para somar a 'quantity' de cada item
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
                <nav>
                    {/* 3. Renderização Condicional baseada no 'isAuthenticated' */}
                    {isAuthenticated ? (
                        <>
                            {/* Mostra isso se o usuário ESTIVER logado */}
                            <Link to="/my-account">Minha Conta</Link>
                            <button onClick={handleLogout} className="logout-button">Sair</button>
                        </>
                    ) : (
                        <>
                            {/* Mostra isso se o usuário NÃO ESTIVER logado */}
                            <Link to="/login">Login</Link>
                            <Link to="/register">Cadastre-se</Link>
                        </>
                    )}
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