package com.example.praktiktam.network

import Model.Food
import retrofit2.http.GET

interface ApiService {
    @GET("menu_makanan.json") // Endpoint API
    suspend fun getFoods(): List<Food>
}
