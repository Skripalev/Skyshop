package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int price;

    public SimpleProduct(String productName, UUID id, int price) {
        super(productName, id);
        this.price = price;
    }

    @Override
    public int getProductPrice() {
        return price;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String getName() {
        return "";
    }
}
