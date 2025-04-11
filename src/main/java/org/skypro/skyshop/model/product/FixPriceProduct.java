package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIXED_PRICE = 100;

    public FixPriceProduct(String productName, UUID id) {

        super(productName, id);
    }

    @Override
    public int getProductPrice() {

        return 0;
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
