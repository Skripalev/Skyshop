package org.skypro.skyshop;

import org.skypro.skyshop.model.basket.ProductBasket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class AppConfig {
    @Bean
    @SessionScope
    public ProductBasket productBasket() {
        return new ProductBasket();
    }
}