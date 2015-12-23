package com.edwise.completespring.repositories.impl;

import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.exceptions.SequenceException;
import com.edwise.completespring.services.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequenceIdRepositoryImplTest {

    private static final long TEST_SEQUENCE_ID = 7L;
    private static final int ONE_TIME = 1;

    @Mock
    private MongoOperations mongoOperation;

    @InjectMocks
    private SequenceIdRepositoryImpl repository = new SequenceIdRepositoryImpl();

    @Test
    public void testGetNextSequenceId() {
        when(mongoOperation.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
                eq(SequenceId.class))).thenReturn(new SequenceId().setId(BookServiceImpl.BOOK_COLLECTION).setSeq(6L));

        long seqId = repository.getNextSequenceId(BookServiceImpl.BOOK_COLLECTION);

        verify(mongoOperation, times(ONE_TIME)).findAndModify(any(Query.class), any(Update.class),
                any(FindAndModifyOptions.class), eq(SequenceId.class));
        assertTrue("Debe devolver un sequence valido", seqId > 0);
    }

    @Test(expected = SequenceException.class)
    public void testGetNextSequenceIdWhenNotExistsSequence() {
        when(mongoOperation.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
                eq(SequenceId.class))).thenReturn(null);

        repository.getNextSequenceId(BookServiceImpl.BOOK_COLLECTION);
    }

    @Test
    public void testSave() throws Exception {
        repository.save(new SequenceId().setId(BookServiceImpl.BOOK_COLLECTION).setSeq(TEST_SEQUENCE_ID));

        verify(mongoOperation, times(ONE_TIME)).save(any(SequenceId.class));
    }
}