package com.tkap11.spendingrecord.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotTemplate {

  @Autowired
  BotService botService;

  /**
   * Create menu flex.
   */
  public FlexMessage createFlexMenu() {
    FlexMessage flexMessage = new FlexMessage("Action Menu", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("menu.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      objectMapper = botService.condition() ? null : objectMapper;
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Action Menu", flexContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create sisa budget flex.
   */
  public FlexMessage createFlexSisa() {
    FlexMessage flexMessage = new FlexMessage("Sisa Pengeluaran", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("sisaBudget.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      objectMapper = botService.condition() ? null : objectMapper;
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Sisa Pengeluaran", flexContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create category flex.
   */
  public FlexMessage createFlexChooseCategory() {
    FlexMessage flexMessage = new FlexMessage("Kategori pengeluaran", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("ChooseCategory.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      objectMapper = botService.condition() ? null : objectMapper;
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Kategori pengeluaran", flexContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create remind me flex.
   */
  public FlexMessage createFlexAlarm() {
    FlexMessage flexMessage = new FlexMessage("Ingatkan Saya", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("ingatkanSaya.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      objectMapper = botService.condition() ? null : objectMapper;
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Ingatkan Saya", flexContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create change alarm flex.
   */
  public FlexMessage createFlexUbah() {
    FlexMessage flexMessage = new FlexMessage("Waktu Alarm", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("waktuAlarm.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      objectMapper = botService.condition() ? null : objectMapper;
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Waktu Alarm", flexContainer);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

}
