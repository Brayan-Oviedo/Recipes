package co.recipes.ui.home.recipesList

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import co.recipes.R
import co.recipes.core.Result
import co.recipes.core.baseAdapter.BaseViewHolder
import co.recipes.core.baseView.BaseFragment
import co.recipes.core.contracts.IProgressBar
import co.recipes.core.utils.mssg
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.remote.recipe.RetrofitClient
import co.recipes.data.remote.recipe.impl.RecipeDaoImpl
import co.recipes.databinding.FragmentRecipesBinding
import co.recipes.domain.repository.recipe.RecipeInteractorImpl
import co.recipes.domain.repository.recipe.RecipeRepositoryImpl
import co.recipes.presentation.recipe.RecipeViewModel
import co.recipes.presentation.recipe.RecipeViewModelFactory
import co.recipes.ui.home.recipesList.adapter.RecipeAdapter

class RecipesFragment : BaseFragment(), IProgressBar, RecipeAdapter.onRecipeClickListener {

    private lateinit var adapter: RecyclerView.Adapter<BaseViewHolder<*>>
    private lateinit var binding: FragmentRecipesBinding
    private lateinit var preferences: SharedPreferences
    private val viewModel by viewModels<RecipeViewModel> {
        RecipeViewModelFactory(
            RecipeInteractorImpl(
                RecipeRepositoryImpl(
                    RecipeDaoImpl(
                        RetrofitClient.recipeService
                    )
                )
            )
        )
    }

    companion object {
        const val IS_TRANSLATOR_WORKING = "isTranslatorWorking"
    }

    override fun setLayout(): Int = R.layout.fragment_recipes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RecipeAdapter(viewModel.recipes, this)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipesBinding.bind(view)
        binding.rcvMovies.adapter = adapter
        hideProgressBarPage()
        configTranslate()
        getAllRecipes()
        binding.rcvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.rcvMovies.canScrollVertically(1)) {
                    viewModel.page += 30
                    getAllRecipes()
                }
            }
        })
    }

    private fun getAllRecipes() {
        viewModel.getAllRecipes().observe(viewLifecycleOwner, Observer { 
            when(it) {
                is Result.Loading -> {
                    if(viewModel.page == 0) showProgressBar()
                    else showProgressBarPage()
                }
                is Result.Success -> { hideProgressBar()
                    hideProgressBarPage()
                    it.data.results.forEach {
                        viewModel.recipes.add(it)
                    }
                }
                is Result.Failure -> { hideProgressBar()
                    hideProgressBarPage()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    fun showProgressBarPage() {
        binding.progressBarPage.visibility = View.VISIBLE
    }

    fun hideProgressBarPage() {
        binding.progressBarPage.visibility = View.GONE
    }

    override fun onRecipeClick(recipe: Recipe) {
        getRecipeById(recipe.id, true)
    }

    private fun getRecipeById(id: Long, includeNutrition: Boolean) {
        viewModel.getRecipeById(id, includeNutrition).observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    if(preferences.getBoolean(IS_TRANSLATOR_WORKING, false)) {
                        translate(it.data)
                    }else navigateToDetailRecipe(it.data)
                }
                is Result.Failure -> { hideProgressBar()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    private fun translate(recipe: Recipe) {
        viewModel.translate(recipe.instructions).observe(viewLifecycleOwner, Observer {
            when(it) {
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    recipe.instructions = it.data
                    navigateToDetailRecipe(recipe)
                }
                is Result.Failure -> { hideProgressBar()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    private fun configTranslate() {
        viewModel.configTranslate().observe(viewLifecycleOwner, Observer {
            when(it) {
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    preferences.edit().putBoolean(IS_TRANSLATOR_WORKING, true).apply()
                }
                is Result.Failure -> { hideProgressBar()
                    preferences.edit().putBoolean(IS_TRANSLATOR_WORKING, false).apply()
                    mssg(requireContext(), it.exception.message.toString())
                }
            }
        })
    }

    private fun navigateToDetailRecipe(recipe: Recipe){
        val bundle = Bundle()
        bundle.putParcelable("recipe", recipe)
        findNavController().navigate(R.id.action_recipesFragment_to_detailRecipeFragment, bundle)
    }
}