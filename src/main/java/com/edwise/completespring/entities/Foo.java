package com.edwise.completespring.entities;

import com.edwise.completespring.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.joda.time.LocalDate;

/**
 * Created by user EAnton on 25/04/2014.
 */
@ApiModel(value = "Foo entity", description = "Complete info of a entity foo")
public class Foo {

    private Long id;
    private String sampleTextAttribute;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate sampleLocalDateAttribute;

    @ApiModelProperty(value = "Sample Id Attribute", required = true)
    public Long getId() {
        return id;
    }

    public Foo setId(Long id) {
        this.id = id;
        return this;
    }

    @ApiModelProperty(value = "Sample Text Attribute", required = true)
    public String getSampleTextAttribute() {
        return sampleTextAttribute;
    }

    public Foo setSampleTextAttribute(String sampleTextAttribute) {
        this.sampleTextAttribute = sampleTextAttribute;
        return this;
    }

    @ApiModelProperty(value = "Sample Local Date Attribute", required = true)
    public LocalDate getSampleLocalDateAttribute() {
        return sampleLocalDateAttribute;
    }

    public Foo setSampleLocalDateAttribute(LocalDate sampleLocalDateAttribute) {
        this.sampleLocalDateAttribute = sampleLocalDateAttribute;
        return this;
    }
}
