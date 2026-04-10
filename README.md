# TezBron

TezBron - bu sport maydonchalari (stadionlar) ni qulay uskunalar yordamida band qilish (booking), jamoalar tuzish va turli xil sport o'yinlari/turnirlarni tashkil etish uchun mo'ljallangan zamonaviy backend tizimi.

## 🛠 Texnologiyalar (Tech Stack)

Asosiy tizim quyidagi zamonaviy texnologiyalar ustiga qurilgan:
- **Java 21**: Dasturlash tili
- **Spring Boot (3.5.5)**: Asosiy freymvork (WebMVC, Data JPA, Security)
- **PostgreSQL**: Asosiy ma'lumotlar bazasi
- **Redis**: Kesh (caching) uchun
- **Docker & Docker Compose**: Konteynerizatsiya
- **JWT (JSON Web Token)**: Autentifikatsiya va API xavfsizlik
- **MapStruct**: DTO (Data Transfer Object) mapping
- **Lombok**: Boilerplate kodlarni kamaytirish uchun
- **Swagger / OpenAPI**: API interfeyslarini hujjatlashtirish va test qilish uchun

## 📂 Loyiha Tuzilishi (Arxitektura)

Loyiha domenlarga asoslangan (Domain-Driven Design) arxitekturaga ega bo'lib, quyidagi asosiy modullarni o'z ichiga oladi:

- `auth`: Foydalanuvchi tizimga kirishi, huquqlarini (Role/Permission) tekshirish va OTP SMS tekshiruvi xizmatlari.
- `stadium` / `location`: Stadionlar ro'yxati, ularning reytingi va joylashuvi ustida amallar bajarish.
- `booking`: Stadionlardagi bo'sh vaqtlarni band qilish xizmatlari.
- `match`: O'yinlar yaratish va ishtirokchilarni shakllantirish.
- `team`: Jamoalarni boshqarish tizimi.
- `tournament`: Mahalliy yoki hududiy turnirlar o'tkazishni boshqarish.
- `coach`: Murabbiylar uchun ajratilgan modul.

## 🚀 Ishga Tushirish (How to Run)

Dasturni o'z kompyuteringizda ishga tushirish uchun sizga **Docker** va **Docker Compose** o'rnatilgan bo'lishi kerak. Ilova avtomatik ravishda barcha kerakli xizmatlarni (PostgreSQL, Redis va ilovaning o'zini) ko'taradi.

1. Loyiha papkasiga kiring:
   ```bash
   cd tezbron
   ```

2. Docker orqali barcha xizmatlarni ishga tushiring:
   ```bash
   docker-compose up -d --build
   ```

> **Eslatma:** Dastur 5002-portda ishlaydi. Ma'lumotlar bazasi hisoblangan PostgreSQL 9001-portga, Redis esa 6379-portga bog'langan.

3. Swagger UI orqali API larni ko'rish va test qilish uchun brauzerda quyidagi manzilga kiring:
   (Agar port sozlamalari o'zgarmagan bo'lsa)
   ```
   http://localhost:5002/swagger-ui.html
   ```

Jarayonlarni to'xtatish uchun:
```bash
docker-compose down
```

## 🔒 Xavfsizlik

Barcha API so'rovlari (ochiq ro'yxatdan tashqari) e-mail yoki telefon raqami (Eskiz korxonasi orqali OTP sms) yordamida tasdiqlangan foydalanuvchilar tomonidan bajarilishi va Authorization header da **Bearer Token (JWT)** yuborilishi shart.
