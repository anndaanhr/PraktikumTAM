package com.example.praktiktam.data.api

import com.example.praktiktam.data.model.Food
import retrofit2.http.GET

interface ApiService {
    @GET("menu_makanan.json") // Endpoint API
    suspend fun getFoods(): List<Food>
}
