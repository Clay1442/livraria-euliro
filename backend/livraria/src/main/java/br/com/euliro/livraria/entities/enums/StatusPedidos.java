package br.com.euliro.livraria.entities.enums;

public enum StatusPedidos {
	 
	 AGUARDANDO_PAGAMENTO(1),
	 PAGO(2),  
     ENVIADO(3),
     ENTREGUE(4),
	 CANCELADO(5);
		
		private int codigo;
		
		
		private StatusPedidos(int codigo) {
			this.codigo = codigo;
		}
		
		public int getCodigo() {
			return codigo;
		}
		
		public static StatusPedidos valueOf(int codigo) {
			for(StatusPedidos value : StatusPedidos.values()) {
				if(value.getCodigo() == codigo) {
					return value; 
				}
			}
			throw new IllegalArgumentException("Código de status do pedido inválido");
		}
}
