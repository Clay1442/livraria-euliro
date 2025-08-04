  import React from 'react';
  import './CustomConfirmModal.css'; 

  const CustomConfirmModal = ({ title, message, onClose, onConfirm }) => {
    return (
      <div className="custom-confirm-overlay">
        <div className="custom-confirm-modal">
          <h2>{title}</h2>
          <p>{message}</p>
          <div className="custom-confirm-buttons">
            <button onClick={onClose} className="button cancel-button">Não</button>
            <button onClick={onConfirm} className="button confirm-button">Sim</button>
          </div>
        </div>
      </div>
    );
  };

  export default CustomConfirmModal;

