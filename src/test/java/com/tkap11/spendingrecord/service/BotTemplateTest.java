package com.tkap11.spendingrecord.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.linecorp.bot.model.message.FlexMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BotTemplateTest {

  @InjectMocks
  private BotTemplate botTemplate;

  @Mock
  private BotService botService;


  @Test
  void createFlexMenu() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexMenu();
  }

  @Test
  void createFlexChooseCategory() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexChooseCategory();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexChooseCategory();
  }

  @Test
  void createFlexSisa() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexSisa();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexSisa();
  }

  @Test
  void createFlexAlarm() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexAlarm();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexAlarm();
  }

  @Test
  void createFlexUbah() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexUbah();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexUbah();
  }
}