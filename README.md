# 🧩 user-company-core-v1

**Modul core/library** untuk menangani entitas dan logika bisnis utama terkait **User**, **Company**, dan relasi antar keduanya dalam bentuk **CompanyMember**.

Dirancang untuk digunakan bersama modul `api-v1` dan cocok untuk arsitektur berbasis microservices atau modul terpisah.

---

## 🔍 Fitur Utama

- Entity & Service untuk:
  - `User` (dengan validasi email, hash password)
  - `Company` (dengan nama unik)
  - `CompanyMember` (many-to-many, antar user & company)
- DTO request & response
- Validasi (`@Email`, `@NotBlank`, dsb)
- UUID sebagai ID utama (bukan auto increment)
- Dependency ringan, cocok untuk dipakai lintas modul

---

## 🧱 Struktur Paket

com.company.user.lib.core.v1
├── master
│ ├── user
│ └── company
├── referensi
│ └── company_member
└── exception


## ⚙️ Konfigurasi

Tambahkan dependency library ini ke project utama (`api-v1`) jika sudah dipublikasikan ke repository Maven lokal/intern.

Contoh penggunaan service di modul `api`:

```java
@Autowired
private UserService userService;

public void registerUser() {
    userService.register(new UserRegisterRequest(...));
}

🛠️ Teknologi
Java 21
Spring Boot 3.5.3
Spring Data JPA
PostgreSQL
Lombok
Hibernate UUID
Jakarta Validation

🚧 Catatan Penggunaan
Belum termasuk controller (disediakan di modul lain, masih dalam proses)

Password dienkripsi menggunakan BCryptPasswordEncoder

Disarankan digunakan bersama config Spring Security minimal (untuk encoder)

📦 Rencana Selanjutnya
 Integrasi dengan Config Server / Central Auth
