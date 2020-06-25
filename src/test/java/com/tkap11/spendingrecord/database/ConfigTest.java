package com.tkap11.spendingrecord.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class ConfigTest {
  @Mock
  private Environment env;

  @InjectMocks
  private Config config;

  @Mock
  private LineMessagingClient lineMessagingClient;

  @Test
  void getChannelSecret() {
    when(env.getProperty("com.linecorp.channel_secret")).thenReturn("channel secret");
    config.getChannelSecret();
    verify(env).getProperty("com.linecorp.channel_secret");
  }

  @Test
  void getChannelAccessToken() {
    when(env.getProperty("com.linecorp.channel_access_token")).thenReturn("channel access token");
    config.getChannelAccessToken();
    verify(env).getProperty("com.linecorp.channel_access_token");
  }

  @Test
  void getMessagingClient() {
    when(env.getProperty("com.linecorp.channel_access_token")).thenReturn("channel access token");
    config.getMessagingClient();
    verify(env).getProperty("com.linecorp.channel_access_token");
  }

  @Test
  void getSignatureValidator() {
    when(env.getProperty("com.linecorp.channel_secret")).thenReturn("channel secret");
    LineSignatureValidator lineSignatureValidator = config.getSignatureValidator();
    Assert.assertNotNull(lineSignatureValidator);
  }

  @Test
  void getDataSource() {
    DataSource source = config.getDataSource();
    Assert.assertNotNull(source);
  }

  @Test
  void getUserDao() {
    UserDao userDao = config.getUserDao();
    Assert.assertNotNull(userDao);
  }

  @Test
  void getSpendingDao() {
    SpendingDao spendingDao = config.getSpendingDao();
    Assert.assertNotNull(spendingDao);
  }
}