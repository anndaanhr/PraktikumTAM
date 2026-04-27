package Model

import android.content.Context

object FoodSource {
    // Fungsi untuk mencari ID drawable berdasarkan nama file (String) dari API
    fun getResourceId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}