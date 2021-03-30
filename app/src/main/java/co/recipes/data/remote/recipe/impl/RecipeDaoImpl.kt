package co.recipes.data.remote.recipe.impl

import android.util.Log
import co.recipes.core.Result
import co.recipes.core.application.Constants
import co.recipes.core.exceptions.RecipeDaoException
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList
import co.recipes.data.remote.recipe.RecipeDao
import co.recipes.data.remote.recipe.RecipeWebService

class RecipeDaoImpl(private val recipeWebService: RecipeWebService): RecipeDao {

    override suspend fun getAllRecipes(): Result<RecipeList> = try {
        Result.Success(recipeWebService.getAllRecipes(Constants.API_KEY))
    }catch (e: Exception) {
        throw RecipeDaoException("Algo salio mal al obtener las recetas")
    }

    override suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe> = try {
        Result.Success(recipeWebService.getRecipeById(id, includeNutrition, Constants.API_KEY))
    }catch (e: Exception) {
        throw RecipeDaoException("Algo salio mal al obtener la receta")
    }
}