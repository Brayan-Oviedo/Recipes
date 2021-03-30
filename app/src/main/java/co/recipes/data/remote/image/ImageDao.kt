package co.recipes.data.remote.image

import co.recipes.core.Result
import co.recipes.data.model.user.User

interface ImageDao {

    suspend fun uploadImage(image: ByteArray, userUid: String): Result<String>
}