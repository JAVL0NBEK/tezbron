//package com.example.bron.config;
//
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class BronManager {
//
//  // Kategoriyalar ro'yxati
//  private static final List<String> CATEGORIES = List.of(
//      "Mini Stadion",
//      "Banket Zal",
//      "Game Club",
//      "Startaroshxona"
//  );
//
//  // Har kategoriya uchun stadionlar ro'yxati (keyincha database dan oling)
//  private static final Map<String, List<String>> CATEGORY_STADIUMS = new HashMap<>();
//  static {
//    CATEGORY_STADIUMS.put("mini stadion", List.of(
//        "🏟 Mini Stadion 1 - 100.000 so'm/soat",
//        "🏟 Mini Stadion 2 - 120.000 so'm/soat"
//    ));
//    CATEGORY_STADIUMS.put("Banket Zal", List.of(
//        "🏛️ Banket Zal A - 500.000 so'm/soat",
//        "🏛️ Banket Zal B - 600.000 so'm/soat"
//    ));
//    CATEGORY_STADIUMS.put("Game Club", List.of(
//        "🎮 Game Club Zone 1 - 50.000 so'm/soat",
//        "🎮 Game Club Zone 2 - 70.000 so'm/soat"
//    ));
//    CATEGORY_STADIUMS.put("Startaroshxona", List.of(
//        "🚀 Startaroshxona Room 1 - 200.000 so'm/soat",
//        "🚀 Startaroshxona Room 2 - 250.000 so'm/soat"
//    ));
//  }
//
//  // Foydalanuvchi holatini saqlash (chatId -> kategoriya)
//  private final Map<Long, String> userStates = new HashMap<>();
//
//  public SendMessage startBot(Long chatId) {
//    // Holatni tozalash
//    userStates.remove(chatId);
//    return sendCategoryMenu(chatId);
//  }
//
//  // Kategoriyalar menyusini yuborish
//  public SendMessage sendCategoryMenu(Long chatId) {
//    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//
//    // Kategoriyalar tugmalari (har biri alohida qator)
//    for (String category : CATEGORIES) {
//      List<InlineKeyboardButton> row = new ArrayList<>();
//      InlineKeyboardButton button = InlineKeyboardButton.builder()
//          .text(category)
//          .callbackData("category_" + category.toLowerCase().replace(" ", "_"))  // Masalan: "category_mini_stadion"
//          .build();
//      row.add(button);
//      rows.add(row);
//    }
//
//    // Oxirgi qator: Aloqa va Menyu
//    List<InlineKeyboardButton> bottomRow = new ArrayList<>();
//    InlineKeyboardButton contactButton = InlineKeyboardButton.builder()
//        .text("📞 Aloqa")
//        .callbackData("contacts")
//        .build();
//    bottomRow.add(contactButton);
//    InlineKeyboardButton menuButton = InlineKeyboardButton.builder()
//        .text("🏠 Menyu")
//        .callbackData("menu")
//        .build();
//    bottomRow.add(menuButton);
//    rows.add(bottomRow);
//
//    InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(rows).build();
//
//    return SendMessage.builder()
//        .chatId(chatId.toString())
//        .text("Salom! Qaysi kategoriyadan bron qilmoqchisiz?")
//        .replyMarkup(markup)
//        .build();
//  }
//
//  // Stadionlar ro'yxatini yuborish (kategoriya bo'yicha)
//  public SendMessage sendStadiumsByCategory(Long chatId, String category) {
//    List<String> stadiums = CATEGORY_STADIUMS.getOrDefault(category, List.of("Hech qanday stadion topilmadi."));
//    userStates.put(chatId, category);  // Holatni saqlash
//
//    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//
//    // Stadion tugmalari
//    for (String stadium : stadiums) {
//      List<InlineKeyboardButton> row = new ArrayList<>();
//      InlineKeyboardButton button = InlineKeyboardButton.builder()
//          .text(stadium)
//          .callbackData("stadium_" + stadium.toLowerCase().replaceAll("[^a-z0-9 ]", "").replace(" ", "_"))  // Masalan: "stadium_mini_stadion_1_100000_soms"
//          .build();
//      row.add(button);
//      rows.add(row);
//    }
//
//    // Orqaga tugmasi
//    List<InlineKeyboardButton> backRow = new ArrayList<>();
//    InlineKeyboardButton backButton = InlineKeyboardButton.builder()
//        .text("🔙 Orqaga")
//        .callbackData("back_to_categories")
//        .build();
//    backRow.add(backButton);
//    rows.add(backRow);
//
//    InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(rows).build();
//
//    return SendMessage.builder()
//        .chatId(chatId.toString())
//        .text(category + " bo'yicha mavjud joylar:")
//        .replyMarkup(markup)
//        .build();
//  }
//
//
//  // Stadion tanlanganda keyingi qadam (vaqt tanlash)
//  public SendMessage handleStadiumSelection(Long chatId, String stadiumData) {
//    String category = userStates.get(chatId);
//    if (category == null) {
//      return createMessage(chatId, "Xatolik: Kategoriya tanlanmagan. /start dan boshlang.");
//    }
//
//    // Vaqt tanlash menyusi (masalan, soatlar)
//    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
//    for (int hour = 9; hour <= 22; hour++) {  // 9:00 dan 22:00 gacha
//      List<InlineKeyboardButton> row = new ArrayList<>();
//      InlineKeyboardButton button = InlineKeyboardButton.builder()
//          .text(hour + ":00 soat")
//          .callbackData("time_" + hour + "_" + stadiumData.replace(" ", "_"))
//          .build();
//      row.add(button);
//      rows.add(row);
//    }
//
//    // Orqaga
//    List<InlineKeyboardButton> backRow = new ArrayList<>();
//    backRow.add(InlineKeyboardButton.builder().text("🔙 Orqaga").callbackData("back_to_stadiums").build());
//    rows.add(backRow);
//
//    InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder().keyboard(rows).build();
//
//    return SendMessage.builder()
//        .chatId(chatId.toString())
//        .text("Qaysi vaqtda bron qilmoqchisiz? (Stadion: " + stadiumData + ")")
//        .replyMarkup(markup)
//        .build();
//  }
//
//  // Vaqt tanlanganda bron tasdiqlash (masalan, kontakt so'rash)
//  public SendMessage handleTimeSelection(Long chatId, String timeData) {
//    // Bu yerda bron ma'lumotlarini saqlang (database yoki faylga)
//    return createMessage(chatId, "Bron muvaffaqiyatli! Admin sizga bog'lanadi. Vaqt: " + timeData + "\nKontaktni yuboring: /contact");
//  }
//
//  // Callback uchun javob (kengaytirilgan)
//  public SendMessage handleCallback(Long chatId, String data) {
//    String lowerData = data.toLowerCase();
//    return switch (lowerData) {
//      case "menu" -> startBot(chatId);  // Asosiy menyuga qaytish
//      case "contacts" -> createMessage(chatId, "Aloqa: +998901234567 | Email: info@tezbron.uz");
//      case "back_to_categories" -> sendCategoryMenu(chatId);
//      case "back_to_stadiums" -> {
//        String category = userStates.get(chatId);
//        yield category != null ? sendStadiumsByCategory(chatId, category) : startBot(chatId);
//      }
//      default -> {
//        if (lowerData.startsWith("category_")) {
//          String category = data.substring(9).replace("_", " ");  // "category_mini_stadion" dan "Mini Stadion" olish
//          yield sendStadiumsByCategory(chatId, category);
//        } else if (lowerData.startsWith("stadium_")) {
//          String stadium = data.substring(8).replace("_", " ");  // Stadion nomini olish
//          yield handleStadiumSelection(chatId, stadium);
//        } else if (lowerData.startsWith("time_")) {
//          String time = data.substring(5).replace("_", ":");  // "time_10_mini_stadion" dan "10:Mini Stadion"
//          yield handleTimeSelection(chatId, time);
//        } else {
//          yield createMessage(chatId, "Noma'lum buyruq! /start dan boshlang.");
//        }
//      }
//    };
//  }
//
//  public SendMessage createMessage(Long chatId, String text) {
//    return SendMessage.builder()
//        .chatId(chatId.toString())
//        .text(text)
//        .build();
//  }
//
//  // /stadiums buyrug'i endi ishlatilmaydi, lekin saqlash mumkin
//  public SendMessage sendStadiumList(Long chatId) {
//    return sendCategoryMenu(chatId);  // Endi kategoriyalarga yo'naltirish
//  }
//}