package com.edwise.completespring.exceptions.helpers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by EAnton on 09/05/2014.
 */
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ErrorInfo {
    private String url;
    private String errors;
}
