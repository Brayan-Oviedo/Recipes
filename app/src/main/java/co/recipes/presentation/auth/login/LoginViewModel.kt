package co.recipes.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import co.recipes.core.Result
import co.recipes.data.model.user.User
import co.recipes.domain.interactor.auth.login.LoginInteractor
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val interactor: LoginInteractor) : ViewModel(){

    fun checkFields(user: User, password: String) = interactor.checkFields(user, password)

    fun login(user: User, password: String) = liveData(Dispatchers.IO) { 
        emit(Result.Loading())
        try{
            emit(interactor.login(user, password))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class LoginViewModelFactory(val iteractor: LoginInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LoginInteractor::class.java).newInstance(iteractor)
    }
}

