import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { toast } from 'react-toastify';
import './AdminCreateAuthor.css';

function AdminCreateAuthor() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: '',
        lastName: '',
    });

    const handleChange = (e) => {
        const { name, value, } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/authors',
                {
                    name: formData.name,
                    lastName: formData.lastName
                }
            );
           toast.success('Autor criado com sucesso!');
            navigate('/admin/authors');
        } catch (error) {
            console.error("Erro ao criar autor:", error);
            toast.error("Não foi possível criar o autor.");
        }
    }


    return (
        <div className="edit-author-page">
            <h2>Adicionar Novo Autor</h2>
            <form onSubmit={handleSubmit} className="edit-author-form">
                <div className="form-group">
                    <label htmlFor="name">Nome</label>
                    <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="LastName">Sobrenome</label>
                    <input type="text" id="lastName" name="lastName" value={formData.lastName} onChange={handleChange} required />
                </div>
                <button type="submit" className="save-button">Adicionar Autor</button>
            </form>
        </div>
    );
}

export default AdminCreateAuthor;