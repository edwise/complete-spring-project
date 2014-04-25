package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by user EAnton on 25/04/2014.
 */
@ApiModel(value = "Publisher entity", description = "Complete info of a entity publisher")
public class Publisher {

    @ApiModelProperty(value = "The name of the publisher", required = true)
    private String name;

    @ApiModelProperty(value = "The country of the publisher", required = true)
    private String country;

    @ApiModelProperty(value = "If the publisher is online or not", required = true)
    private boolean isOnline;

    public String getName() {
        return name;
    }

    public Publisher setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Publisher setCountry(String country) {
        this.country = country;
        return this;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Publisher setOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }
}
