package com.edwise.completespring.entities;

import com.edwise.completespring.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@Document(collection = "books")
@ApiModel(value = "Book entity", description = "Complete info of a entity book")
public class Book {

    @Id
    @ApiModelProperty(value = "The id of the book", required = false)
    private Long id;

    @ApiModelProperty(value = "The title of the book", required = true)
    private String title;

    @ApiModelProperty(value = "The authors of the book", required = true)
    private List<String> authors;

    @ApiModelProperty(value = "The isbn of the book", required = true)
    private String isbn;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @ApiModelProperty(value = "The release date of the book", required = true)
    private LocalDate releaseDate;

    public Book() {
    }

    public Book(Long id, String title, List<String> authors, String isbn, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public Book copyFrom(Book other) {
        if (StringUtils.isNotBlank(other.title)) {
            this.title = other.title;
        }
        if (other.authors != null && other.authors.size() > 0) {
            this.authors = other.authors;
        }
        if (StringUtils.isNotBlank(other.isbn)) {
            this.isbn = other.isbn;
        }
        if (other.releaseDate != null) {
            this.releaseDate = other.releaseDate;
        }

        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", isbn='" + isbn + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
