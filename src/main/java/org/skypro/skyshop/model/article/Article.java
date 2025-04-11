package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private final String title;
    private final String text;
    private final UUID id;

    @Override
    public UUID getId() {
        return id;
    }

    public Article(String title, String text, UUID id) {
        this.title = title;
        this.text = text;
        this.id = id;
    }

    @Override
    public String getName() {
        return title;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return toString();
    }

    @JsonIgnore
    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title) && Objects.equals(text, article.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, text);
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }
}
