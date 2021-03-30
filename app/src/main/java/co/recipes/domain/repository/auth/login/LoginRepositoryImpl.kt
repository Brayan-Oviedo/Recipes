package co.recipes.domain.repository.auth.login

import co.recipes.core.Result
import co.recipes.core.exceptions.LoginException
import co.recipes.core.exceptions.UserDaoException
import co.recipes.data.model.user.User
import co.recipes.data.remote.user.UserDao
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginRepositoryImpl(private val userDao: UserDao): LoginRepository {

    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(user: User, password: String): Result<User?> = try {
        auth.signInWithEmailAndPassword(user.email, password).addOnSuccessListener{}.await()
        userDao.getUserById(auth.currentUser!!.uid)
    }catch (e: Exception) {
        if(e is UserDaoException) {
            throw LoginException(e.message.toString())
        }else
            throw LoginException()
    }
}