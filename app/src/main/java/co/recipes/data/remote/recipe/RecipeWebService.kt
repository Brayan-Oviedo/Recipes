package co.recipes.data.remote.recipe

import co.recipes.core.application.Constants
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeWebService {

    @Headers("Content-Type: application/json")
    @GET("complexSearch")
    suspend fun getAllRecipes(@Query("apiKey") apiKey: String) : RecipeList

    @Headers("Content-Type: application/json")
    @GET("{id}/information")
    suspend fun getRecipeById(@Path("id") id: Long, @Query("includeNutrition") includeNutrition: Boolean, @Query("apiKey") apiKey: String) : Recipe
}

object RetrofitClient {

    val recipeService by lazy {
        Retrofit.Builder().baseUrl(Constants.API_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(RecipeWebService::class.java)
    }
}