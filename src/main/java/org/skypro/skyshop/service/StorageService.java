package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> article;


    public StorageService(Map<UUID, Product> products, Map<UUID, Article> article) {
        this.products = new HashMap<>();
        this.article = new HashMap<>();
        initializeTestData();
    }

    private void initializeTestData() {
        products.put(UUID.randomUUID(), new SimpleProduct("Яблоко", UUID.randomUUID(), 200));
        products.put(UUID.randomUUID(), new FixPriceProduct("Манго", UUID.randomUUID()));
        products.put(UUID.randomUUID(), new DiscountedProduct("Банан", 10, UUID.randomUUID(), 50));

        article.put(UUID.randomUUID(), new Article("Как выбрать яблоко?",
                "Выбирайте яблоки по цвету, без вмятин.",
                UUID.randomUUID()));
        article.put(UUID.randomUUID(), new Article("Как выбрать Манго",
                "Понятие не иммею как, выбирайте который больше нравится!",
                UUID.randomUUID()));
        article.put(UUID.randomUUID(), new Article("Как выбрать Банан",
                "Банан следует брать желтого цвета, желтый цвет свидетельствует о его спелости.",
                UUID.randomUUID()));

    }

    public Collection<Article> getAllArticles() {
        return article.values();
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Searchable> getAllSearchables() {
        Collection<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(article.values());
        return searchables;
    }
}
