package co.recipes.domain.interactor.auth.login

import co.recipes.core.Result
import co.recipes.data.model.user.User

interface LoginInteractor {

    suspend fun login(user: User, password: String): Result<User?>
    fun checkFields(user: User, password: String) : Boolean

}