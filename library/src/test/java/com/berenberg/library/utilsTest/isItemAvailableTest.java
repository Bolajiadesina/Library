package com.berenberg.library.utilsTest;

import com.berenberg.library.service.CsvServiceImplementation;
import com.berenberg.library.service.LibraryServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.io.IOException;


@ExtendWith(MockitoExtension.class)
class isItemAvailableTest {

    @Mock
    CsvServiceImplementation csvServiceImplementation;

    @InjectMocks
    LibraryServiceImpl libraryService;


    @Test
    void checkIfParticularItemIsAvailable() throws IOException{

        //given

        String id = "dummyId";
        doNothing().when(csvServiceImplementation).confirmAvailability(id);
        //when
        libraryService.checkItemAvailability(id);


        //then
        verify(csvServiceImplementation, times(1)).confirmAvailability(id);
    }
}
