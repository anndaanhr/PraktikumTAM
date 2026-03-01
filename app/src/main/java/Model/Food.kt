package Model
import androidx.annotation.DrawableRes

data class Food(
    val nama: String,
    val deskripsi: String,
    val harga: Int,
    @DrawableRes val ImageRes: Int
)
