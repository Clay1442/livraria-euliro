import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import axios from "axios";
import './EditBookPage.css';

function EditBookPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [originalData, setOriginalData] = useState(null);
    const [allAuthors, setAllAuthors] = useState([]);
    const [currentStock, setCurrentStock] = useState(0);
    const [adjustmentQuantity, setAdjustmentQuantity] = useState(1);
    const [ formData, setFormData] = useState({
        title: '',
        description: '',
        price: '',
        authorIds: [],
        quantity: ''
    });

    useEffect(() => {
        const fetchBookData = axios.get(`${import.meta.env.VITE_API_BASE_URL}/books/${id}`);
        const fetchAuthorsData = axios.get(`${import.meta.env.VITE_API_BASE_URL}/authors`);

        Promise.all([fetchBookData, fetchAuthorsData])
            .then(([bookResponse, authorsResponse]) => {
                const { title, description, price, stock, authors } = bookResponse.data;
                const currentAuthorIds = authors.map(author => author.id);

                setFormData({ title, description, price, authorIds: currentAuthorIds });
                setCurrentStock(stock);
                setOriginalData(bookResponse.data);
                setAllAuthors(authorsResponse.data);
            })
            .catch(error => console.error("Erro ao buscar dados:", error));
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleAuthorChange = (e) => {
        const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
        setFormData(prev => ({ ...prev, authorIds: selectedIds }));
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatePromises = [];

            // Atualiza título e descrição e o autor
            updatePromises.push(
                axios.put(`${import.meta.env.VITE_API_BASE_URL}/books/${id}`, {
                    title: formData.title,
                    description: formData.description,
                    authorIds: formData.authorIds
                })
            );

            // atualiza o preço se ele tiver mudado
            if (formData.price !== originalData.price) {
                updatePromises.push(
                    axios.patch(`${import.meta.env.VITE_API_BASE_URL}/books/${id}/price`, { price : formData.price })
                );
            }
            await Promise.all(updatePromises);

            toast.success('Livro atualizado com sucesso!');
            navigate('/admin/books');
        } catch (error) {
            console.error("Erro ao atualizar o livro:", error);
            toast.error('Falha ao atualizar o livro.');
        }
    };

    const handleStockUpdate = async (action) => {
        const url = `${import.meta.env.VITE_API_BASE_URL}/books/${id}/stock${action === 'remove' ? '/remove' : ''}`;
        try {
            const response = await axios.patch(url, { quantity: adjustmentQuantity });
            setCurrentStock(response.data.stock);
            toast.success(`Estoque atualizado com sucesso! Novo total: ${response.data.stock}`);
        } catch (error) {
            console.error(`Erro ao ${action} estoque:`, error);
            toast.error(`Falha ao atualizar o estoque.`);
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
                <div className="form-group">
                    <label htmlFor="authorIds">Autores</label>
                    <select id="authorIds" name="authorIds" multiple={true} value={formData.authorIds} onChange={handleAuthorChange} required>
                        {allAuthors.map(author => (
                            <option key={author.id} value={author.id}>
                                {author.name} {author.lastName}
                            </option>
                        ))}
                    </select>
                </div>


                <button type="submit" className="save-button">Salvar Todas as Alterações</button>
            </form>


            <hr/>
                <h3>Gerenciar Estoque</h3>
                <p>Estoque Atual: <strong>{currentStock}</strong></p>
            <div className="stock-management">
                <div className="stock-adjustment-controls">
                    <label htmlFor="adjustment">Quantidade:</label>
                    <input
                        type="number"
                        id="adjustment"
                        min="1"
                        value={adjustmentQuantity}
                        onChange={(e) => setAdjustmentQuantity(Number(e.target.value))}
                    />
                    <div className="button-stock">
                    <button  type="button" onClick={() => handleStockUpdate('add')}>+ Adicionar</button>
                    <button   type="button" onClick={() => handleStockUpdate('remove')}>- Remover</button>
                    </div>
                </div>
            </div>
        </div>
    );
}


export default EditBookPage;


