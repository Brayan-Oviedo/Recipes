package co.recipes.domain.repository.auth.register

import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.data.remote.image.ImageDao

interface RegisterRepository {

    suspend fun register(user: User, password: String): Result<Unit>
    suspend fun uploadImage(image: ByteArray) : Result<String>
    suspend fun saveUser(user: User): Result<Unit>
}