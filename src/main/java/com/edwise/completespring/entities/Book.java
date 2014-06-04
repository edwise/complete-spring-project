package com.edwise.completespring.entities;

import com.edwise.completespring.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user EAnton on 04/04/2014.
 */
@Document(collection = "books")
@ApiModel(value = "Book entity", description = "Complete info of a entity book")
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Book {

    @Id
    private Long id;

    @NotEmpty
    private String title;

    @Valid
    private List<Author> authors;

    @NotEmpty
    private String isbn;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @NotNull
    private LocalDate releaseDate;

    @Valid
    @NotNull
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

    @ApiModelProperty(value = "The title of the book", required = true)
    public String getTitle() {
        return title;
    }

    @ApiModelProperty(value = "The authors of the book", required = true)
    public List<Author> getAuthors() {
        return authors;
    }

    @ApiModelProperty(value = "The isbn of the book", required = true)
    public String getIsbn() {
        return isbn;
    }

    @ApiModelProperty(value = "The release date of the book", required = true, allowableValues = "YYYY-MM-DD",
            dataType = "org.joda.time.LocalDate")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    @ApiModelProperty(value = "The publisher of the book", required = true)
    public Publisher getPublisher() {
        return publisher;
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
}
