package com.tkap11.spendingrecord.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.FlexMessage;
import org.junit.jupiter.api.Test;

class BotTemplateTest {
  private final BotTemplate botTemplate = new BotTemplate();

  @Test
  void createFlexMenu() {
    FlexMessage flexMessage = botTemplate.createFlexMenu();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexChooseCategory() {
    FlexMessage flexMessage = botTemplate.createFlexChooseCategory();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexBudgetCategory() {
    FlexMessage flexMessage = botTemplate.createFlexBudgetCategory();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexSisaCategory() {
    FlexMessage flexMessage = botTemplate.createFlexSisaCategory();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexSisa() {
    FlexMessage flexMessage = botTemplate.createFlexSisa("category", "nominal", "sisa");
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexSisaBackup() {
    FlexMessage flexMessage = botTemplate.createFlexSisaBackup("category");
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexAlarm() {
    FlexMessage flexMessage = botTemplate.createFlexAlarm("state");
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexUbah() {
    FlexMessage flexMessage = botTemplate.createFlexUbah();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexLihatLaporan() {
    FlexMessage flexMessage = botTemplate.createFlexLihatLaporan();
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexDetailLaporan() {
    FlexMessage flexMessage = botTemplate.createFlexDetailLaporan(
        "url", 1, 2, 3
    );
    assertTrue(flexMessage instanceof FlexMessage);
  }

  @Test
  void createFlexDetailPersentase() {
    FlexMessage flexMessage = botTemplate.createFlexDetailPersentase(
        "a", "b", "c", "d", "e"
    );
    assertTrue(flexMessage instanceof FlexMessage);
  }
}