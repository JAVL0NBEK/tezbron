package com.example.bron.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

  @Value("${telegram.bot.token}")
  private String botToken;

  @Bean
  public BronBot bronBot(BronManager bronManager) {
    return new BronBot(botToken, bronManager);
  }

  @Bean
  public TelegramBotsApi telegramBotsApi(BronBot bronBot) throws TelegramApiException {
    var botsApi = new TelegramBotsApi(DefaultBotSession.class);
    botsApi.registerBot(bronBot);
    return botsApi;
  }
}
