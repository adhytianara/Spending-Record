package com.tkap11.spendingrecord.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventsModelTest {

    @InjectMocks
    EventsModel eventsModel;

    @Test
    void getEvents() {
        assertTrue(eventsModel.getEvents() instanceof List);
    }
}