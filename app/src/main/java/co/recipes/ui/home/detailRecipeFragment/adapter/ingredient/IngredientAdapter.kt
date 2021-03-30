package co.recipes.ui.home.detailRecipeFragment.adapter.ingredient

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.recipes.core.application.Constants
import co.recipes.core.baseAdapter.BaseViewHolder
import co.recipes.data.model.recipe.ingredient.Ingredient
import co.recipes.databinding.IngredientItemBinding
import com.bumptech.glide.Glide
import java.util.*

class IngredientAdapter( private val ingredients: List<Ingredient> ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = IngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = IngredientViewHolder(itemBinding, parent.context)
        return holder
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is IngredientViewHolder -> { holder.bind(ingredients[position]) }
        }
    }
    
    private inner class IngredientViewHolder( 
        private val binding: IngredientItemBinding,
        private val context: Context
    ) : BaseViewHolder<Ingredient>(binding.root) {
        override fun bind(ingredient: Ingredient) {
            binding.txtAmount.text = ingredient.original
            binding.txtName.text = ingredient.name.toUpperCase(Locale.ROOT)
            Glide.with(context).load("${Constants.URL_INGREDIENTS}${ingredient.imageUrl}")
                .into(binding.imgIngredient)
        }
    }
}