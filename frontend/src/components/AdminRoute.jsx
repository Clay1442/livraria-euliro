import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../contexts/useAuth';

function AdminRoute() {
    const { isAuthenticated, hasRole, loading } = useAuth();

    if (loading) {
        return <div>Carregando...</div>;
    }
       if (isAuthenticated && hasRole('ROLE_ADMIN')) {
        return <Outlet />; // Se for admin, permite o acesso
    } else {
        return <Navigate to="/" replace />; // Se não for, redireciona para a home
    }
}

export default AdminRoute;