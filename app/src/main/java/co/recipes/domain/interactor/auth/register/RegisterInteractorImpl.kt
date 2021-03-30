package co.recipes.domain.interactor.auth.register

import androidx.core.util.PatternsCompat
import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.domain.repository.auth.register.RegisterRepository

class RegisterInteractorImpl(private val repo: RegisterRepository): RegisterInteractor {

    override suspend fun register(user: User, password: String): Result<Unit> = repo.register(user, password)

    override suspend fun uploadImage(image: ByteArray): Result<String> = repo.uploadImage(image)

    override suspend fun saveUser(user: User): Result<Unit> = repo.saveUser(user)

    override fun checkEmptyName(name: String): Boolean = name.isEmpty()
    override fun checkEmptyEmail(email: String): Boolean = email.isEmpty()
    override fun checkValidEmail(email: String): Boolean = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    override fun checkEmptyPassword(password: String): Boolean = password.isEmpty()
    override fun checkValidPassword(password: String): Boolean = password.length >= 6
    override fun checkSamePasswords(p1: String, p2: String): Boolean = p1 == p2
}