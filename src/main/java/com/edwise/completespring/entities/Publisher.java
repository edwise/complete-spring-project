package com.edwise.completespring.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotEmpty;

@ApiModel(value = "Publisher entity", description = "Complete info of a entity publisher")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Publisher {

    @ApiModelProperty(value = "The name of the publisher", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "The country of the publisher", required = true)
    private String country;

    @ApiModelProperty(value = "If the publisher is online or not", required = true)
    private boolean isOnline;

    public Publisher copyFrom(Publisher other) {
        this.name = other.name;
        this.country = other.country;
        this.isOnline = other.isOnline;

        return this;
    }
}
