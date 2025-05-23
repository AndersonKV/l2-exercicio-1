package com.l2.packing_service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record Order(
        String orderId, @NotEmpty @Valid List<Product> products) {}