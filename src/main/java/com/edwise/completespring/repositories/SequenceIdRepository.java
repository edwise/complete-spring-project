package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.exceptions.SequenceException;

/**
 * Created by user EAnton on 07/04/2014.
 */
public interface SequenceIdRepository {
    public void save(SequenceId sequenceId);

    long getNextSequenceId(String key) throws SequenceException;
}
