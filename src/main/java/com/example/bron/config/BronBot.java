package com.example.bron.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class BronBot extends TelegramLongPollingBot {

  private final BronManager bronManager;

  public BronBot(@Value("${telegram.bot.token}") String botToken, BronManager bronManager) {
    super(botToken);
    this.bronManager = bronManager;
  }

  @Override
  public String getBotUsername() {
    return "TezBron_bot";
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      if (update.hasMessage() && update.getMessage().hasText()) {
        handleTextMessage(update.getMessage());
      } else if (update.hasCallbackQuery()) {
        var callback = update.getCallbackQuery();
        Long chatId = callback.getMessage() != null ? callback.getMessage().getChatId() : callback.getFrom().getId();
        String data = callback.getData();
        if (data != null) {
          execute(bronManager.handleCallback(chatId, data));
        }
      }
    } catch (Exception e) {
      log.error("Xatolik yuz berdi: {}", e.getMessage());
    }
  }

  private void handleTextMessage(Message message) throws TelegramApiException {
    Long chatId = message.getChatId();
    String text = message.getText();

    switch (text.toLowerCase()) {
      case "/start":
        execute(bronManager.startBot(chatId));
        break;
      case "/stadiums":
        execute(bronManager.sendStadiumList(chatId));
        break;
      case "/share":
        execute(bronManager.createMessage(chatId, "Bu Share button edi"));
        break;
      default:
        execute(bronManager.createMessage(chatId, "Noto‘g‘ri buyruq! /start yozing."));
    }
  }
}
