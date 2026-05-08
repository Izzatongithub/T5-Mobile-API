package com.example.datapasien.model

data class PasienResponse(
    val success: Boolean,
    val message: String,
    val data: List<PasienModel>
)

data class PasienModel(
    val id: Int,
    val nama: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val alamat: String,
    val no_telepon: String,
    val created_at: String?,
    val updated_at: String?
)