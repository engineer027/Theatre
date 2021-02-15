package com.dev.cinema.model.dto;

import com.dev.cinema.model.Ticket;
import java.util.List;

public class OrderRequestDto {
    private String orderDate;
    private Long userId;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
