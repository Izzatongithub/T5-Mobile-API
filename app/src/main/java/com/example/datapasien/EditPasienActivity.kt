package com.example.datapasien

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.datapasien.model.PasienModel
import com.example.datapasien.network.RetrofitClient
import kotlinx.coroutines.launch

class EditPasienActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etTanggalLahir: EditText
    private lateinit var etJenisKelamin: EditText
    private lateinit var etAlamat: EditText
    private lateinit var etNoTelepon: EditText
    private lateinit var btnEdit: Button
    private lateinit var progressBar: ProgressBar

    private var pasienId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasien)

        // Inisialisasi view
        etNama = findViewById(R.id.etNama)
        etTanggalLahir = findViewById(R.id.etTanggalLahir)
        etJenisKelamin = findViewById(R.id.etJenisKelamin)
        etAlamat = findViewById(R.id.etAlamat)
        etNoTelepon = findViewById(R.id.etNoTelepon)
        btnEdit = findViewById(R.id.btnEdit)
        progressBar = findViewById(R.id.progressBar)

        // Ambil data dari Intent
        pasienId = intent.getIntExtra("PASIEN_ID", 0)

        val nama = intent.getStringExtra("PASIEN_NAMA") ?: ""
        val tanggalLahir = intent.getStringExtra("PASIEN_TANGGAL_LAHIR") ?: ""
        val jenisKelamin = intent.getStringExtra("PASIEN_JENIS_KELAMIN") ?: ""
        val alamat = intent.getStringExtra("PASIEN_ALAMAT") ?: ""
        val telepon = intent.getStringExtra("PASIEN_TELEPON") ?: ""

        // Validasi ID
        if (pasienId == 0) {
            showMessage("Data pasien tidak ditemukan")
            finish()
            return
        }

        // Isi form
        etNama.setText(nama)
        etTanggalLahir.setText(tanggalLahir)
        etJenisKelamin.setText(jenisKelamin)
        etAlamat.setText(alamat)
        etNoTelepon.setText(telepon)

        // Tombol update
        btnEdit.setOnClickListener {
            updatePasien()
        }
    }

    private fun updatePasien() {

        val nama = etNama.text.toString().trim()
        val tanggalLahir = etTanggalLahir.text.toString().trim()
        val jenisKelamin = etJenisKelamin.text.toString().trim()
        val alamat = etAlamat.text.toString().trim()
        val telepon = etNoTelepon.text.toString().trim()

        // Validasi
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
            etNoTelepon.error = "No telepon tidak boleh kosong"
            etNoTelepon.requestFocus()
            return
        }

        // Object pasien baru
        val pasien = PasienModel(
            id = pasienId,
            nama = nama,
            tanggal_lahir = tanggalLahir,
            jenis_kelamin = jenisKelamin,
            alamat = alamat,
            no_telepon = telepon
        )

        // Kirim update ke API
        updatePasienToApi(pasienId, pasien)
    }

    private fun updatePasienToApi(id: Int, pasien: PasienModel) {

        lifecycleScope.launch {

            showLoading(true)

            try {

                val prefs = getSharedPreferences("auth", MODE_PRIVATE)

                val token = prefs.getString("token", "") ?: ""

                val response = RetrofitClient.apiService.updatePasien(
                    "Bearer $token",
                    id,
                    pasien
                )

                if (response.isSuccessful) {

                    showMessage("Data berhasil diupdate")

                    finish()

                } else {

                    showMessage("Gagal update data: ${response.code()}")
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

        btnEdit.isEnabled = !isLoading
    }

    private fun showMessage(message: String) {

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}