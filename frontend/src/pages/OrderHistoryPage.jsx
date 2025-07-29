import React, { useState, useEffect } from 'react';
import axios from 'axios';

function OrderHistoryPage() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        axios.get('http://localhost:8080/orders/my-orders')
            .then(response => {
                setOrders(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Erro ao buscar histórico de pedidos!', error);
                setLoading(false);
            });
    }, []);     

    if (loading) {
        return <div>Carregando histórico...</div>
    }

    return (
        <div style={{ padding: '40px' }}>
            <h2>Meus Pedidos</h2>
            {orders.length === 0 ? (
                <p>Você ainda não fez nenhum pedido.</p>
            ) : (
                orders.map(order => (
                    <div key={order.id} style={{ border: '1px solid #ccc', borderRadius: '8px', padding: '15px', marginBottom: '20px' }}>
                        <h3>Pedido #{order.id}</h3>
                        <p><strong>Data:</strong> {new Date(order.orderDate).toLocaleDateString()}</p>
                        <p><strong>Status:</strong> {order.orderStatus}</p>
                        <p><strong>Total:</strong> R$ {Number(order.totalPrice).toFixed(2)}</p>
                    </div>
                ))
            )}
        </div>
    );
}

export default OrderHistoryPage;