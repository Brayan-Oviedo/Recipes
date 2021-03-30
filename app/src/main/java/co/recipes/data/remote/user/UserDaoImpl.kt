package co.recipes.data.remote.user

import co.recipes.core.Result
import co.recipes.core.exceptions.UserDaoException
import co.recipes.data.model.user.User
import co.recipes.data.remote.image.ImageDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDaoImpl(private val imageDao: ImageDao): UserDao {

    private val firestore = FirebaseFirestore.getInstance().collection("users")

    override suspend fun getUserById(idUser: String): Result<User?> = try{
        val document = firestore.document(idUser).get().await()
        Result.Success(document.toObject(User::class.java))
    }catch (e: Exception) {
        throw UserDaoException("Algo salio mal al obtener el usuario")
    }

    override suspend fun saveUser(user: User, userUid: String): Result<Unit> = try {
        firestore.document(userUid).set(user).await()
        Result.Success(Unit)
    }catch (e: Exception) {
        throw UserDaoException("Algo salio mal al guardar el usuario.")
    }

    override suspend fun uploadImage(image: ByteArray, userUid: String): Result<String> = imageDao.uploadImage(image, userUid)
}