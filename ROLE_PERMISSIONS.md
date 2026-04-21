# Role va Permission — DISTRICT_ADMIN + OWNER kengaytmasi

## Maqsad

- `ROLE_DISTRICT_ADMIN` qo'shish — faqat o'z district'ini boshqaradi.
- `SUPER_ADMIN` — yagona admin yaratuvchi (boshqa adminlarni faqat u yaratadi).
- `DISTRICT_ADMIN` — o'z district'ida `OWNER` va `COACH` yaratadi, stadion va turnir yaratadi.
- `OWNER` (stadion egasi) — o'zi uchun stadion va turnir yaratadi.

## Ruxsat jadvali

| Harakat | SUPER_ADMIN | DISTRICT_ADMIN | OWNER | COACH/PLAYER |
|---|---|---|---|---|
| Admin (DISTRICT_ADMIN) yaratish | ✅ | ❌ | ❌ | ❌ |
| OWNER/COACH yaratish | ✅ har yerda | ✅ o'z district | ❌ | ❌ |
| Stadion yaratish | ✅ har kim uchun | ✅ o'z district, har kim owner | ✅ faqat o'ziga, o'z district | ❌ |
| Stadion update/delete | ✅ hamma | ✅ o'z district | ✅ faqat o'ziniki | ❌ |
| Turnir yaratish | ✅ har yerda | ✅ o'z district | ✅ o'z district | ❌ |

## O'zgargan fayllar

### 1. Role enum va seed

- `src/main/java/com/example/bron/enums/StaffRole.java`
  - `DISTRICT_ADMIN("ROLE_DISTRICT_ADMIN")` qo'shildi.
- `src/main/java/com/example/bron/config/DataInitializer.java`
  - `DEFAULT_ROLES` ro'yxatiga `ROLE_DISTRICT_ADMIN` qo'shildi (ilk ishga tushirishda seed bo'ladi).

### 2. Current user helperlari

- `src/main/java/com/example/bron/auth/security/CurrentUserService.java`
  - Konstantalar: `SUPER_ADMIN_ROLE`, `DISTRICT_ADMIN_ROLE`, `OWNER_ROLE`.
  - Metodlar: `isSuperAdmin()`, `isDistrictAdmin()`, `isOwner()`.
  - `getCurrentDistrictId()` — joriy foydalanuvchi district id.
  - `requireSameDistrict(targetDistrictId)` — SUPER_ADMIN bypass qiladi; boshqalar uchun district tekshiruvi, mos kelmasa `AccessDenied`.
  - `requireOwnerOrDistrictScope(ownerId, districtId)` — stadion update/delete uchun: OWNER o'zi bo'lsa yoki DISTRICT_ADMIN o'z district'ida bo'lsa ruxsat, aks holda `AccessDenied`.

### 3. Staff yaratish

- `src/main/java/com/example/bron/admin/AdminUserApi.java`
  - `createStaff` uchun `@PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN')")`.
- `src/main/java/com/example/bron/auth/user/UserServiceImpl.java`
  - `resolveTargetDistrictForStaff(dto)` helper qo'shildi:
    - `SUPER_ADMIN` → `dto.districtId` bo'yicha district qaytariladi (yoki `null`).
    - `DISTRICT_ADMIN` → `SUPER_ADMIN` yoki `DISTRICT_ADMIN` rol so'ralsa → `AccessDenied`; aks holda `currentUser.district` majburiy qaytariladi (DTO e'tiborga olinmaydi — spoofing oldi olinadi).
    - Boshqa rollar → `AccessDenied`.
  - `OWNER` yaratishda stadion bog'lanishi olib tashlandi. Endi OWNER hisobi yaratilgandan keyin, OWNER tizimga kirib o'zi uchun stadionni `Stadium` endpoint orqali yaratadi. Oldingi tartib (avval stadion, keyin owner) mantiqqa zid edi, chunki stadion yaratish `ownerId`ni talab qiladi.
