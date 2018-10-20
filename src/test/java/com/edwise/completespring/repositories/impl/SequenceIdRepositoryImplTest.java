package com.edwise.completespring.repositories.impl;

import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.exceptions.SequenceException;
import com.edwise.completespring.services.impl.BookServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

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

        assertThat(seqId).as("Should return a valid sequence").isGreaterThan(0);
        verify(mongoOperation, times(ONE_TIME)).findAndModify(any(Query.class), any(Update.class),
                any(FindAndModifyOptions.class), eq(SequenceId.class));
    }

    @Test
    public void testGetNextSequenceIdWhenNotExistsSequence() {
        when(mongoOperation.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class),
                eq(SequenceId.class))).thenReturn(null);

        Throwable thrown = catchThrowable(() -> repository.getNextSequenceId(BookServiceImpl.BOOK_COLLECTION));

        assertThat(thrown)
                .isInstanceOf(SequenceException.class)
                .hasMessageContaining("Unable to get sequence id for key");
    }

    @Test
    public void testSave() {
        repository.save(new SequenceId().setId(BookServiceImpl.BOOK_COLLECTION).setSeq(TEST_SEQUENCE_ID));

        verify(mongoOperation, times(ONE_TIME)).save(any(SequenceId.class));
    }
}