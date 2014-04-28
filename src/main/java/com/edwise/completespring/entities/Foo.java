package com.edwise.completespring.entities;

import com.edwise.completespring.util.CustomLocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
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

    public Foo copyFrom(Foo other) {
        if (StringUtils.isNotBlank(other.sampleTextAttribute)) {
            this.sampleTextAttribute = other.sampleTextAttribute;
        }
        if (other.sampleLocalDateAttribute != null) {
            this.sampleLocalDateAttribute = other.sampleLocalDateAttribute;
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Foo foo = (Foo) o;
        return !(sampleLocalDateAttribute != null ? !sampleLocalDateAttribute.equals(foo.sampleLocalDateAttribute) : foo
                .sampleLocalDateAttribute != null) && !(sampleTextAttribute != null ? !sampleTextAttribute.equals(foo
                .sampleTextAttribute) : foo.sampleTextAttribute != null);

    }

    @Override
    public int hashCode() {
        int result = sampleTextAttribute != null ? sampleTextAttribute.hashCode() : 0;
        result = 31 * result + (sampleLocalDateAttribute != null ? sampleLocalDateAttribute.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                ", sampleTextAttribute='" + sampleTextAttribute + '\'' +
                ", sampleLocalDateAttribute=" + sampleLocalDateAttribute +
                '}';
    }
}
