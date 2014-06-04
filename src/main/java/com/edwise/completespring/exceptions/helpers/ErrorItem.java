package com.edwise.completespring.exceptions.helpers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Edu on 04/06/2014.
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
class ErrorItem {
    protected String field;
    protected String message;
}