- `src/main/java/com/example/bron/auth/user/dto/CreateStaffUserDto.java`
  - `stadiumId` maydoni olib tashlandi (endi kerak emas).

### 4. Stadion (Stadium)

- `src/main/java/com/example/bron/stadium/StadiumApi.java`
  - `create`, `update`, `delete` endpointlarga `@PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN','OWNER')")`.
- `src/main/java/com/example/bron/stadium/StadiumServiceImpl.java`
  - `CurrentUserService` inject qilindi.
  - `create(dto)`:
    - **OWNER** (va super/district bo'lmasa) → `owner = currentUser`, `districtId = currentUser.district.id` majburiy. District `null` bo'lsa `AccessDenied`.
    - **DISTRICT_ADMIN / SUPER_ADMIN** → `requireSameDistrict(dto.districtId)` + owner DTO'dan.
  - `update(id, dto)` → `requireOwnerOrDistrictScope(entity.owner.id, entity.district.id)`; `dto.districtId` yuborilsa va user OWNER emas bo'lsa, `requireSameDistrict(dto.districtId)`.
  - `delete(id)` → `requireOwnerOrDistrictScope(...)`.

### 5. Turnir (Tournament)

- `src/main/java/com/example/bron/tournament/TournamentEntity.java`
  - Yangi maydon: `DistrictEntity district` (ManyToOne, `district_id` ustuni).
- `src/main/java/com/example/bron/tournament/dto/TournamentRequestDto.java`
  - `Long districtId` maydoni qo'shildi (faqat SUPER_ADMIN uchun ishlatiladi).
- `src/main/java/com/example/bron/tournament/TournamentMapper.java`
  - `district` target ignore qilindi (service qo'l bilan set qiladi).
- `src/main/java/com/example/bron/tournament/TournamentApi.java`
  - `create` uchun `@PreAuthorize("hasAnyRole('SUPER_ADMIN','DISTRICT_ADMIN','OWNER')")`.
- `src/main/java/com/example/bron/tournament/TournamentServiceImpl.java`
  - `CurrentUserService`, `DistrictRepository` inject qilindi.
  - `create(dto)`:
    - **SUPER_ADMIN** → `organizer = dto.organizerId`, `district = dto.districtId` (bor bo'lsa).
    - **DISTRICT_ADMIN / OWNER** → `organizer = currentUser`, `district = currentUser.district`. District `null` bo'lsa `AccessDenied`.
    - Boshqalari → `AccessDenied`.

## Xavfsizlik qoidalari

1. DISTRICT_ADMIN boshqa DISTRICT_ADMIN yoki SUPER_ADMIN yaratolmaydi.
2. DISTRICT_ADMIN yoki OWNER uchun DTO'dagi `districtId`/`organizerId`/`ownerId` ishonchsiz — ular har doim `currentUser` ma'lumotidan olinadi.
3. `@PreAuthorize` bilan rolga ruxsat beriladi, service qatlamida esa district/owner scope tekshiriladi (ikki qatlam).
4. OWNER faqat **o'zining** stadionini o'zgartira oladi yoki o'chira oladi (`requireOwnerOrDistrictScope`).

## Yangi OWNER yaratish oqimi

1. `SUPER_ADMIN` yoki `DISTRICT_ADMIN` `POST /v1/admin/users` orqali `StaffRole.OWNER` bilan hisob yaratadi (stadion ko'rsatilmaydi).
2. OWNER tizimga kiradi.
3. OWNER `POST /v1/stadiums/create` orqali o'zi uchun stadion yaratadi (owner va district avtomatik `currentUser`'dan olinadi).

## Keyingi iteratsiya (hozir qilinmagan)

- List/filter endpointlarda DISTRICT_ADMIN/OWNER uchun `districtId` (yoki `ownerId`) ni avtomatik inject qilish — `getAll` chaqiruvlarida boshqa district'ni ko'rolmaydi.
- `TournamentFilterParams` va `StadiumFilterParams` uchun shu scope auto-filterlash.
- `CoachEntity` uchun ham district bog'lanishi (keyinroq kerak bo'lsa).
