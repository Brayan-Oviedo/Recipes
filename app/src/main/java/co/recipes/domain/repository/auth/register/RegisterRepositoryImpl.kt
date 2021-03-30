package co.recipes.domain.repository.auth.register

import co.recipes.core.Result
import co.recipes.core.exceptions.RegisterException
import co.recipes.core.exceptions.UserDaoException
import co.recipes.data.model.user.User
import co.recipes.data.remote.user.UserDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RegisterRepositoryImpl(private val userDao: UserDao): RegisterRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun register(user: User, password: String): Result<Unit> = try{
        auth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {  }.await()
        Result.Success(Unit)
    }catch (e: Exception) {
        if(e is UserDaoException){
            throw RegisterException(e.message.toString())
        }else
            throw RegisterException()
    }

    override suspend fun uploadImage(image: ByteArray): Result<String> {
        val userUid = auth.currentUser?.uid.toString()
        return userDao.uploadImage(image, userUid)
    }

    override suspend fun saveUser(user: User): Result<Unit> {
        val userUid = auth.currentUser?.uid.toString()
        return userDao.saveUser(user, userUid)
    }
}