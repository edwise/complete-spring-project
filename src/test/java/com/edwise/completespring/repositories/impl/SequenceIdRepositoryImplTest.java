package com.edwise.completespring.repositories.impl;

import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.exceptions.SequenceException;
import com.edwise.completespring.services.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SequenceIdRepositoryImplTest {

    private static final long TEST_SEQUENCE_ID = 7l;
    private static final int ONE_TIME = 1;

    private SequenceIdRepositoryImpl repository;

    @Mock
    private MongoOperations mongoOperation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new SequenceIdRepositoryImpl();
        ReflectionTestUtils.setField(this.repository, "mongoOperation", this.mongoOperation);
    }

    @Test
    public void testGetNextSequenceId() {
        when(mongoOperation.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
                eq(SequenceId.class))).thenReturn(new SequenceId().setId(BookServiceImpl.BOOK_COLLECTION).setSeq(6l));

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