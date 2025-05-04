package org.skypro.skyshop.model.search;

import org.skypro.skyshop.model.SearchResult;
import org.skypro.skyshop.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Search pattern cannot be null");
        }

        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm().toLowerCase()
                        .contains(pattern.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}