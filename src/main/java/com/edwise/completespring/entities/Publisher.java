package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Created by user EAnton on 25/04/2014.
 */
@ApiModel(value = "Publisher entity", description = "Complete info of a entity publisher")
public class Publisher {

    private String name;

    private String country;

    private boolean isOnline;

    @ApiModelProperty(value = "The name of the publisher", required = true)
    public String getName() {
        return name;
    }

    public Publisher setName(String name) {
        this.name = name;
        return this;
    }

    @ApiModelProperty(value = "The country of the publisher", required = true)
    public String getCountry() {
        return country;
    }

    public Publisher setCountry(String country) {
        this.country = country;
        return this;
    }

    @ApiModelProperty(value = "If the publisher is online or not", required = true)
    public boolean isOnline() {
        return isOnline;
    }

    public Publisher setOnline(boolean isOnline) {
        this.isOnline = isOnline;
        return this;
    }
}
