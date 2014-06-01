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
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by user EAnton on 25/04/2014.
 */


@ApiModel(value = "Foo entity", description = "Complete info of a entity foo")
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Foo {

    private Long id;

    @NotNull
    private String sampleTextAttribute;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate sampleLocalDateAttribute;

    @ApiModelProperty(value = "Sample Id Attribute", required = true)
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Sample Text Attribute", required = true)
    public String getSampleTextAttribute() {
        return sampleTextAttribute;
    }

    @ApiModelProperty(value = "Sample Local Date Attribute", required = true)
    public LocalDate getSampleLocalDateAttribute() {
        return sampleLocalDateAttribute;
    }

    public Foo copyFrom(Foo other) {
        this.sampleTextAttribute = other.sampleTextAttribute;
        this.sampleLocalDateAttribute = other.sampleLocalDateAttribute;

        return this;
    }
}
