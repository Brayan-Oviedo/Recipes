package co.recipes.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import co.recipes.core.Result
import co.recipes.domain.repository.recipe.RecipeInteractor
import kotlinx.coroutines.Dispatchers

class RecipeViewModel(private val interactor: RecipeInteractor): ViewModel() {

    fun getAllRecipes() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(interactor.getAllRecipes())
        }catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun getRecipeById(id: Long, includeNutrition: Boolean) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(interactor.getRecipeById(id, includeNutrition))
        }catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class RecipeViewModelFactory(val interactor: RecipeInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeInteractor::class.java).newInstance(interactor)
    }
}