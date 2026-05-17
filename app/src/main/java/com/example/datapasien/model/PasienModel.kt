package com.example.datapasien.model

data class PasienResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)

data class PasienModel(
    val id:  Int? = null,
    val nama: String,
    val tanggal_lahir: String,
    val jenis_kelamin: String,
    val alamat: String,
    val no_telepon: String,
    val created_at: String? = null,
    val updated_at: String? = null
)