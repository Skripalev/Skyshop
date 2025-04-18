package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {

    private final int price;
    private final int discount;

    public DiscountedProduct(String productName, int price, UUID id, int discount) {
        super(productName, id);
        this.price = price;
        this.discount = discount;
    }

    @Override
    public int getProductPrice() {
        return price - (price * discount / 100);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getName() {
        return "";
    }
}
