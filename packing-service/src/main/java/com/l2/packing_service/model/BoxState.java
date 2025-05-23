package com.l2.packing_service.model;

import com.l2.packing_service.utils.FitUtil;

import java.util.ArrayList;
import java.util.List;

public class BoxState {

    private final BoxSize size;
    private final double maxVolume;
    private double usedVolume = 0;

    /** produtos que já foram colocados nesta caixa */
    private final List<Product> items = new ArrayList<>();

    public BoxState(BoxSize size) {
        this.size = size;
        this.maxVolume = size.dim().volume();
    }

    /** Tenta adicionar o produto; devolve true se coube e foi adicionado */
    private int maxCapacity = Integer.MAX_VALUE;   // novo campo

    public boolean tryAdd(Product p) {
        // 1. dimensões compatíveis
        if (!p.dimension().fitsInside(size.dim())) return false;

        // 2. se for o primeiro item, calcula capacidade real
        if (items.isEmpty()) {
            double[] outer = { size.dim().h(), size.dim().w(), size.dim().l() };
            double[] inner = { p.dimension().h(), p.dimension().w(), p.dimension().l() };
            maxCapacity = FitUtil.maxFit(outer, inner);
        }

        // 3. não ultrapassar maxCapacity
        if (items.size() >= maxCapacity) return false;

        items.add(p);
        return true;
    }

    /* ---------- getters usados pelo serviço ---------- */

    public BoxSize size() {
        return size;
    }

    /** quantidade de itens dentro da caixa */
    public int count() {
        return items.size();
    }

    /** lista completa de produtos colocados na caixa */
    public List<Product> items() {
        return items;
    }
}
