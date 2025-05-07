package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchService;
import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoObjectsInStorage_ReturnsEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        var result = searchService.search("anything");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenNoMatchingObjects_ReturnsEmptyList() {
        UUID id = UUID.randomUUID();
        Searchable product = new SimpleProduct("Apple", id, 100);
        when(storageService.getAllSearchables()).thenReturn(List.of(product));

        var result = searchService.search("Banana");

        assertTrue(result.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchingProductExists_ReturnsSearchResult() {
        UUID id = UUID.randomUUID();
        String productName = "Apple";
        Searchable product = new SimpleProduct(productName, id, 100);
        when(storageService.getAllSearchables()).thenReturn(List.of(product));

        var result = searchService.search("app");

        assertEquals(1, result.size());
        SearchResult searchResult = result.iterator().next();
        assertEquals(productName, searchResult.getName());
        assertEquals("PRODUCT: ", searchResult.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchingArticleExists_ReturnsSearchResult() {
        UUID id = UUID.randomUUID();
        String title = "How to choose apples";
        Article article = new Article(title, "Some text", id);
        when(storageService.getAllSearchables()).thenReturn(List.of(article));

        var result = searchService.search("choose");

        assertEquals(1, result.size());
        SearchResult searchResult = result.iterator().next();
        assertEquals(title, searchResult.getName());
        assertEquals("ARTICLE", searchResult.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenPatternIsNull_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> searchService.search(null));
    }

    @Test
    void search_IsCaseInsensitive() {
        UUID id = UUID.randomUUID();
        Searchable product = new SimpleProduct("Apple", id, 100);
        when(storageService.getAllSearchables()).thenReturn(List.of(product));

        var result1 = searchService.search("app");
        var result2 = searchService.search("APP");

        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
    }
}