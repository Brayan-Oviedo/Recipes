package co.recipes.domain.interactor.auth.register

import co.recipes.core.Result
import co.recipes.data.model.user.User

interface RegisterInteractor {

    suspend fun register(user: User, password: String): Result<Unit>
    suspend fun uploadImage(image: ByteArray): Result<String>
    suspend fun saveUser(user: User): Result<Unit>

    fun checkEmptyName(name: String) : Boolean
    fun checkEmptyEmail(email: String) : Boolean
    fun checkValidEmail(email: String) : Boolean
    fun checkEmptyPassword(password: String) : Boolean
    fun checkValidPassword(password: String) : Boolean
    fun checkSamePasswords(p1: String, p2: String) : Boolean
}