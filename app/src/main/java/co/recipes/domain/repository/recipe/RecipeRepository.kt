package co.recipes.domain.repository.recipe

import co.recipes.core.Result
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList

interface RecipeRepository {

    suspend fun getAllRecipes(): Result<RecipeList>
    suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe>
}