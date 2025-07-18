# 🧩 user-company-core-v1

**Modul core/library** untuk menangani entitas dan logika bisnis utama terkait **User**, **Company**, dan relasi antar keduanya dalam bentuk **CompanyMember**.

Dirancang untuk digunakan bersama modul `web-v1` dan cocok untuk arsitektur berbasis microservices atau modular monolith.

---

## 🔍 Fitur Utama

- Entity & Service untuk:
  - `User` (dengan validasi email, hash password)
  - `Company` (dengan nama unik)
  - `CompanyMember` (many-to-many, user ↔ company)
- DTO untuk request & response
- Validasi menggunakan Jakarta Validation (`@Email`, `@NotBlank`, dll)
- UUID sebagai ID utama
- Dependency ringan, siap digunakan oleh modul lain

---

## 🧱 Struktur Paket

```
com.company.user.lib.core.v1
├── master
│   ├── user
│   └── company
├── referensi
│   └── company_member
└── exception
```

---

## ⚙️ Konfigurasi

Tambahkan ke `pom.xml` project lain (setelah install ke `.m2`):

```xml
<dependency>
    <groupId>com.company.user.lib</groupId>
    <artifactId>core-v1</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Contoh penggunaan service:

```java
@Autowired
private UserService userService;

public void register() {
    userService.register(new UserRegisterRequest(...));
}
```

---

## 🛠 Teknologi

- Java 21
- Spring Boot 3.5.3
- Spring Data JPA
- PostgreSQL
- Lombok
- Hibernate UUID
- Jakarta Validation

---

## 🚧 Catatan

- Tidak berisi controller (disediakan oleh `web-v1`)
- Password disimpan terenkripsi menggunakan `BCryptPasswordEncoder`
- Siap diintegrasikan dengan Eureka, Gateway, dan Auth Server

---

## 📦 Rencana Pengembangan

- [ ] Integrasi ke Spring Cloud Config

---

## 👨‍💻 Pengembang

Maintainer: [@dwiaribowokj](https://github.com/dwiaribowokj)
