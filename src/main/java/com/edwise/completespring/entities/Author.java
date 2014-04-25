package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by user EAnton on 24/04/2014.
 */
@ApiModel(value = "Author entity", description = "Complete info of a entity author")
public class Author {

    @ApiModelProperty(value = "The name of the author", required = true)
    private String name;

    @ApiModelProperty(value = "The surname of the author", required = false)
    private String surname;

    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Author setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
