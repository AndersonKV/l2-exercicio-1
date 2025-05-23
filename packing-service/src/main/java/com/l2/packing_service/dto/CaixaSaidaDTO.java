package com.l2.packing_service.dto;

import java.util.List;

public record CaixaSaidaDTO(
        String caixa_id,              // "Caixa 1" / "Caixa 2" / "Caixa 3" ou null
        List<String> produtos,        // lista de produto_id
        String observacao) { }