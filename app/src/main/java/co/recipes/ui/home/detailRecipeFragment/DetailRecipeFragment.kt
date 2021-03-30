package co.recipes.ui.home.detailRecipeFragment

import android.os.Bundle
import android.view.View
import co.recipes.R
import co.recipes.core.baseView.BaseFragment
import co.recipes.data.model.recipe.Recipe
import co.recipes.databinding.FragmentDetailRecipeBinding
import co.recipes.ui.home.detailRecipeFragment.adapter.ingredient.IngredientAdapter
import com.bumptech.glide.Glide

class DetailRecipeFragment : BaseFragment() {

    private lateinit var binding: FragmentDetailRecipeBinding
    private var recipe : Recipe ? = null

    override fun setLayout(): Int = R.layout.fragment_detail_recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Recipe>("recipe")?.let { recipe = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailRecipeBinding.bind(view)
        recipe?.let {
            binding.txtName.text = it.name
            binding.txtServings.text = it.servings.toString()
            binding.txtReadyInMinutes.text = it.readyInMinutes.toString()
            Glide.with(requireContext()).load(it.imageUrl).centerCrop().into(binding.imgRecipe)
            binding.txtHealthScore.text = "${it.healthScore} /100"
            binding.txtPrice.text = (it.pricePerServing * it.servings).toString() + "$"

            binding.rcvIngredients.adapter = IngredientAdapter(it.ingredients)
            binding.txtInstructions.text = it.instructions

        }
    }

}