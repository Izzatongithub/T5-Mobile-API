# Aplikasi Login dan Data Pasien

## Deskripsi

Aplikasi Android berbasis Kotlin yang menggunakan API untuk proses login dan pengelolaan data pasien menggunakan RecyclerView.

Fitur utama aplikasi:

* Login menggunakan API dengan email dan password
* Menyimpan token autentikasi
* Mengambil data pasien menggunakan Bearer Token
* Menampilkan daftar pasien menggunakan RecyclerView
* Menambahkan data pasien
* Mengedit data pasien
* Menghapus data pasien
* Menampilkan loading dan error state

---

## Teknologi yang Digunakan

* Kotlin
* XML Layout
* Retrofit
* RecyclerView
* SharedPreferences
* Coroutines

---

## Endpoint API

| Fitur         | Method | Endpoint                                   |
| ------------- | ------ | ------------------------------------------ |
| Login         | POST   | `https://api.pahrul.my.id/api/login`       |
| Data Pasien   | GET    | `https://api.pahrul.my.id/api/pasien`      |
| Tambah Pasien | POST   | `https://api.pahrul.my.id/api/pasien`      |
| Edit Pasien   | PUT    | `https://api.pahrul.my.id/api/pasien/{id}` |
| Hapus Pasien  | DELETE | `https://api.pahrul.my.id/api/pasien/{id}` |

---

## Fitur Aplikasi

### 1. Login

User login menggunakan:

* Email
* Password

Jika berhasil:

* Token disimpan ke SharedPreferences
* Nama user ditampilkan
* Berpindah ke halaman data pasien

---

### 2. Data Pasien

Menampilkan data pasien menggunakan RecyclerView:

* Nama Pasien
* Tanggal Lahir
* Jenis Kelamin
* Alamat
* Nomor Telepon

Request data pasien menggunakan:

```http
Authorization: Bearer {token}
```

---

### 3. Tambah Data Pasien

User dapat menambahkan data pasien baru melalui form input:

* Nama
* Tanggal Lahir
* Jenis Kelamin
* Alamat
* Nomor Telepon

Data dikirim ke API menggunakan metode POST.

---

### 4. Edit Data Pasien

User dapat mengubah data pasien yang sudah tersedia. Data yang dipilih akan ditampilkan pada form edit dan diperbarui menggunakan metode PUT.

---

### 5. Hapus Data Pasien

User dapat menghapus data pasien dari daftar menggunakan metode DELETE.

---

## Tampilan Aplikasi

<table>
<tr>
<td align="center">
<h3>Halaman Login</h3>
<img src="https://github.com/user-attachments/assets/9e2dc29b-619e-4c1c-9baf-63c39e5dd620" width="250"/>
</td>

<td align="center">
<h3>Dashboard</h3>
<img width="738" height="1600" alt="dashboard" src="https://github.com/user-attachments/assets/f094b581-110c-47cd-a5fc-54362b796e0e" />
</td>

<td align="center">
<h3>Form Tambah</h3>
<img width="1080" height="2340" alt="tambah" src="https://github.com/user-attachments/assets/5ae11f26-d285-4242-b8da-03b2aa51170e" />
</td>

<td align="center">
<h3>Form Edit</h3>
<img width="1080" height="2340" alt="edit" src="https://github.com/user-attachments/assets/6a28f633-5c0c-4299-934b-a7557cc5f13a" />
</td>

<td align="center">
<h3>Dialog Hapus</h3>
<img width="738" height="1600" alt="hapus" src="https://github.com/user-attachments/assets/eeb92374-dee7-4614-94f1-54690908ff3d" />
</td>

</tr>
</table>
