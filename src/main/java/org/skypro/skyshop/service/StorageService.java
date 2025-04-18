package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StorageService {
    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initializeTestData();
    }

    private void initializeTestData() {

        UUID appleId = UUID.randomUUID();
        products.put(appleId, new SimpleProduct("Яблоко", appleId, 200));

        UUID mangoId = UUID.randomUUID();
        products.put(mangoId, new FixPriceProduct("Манго", mangoId));

        UUID bananaId = UUID.randomUUID();
        products.put(bananaId, new DiscountedProduct("Банан", 1000, bananaId, 50));


        UUID appleArticleId = UUID.randomUUID();
        articles.put(appleArticleId, new Article("Как выбрать яблоко?",
                "Выбирайте яблоки по цвету, без вмятин.",
                appleArticleId));

        UUID mangoArticleId = UUID.randomUUID();
        articles.put(mangoArticleId, new Article("Как выбрать Манго",
                "Понятие не иммею как, выбирайте который больше нравится!",
                mangoArticleId));

        UUID bananaArticleId = UUID.randomUUID();
        articles.put(bananaArticleId, new Article("Как выбрать Банан",
                "Банан следует брать желтого цвета, желтый цвет свидетельствует о его спелости.",
                bananaArticleId));
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(articles.values());
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<Searchable> getAllSearchables() {
        return Stream.concat(
                products.values().stream(),
                articles.values().stream()
        ).collect(Collectors.toUnmodifiableList());
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}