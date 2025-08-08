import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { AuthContext } from './AuthContext';

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null); 
    const [loading, setLoading] = useState(true);

    // Declara a função logout primeiro
    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.removeItem('authToken');
        delete axios.defaults.headers.common['Authorization'];
    };

    // Efeito para carregar o usuário na inicialização
    useEffect(() => {
        const loadUserFromToken = async () => {
            const storedToken = localStorage.getItem('authToken');
            if (storedToken) {
                try {
                    axios.defaults.headers.common['Authorization'] = `Bearer ${storedToken}`;
                    const response = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/users/me`);
                    setUser(response.data);
                    setToken(storedToken);
                } catch (error) {
                    console.error("Token inválido ou expirado.", error);
                    logout(); 
                }
            }
            setLoading(false);
        };
        loadUserFromToken();
    }, []); 

    const login = async (email, password) => {
        try {
            const response = await axios.post(`${import.meta.env.VITE_API_BASE_URL}/auth/login`, { email, password });
            const newToken = response.data.token;
            
            localStorage.setItem('authToken', newToken);
            axios.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
            setToken(newToken);

            const userResponse = await axios.get(`${import.meta.env.VITE_API_BASE_URL}/users/me`);
            setUser(userResponse.data);
            
             return userResponse.data;
        } catch (error) {
            console.error("Erro no login:", error);
            toast.error('Email ou senha inválidos.');
            return false;
        }
    };

    // Determina qual é o papel do usuário 
    const hasRole = (roleName) => {
            return user && user.roles && user.roles.includes(roleName);    
        };


    if (loading) {
        return <div>Carregando aplicação...</div>;
    }

    const value = { user, token, login, logout, isAuthenticated: !!token, hasRole };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}