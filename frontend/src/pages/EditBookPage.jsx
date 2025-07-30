import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import './EditBookPage.css';

function EditBookPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [originalData, setOriginalData] = useState(null);
    const [currentStock, setCurrentStock] = useState(0);
    const [adjustmentQuantity, setAdjustmentQuantity] = useState(1);
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        price: '',
        quantity: ''
    });


    useEffect(() => {
        axios.get(`http://localhost:8080/books/${id}`)
            .then(response => {
                setFormData(response.data);
                //armazena os dados originais para comparação
                setOriginalData(response.data);
                // armazena o estoque atual
                setCurrentStock(response.data.stock);
            })
            .catch(error => console.error("Erro ao buscar dados do livro:", error));
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatePromises = [];

            // Atualiza título e descrição
            updatePromises.push(
                axios.put(`http://localhost:8080/books/${id}`, {
                    title: formData.title,
                    description: formData.description
                })
            );

            // atualiza o preço se ele tiver mudado
            if (formData.price !== originalData.price) {
                updatePromises.push(
                    axios.patch(`http://localhost:8080/books/${id}/price`, { price: formData.price })
                );
            }
            await Promise.all(updatePromises);

            alert('Livro atualizado com sucesso!');
            navigate('/admin/books');
        } catch (error) {
            console.error("Erro ao atualizar o livro:", error);
            alert('Falha ao atualizar o livro.');
        }
    };

    const handleStockUpdate = async (action) => {
        const url = `http://localhost:8080/books/${id}/stock${action === 'remove' ? '/remove' : ''}`;
        try {
            const response = await axios.patch(url, { quantity: adjustmentQuantity });
            setCurrentStock(response.data.stock);
            alert(`Estoque atualizado com sucesso! Novo total: ${response.data.stock}`);
        } catch (error) {
            console.error(`Erro ao ${action} estoque:`, error);
            alert(`Falha ao atualizar o estoque.`);
        }
    };

    if (!originalData) return <div>Carregando...</div>;


    return (
        <div className="edit-book-page">
            <h2>Editando Livro: {originalData.title}</h2>
            <form onSubmit={handleSubmit} className="edit-book-form">
                <div className="form-group">
                    <label htmlFor="title">Título</label>
                    <input type="text" id="title" name="title" value={formData.title} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="description">Descrição</label>
                    <textarea id="description" name="description" value={formData.description} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="price">Preço</label>
                    <input type="number" step="0.01" id="price" name="price" value={formData.price} onChange={handleChange} required />
                </div>
                <button type="submit" className="save-button">Salvar Todas as Alterações</button>
            </form>


            <hr style={{ margin: '40px 0' }} />
            <div className="stock-management">
                <h3>Gerenciar Estoque</h3>
                <p>Estoque Atual: <strong>{currentStock}</strong></p>
                <div className="stock-adjustment-controls">
                    <label htmlFor="adjustment">Quantidade:</label>
                    <input
                        type="number"
                        id="adjustment"
                        min="1"
                        value={adjustmentQuantity}
                        onChange={(e) => setAdjustmentQuantity(Number(e.target.value))}
                    />
                    <button type="button" onClick={() => handleStockUpdate('add')}>+ Adicionar</button>
                    <button type="button" onClick={() => handleStockUpdate('remove')}>- Remover</button>
                </div>
            </div>
        </div>
    );
}


export default EditBookPage;


