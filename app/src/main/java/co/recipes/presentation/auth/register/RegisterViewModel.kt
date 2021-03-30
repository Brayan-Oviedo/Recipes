package co.recipes.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.domain.interactor.auth.register.RegisterInteractor
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val interactor: RegisterInteractor) : ViewModel(){

    fun registerUser(user: User, password: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(interactor.register(user, password))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun uploadImageUser(image: ByteArray) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(interactor.uploadImage(image))
        }catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun saveUser(user: User) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(interactor.saveUser(user))
        }catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun checkEmptyName(name: String) : Boolean = interactor.checkEmptyName(name)
    fun checkEmptyEmail(email: String) : Boolean = interactor.checkEmptyEmail(email)
    fun checkValidEmail(email: String) : Boolean = interactor.checkValidEmail(email)
    fun checkEmptyPassword(password: String) : Boolean = interactor.checkEmptyPassword(password)
    fun checkValidPassword(password: String) : Boolean = interactor.checkValidPassword(password)
    fun checkSamePasswords(p1: String, p2: String) : Boolean = interactor.checkSamePasswords(p1, p2)

}

class RegisterViewModelFactory(val interactor: RegisterInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RegisterInteractor::class.java).newInstance(interactor)
    }
}
