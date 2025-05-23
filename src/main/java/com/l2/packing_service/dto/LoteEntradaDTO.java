package com.l2.packing_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record LoteEntradaDTO(
        @NotEmpty @Valid List<PedidoEntradaDTO> pedidos) { }