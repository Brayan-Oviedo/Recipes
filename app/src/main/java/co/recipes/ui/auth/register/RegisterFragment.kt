package co.recipes.ui.auth.register

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import co.recipes.R
import co.recipes.core.Result
import co.recipes.core.baseView.BaseFragment
import co.recipes.core.contracts.IProgressBar
import co.recipes.core.utils.mssg
import co.recipes.core.utils.setLoggedUser
import co.recipes.core.utils.toBitmap
import co.recipes.core.utils.toByteArray
import co.recipes.data.model.user.User
import co.recipes.data.remote.image.ImageDaoImpl
import co.recipes.data.remote.user.UserDaoImpl
import co.recipes.databinding.FragmentRegisterBinding
import co.recipes.domain.interactor.auth.register.RegisterInteractorImpl
import co.recipes.domain.repository.auth.register.RegisterRepositoryImpl
import co.recipes.presentation.auth.register.RegisterViewModel
import co.recipes.presentation.auth.register.RegisterViewModelFactory
import co.recipes.ui.home.HomeActivity

class RegisterFragment : BaseFragment(), IProgressBar   {
    
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel by viewModels<RegisterViewModel> { RegisterViewModelFactory( RegisterInteractorImpl( RegisterRepositoryImpl( UserDaoImpl ( ImageDaoImpl() ) ) ) ) }
    private var imageUser: Bitmap ?= null
    private val PICK_IMAGE_REQUEST = 100

    override fun setLayout(): Int = R.layout.fragment_register

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        binding.btnRegister.setOnClickListener { register() }
        binding.imgUser.setOnClickListener { openGalleryForImage() }
    }

    fun register() {
        val name = binding.eTxtName.text.toString()
        val email = binding.eTxtEmail.text.toString()
        val password =binding.eTxtPassword.text.toString()
        val checkPassword = binding.eTxtCheckPassword.text.toString()
        val user = User(email)
        user.name = name

        if(viewModel.checkEmptyName(name)) {
            binding.eTxtName.error = "Ingresar nombre."
            return
        }
        if(viewModel.checkEmptyEmail(email)) {
            binding.eTxtEmail.error = "Ingrese el email."
            return
        }
        if(!viewModel.checkValidEmail(email)) {
            binding.eTxtEmail.error = "El email no es valido."
            return
        }
        if(viewModel.checkEmptyPassword(password)) {
            binding.eTxtPassword.error = "Ingrese una contraseña."
            return
        }
        if(!viewModel.checkValidPassword(password)) {
            binding.eTxtPassword.error = "La contraseña no es valida"
            return
        }
        if(viewModel.checkEmptyPassword(checkPassword)) {
            binding.eTxtCheckPassword.error = "Confirma la contraseña"
            return
        }
        if(!viewModel.checkSamePasswords(password, checkPassword)) {
            binding.eTxtCheckPassword.error = "Las contraseñas no coinciden"
            return
        }

        viewModel.registerUser(user, password).observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    if(imageUser != null) doUploadImage(user)
                    else doSaveUser(user)
                }
                is Result.Failure -> { hideProgressBar()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    fun doUploadImage(user: User) {
        imageUser.let {
           it?.let {
               viewModel.uploadImageUser(it.toByteArray()).observe(viewLifecycleOwner, Observer {
                   when(it){
                       is Result.Loading -> { showProgressBar() }
                       is Result.Success -> { hideProgressBar()
                           user.imageUrl = it.data
                           mssg(requireContext(), "mssg: ${user.imageUrl}")
                           doSaveUser(user)
                       }
                       is Result.Failure -> { hideProgressBar()
                           mssg(requireContext(), it.exception.message.toString())
                       }
                   }
               })
           }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun doSaveUser(user: User) {
        viewModel.saveUser(user).observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    mssg(requireContext(), "4")
                    mssg(requireContext(), "Usuario registrado con exito.")
                    setLoggedUser(user, preferences)
                    navigateToHome()
                }
                is Result.Failure -> { hideProgressBar()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        try {
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }catch (e: Exception) {
            mssg(requireContext(), "Algo salio mal al seleccionar tu foto.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK ) {
                imageUser = data?.data?.toBitmap(requireContext())
                imageUser.let { binding.imgUser.setImageBitmap(imageUser) }
            }
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}
