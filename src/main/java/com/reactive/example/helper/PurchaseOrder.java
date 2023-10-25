package com.reactive.example.helper;

import com.reactive.example.utils.MyUtils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder {

    private String item;
    private String price;
    private int userId;

    public PurchaseOrder(int userId) {
        this.userId = userId;
        this.item = MyUtils.faker().commerce().productName();
        this.price = MyUtils.faker().commerce().price();
    }
}
