package com.tkap11.spendingrecord.database;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineMessagingClientBuilder;
import com.linecorp.bot.client.LineSignatureValidator;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {
  @Autowired
  private Environment env;

  @Bean(name = "com.linecorp.channel_secret")
  public String getChannelSecret() {
    return env.getProperty("com.linecorp.channel_secret");
  }

  @Bean(name = "com.linecorp.channel_access_token")
  public String getChannelAccessToken() {
    return env.getProperty("com.linecorp.channel_access_token");
  }

  /**
   * Line Messaging Client.
   */
  @Bean(name = "lineMessagingClient")
  public LineMessagingClient getMessagingClient() {
    return LineMessagingClient
        .builder(getChannelAccessToken())
        .apiEndPoint(LineMessagingClientBuilder.DEFAULT_API_END_POINT)
        .connectTimeout(LineMessagingClientBuilder.DEFAULT_CONNECT_TIMEOUT)
        .readTimeout(LineMessagingClientBuilder.DEFAULT_READ_TIMEOUT)
        .writeTimeout(LineMessagingClientBuilder.DEFAULT_WRITE_TIMEOUT)
        .build();
  }

  @Bean(name = "lineSignatureValidator")
  public LineSignatureValidator getSignatureValidator() {
    return new LineSignatureValidator(getChannelSecret().getBytes());
  }

  @Bean
  DataSource getDataSource() {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
    String username = System.getenv("JDBC_DATABASE_USERNAME");
    String password = System.getenv("JDBC_DATABASE_PASSWORD");

    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(dbUrl);
    ds.setUsername(username);
    ds.setPassword(password);

    return ds;
  }

  @Bean
  public UserDao getUserDao() {
    return new UserDaoImplementation(getDataSource());
  }

  @Bean
  public SpendingDao getSpendingDao() {
    return new SpendingDaoImplementation(getDataSource());
  }

  @Bean
  public BudgetDao getBudgetDao() {
    return new BudgetDaoImplementation(getDataSource());
  }
}