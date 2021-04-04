package co.recipes.domain.interactor.recipe

import co.recipes.core.Result
import co.recipes.data.remote.recipe.RecipeDao

interface RecipeInteractor: RecipeDao {

    suspend fun translate(text: String): Result<String>
    suspend fun configTranslate(): Result<Unit>

}