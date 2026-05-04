package com.example.praktiktam.data.repository

import com.example.praktiktam.data.api.RetrofitClient
import com.example.praktiktam.data.model.Food

class FoodRepository {
    suspend fun getFoods(): List<Food> {
        return try {
            RetrofitClient.instance.getFoods()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
