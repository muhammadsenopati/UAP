# Sistem Manajemen Event

## Gambaran Umum
**Sistem Manajemen Event** adalah aplikasi desktop berbasis Java yang dirancang untuk mempermudah pengelolaan event. Program ini memiliki antarmuka GUI interaktif, mendukung pengelolaan gambar (image handling), melakukan operasi CRUD, dan terintegrasi dengan API eksternal untuk memperluas fungsionalitasnya. Selain itu, program ini memungkinkan pengguna untuk mengekspor detail event ke dalam dokumen Word dengan format yang menarik.

## Fitur
### 1. **Antarmuka Pengguna Grafis (GUI)**
- Antarmuka yang ramah pengguna dibuat dengan Java Swing.
- Skema warna modern dan tata letak yang intuitif.

### 2. **Operasi CRUD**
- **Create**: Menambahkan event baru dengan detail terkait.
- **Read**: Menampilkan semua event dalam format tabel.
- **Update**: Memodifikasi detail event yang sudah ada.
- **Delete**: Menghapus event dari daftar.

### 3. **Pengelolaan Gambar**
- Mengunggah dan melihat pratinjau poster event langsung di dalam aplikasi.
- Menyertakan gambar poster di dokumen Word yang diekspor.

### 4. **Ekspor ke Word**
- Mengekspor detail event ke dalam dokumen Word dengan:
  - Judul yang diformat secara gaya.
  - Deskripsi event.
  - Poster event yang terintegrasi.

### 5. **Integrasi API**
- Secara otomatis mengirim detail event ke API REST yang telah dikonfigurasi untuk penyimpanan dan pengambilan data.
- Menggunakan `HttpURLConnection` untuk komunikasi HTTP yang mulus.

### 6. **Validasi dan Penanganan Kesalahan**
- Memastikan semua kolom diisi sebelum menambahkan atau memperbarui event.
- Memvalidasi format tanggal sebagai `DD-MM-YYYY`.
- Menampilkan pesan kesalahan yang sesuai menggunakan dialog.

## Persyaratan
- **Java Development Kit (JDK)** versi 11 atau lebih tinggi.
- **Library Apache POI** untuk pembuatan dokumen Word.
- **Akses Internet** untuk integrasi API.

## Library dan Dependensi
### Dependensi Maven
Tambahkan dependensi berikut ke `pom.xml` Anda:
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi</artifactId>
        <version>5.2.3</version>
    </dependency>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.3</version>
    </dependency>
</dependencies>
```

## Cara Menjalankan Program
1. Clone repositori:
   ```bash
   git clone <repository-url>
   ```
2. Buka proyek di IDE Java pilihan Anda (misalnya, IntelliJ IDEA, Eclipse).
3. Pastikan semua dependensi terinstal (menggunakan Maven atau secara manual).
4. Jalankan kelas `EventManagement`.

## Penggunaan
1. **Menambahkan Event**:
   - Isi detail event (nama, tanggal, lokasi, deskripsi).
   - Unggah gambar poster.
   - Klik "Tambah Event" untuk menambahkannya ke daftar.

2. **Memperbarui Event**:
   - Pilih event dari tabel.
   - Modifikasi detailnya dan klik "Update Event".

3. **Menghapus Event**:
   - Pilih event dari tabel.
   - Klik "Hapus Event" untuk menghapusnya.

4. **Mengekspor ke Word**:
   - Klik "Save to Word" untuk mengekspor semua event ke dalam dokumen Word.

5. **Integrasi API**:
   - Program secara otomatis mengirim data event ke endpoint API yang telah dikonfigurasi saat event dibuat.

## Screenshot
### Antarmuka Utama
<img width="790" alt="Screenshot 2024-12-23 at 08 27 10" src="https://github.com/user-attachments/assets/4558abe5-1978-4a01-b71c-0d07a63a33f5" />

### Detail Event di Dokumen Word
<img width="901" alt="Screenshot 2024-12-23 at 08 27 46" src="https://github.com/user-attachments/assets/536a2f2b-5ce3-430f-b1eb-28d7cfa12eca" />


## Pengujian
Pengujian dengan JUnit disediakan untuk memverifikasi:
- Penambahan event dan integritas struktur data.
- Validasi format tanggal.
- Komunikasi API.

## Pengembangan Selanjutnya
- **Peningkatan UI/UX**: Migrasi ke JavaFX untuk komponen UI yang lebih modern.
- **Fitur Pencarian**: Tambahkan bilah pencarian untuk memfilter event.
- **Fitur API**: Termasuk permintaan GET untuk mengambil data event yang ada.
- **Penyimpanan Data**: Tambahkan database lokal untuk penyimpanan event secara offline.

## Kontributor
- [Muhammad Senopati Panotogomo](https://github.com/muhammadsenopati)

---

Silakan ajukan saran perbaikan atau laporkan bug melalui [halaman issues](<repository-url>/issues).

