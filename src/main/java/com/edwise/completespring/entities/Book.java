package com.edwise.completespring.entities;

import com.edwise.completespring.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@Document(collection = "books")
@ApiModel(value = "Book entity", description = "Complete info of a entity book")
public class Book {

    @Id
    private Long id;

    private String title;

    private List<Author> authors;

    private String isbn;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate releaseDate;

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

    @ApiModelProperty(value = "The id of the book", required = false)
    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    @ApiModelProperty(value = "The title of the book", required = true)
    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    @ApiModelProperty(value = "The authors of the book", required = true)
    public List<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(List<Author> authors) {
        this.authors = authors;
        return this;
    }

    @ApiModelProperty(value = "The isbn of the book", required = true)
    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    @ApiModelProperty(value = "The release date of the book", required = true, allowableValues = "YYYY-MM-DD",
            dataType = "org.joda.time.LocalDate")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @ApiModelProperty(value = "The publisher of the book", required = true)
    public Publisher getPublisher() {
        return publisher;
    }

    public Book setPublisher(Publisher publisher) {
        this.publisher = publisher;
        return this;
    }

    public Book copyFrom(Book other) {
        this.title = other.title;
        if (other.authors != null) {
            this.authors = new ArrayList<>();
            for (Author author : other.authors) {
                this.authors.add(new Author().copyFrom(author));
            }
        }
        this.isbn = other.isbn;
        this.releaseDate = other.releaseDate;
        if (other.publisher != null) {
            this.publisher = new Publisher().copyFrom(other.publisher);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return !(authors != null ? !authors.equals(book.authors) : book.authors != null) && !(isbn != null ? !isbn.equals(book.isbn) :
                book.isbn != null) && !(publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) && !(releaseDate
                != null ? !releaseDate.equals(book.releaseDate) : book.releaseDate != null) && !(title != null ? !title.equals(book
                .title) : book.title != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
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
