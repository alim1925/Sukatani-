# Sukatani-
# 🌱 Sukatani — Smart Agriculture Management System

**Sukatani** adalah aplikasi desktop berbasis JavaFX yang dirancang untuk mendigitalisasi aktivitas pertanian. Aplikasi ini membantu petani modern dalam mengelola inventaris, memantau kondisi sensor secara real-time, menyusun jadwal kegiatan, hingga melakukan analisis keuangan yang akurat.

> *"Smart Agriculture Management System for Modern Farmers."*

---

## 🎯 Fitur Utama

-   🔐 **Sistem Autentikasi**: Login dan Register dengan keamanan hashing SHA-256.
-   📦 **Manajemen Inventaris**: Pengelolaan stok hasil panen secara terorganisir.
-   🛒 **Sistem Pre-Order**: Manajemen pesanan dari pembeli sebelum masa panen.
-   📅 **Penjadwalan Otomatis**: Generator tugas otomatis untuk kegiatan penanaman, pemupukan, dan pengairan.
-   💰 **Pencatatan Keuangan**: Monitoring arus kas (pemasukan/pengeluaran) serta kalkulasi ROI (Return on Investment).
-   📊 **Visualisasi Data**: Grafik real-time untuk pemantauan data sensor dan statistik pertanian.
-   🚨 **Sistem Alert & Rekomendasi**: Pemberitahuan otomatis berdasarkan kondisi sensor (kelembapan tanah, pH, suhu) disertai saran tindakan.

---

## 🛠️ Tech Stack

| Komponen             | Teknologi                  |
| -------------------- | -------------------------- |
| **Bahasa Pemrograman**| Java 21+                   |
| **GUI Framework**    | JavaFX (No FXML)           |
| **Build Tool**       | Gradle                     |
| **Database**         | SQLite                     |
| **Architecture**     | MVC + Service + Repository |
| **Security**         | SHA-256 Password Hashing   |

---

## 🏗️ Arsitektur Proyek

Sukatani menggunakan pola arsitektur yang modular dan scalable untuk memastikan pemisahan tanggung jawab yang bersih (*Separation of Concerns*):

1.  **UI Layer**: Menangani tampilan menggunakan JavaFX murni (programmatic UI).
2.  **Service Layer**: Menampung logika bisnis utama.
3.  **Repository Layer**: Menangani abstraksi akses data ke SQLite.
4.  **Model Layer**: Representasi objek data (POJO) dengan prinsip enkapsulasi.

---

## 🧠 Implementasi OOP

Proyek ini dirancang sebagai implementasi nyata dari konsep Object-Oriented Programming:
-   **Encapsulation**: Penggunaan akses modifier private dan getter/setter pada semua model.
-   **Inheritance**: Struktur User yang diwariskan ke role spesifik.
-   **Abstraction**: Penggunaan abstract class untuk sistem notifikasi atau base repository.
-   **Polymorphism**: Implementasi method overriding untuk perilaku komponen UI yang dinamis.
-   **Composition**: Objek `Crop` yang mengagregasi data `Inventory`, `Schedule`, dan `Sensor`.

---

## 🚀 Cara Menjalankan

### Prasyarat
-   Java JDK 21 atau lebih baru.
-   Gradle (sudah termasuk dalam wrapper).

### Langkah-langkah
1.  **Clone repositori**:
    ```bash
    git clone https://github.com/username/sukatani.git
    cd sukatani
    ```

2.  **Jalankan aplikasi**:
    ```bash
    ./gradlew run
    ```

3.  **Build executable**:
    ```bash
    ./gradlew build
    ```

---

## 📂 Struktur Folder

```text
Sukatani/
├── app/                  # Modul utama aplikasi
│   ├── src/main/java/    # Source code Java
│   ├── src/main/resources/ # Assets (CSS, SQL, Icons)
│   └── database/         # File database SQLite
├── gradle/               # Konfigurasi Gradle
└── gemini.md             # Spesifikasi teknis lengkap
```

---

## 🎨 Design System

Aplikasi ini menggunakan tema warna **Nature Green** untuk memberikan kesan agrikultur yang modern:
-   **Primary**: `#2E7D32` (Forest Green)
-   **Secondary**: `#81C784` (Light Green)
-   **Accent**: `#F9A825` (Amber)
-   **Background**: `#F7FAF7` (Off White-Green)

---

## 👥 Kontribusi
1. Muhammad Naufal Alim (H071251009)
2. Muhammad Aiman Amil (H071251071)
3. Chereen Bunga Catalina Ramba (H071251072)
Proyek ini dikembangkan sebagai tugas final mata kuliah **Object-Oriented Programming**. 

---

**© 2026 Sukatani Team.**
