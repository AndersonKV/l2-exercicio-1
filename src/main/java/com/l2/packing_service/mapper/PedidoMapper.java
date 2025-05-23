package com.l2.packing_service.mapper;

import com.l2.packing_service.dto.*;
import com.l2.packing_service.model.*;
import java.util.*;

public final class PedidoMapper {

    private PedidoMapper() { }

    public static List<Order> toDomain(LoteEntradaDTO dto) {
        return dto.pedidos().stream().map(p ->
                new Order(
                        String.valueOf(p.pedido_id()),
                        p.produtos().stream().map(prod ->
                                new Product(
                                        prod.produto_id(),
                                        new Dimension(
                                                prod.dimensoes().altura(),
                                                prod.dimensoes().largura(),
                                                prod.dimensoes().comprimento()
                                        )
                                )
                        ).toList()
                )
        ).toList();
    }

    public static LoteSaidaDTO toResponse(List<PackingSummary> summaries) {
        List<PedidoSaidaDTO> pedidos = new ArrayList<>();

        for (PackingSummary sum : summaries) {
            List<CaixaSaidaDTO> caixas = new ArrayList<>();

            for (BoxLoad bl : sum.boxes()) {
                caixas.add(new CaixaSaidaDTO(
                        switch (bl.box()) {
                            case PEQUENA  -> "Caixa 1";
                            case MEDIA -> "Caixa 2";
                            case GRANDE  -> "Caixa 3";
                        },
                        bl.items().stream().map(ItemInfo::sku).toList(),
                        null));
            }

            if (!sum.notPacked().isEmpty()) {
                caixas.add(new CaixaSaidaDTO(
                        null,
                        sum.notPacked().stream().map(ItemInfo::sku).toList(),
                        "Produto não cabe em nenhuma caixa disponível."));
            }

            pedidos.add(new PedidoSaidaDTO(
                    Integer.parseInt(sum.orderId()), caixas));
        }
        return new LoteSaidaDTO(pedidos);
    }
}

