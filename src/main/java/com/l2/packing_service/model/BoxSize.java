package com.l2.packing_service.model;


public enum BoxSize {
    PEQUENA (new Dimension(30, 40, 80)),
    MEDIA   (new Dimension(80, 50, 40)),
    GRANDE  (new Dimension(50, 80, 60));


    private final Dimension dim;
    BoxSize(Dimension dim) { this.dim = dim; }
    public Dimension dim() { return dim; }
}

