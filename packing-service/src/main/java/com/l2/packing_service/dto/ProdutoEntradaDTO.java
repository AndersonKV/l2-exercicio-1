package com.l2.packing_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoEntradaDTO(
        @NotBlank String produto_id,
        @NotNull @Valid DimensoesDTO dimensoes) { }