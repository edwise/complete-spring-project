package com.edwise.completespring;

import com.edwise.completespring.dbutils.DataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
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

        verifyNoInteractions(dataLoader);
    }

}
