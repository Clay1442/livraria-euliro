import React, { useState } from 'react';
import { useAuth } from '../contexts/useAuth';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './MyAccountPage.css';

function MyAccountPage() {
    const { user } = useAuth();
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handlePasswordChange = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');

        // 1. Validação no frontend primeiro
        if (newPassword !== confirmPassword) {
            setError('A nova senha e a confirmação não conferem.');
            return;
        }
        if (newPassword.length < 8) {
            setError('A nova senha deve ter no mínimo 8 caracteres.');
            return;
        }

        try {
            // 2. Chama a API do backend
            const passwordUpdateDTO = { oldPassword, newPassword };
            await axios.patch(`${import.meta.env.VITE_API_BASE_URL}/users/${user.id}/password`, passwordUpdateDTO);

            setSuccess('Senha alterada com sucesso! Recomendamos que você faça o login novamente.');

            // Limpa os campos
            setOldPassword('');
            setNewPassword('');
            setConfirmPassword('');

        } catch (err) {
            console.error("Erro ao alterar senha:", err);
            setError(err.response?.data?.message || 'Não foi possível alterar a senha. Verifique sua senha antiga.');
        }
    };

    if (!user) {
        return <div>Carregando informações do usuário...</div>;
    }


    return (
        // A classe principal do container
        <div className='MyAccount-container'>
            <h2>Minha Conta</h2>
            <p>Bem-vindo(a) de volta, {user.name}!</p>

            <div>
                <h3>Seus Dados:</h3>
                <p><strong>Nome:</strong> {user.name}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><Link to="/my-orders">Ver meu histórico de pedidos</Link></p>
            </div>

            {/* Use a classe correta para a linha horizontal */}
            <hr className="hr" />

            <div className="password-change-container">
                <h3>Alterar Senha</h3>
                <form onSubmit={handlePasswordChange}>
                    <div className="form-group">
                        <label htmlFor="oldPassword">Senha Atual</label>
                        <input
                            type="password" id="oldPassword"
                            value={oldPassword} onChange={(e) => setOldPassword(e.target.value)} required
                        />


                        <label htmlFor="newPassword">Nova Senha</label>
                        <input
                            type="password" id="newPassword"
                            value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required
                        />

                        <label htmlFor="confirmPassword">Confirmar Nova Senha</label>
                        <input
                            type="password" id="confirmPassword"
                            value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required
                        />
                    </div>
                    <div className='form-message-container'>
                        {error && <p className="form-message error">{error}</p>}
                        {success && <p className="form-message success">{success}</p>}
                    </div>
                    <button type="submit" className="save-button">Salvar Nova Senha</button>
                </form>
            </div>
        </div>
    );
}

export default MyAccountPage;