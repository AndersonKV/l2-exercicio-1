package com.l2.packing_service.service;

import com.l2.packing_service.model.BoxLoad;
import com.l2.packing_service.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PackingService {

    private static final List<BoxSize> ORDER = List.of(
            BoxSize.PEQUENA, BoxSize.MEDIA, BoxSize.GRANDE
    );

    public PackingSummary pack(Order order) {

        Map<BoxSize, BoxState> states = new EnumMap<>(BoxSize.class);
        ORDER.forEach(sz -> states.put(sz, new BoxState(sz)));

        List<Product> items = new ArrayList<>(order.products());
        items.sort(Comparator.comparingDouble((Product p) -> p.dimension().volume()).reversed());

        List<ItemInfo> notPacked = new ArrayList<>();

        for (Product p : items) {
            boolean placed = false;
            for (BoxSize sz : ORDER) {
                if (states.get(sz).tryAdd(p)) {
                    placed = true;
                    break;
                }
            }
            if (!placed) notPacked.add(new ItemInfo(p.sku(), p.dimension()));
        }

        List<BoxLoad> loads = ORDER.stream()
                .map(sz -> {
                    List<ItemInfo> packed = states.get(sz).items().stream()
                            .map(prod -> new ItemInfo(prod.sku(), prod.dimension()))
                            .toList();
                    return new BoxLoad(sz, packed);
                })
                .filter(bl -> !bl.items().isEmpty())
                .toList();

        // ---------- aqui entra o id ----------
        return new PackingSummary(order.orderId(), loads, notPacked);
    }


}
