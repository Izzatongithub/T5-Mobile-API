package com.example.datapasien.network

import com.example.datapasien.model.LoginRequest
import com.example.datapasien.model.LoginResponse
import com.example.datapasien.model.PasienModel
import com.example.datapasien.model.PasienResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<PasienResponse<List<PasienModel>>>

    @POST("pasien")
    suspend fun createPasien(
        @Header("Authorization") token: String,
        @Body pasien: PasienModel
    ): Response<PasienResponse<PasienModel>>

    @PUT("pasien/{id}")
    suspend fun updatePasien(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body pasien: PasienModel
    ): Response<PasienResponse<PasienModel>>

    @DELETE("pasien/{id}")
    suspend fun deletePasien(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>

}
