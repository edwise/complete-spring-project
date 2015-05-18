package com.edwise.completespring.exceptions.helpers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
class ErrorItem implements Serializable {
    private static final long serialVersionUID = 582572111791708597L;

    private String field;
    private String message;
}