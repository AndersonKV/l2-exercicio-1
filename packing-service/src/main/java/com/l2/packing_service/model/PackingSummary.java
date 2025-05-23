package com.l2.packing_service.model;

import java.util.List;

public record PackingSummary(
        String orderId,
        List<BoxLoad> boxes,
        List<ItemInfo> notPacked) {}



