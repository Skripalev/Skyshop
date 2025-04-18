package org.skypro.skyshop.model.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;


public abstract class Product implements Searchable {
    private final String productName;
    private final UUID id;

    @Override
    public UUID getId() {

        return id;
    }

    protected Product(String productName, UUID id) {
        this.id = id;
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Введите корректное значение наименования!");
        }
        this.productName = productName;
    }

    public String getProductName() {

        return productName;
    }

    @JsonIgnore
    public String getSearchTerm() {

        return productName;
    }

    @JsonIgnore
    public String getContentType() {

        return "PRODUCT: ";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(productName);
    }

    public abstract int getProductPrice();

    public abstract boolean isSpecial();


}
