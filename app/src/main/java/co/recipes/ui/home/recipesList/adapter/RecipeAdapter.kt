package co.recipes.ui.home.recipesList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.recipes.core.baseAdapter.BaseViewHolder
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList
import co.recipes.databinding.MovieItemBinding
import com.bumptech.glide.Glide

class RecipeAdapter(private val recipeList: List<Recipe>, private val itemClickListener: onRecipeClickListener): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface onRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = RecipeViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION } ?: return@setOnClickListener
            itemClickListener.onRecipeClick(recipeList[position])
        }

        return holder
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is RecipeViewHolder -> { holder.bind(recipeList[position]) }
        }
    }

    private inner class RecipeViewHolder(
        val binding: MovieItemBinding,
        val context: Context
    ) : BaseViewHolder<Recipe>(binding.root) {
        override fun bind(recipe: Recipe) {
            Glide.with(context).load(recipe.imageUrl).centerCrop().into(binding.imgMovie)
            binding.txtTitle.text = recipe.title
        }
    }
}