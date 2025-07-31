import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function AdminAuthorsPage() {
    const [authors, setAuthors] = useState([]);

    const fetchAuthors = () => {
        axios.get('http://localhost:8080/authors')
            .then(response => {
                setAuthors(response.data);
            })
            .catch(error => console.error("Erro ao buscar a Lista de Autores!", error));
    };

    useEffect(() => {
        fetchAuthors();
    }, []);


    const handleDelete = async (authorId) => {
        if (window.confirm("Tem certeza que deseja deletar este livro?")) {
            try {
                await axios.delete(`http://localhost:8080/authors/${authorId}`)
                alert("Autor deletado com sucesso!");
                //Remover da lista localmente
                setAuthors(authors.filter(author => author.id !== authorId));
            }
            catch (error) {
                console.error("Erro ao deletar Autor!", error);
                alert("Não foi possível deletar o Autor.");
            }
        }
    };


    return (
        
        <div style={{ padding: '40px' }}>
            <h1>Gerenciamento de Autores</h1>
             <Link to="/admin/authors/new">
                    <button style={{ padding: '10px 15px', fontSize: '1em' }}>+ Adicionar Novo Autor</button>
                </Link>
            <table border="1" style={{ width: '100%', marginTop: '20px' }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {authors.length === 0 ? (
                        <tr>
                            <td colSpan="3">Nenhum autor encontrado.</td>
                        </tr>
                    ) : (
                        authors.filter(author => author != null).map(author => (
                            <tr key={author.id}>
                                <td>{author.id}</td>                                 
                                <td>{author.name + ' ' + author.lastName}</td>                                 
                                <td>
                                    <Link to={`/admin/authors/edit/${author.id}`}>
                                        <button>Editar</button>
                                    </Link>
                                    <button onClick={() => handleDelete(author.id)}>Deletar</button>
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );

}

export default AdminAuthorsPage;




