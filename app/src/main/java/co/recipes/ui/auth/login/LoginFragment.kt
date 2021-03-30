package co.recipes.ui.auth.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import co.recipes.R
import co.recipes.core.Result
import co.recipes.core.baseView.BaseFragment
import co.recipes.core.contracts.IProgressBar
import co.recipes.core.utils.getLoggedUser
import co.recipes.core.utils.mssg
import co.recipes.core.utils.setLoggedUser
import co.recipes.data.model.user.User
import co.recipes.data.remote.image.ImageDaoImpl
import co.recipes.data.remote.user.UserDaoImpl
import co.recipes.databinding.FragmentLoginBinding
import co.recipes.domain.interactor.auth.login.LoginInteractorImpl
import co.recipes.domain.repository.auth.login.LoginRepositoryImpl
import co.recipes.presentation.auth.login.LoginViewModel
import co.recipes.presentation.auth.login.LoginViewModelFactory
import co.recipes.ui.home.HomeActivity

class LoginFragment : BaseFragment(), IProgressBar {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel by viewModels<LoginViewModel> { LoginViewModelFactory ( LoginInteractorImpl ( LoginRepositoryImpl ( UserDaoImpl( ImageDaoImpl() ) ) ) ) }

    override fun setLayout(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.btnLogin.setOnClickListener { login() }
        binding.btnRegister.setOnClickListener { navigateToRegister() }
        checkLoggedUser()
    }

    private fun checkLoggedUser() {
        if(getLoggedUser(preferences).email != "") {
            navigateToHome()
        }
    }

    private fun login() {
        val email = binding.eTxtEmail.text.trim().toString()
        val password = binding.eTxtPassword.text.trim().toString()
        val user = User(email)
        if(viewModel.checkFields(user, password)){
            viewModel.login(user, password).observe(viewLifecycleOwner, Observer {result ->
                when(result) {
                    is Result.Loading -> { showProgressBar() }
                    is Result.Success -> { hideProgressBar()
                        result.let { setLoggedUser(user, preferences)
                            navigateToHome() }
                    }
                    is Result.Failure -> { hideProgressBar()
                        mssg(requireContext(), result.exception.message.toString())
                    }
                }
            })
        }else {
            mssg(requireContext(), "Uno o ambos campos estan vacios")
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}