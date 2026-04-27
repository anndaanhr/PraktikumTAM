# 🚀 Panduan Live Coding: Migrasi dari Modul API ke Modul Coil

Dokumen ini berisi detail semua kode yang ditambah, dikurang, dan diedit, **dilengkapi dengan naskah penjelasan (apa yang harus diucapkan)** saat melakukan live coding di depan kelas.

---

## 1. Konfigurasi Library (Gradle)

### A. `gradle/libs.versions.toml`
📍 **Lokasi File:** Ada di panel kiri (Project), buka folder **`gradle`** -> **`libs.versions.toml`**

**[DITAMBAHKAN]** Mendaftarkan versi dan library Coil.
```toml
[versions]
# ... versi lainnya ...
+ coil = "2.6.0"

[libraries]
# ... library lainnya ...
+ coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Teman-teman, sebelum kita bisa menggunakan Coil, kita harus mendeklarasikan dulu versinya di `libs.versions.toml`. Sama seperti Retrofit kemarin, kita daftarkan versinya yaitu 2.6.0, lalu kita definisikan dependency khusus untuk Jetpack Compose yaitu `coil-compose`."*

### B. `app/build.gradle.kts`
📍 **Lokasi File:** Buka folder **`app`** -> **`build.gradle.kts`** (Pilih yang ada tulisan Module :app)

**[DITAMBAHKAN]** Menerapkan dependency Coil ke dalam module app.
```kotlin
dependencies {
    // ... Retrofit & Gson (Materi Sebelumnya) ...
+   // Coil untuk load gambar
+   implementation(libs.coil.compose)
}
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Setelah versinya didaftarkan di version catalog, sekarang kita pasang dependency library-nya di file `build.gradle.kts` (Module: app). Ketikkan `implementation(libs.coil.compose)`. Jika sudah, jangan lupa klik tombol 'Sync Now' di kanan atas agar Android Studio dapat mengunduh library Coil tersebut."*

---

## 2. Pembaruan Model Data

### A. `Food.kt`
📍 **Lokasi File:** Buka folder **`app/src/main/java/Model`** -> **`Food.kt`**

**[DIEDIT]** Mengganti referensi nama gambar lokal menjadi URL gambar internet.
```diff
data class Food(
    @SerializedName("nama") val nama: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("harga") val harga: Int,
    
-   @SerializedName("image_name")
-   val imageName: String 
+   @SerializedName("image_url")
+   val imageUrl: String 
)
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Coba perhatikan respon JSON dari Gist yang kita buat. Key-nya sudah bukan `image_name` lagi, melainkan `image_url` yang isinya link HTTP. Oleh karena itu, Model data `Food` kita harus disesuaikan. Kita ganti anotasi `@SerializedName` menjadi `image_url`, dan kita sesuaikan juga nama variabelnya menjadi `imageUrl`."*

### B. Menghapus `FoodSource.kt`
📍 **Lokasi File:** Buka folder **`app/src/main/java/Model`** -> Klik kanan pada **`FoodSource.kt`**

**[DIHAPUS]** File ini dihapus sepenuhnya secara permanen (`Delete File`).
🗣️ **Penjelasan Saat Live Coding:**
> *(Klik Kanan file FoodSource.kt -> Delete -> OK)*
> *"Karena sekarang kita tidak lagi memuat gambar dari resource lokal (`R.drawable`), file `FoodSource` yang fungsi utamanya mencari ID gambar lokal ini sudah tidak diperlukan. Kita hapus saja filenya agar struktur kode project kita lebih bersih."*

---

## 3. Rombak Tampilan (UI) di `MainActivity.kt`

📍 **Lokasi Keseluruhan:** Buka folder **`app/src/main/java/com/example/praktiktam`** -> **`MainActivity.kt`**

Banyak perubahan terjadi di `MainActivity.kt`. Untuk mempermudah saat live coding, gunakan shortcut **Ctrl + F** (atau Cmd + F di Mac) untuk mencari nama fungsinya!

### A. Import Package
📍 **Lokasi:** Gulir ke baris paling atas (area `import ...`)

**[DITAMBAHKAN]**
```kotlin
+ import coil.compose.AsyncImage
+ import androidx.compose.ui.text.style.TextAlign
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Pastikan kita melakukan import komponen `AsyncImage` dari package Coil. Sesuai namanya, `AsyncImage` akan memuat gambar secara Asynchronous di background thread, sehingga tidak membuat UI aplikasi kita freeze atau terhenti saat proses download gambar berlangsung."*

### B. Fungsi `DaftarMakananScreen` (State Error Handling)
📍 **Cara Mencari:** Tekan **Ctrl + F**, ketik `fun DaftarMakananScreen` (Biasanya ada di pertengahan kode, sekitar baris ke-110).

