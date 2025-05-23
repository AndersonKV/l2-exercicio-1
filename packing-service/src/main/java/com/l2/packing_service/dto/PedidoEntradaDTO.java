package com.l2.packing_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoEntradaDTO(
        @NotNull Integer pedido_id,
        @NotEmpty @Valid List<ProdutoEntradaDTO> produtos) { }