package co.recipes.domain.interactor.auth.login

import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.domain.repository.auth.login.LoginRepository

class LoginInteractorImpl(private val loginRepository: LoginRepository) : LoginInteractor {

    override suspend fun login(user: User, password: String): Result<User?> = loginRepository.login(user, password)

    override fun checkFields(user: User, password: String): Boolean {
        return user.email.isNotEmpty() || password.isNotEmpty()
    }
}