package com.l2.packing_service.dto;

import java.util.List;

public record PedidoSaidaDTO(
        int pedido_id,
        List<CaixaSaidaDTO> caixas) { }
