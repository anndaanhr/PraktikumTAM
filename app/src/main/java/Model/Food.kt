package Model

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("nama") 
    val nama: String,
    
    @SerializedName("deskripsi") 
    val deskripsi: String,
    
    @SerializedName("harga") 
    val harga: Int,
    
    @SerializedName("image_name")
    val imageName: String 
)
