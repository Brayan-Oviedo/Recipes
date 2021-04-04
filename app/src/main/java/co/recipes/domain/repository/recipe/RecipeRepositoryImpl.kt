package co.recipes.domain.repository.recipe

import co.recipes.core.Result
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList
import co.recipes.data.remote.recipe.RecipeDao

class RecipeRepositoryImpl(private val recipeDao: RecipeDao): RecipeRepository {

    override suspend fun getAllRecipes(page: Int): Result<RecipeList> = recipeDao.getAllRecipes(page)
    override suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe> = recipeDao.getRecipeById(id, includeNutrition)
}