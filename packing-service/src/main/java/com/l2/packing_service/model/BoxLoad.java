package com.l2.packing_service.model;

import com.l2.packing_service.model.BoxSize;
import com.l2.packing_service.model.ItemInfo;
import com.l2.packing_service.model.Product;

import java.util.List;

public record BoxLoad(
        BoxSize box,
        List<ItemInfo> items
) {}