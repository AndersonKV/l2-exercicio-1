package com.l2.packing_service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Product(
        @NotBlank String sku,
        @NotNull @Valid Dimension dimension) {}
