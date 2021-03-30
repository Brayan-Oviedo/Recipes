package co.recipes.domain.repository.auth.login

import co.recipes.core.Result
import co.recipes.data.model.user.User

interface LoginRepository {

    suspend fun login(user: User, password: String): Result<User?>
}