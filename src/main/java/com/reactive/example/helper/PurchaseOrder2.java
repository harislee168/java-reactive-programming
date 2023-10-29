package com.reactive.example.helper;

import com.reactive.example.utils.MyUtils;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrder2 {
    private String item;
    private String category;
    private double price;
    private int quantity;

    public PurchaseOrder2() {
        this.item = MyUtils.faker().commerce().productName();
        this.category = MyUtils.faker().commerce().department();
        this.price = Double.parseDouble(MyUtils.faker().commerce().price());
        this.quantity = MyUtils.faker().random().nextInt(1, 10);
    }
}
