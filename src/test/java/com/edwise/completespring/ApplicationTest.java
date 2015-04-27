package com.edwise.completespring;

import com.edwise.completespring.entities.Book;
import com.edwise.completespring.entities.SequenceId;
import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.repositories.BookRepository;
import com.edwise.completespring.repositories.SequenceIdRepository;
import com.edwise.completespring.repositories.UserAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    private static final int TWO_TIMES = 2;
    private static final int FOUR_TIMES = 4;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private SequenceIdRepository sequenceRepository;

    @InjectMocks
    private Application application = new Application();

    @Test
    public void testRun() throws Exception {
        application.fillDBData(sequenceRepository, userAccountRepository, bookRepository);

        verifyRepositoriesCalls();
    }

    private void verifyRepositoriesCalls() {
        verify(sequenceRepository, times(TWO_TIMES)).save(any(SequenceId.class));
        verify(bookRepository).deleteAll();
        verify(bookRepository, times(FOUR_TIMES)).save(any(Book.class));
        verify(userAccountRepository).deleteAll();
        verify(userAccountRepository, times(TWO_TIMES)).save(any(UserAccount.class));
    }
}
