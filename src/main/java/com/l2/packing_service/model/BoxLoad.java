package com.l2.packing_service.model;

import java.util.List;

public record BoxLoad(
        BoxSize box,
        List<ItemInfo> items
) {}