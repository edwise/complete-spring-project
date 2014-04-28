package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

/**
 * Created by user EAnton on 24/04/2014.
 */
@ApiModel(value = "Author entity", description = "Complete info of a entity author")
public class Author {

    private String name;

    private String surname;

    @ApiModelProperty(value = "The name of the author", required = true)
    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    @ApiModelProperty(value = "The surname of the author", required = false)
    public String getSurname() {
        return surname;
    }

    public Author setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Author copyFrom(Author other) {
        if (StringUtils.isNotBlank(other.name)) {
            this.name = other.name;
        }
        if (StringUtils.isNotBlank(other.surname)) {
            this.surname = other.surname;
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return !(name != null ? !name.equals(author.name) : author.name != null) && !(surname != null ? !surname.equals(author.surname) :
                author.surname != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
