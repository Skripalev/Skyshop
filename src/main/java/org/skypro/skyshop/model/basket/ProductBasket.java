package org.skypro.skyshop.model.basket;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductBasket {
    private final Map<UUID,Integer> products = new HashMap<>();
public void addProduct(UUID id){
    products.merge(id, 1, Integer::sum);
}

    public Map<UUID, Integer> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
