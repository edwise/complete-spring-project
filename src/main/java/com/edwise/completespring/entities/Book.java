package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

@Document(collection = "books")
@ApiModel(value = "Book entity", description = "Complete info of a entity book")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Book {

    @ApiModelProperty(value = "The id of the book", required = false)
    @Id
    private Long id;

    @ApiModelProperty(value = "The title of the book", required = true)
    @NotEmpty
    private String title;

    @ApiModelProperty(value = "The authors of the book", required = true)
    @Valid
    private List<Author> authors;

    @ApiModelProperty(value = "The isbn of the book", required = true)
    @NotEmpty
    private String isbn;

    @ApiModelProperty(value = "The release date of the book", required = true, allowableValues = "YYYY-MM-DD",
            dataType = "org.joda.time.LocalDate")
    @NotNull
    private LocalDate releaseDate;

    @ApiModelProperty(value = "The publisher of the book", required = true)
    @Valid
    @NotNull
    private Publisher publisher;

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
