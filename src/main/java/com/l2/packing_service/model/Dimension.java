package com.l2.packing_service.model;

import jakarta.validation.constraints.Min;

public record Dimension(
        @Min(1) double h,
        @Min(1) double w,
        @Min(1) double l) {

    public double volume() { return h * w * l; }

    public boolean fitsInside(Dimension box) {
        return (h <= box.h && w <= box.w && l <= box.l) ||
                (h <= box.h && l <= box.w && w <= box.l) ||
                (w <= box.h && h <= box.w && l <= box.l) ||
                (w <= box.h && l <= box.w && h <= box.l) ||
                (l <= box.h && h <= box.w && w <= box.l) ||
                (l <= box.h && w <= box.w && h <= box.l);
    }
}
