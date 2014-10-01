package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.SequenceId;

public interface SequenceIdRepository {
    void save(SequenceId sequenceId);

    long getNextSequenceId(String key);
}
