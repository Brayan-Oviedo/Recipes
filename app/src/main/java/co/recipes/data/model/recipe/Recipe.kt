package co.recipes.data.model.recipe

import android.os.Parcelable
import co.recipes.data.model.recipe.ingredient.Ingredient
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("id")
    val id: Long = 0,
    val title: String = "",
    @SerializedName("image")
    val imageUrl: String = "",
    val servings: Int = 0,
    val readyInMinutes: Int = 0,
    @SerializedName("sourceName")
    val name: String = "",
    val healthScore: Double = 0.0,
    val pricePerServing: Double = 0.0,
    val vegetarian: Boolean = false,
    val vegan: Boolean = false,
    @SerializedName("glitenFree")
    val containsGluten: Boolean = false,
    val dairyFree: Boolean = false,
    val cheap: Boolean = false,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient> = listOf(),
    val instructions: String = ""
) : Parcelable

@Parcelize
data class RecipeList(val results: List<Recipe> = listOf()): Parcelable