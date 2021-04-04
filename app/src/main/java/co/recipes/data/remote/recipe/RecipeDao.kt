package co.recipes.data.remote.recipe

import co.recipes.core.Result
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList

interface RecipeDao {

    suspend fun getAllRecipes(page: Int): Result<RecipeList>
    suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe>
}