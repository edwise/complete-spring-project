package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by user EAnton on 25/04/2014.
 */
@ApiModel(value = "Publisher entity", description = "Complete info of a entity publisher")
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Publisher {

    @NotEmpty
    private String name;

    private String country;
    private boolean isOnline;

    @ApiModelProperty(value = "The name of the publisher", required = true)
    public String getName() {
        return name;
    }

    @ApiModelProperty(value = "The country of the publisher", required = true)
    public String getCountry() {
        return country;
    }

    @ApiModelProperty(value = "If the publisher is online or not", required = true)
    public boolean isOnline() {
        return isOnline;
    }

    public Publisher copyFrom(Publisher other) {
        this.name = other.name;
        this.country = other.country;
        this.isOnline = other.isOnline;

        return this;
    }
}
