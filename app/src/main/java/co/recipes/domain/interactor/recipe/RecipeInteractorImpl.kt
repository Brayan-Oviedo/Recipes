package co.recipes.domain.repository.recipe

import co.recipes.core.Result
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList

class RecipeInteractorImpl(private val recipeRepository: RecipeRepository): RecipeInteractor {

    override suspend fun getAllRecipes(): Result<RecipeList> = recipeRepository.getAllRecipes()
    override suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe> = recipeRepository.getRecipeById(id, includeNutrition)

}