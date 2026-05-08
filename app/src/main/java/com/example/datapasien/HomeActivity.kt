package com.example.datapasien

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.datapasien.adapter.PasienAdapter
import com.example.datapasien.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    companion object {
        // Konstanta untuk key Intent, dipakai di MainActivity saat mengirim data
        const val EXTRA_NAME = "extra_name"
    }

    private lateinit var rvPasien: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var pasienAdapter: PasienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ambil nama user yang dikirim dari MainActivity
        val name = intent.getStringExtra(EXTRA_NAME).orEmpty()

        // Tampilkan nama user ke TextView
        val tvName = findViewById<TextView>(R.id.tvName)
        tvName.text = name

        rvPasien = findViewById(R.id.rvPasien)
        progressBar = findViewById(R.id.progressBar)

        rvPasien.layoutManager = LinearLayoutManager(this)

        // Inisialisasi Adapter dengan lambda untuk handle klik
        pasienAdapter = PasienAdapter(
            onEditClick = { pasien ->
                Toast.makeText(this, "Edit: ${pasien.nama}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { pasien ->
                Toast.makeText(this, "Hapus: ${pasien.nama}", Toast.LENGTH_SHORT).show()
            }
        )
        rvPasien.adapter = pasienAdapter

        // Setup tombol logout
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            showLogoutConfirmation()
        }

        // Panggil fungsi untuk ambil data
        fetchDataPasien()
    }

    private fun fetchDataPasien() {
        // Ambil token dari SharedPreferences yang disimpan saat Login
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "")

        // Jika token kosong, berarti belum login / error, kembalikan ke MainActivity
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Sesi habis, silakan login ulang", Toast.LENGTH_SHORT).show()
            logout()
            return
        }

        showLoading(true)
        // Mulai ambil data ke Internet (Server)
        lifecycleScope.launch {
            try {
                // Perhatikan: Kita sisipkan kata "Bearer " ditambah spasi di depan token
                val response = RetrofitClient.apiService.getPasien("Bearer $token")

                if (response.isSuccessful) {
                    // Jika sukses, ambil datanya. Jika null, berikan list kosong
                    val listPasien = response.body()?.data ?: emptyList()

                    // Tempelkan data tersebut ke Adapter agar dirender ke layar
                    pasienAdapter.setData(listPasien)
                } else {
                    Toast.makeText(this@HomeActivity, "Gagal memuat data pasien", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Tangkap error jika internet mati atau server down
                Toast.makeText(this@HomeActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Logout") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun logout() {
        // Hapus token dari SharedPreferences
        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        prefs.edit().remove("token").apply()

        // Kembali ke MainActivity dan tutup semua Activity di atasnya
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
