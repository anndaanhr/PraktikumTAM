# 📖 Sesi Teori: Memuat Gambar dari Internet menggunakan Coil

Gunakan materi ini sebagai bahan presentasi atau ceramah (Sesi Teori) sebelum Anda memulai *Live Coding* Modul 11 di depan mahasiswa.

---

## 1. Latar Belakang: Mengapa Kita Butuh Library Tambahan?
Buka sesi dengan memberikan pemahaman tentang perbedaan gambar **Lokal** vs **Internet**.

*   **Gambar Lokal (`R.drawable`)**: Selama ini kita menyimpan gambar langsung di dalam folder aplikasi. Kelebihannya: langsung muncul dan tidak butuh internet. Kekurangannya: membuat ukuran aplikasi (APK) menjadi sangat bengkak (besar) dan datanya statis (tidak bisa diubah tanpa update aplikasi di PlayStore).
*   **Gambar Internet (URL/API)**: Di dunia nyata (seperti GoFood, Instagram, Shopee), jutaan gambar diambil dari *Server* (Internet) melalui URL.
*   **Masalahnya:** Komponen `Image` standar di Jetpack Compose tidak didesain untuk mendownload gambar dari internet. Jika kita mendownload gambar secara manual, kita harus mengatur *background thread*, menyimpan gambar sementara di memori (*caching*), dan menangani error jika internet putus. Itu sangat rumit!

---

## 2. Solusinya: Apa itu Coil?
*   **Coil** adalah singkatan dari **Co**routine **I**mage **L**oader.
*   Ini adalah sebuah *library* (pustaka) pihak ketiga yang bertugas secara spesifik untuk mengambil gambar dari internet dan menampilkannya ke layar aplikasi Android kita.
*   Coil bekerja di belakang layar secara **Asynchronous** (jalan di latar belakang tanpa mengganggu layar utama yang sedang dilihat pengguna).

---

## 3. Kenapa Harus Coil? Kenapa Tidak Pakai yang Lain?
Bagi mahasiswa yang mungkin pernah belajar Android (menggunakan Java/XML) di masa lalu, mereka mungkin mengenal library legendaris seperti **Glide** atau **Picasso**. Jelaskan mengapa sekarang standar industrinya bergeser ke Coil:

1.  **Dibuat Khusus untuk Kotlin & Compose (Native):** Coil ditulis 100% menggunakan bahasa Kotlin dan sangat mengandalkan *Coroutines*. Coil juga memiliki komponen khusus bernama `AsyncImage` yang dirancang eksklusif untuk Jetpack Compose. (Sedangkan Glide/Picasso aslinya dirancang untuk sistem XML lama).
2.  **Sangat Ringan (Lightweight):** Coil ukurannya sangat kecil sehingga tidak membebani memori aplikasi.
3.  **Manajemen Memori Otomatis (Caching):** Saat kita menscroll daftar makanan ke bawah, lalu menscroll ke atas lagi, Coil tidak akan mendownload ulang gambarnya. Coil secara cerdas menyimpan gambar tersebut di dalam *Cache* (memori sementara HP). Ini sangat menghemat kuota internet pengguna dan mempercepat performa aplikasi.

---

## 4. Konsep Komponen `AsyncImage`
Berikan "bocoran" tentang komponen `AsyncImage` yang nanti akan mereka gunakan di sesi praktik. `AsyncImage` punya 3 senjata utama:

*   🎯 **`model`**: Ini adalah sasaran utamanya. Diisi dengan link/URL internet (Contoh: `https://.../rendang.jpg`).
*   ⏳ **`placeholder`**: Pernah lihat kotak abu-abu berkedip saat gambar Instagram sedang loading? Nah, *placeholder* adalah gambar sementara yang muncul selagi Coil sedang berusaha mendownload gambar asli dari internet.
*   ⚠️ **`error`**: Ini adalah "tameng pelindung". Jika internet pengguna mati, atau link gambar dari API ternyata rusak, aplikasi tidak akan *Force Close*. Coil akan menggantinya dengan gambar *error* (gambar *fallback*/cadangan) yang sudah kita siapkan dari *resource* lokal.

---

*💡 **Tips Penutupan Sesi Teori:** Akhiri teori dengan kalimat transisi, "Sekarang kita sudah paham teorinya, mari kita buka Android Studio dan praktekkan langsung bagaimana mengubah aplikasi kita menjadi aplikasi dinamis yang terhubung ke internet menggunakan Coil!"*
