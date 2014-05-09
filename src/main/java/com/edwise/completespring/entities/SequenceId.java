package com.edwise.completespring.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by user EAnton on 07/04/2014.
 */
@Document(collection = "sequences")
@Setter @Getter
@Accessors(chain = true)
@ToString(doNotUseGetters = true)
public class SequenceId {
    @Id
    private String id;
    private long seq;

}
