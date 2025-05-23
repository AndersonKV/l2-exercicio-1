package com.l2.packing_service.dto;

import java.util.List;

public record CaixaSaidaDTO(
        String caixa_id,
        List<String> produtos,
        String observacao) { }