package com.edwise.completespring.exceptions.helpers;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EAnton on 09/05/2014.
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ErrorInfo {
    private String url;
    private List<ErrorItem> errors;

    public ErrorInfo addError(String field, String message) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new ErrorItem().setField(field).setMessage(message));
        return this;
    }
}
