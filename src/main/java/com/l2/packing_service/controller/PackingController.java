package com.l2.packing_service.controller;

import com.l2.packing_service.dto.LoteEntradaDTO;
import com.l2.packing_service.dto.LoteSaidaDTO;
import com.l2.packing_service.mapper.PedidoMapper;

import com.l2.packing_service.service.PackingService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/packing")
@Validated
public class PackingController {

    private final PackingService service;

    public PackingController(PackingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoteSaidaDTO> empacotar(
            @RequestBody @Valid LoteEntradaDTO pedidoLote) {

        var domainOrders = PedidoMapper.toDomain(pedidoLote);
        var summaries = domainOrders.stream()
                .map(service::pack)
                .toList();
        var resposta = PedidoMapper.toResponse(summaries);
        return ResponseEntity.ok(resposta);
    }
}
