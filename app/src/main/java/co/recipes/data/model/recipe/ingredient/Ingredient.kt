package co.recipes.data.model.recipe.ingredient

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    @SerializedName("id")
    val id: Long = 0,
    val name: String = "",
    val originalName: String= "",
    @SerializedName("image")
    val imageUrl: String = "",
    val consistency: String = "",
    val amount: Double = 0.0,
    val unit: String = "",
    val original: String = ""
) : Parcelable