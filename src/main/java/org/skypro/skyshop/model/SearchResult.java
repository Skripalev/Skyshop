package org.skypro.skyshop.model;

import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public class SearchResult {
    private final String searchTerm;
    private final String name;
    private final String contentType;
    private final UUID id;

    private SearchResult(String name, String contentType, UUID id, String searchTerm) {
        this.name = name;
        this.contentType = contentType;
        this.id = id;
        this.searchTerm = searchTerm;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(
                searchable.getName(),
                searchable.getContentType(),
                searchable.getId(),
                searchable.getSearchTerm()
        );
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public UUID getId() {
        return id;
    }
}
