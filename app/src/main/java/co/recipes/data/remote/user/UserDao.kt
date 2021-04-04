package co.recipes.data.remote.user

import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.data.remote.image.ImageDao

interface UserDao: ImageDao {

    suspend fun getUserById(idUser: String) : Result<User?>
    suspend fun saveUser(user: User, userUid: String) : Result<Unit>
}