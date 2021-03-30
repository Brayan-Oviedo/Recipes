package co.recipes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.recipes.data.model.user.User
import co.recipes.domain.repository.recipe.RecipeInteractor

class HomeViewModel(): ViewModel() {

    lateinit var userLogged: User
}

class HomeViewModelFactory(val interactor: RecipeInteractor): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeInteractor::class.java).newInstance(interactor)
    }
}