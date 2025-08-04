import React, {useState, useEffect} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import './AdminCreateBookPage.css';


function AdminCreateBookPage() {
const navigate = useNavigate();
const [allAuthors, setAllAuthors] = useState([]);
const [formData, setFormData] = useState({
    title: '',
    description: '',
    price: '',
    stock: '',
    imageUrl: '',
    authorIds: []
});

   useEffect(() => {
       axios.get('http://localhost:8080/authors')
              .then(response => {
                setAllAuthors(response.data);
              })
              .catch(error => console.error("Erro ao buscar autores!", error));
   }, []);

     const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

      const handleAuthorChange = (e) => {
        // Pega todos os valores selecionados do <select> e os converte para Long
        const selectedIds = Array.from(e.target.selectedOptions, option => Number(option.value));
        setFormData(prev => ({ ...prev, authorIds: selectedIds }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/books', formData);
            alert('Livro criado com sucesso!');
            navigate('/admin/books');
        }catch (error) {
            console.error("Erro ao criar livro:", error);
            alert("Não foi possível criar o livro.");
        }

    }
    
 return (
        <div className="create-book-page">
            <h2>Adicionar Novo Livro</h2>
            <form onSubmit={handleSubmit} className="create-book-form">
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
                    <label htmlFor="stock">Estoque</label>
                    <input type="number" id="stock" name="stock" value={formData.stock} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="stock">Imagem (Passe a url da capa do livro)</label>
                    <input type="text" id="imageUrl" name="imageUrl" value={formData.imageUrl} onChange={handleChange} required />
                </div>
                <div className="form-group">
                    <label htmlFor="authorIds">Autores (segure Ctrl ou Cmd para selecionar múltiplos)</label>
                    <select id="authorIds" name="authorIds" multiple={true} onChange={handleAuthorChange} required>
                        {allAuthors.map(author => (
                            <option key={author.id} value={author.id}>
                                {author.name} {author.lastName}
                            </option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="create-button">Criar Livro</button>
            </form>
        </div>
    );
}

export default AdminCreateBookPage;
