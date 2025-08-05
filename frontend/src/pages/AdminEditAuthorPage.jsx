import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './AdminEditAuthorPage.css';

function AdminEditAuthorPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [originalData, setOriginalData] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        lastName: '',
    });

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_API_BASE_URL}/authors/${id}`).
            then(response => {
                setOriginalData(response.data);
                setFormData(response.data);
            }).catch(error => console.error("Erro ao buscar dados:", error));
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault(); 
         try {
           await axios.put(`${import.meta.env.VITE_API_BASE_URL}/authors/${id}`, formData);
                    alert('Autor atualizado com sucesso!');
                    navigate('/admin/authors');
         } catch (error) {
            console.error("Erro ao atualizar autor:", error);
            alert("Não foi possível atualizar o autor.");
                }
    }    

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };


    return (
        <div className="edit-author-page">
            <h2>Editando Autor:  {originalData ? `${originalData.name} ${originalData.lastName}` : "Carregando..."}</h2>
            <form onSubmit={handleSubmit} className="edit-author-form">
                <div className="form-group">
                    <label htmlFor="title">Nome</label>
                    <input type="text" id="title" name="name" value={formData.name} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Sobrenome</label>
                    <input type="text" id="lastName" name="lastName" value={formData.lastName} onChange={handleChange} required />
                </div>
                <button type="submit" className="save-button">Salvar Todas as Alterações</button>
            </form>
        </div>
    );
}

export default AdminEditAuthorPage;


