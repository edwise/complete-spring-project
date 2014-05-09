package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by user EAnton on 24/04/2014.
 */
@ApiModel(value = "Author entity", description = "Complete info of a entity author")
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Author {

    private String name;
    private String surname;

    @ApiModelProperty(value = "The name of the author", required = true)
    public String getName() {
        return name;
    }

    @ApiModelProperty(value = "The surname of the author", required = false)
    public String getSurname() {
        return surname;
    }

    public Author copyFrom(Author other) {
        this.name = other.name;
        this.surname = other.surname;

        return this;
    }
}
