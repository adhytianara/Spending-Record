package com.tkap11.spendingrecord.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.container.FlexContainer;
import com.linecorp.bot.model.objectmapper.ModelObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

@Service
public class BotTemplate {

  public String escape(String text) {
    return StringEscapeUtils.escapeJson(text.trim());
  }

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
  public FlexMessage createFlexSisa(String category, String budget, String sisa) {
    FlexMessage flexMessage = new FlexMessage("Sisa Pengeluaran", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("sisaBudget.json")), encoding);

      flexTemplate = String.format(flexTemplate, escape(category),
          escape(budget), escape(sisa));

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Sisa Pengeluaran", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create sisa pengeluaran backup flex.
   */
  public FlexMessage createFlexSisaBackup(String category) {
    FlexMessage flexMessage = new FlexMessage("Sisa Pengeluaran [NOT FOUND]", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("sisaBudgetBackup.json")), encoding);

      flexTemplate = String.format(flexTemplate, escape(category));

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Sisa Pengeluaran [NOT FOUND]", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create sisa pengeluaran flex.
   */
  public FlexMessage createFlexSisaCategory() {
    FlexMessage flexMessage = new FlexMessage("Kategori Sisa Pengeluaran", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("sisaCategory.json")), encoding);

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

  /**
   * Create lihat laporan flex.
   */
  public FlexMessage createFlexLihatLaporan() {
    FlexMessage flexMessage = new FlexMessage("Lihat Laporan", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("lihatLaporan.json")), encoding);
      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Lihat Laporan", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create detail laporan of each category flex.
   */
  public FlexMessage createFlexDetailLaporan(String url, int day, int week, int month) {
    FlexMessage flexMessage = new FlexMessage("Detail Laporan", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("detailLaporan.json")), encoding);

      NumberFormat formatter = new DecimalFormat("#,###");
      String formatedDay = formatter.format(Double.valueOf(day));
      String formatedWeek = formatter.format(Double.valueOf(week));
      String formatedMonth = formatter.format(Double.valueOf(month));

      flexTemplate = String.format(flexTemplate,
          escape(url),
          escape(String.valueOf(formatedDay)),
          escape(String.valueOf(formatedWeek)),
          escape(String.valueOf(formatedMonth)));

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Detail Laporan", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }

  /**
   * Create persentage of laporan flex.
   */
  public FlexMessage createFlexDetailPersentase(
      String makanan, String transportasi, String tagihan, String belanja, String lainnya) {
    FlexMessage flexMessage = new FlexMessage("Detail Persentase", null);
    try {
      ClassLoader classLoader = getClass().getClassLoader();
      String encoding = StandardCharsets.UTF_8.name();
      String flexTemplate = IOUtils.toString(Objects.requireNonNull(
          classLoader.getResourceAsStream("detailPersentase.json")), encoding);

      flexTemplate = String.format(flexTemplate,
          escape(makanan) + "%",
          escape(transportasi) + "%",
          escape(tagihan) + "%",
          escape(belanja) + "%",
          escape(lainnya) + "%");

      ObjectMapper objectMapper = ModelObjectMapper.createNewObjectMapper();
      FlexContainer flexContainer = objectMapper.readValue(flexTemplate, FlexContainer.class);
      flexMessage = new FlexMessage("Detail Persentase", flexContainer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flexMessage;
  }
}
