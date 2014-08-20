package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.SequenceId;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface SequenceIdRepository {
    void save(SequenceId sequenceId);

    long getNextSequenceId(String key);
}
