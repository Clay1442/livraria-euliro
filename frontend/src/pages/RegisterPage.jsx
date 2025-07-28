import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import './RegisterPage.css';

function RegisterPage() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [birthDate, setBirthDate] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();

        const userCreatDTO = {
            name,
            email,
            password,
            birthDate
        };

        try {
            await axios.post('http://localhost:8080/users', userCreatDTO);

            alert("Usuário cadastrado com sucesso!");
            navigate('/login');
        } catch (error) {
            console.error("Erro ao cadastrar usuário:", error);
            alert('Erro ao cadastrar usuário. Por favor, tente novamente.');
        }
    };


    return (
        <div className="register-page">
            <div className="register-container">
                <h1>Registre-se</h1>
                <form onSubmit={handleSubmit}>
                    <div className='input-group'>
                        <label htmlFor="name">Nome Completo</label>
                        <input
                            id="name"
                            type="text"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="email">Email</label>
                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="birthDate">Data de Nascimento</label>
                        <input
                            id="birthDate"
                            type="date"
                            value={birthDate}
                            onChange={(e) => setBirthDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-group">
                        <label htmlFor="password">Senha</label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="register-button">Criar Conta</button>
                </form>
                <p className="register-link">Já tem uma conta? <Link to="/login">Faça o login!</Link></p>
            </div>
        </div>
    );
}

export default RegisterPage;