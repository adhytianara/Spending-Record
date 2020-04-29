package com.tkap11.spendingrecord.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventsModelTest {

    @Mock
    EventsModel eventsModel;

    @Test
    void getEvents() {
        when(eventsModel.getEvents())
                .thenReturn(new ArrayList<>());
        assertTrue(eventsModel.getEvents() instanceof List);
    }
}