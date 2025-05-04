package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.controller.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProduct_WhenProductNotExists_ThrowsException() {
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProduct(productId));

        verify(storageService).getProductById(productId);
        verifyNoInteractions(productBasket);
    }

    @Test
    void addProduct_WhenProductExists_CallsAddProductOnBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct("Apple", productId, 100);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProduct(productId);

        verify(storageService).getProductById(productId);
        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketIsEmpty_ReturnsEmptyBasket() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket).getProducts();
        verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasProducts_ReturnsCorrectBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new SimpleProduct("Apple", productId, 100);
        Map<UUID, Integer> basketProducts = Map.of(productId, 2);

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        UserBasket result = basketService.getUserBasket();

        assertEquals(1, result.getItems().size());
        BasketItem item = result.getItems().get(0);
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
        assertEquals(200, result.getTotal());
        verify(productBasket).getProducts();
        verify(storageService).getProductById(productId);
    }

    @Test
    void getUserBasket_WhenProductInBasketButNotInStorage_ThrowsException() {
        UUID productId = UUID.randomUUID();
        Map<UUID, Integer> basketProducts = Map.of(productId, 1);

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.getUserBasket());
    }

    @Test
    void getUserBasket_WithMultipleProducts_CalculatesTotalCorrectly() {
        UUID appleId = UUID.randomUUID();
        UUID bananaId = UUID.randomUUID();
        Product apple = new SimpleProduct("Apple", appleId, 100);
        Product banana = new SimpleProduct("Banana", bananaId, 50);

        Map<UUID, Integer> basketProducts = Map.of(
                appleId, 2,
                bananaId, 3
        );

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(appleId)).thenReturn(Optional.of(apple));
        when(storageService.getProductById(bananaId)).thenReturn(Optional.of(banana));

        UserBasket result = basketService.getUserBasket();

        assertEquals(2, result.getItems().size());
        assertEquals(350, result.getTotal());
    }
}