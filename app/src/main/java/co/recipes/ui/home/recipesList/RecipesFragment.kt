package co.recipes.ui.home.recipesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.recipes.R
import co.recipes.core.Result
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

    private lateinit var binding: FragmentRecipesBinding
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

    override fun setLayout(): Int = R.layout.fragment_recipes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRecipesBinding.bind(view)
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModel.getAllRecipes().observe(viewLifecycleOwner, Observer { 
            when(it) {
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    val adapter = RecipeAdapter(it.data.results, this)
                    binding.rcvMovies.adapter = adapter
                }
                is Result.Failure -> { hideProgressBar()
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

    override fun onRecipeClick(recipe: Recipe) {
        getRecipeById(recipe.id, true)
    }

    private fun getRecipeById(id: Long, includeNutrition: Boolean) {
        viewModel.getRecipeById(id, includeNutrition).observe(viewLifecycleOwner, Observer {
            when(it){
                is Result.Loading -> { showProgressBar() }
                is Result.Success -> { hideProgressBar()
                    navigateToDetailRecipe(it.data)
                }
                is Result.Failure -> { hideProgressBar()
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