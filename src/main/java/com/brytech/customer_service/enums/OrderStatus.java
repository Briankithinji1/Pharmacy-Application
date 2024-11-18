package com.brytech.customer_service.enums;

import org.yaml.snakeyaml.util.EnumUtils;

public enum OrderStatus {
    PENDING, SHIPPED ,DELIVERED, CANCELED

}

//if (!EnumUtils.isValidEnum(OrderStatus.class, newStatus.toUpperCase())) {
//        throw new IllegalArgumentException("Invalid status: " + newStatus);
//    }

