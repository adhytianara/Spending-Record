package com.tkap11.spendingrecord.model;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventsModelTest {

  @InjectMocks
  EventsModel eventsModel;

  @Test
  void getEvents() {
    assertTrue(eventsModel.getEvents() instanceof List);
  }
}