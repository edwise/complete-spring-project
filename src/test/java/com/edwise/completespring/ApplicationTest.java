package com.edwise.completespring;

import com.edwise.completespring.dbutils.DataLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {
    
    @Mock
    private DataLoader dataLoader;

    @InjectMocks
    private Application application;

    @Test
    public void testInitAppLoadingDBData() {
        application.initApp(true);

        verify(dataLoader).fillDBData();
    }

    @Test
    public void testInitAppWithoutLoadDBData() {
        application.initApp(false);

        verifyZeroInteractions(dataLoader);
    }

}
