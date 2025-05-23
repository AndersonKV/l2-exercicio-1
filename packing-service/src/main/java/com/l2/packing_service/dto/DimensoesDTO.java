package com.l2.packing_service.dto;

import jakarta.validation.constraints.Min;

public record DimensoesDTO(
        @Min(value = 1, message = "deve ser maior que 0") double altura,
        @Min(value = 1, message = "deve ser maior que 0") double largura,
        @Min(value = 1, message = "deve ser maior que 0") double comprimento) { }



