package com.edwise.completespring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by user EAnton on 07/04/2014.
 */
@Document(collection = "sequences")
public class SequenceId {
    @Id
    private String id;

    private long seq;

    public SequenceId() {
    }

    public SequenceId(String id, long seq) {
        this.id = id;
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
