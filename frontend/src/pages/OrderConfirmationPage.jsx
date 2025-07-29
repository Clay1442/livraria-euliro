import React from "react";
import { useParams, Link } from 'react-router-dom';

function OrderConfirmationPage() {
    const {orderId} = useParams();

    return (
       <div style={{ padding: '40px', textAlign: 'center' }}>
            <h2>🎉 Pedido Realizado com Sucesso! 🎉</h2>
            <p>Obrigado pela sua compra.</p>
            <p>O número do seu pedido é: <strong>{orderId}</strong></p>
            <br />
            <Link to="/my-orders">Ver todos os meus pedidos</Link>
        </div>
    );
}

export default OrderConfirmationPage;