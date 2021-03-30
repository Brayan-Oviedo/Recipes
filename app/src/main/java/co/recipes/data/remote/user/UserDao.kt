package co.recipes.data.remote.user

import co.recipes.core.Result
import co.recipes.data.model.user.User

interface UserDao {

    suspend fun getUserById(idUser: String) : Result<User?>
    suspend fun uploadImage(image: ByteArray, userUid: String): Result<String>
    suspend fun saveUser(user: User, userUid: String) : Result<Unit>
}