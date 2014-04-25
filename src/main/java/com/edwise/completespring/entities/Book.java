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

    @ApiModelProperty(value = "The authors of the book", required = true, dataType = "java.util.List")
    private List<Author> authors;

    @ApiModelProperty(value = "The isbn of the book", required = true)
    private String isbn;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @ApiModelProperty(value = "The release date of the book", required = true)
    private LocalDate releaseDate;

    @ApiModelProperty(value = "The publisher of the book", required = true)
    private Publisher publisher;

    public Book() {
    }

    public Book(Long id, String title, List<Author> authors, String isbn, LocalDate releaseDate, Publisher publisher) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public Book setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public Book copyFrom(Book other) {
        if (StringUtils.isNotBlank(other.title)) {
            this.title = other.title;
        }
        if (other.authors != null && other.authors.size() > 0) {
            // TODO revisar si hacer un "clone" o similar para los authors
            this.authors = other.authors;
        }
        if (StringUtils.isNotBlank(other.isbn)) {
            this.isbn = other.isbn;
        }
        if (other.releaseDate != null) {
            this.releaseDate = other.releaseDate;
        }

        if (other.publisher != null) {
            // TODO revisar si hacer un "clone" o similar para los publisher
            this.publisher = other.publisher;
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
                ", publisher=" + publisher +
                '}';
    }
}
