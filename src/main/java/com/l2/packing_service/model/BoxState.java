package com.l2.packing_service.model;

import com.l2.packing_service.utils.FitUtil;

import java.util.ArrayList;
import java.util.List;

public class BoxState {

    private final BoxSize size;
    private final double maxVolume;
    private double usedVolume = 0;

    private final List<Product> items = new ArrayList<>();

    public BoxState(BoxSize size) {
        this.size = size;
        this.maxVolume = size.dim().volume();
    }

    private int maxCapacity = Integer.MAX_VALUE;   // novo campo

    public boolean tryAdd(Product p) {
        if (!p.dimension().fitsInside(size.dim())) return false;

        if (items.isEmpty()) {
            double[] outer = { size.dim().h(), size.dim().w(), size.dim().l() };
            double[] inner = { p.dimension().h(), p.dimension().w(), p.dimension().l() };
            maxCapacity = FitUtil.maxFit(outer, inner);
        }

        if (items.size() >= maxCapacity) return false;

        items.add(p);
        return true;
    }

    public BoxSize size() {
        return size;
    }

    public List<Product> items() {
        return items;
    }
}
