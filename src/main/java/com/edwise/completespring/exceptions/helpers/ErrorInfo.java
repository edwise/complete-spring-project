package com.edwise.completespring.exceptions.helpers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.validation.BindingResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ErrorInfo implements Serializable {

    private static final long serialVersionUID = 673514291953827696L;

    @Getter
    @Setter
    private String url;

    @Getter
    private List<ErrorItem> errors = new ArrayList<>();

    public ErrorInfo addError(String field, String message) {
        errors.add(new ErrorItem().setField(field).setMessage(message));
        return this;
    }

    public static ErrorInfo generateErrorInfoFromBindingResult(BindingResult errors) {
        ErrorInfo errorInfo = new ErrorInfo();
        errors.getFieldErrors()
                .forEach(fieldError -> errorInfo.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return errorInfo;
    }
}
