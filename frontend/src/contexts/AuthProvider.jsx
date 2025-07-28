import React, { useState } from 'react';
import axios from 'axios';
import { AuthContext } from './AuthContext';

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null); 
    const [token, setToken] = useState(null); 

    const login = async (email, password) => {
        try {
            const response = await axios.post('http://localhost:8080/auth/login', {
                email,
                password
            });
            
            const newToken = response.data.token;
            setToken(newToken);

            localStorage.setItem('authToken', newToken);

            axios.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
                        
            alert('Login bem-sucedido!');
            return true;
        } catch (error) {
            console.error("Erro no login:", error);
            alert('Email ou senha inválidos.');
            return false;
        }
    };

    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem('authToken');
        delete axios.defaults.headers.common['Authorization'];
    };

    const value = { user, token, login, logout, isAuthenticated: !!token };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}