**[DITAMBAHKAN & DIEDIT]** Menambahkan logika *error fallback* ketika API mati.
```diff
@Composable
fun DaftarMakananScreen(navController: NavController, onFoodsLoaded: (List<Food>) -> Unit = {}) {
    var foods by remember { mutableStateOf<List<Food>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
+   var isError by remember { mutableStateOf(false) } // State baru untuk error

    LaunchedEffect(Unit) {
        try {
            foods = RetrofitClient.instance.getFoods()
            onFoodsLoaded(foods)
            isLoading = false
+           isError = false
        } catch (e: Exception) {
            isLoading = false
+           isError = true // Trigger error jika gagal
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
+   } else if (isError || foods.isEmpty()) {
+       // Tampilan saat internet mati atau gagal load data
+       Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
+           Column(horizontalAlignment = Alignment.CenterHorizontally) {
+               Text(
+                   text = "Gagal Memuat Data",
+                   style = MaterialTheme.typography.titleLarge,
+                   fontWeight = FontWeight.Bold,
+                   color = Color.Red
+               )
+               Spacer(modifier = Modifier.height(8.dp))
+               Text(
+                   text = "Pastikan koneksi internet Anda menyala",
+                   style = MaterialTheme.typography.bodyMedium,
+                   color = Color.Gray,
+                   textAlign = TextAlign.Center,
+                   modifier = Modifier.fillMaxWidth()
+               )
+           }
+       }
+       return
    }
// ... sisa kode LazyColumn ...
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Koneksi internet pengguna tidak selalu stabil. Jika internet mati saat API Retrofit dijalankan, aplikasi biasanya akan menampilkan layar kosong (blank) atau bahkan force close. Oleh karena itu, kita menambahkan state penanganan error yang dinamakan `isError`. Apabila request masuk ke blok `catch` (gagal memuat data), kita ubah nilai `isError` menjadi `true`, lalu kita render tampilan peringatan 'Gagal Memuat Data'. Ini adalah penerapan error handling yang standar."*

### C. Fungsi `DetailScreen`
📍 **Cara Mencari:** Tekan **Ctrl + F**, ketik `fun DetailScreen` (Biasanya ada di bawah DaftarMakananScreen, sekitar baris ke-170).

**[DIHAPUS]** Membuang kode pemanggil gambar lokal.
```kotlin
- val context = LocalContext.current
- val resId = FoodSource.getResourceId(context, food.imageName)
- val imageRes = if (resId != 0) resId else R.drawable.rendang
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Di dalam fungsi `DetailScreen`, kita hapus kode yang mendeklarasikan `LocalContext` dan fungsi `FoodSource` ini, karena kita tidak lagi membutuhkan konversi nama ke ID resource lokal."*

**[DIEDIT]** Mengganti `Image` standar menjadi `AsyncImage`.
```diff
- Image(
-     painter = painterResource(id = imageRes),
-     contentDescription = food.nama,
-     modifier = Modifier
-         .fillMaxWidth()
-         .height(200.dp),
-     contentScale = ContentScale.Crop
- )
+ AsyncImage(
+     model = food.imageUrl,
+     contentDescription = food.nama,
+     placeholder = painterResource(id = R.drawable.rendang), // Efek loading
+     error = painterResource(id = R.drawable.sate), // Efek gagal
+     modifier = Modifier
+         .fillMaxWidth()
+         .height(200.dp)
+         .clip(RoundedCornerShape(12.dp)), // Wajib ada clip agar gambar membulat sesuai Card
+     contentScale = ContentScale.Crop
+ )
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Nah, di sinilah implementasi utamanya. Kita mengganti komponen `Image` bawaan Android dengan `AsyncImage` dari Coil. Parameter sumber datanya menggunakan `model` yang kita passing dengan URL gambar dari internet."*
> 
> *"Sebagai tambahan yang sangat bermanfaat, Coil menyediakan properti `placeholder` untuk menampilkan gambar lokal sementara saat gambar asli sedang di-download, dan properti `error` untuk memunculkan gambar alternatif jika URL bermasalah atau koneksi gagal."*

### D. Fungsi `FoodRowItem`
📍 **Cara Mencari:** Tekan **Ctrl + F**, ketik `fun FoodRowItem` (Biasanya ada di bagian paling bawah kode, sekitar baris ke-290).

**[DIHAPUS & DIEDIT]** Lakukan persis sama dengan bagian `DetailScreen`.
```diff
- val context = LocalContext.current
- val resId = FoodSource.getResourceId(context, food.imageName)
- val imageRes = if (resId != 0) resId else R.drawable.rendang

- Image(
-     painter = painterResource(id = imageRes),
-     contentDescription = food.nama,
-     modifier = Modifier
-         .fillMaxWidth()
-         .height(100.dp),
-     contentScale = ContentScale.Crop
- )
+ AsyncImage(
+     model = food.imageUrl,
+     contentDescription = food.nama,
+     placeholder = painterResource(id = R.drawable.rendang),
+     error = painterResource(id = R.drawable.sate),
+     modifier = Modifier
+         .fillMaxWidth()
+         .height(100.dp),
+     contentScale = ContentScale.Crop
+ )
```
🗣️ **Penjelasan Saat Live Coding:**
> *"Sama seperti pada `DetailScreen`, di dalam komponen list item (`FoodRowItem`), kita juga mengganti komponen `Image` menjadi `AsyncImage`. Gunakan struktur parameter yang sama, dan pastikan nilai `modifier` tingginya tetap dijaga di 100.dp agar desain *layout* aplikasinya tetap konsisten."*
