package com.edwise.completespring.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;

@ApiModel(value = "Foo entity", description = "Complete info of a entity foo")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Foo {

    @ApiModelProperty(value = "Sample Id Attribute", required = true)
    private Long id;

    @ApiModelProperty(value = "Sample Text Attribute", required = true)
    @NotNull
    private String sampleTextAttribute;

    @ApiModelProperty(value = "Sample Local Date Attribute", required = true)
    @NotNull
    private LocalDate sampleLocalDateAttribute;

    public Foo copyFrom(Foo other) {
        this.sampleTextAttribute = other.sampleTextAttribute;
        this.sampleLocalDateAttribute = other.sampleLocalDateAttribute;

        return this;
    }
}
