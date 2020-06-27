package com.tkap11.spendingrecord.service;

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
  void createFlexBudgetCategory() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexBudgetCategory();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexBudgetCategory();

  }

  @Test
  void createFlexSisaCategory() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexSisaCategory();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexSisaCategory();
  }

  @Test
  void createFlexSisa() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexSisa("category", "nominal", "sisa");
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexSisa("category", "nominal", "sisa");
  }

  @Test
  void createFlexSisaBackup() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexSisaBackup("category");
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexSisaBackup("category");
  }

  @Test
  void createFlexAlarm() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexAlarm("state");
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexAlarm("state");
  }

  @Test
  void createFlexUbah() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexUbah();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexUbah();
  }

  @Test
  void createFlexLihatLaporan() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexLihatLaporan();
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexLihatLaporan();
  }

  @Test
  void createFlexDetailLaporan() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexDetailLaporan(
        "url", 1, 2, 3
    );
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexDetailLaporan(
        "url", 1, 2, 3
    );
  }

  @Test
  void createFlexDetailPersentase() {
    when(botService.condition()).thenReturn(false);
    FlexMessage flexMessage = botTemplate.createFlexDetailPersentase(
        "a", "b", "c", "d", "e"
    );
    assertTrue(flexMessage instanceof FlexMessage);
    when(botService.condition()).thenReturn(true);
    botTemplate.createFlexDetailPersentase(
        "a", "b", "c", "d", "e"
    );
  }
}