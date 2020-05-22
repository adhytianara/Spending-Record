package com.tkap11.spendingrecord.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class BotTemplate {

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
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Action Menu", flexContainer);
    } catch (IOException e) {
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
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Sisa Pengeluaran", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create sisa pengeluaran flex.
   */
  public FlexMessage createFlexSisaKategori() {
    FlexMessage flexMessage = new FlexMessage("Kategori Sisa Pengeluaran", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
            classLoader.getResourceAsStream("sisaKategori.json")), encoding);

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Kategori Sisa Pengeluaran", flexContainer);
    } catch (IOException e) {
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
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Kategori pengeluaran", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create alarm flex.
   */
  public FlexMessage createFlexAlarm() {
    FlexMessage flexMessage = new FlexMessage("Ingatkan Saya", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
            classLoader.getResourceAsStream("ingatkanSaya.json")), encoding);

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Ingatkan Saya", flexContainer);
    } catch (IOException e) {
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
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Waktu Alarm", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

}
