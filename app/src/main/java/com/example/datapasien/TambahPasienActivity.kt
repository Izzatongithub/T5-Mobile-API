package com.example.datapasien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.datapasien.model.PasienModel
import com.example.datapasien.network.RetrofitClient
import kotlinx.coroutines.launch
import android.view.View
import android.widget.Toast

class TambahPasienActivity : AppCompatActivity() {
    private lateinit var etNama: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etJenisKelamin: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etTelepon: EditText
    private lateinit var btnSave: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_pasien)

        // Inisialisasi view
        etNama = findViewById(R.id.etNama)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        etJenisKelamin = findViewById(R.id.etJenisKelamin)
        etAlamat = findViewById(R.id.etAlamat)
        etTelepon = findViewById(R.id.etNoTelepon)
        btnSave = findViewById(R.id.btnSave)
        progressBar = findViewById(R.id.progressBar)

        // Setup tombol simpan
        btnSave.setOnClickListener {
            savePasien()
        }
    }

    private fun savePasien() {

        // Ambil data dari input
        val nama = etNama.text.toString().trim()
        val tanggalLahir = etTanggalLahir.text.toString().trim()
        val jenisKelamin = etJenisKelamin.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val telepon = etTelepon.text.toString().trim()

        // Validasi input
        if (nama.isEmpty()) {
            etNama.error = "Nama tidak boleh kosong"
            etNama.requestFocus()
            return
        }

        if (tanggalLahir.isEmpty()) {
            etTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
            etTanggalLahir.requestFocus()
            return
        }

        if (jenisKelamin.isEmpty()) {
            etJenisKelamin.error = "Jenis kelamin tidak boleh kosong"
            etJenisKelamin.requestFocus()
            return
        }

        if (alamat.isEmpty()) {
            etAlamat.error = "Alamat tidak boleh kosong"
            etAlamat.requestFocus()
            return
        }

        if (telepon.isEmpty()) {
            etTelepon.error = "Nomor telepon tidak boleh kosong"
            etTelepon.requestFocus()
            return
        }

        // Buat object Pasien
        val pasien = PasienModel(
            nama = nama,
            tanggal_lahir = tanggalLahir,
            jenis_kelamin = jenisKelamin,
            alamat = alamat,
            no_telepon = telepon
        )

        // Kirim ke API
        createPasien(pasien)
    }

    private fun createPasien(pasien: PasienModel) {

        lifecycleScope.launch {

            showLoading(true)

            try {

                val prefs = getSharedPreferences("auth", MODE_PRIVATE)

                val token = prefs.getString("token", "") ?: ""

                val response =
                    RetrofitClient.apiService.createPasien(
                        "Bearer $token",
                        pasien
                    )

                if (response.isSuccessful) {

                    showMessage("Data berhasil ditambahkan")

                    // Kembali ke HomeActivity
                    finish()

                } else {

                    showMessage(
                        "Gagal menambah data: ${response.code()}"
                    )
                }

            } catch (e: Exception) {

                showMessage("Error: ${e.message}")

            } finally {

                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

        progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE

        btnSave.isEnabled = !isLoading
    }

    private fun showMessage(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}