package com.edwise.completespring.repositories.impl;

import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.exceptions.SequenceException;
import com.edwise.completespring.repositories.SequenceIdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SequenceIdRepositoryImpl implements SequenceIdRepository {

    @Autowired
    private MongoOperations mongoOperation;

    @Override
    public long getNextSequenceId(String key) {
        //get sequence id
        Query query = new Query(Criteria.where("_id").is(key));

        //increase sequence id by 1
        Update update = new Update();
        update.inc("seq", 1);

        //return new increased id
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        SequenceId seqId =
                mongoOperation.findAndModify(query, update, options, SequenceId.class);

        //if no id, throws SequenceException
        if (seqId == null) {
            log.error("Unable to get sequence id for key: {}", key);
            throw new SequenceException("Unable to get sequence id for key: " + key);
        }

        log.debug("Next sequendId: {}", seqId);
        return seqId.getSeq();
    }

    @Override
    public void save(SequenceId sequenceId) {
        log.debug("New sequenceId: {}", sequenceId);
        mongoOperation.save(sequenceId);
    }
}
