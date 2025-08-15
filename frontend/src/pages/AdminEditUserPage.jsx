import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './AdminEditUserPage.css';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';
import axios from 'axios';

function AdminEditUserPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [originalData, setOriginalData] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        birthDate: '',
        roles: []
    });
    
    
    useEffect(() => {
        axios.get(`http://localhost:8080/users/${id}`)
        .then(response => {
            setFormData({
                name: response.data.name,
                email: response.data.email,
                birthDate: response.data.birthDate,
                roles: response.data.roles
            })
            setOriginalData(response.data);
        })
        .catch(error => console.error("Erro ao buscar usuário:", error));
    }, [id]);
    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };
    
    const handleRoleChange = (role) => {
        setFormData(prev => {
            const newRoles = prev.roles.includes(role)
            ? prev.roles.filter(r => r !== role)
            : [...prev.roles, role];
            return { ...prev, roles: newRoles };
        })
    };
    
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (formData.roles.length === 0) {
            toast.error('Por favor, selecione pelo menos um papel para o usuário.');
            return false;
        }
        try {
            
            // DTO para atualizar dados básicos
            const updateUserDTO = {
                name: formData.name,
                email: formData.email,
                birthDate: formData.birthDate
            };
            
            // DTO para atualizar os papéis
            const updateRolesDTO = {
                roles: formData.roles
            };
            
            await Promise.all([
                axios.put(`http://localhost:8080/users/${id}`, updateUserDTO),
                axios.put(`http://localhost:8080/users/${id}/roles`, updateRolesDTO)
            ]);

            toast.success('Papéis do usuário atualizados com sucesso!');
            navigate('/admin/users');
        }
        catch (error) {
            console.error("Erro ao atualizar usuário:", error);
            toast.error('Falha ao atualizar o usuário.');
        }
    };

    if (!formData.email) return <div>Carregando...</div>;

    return (
        <div className="edit-user-page">
            <h2>Editando Usuário: {originalData.name}</h2>
            <form onSubmit={handleSubmit} className="edit-user-form">
                <h3>Dados Pessoais</h3>
                <div className="input-groupEditUser">
                    <label htmlFor="name">Nome Completo</label>
                    <input id="name" name='name' type="text" value={formData.name} onChange={handleChange} required />
                </div>
                <div className="input-groupEditUser">
                    <label htmlFor="email">Email</label>
                    <input id="email" name="email" type="email" value={formData.email} onChange={handleChange} required />
                </div>
                <div className="input-groupEditUser">
                    <label htmlFor="birthDate">Data de Nascimento</label>
                    <input id="birthDate" name="birthDate" type="date" value={formData.birthDate || ''} onChange={handleChange} required />
                </div>

                <h3>Papéis (Roles)</h3>
                <div className="roles-container">
                    <label className="role-label">
                        <input
                            type="checkbox"
                            checked={formData.roles.includes('ROLE_CLIENTE')}
                            onChange={() => handleRoleChange('ROLE_CLIENTE')}
                        />
                        CLIENTE
                    </label>
                    <label className="role-label">
                        <input
                            type="checkbox"
                            checked={formData.roles.includes('ROLE_ADMIN')}
                            onChange={() => handleRoleChange('ROLE_ADMIN')}
                        />
                        ADMINISTRADOR
                    </label>
                </div>
                <button type="submit" className="save-button">Salvar Alterações</button>
            </form >
        </div >
    );
}

export default AdminEditUserPage;