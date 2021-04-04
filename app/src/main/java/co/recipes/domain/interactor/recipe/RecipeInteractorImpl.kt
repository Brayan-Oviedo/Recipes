package co.recipes.domain.repository.recipe

import co.recipes.core.Result
import co.recipes.core.exceptions.TranslatorException
import co.recipes.data.model.recipe.Recipe
import co.recipes.data.model.recipe.RecipeList
import co.recipes.domain.interactor.recipe.RecipeInteractor
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class RecipeInteractorImpl(private val recipeRepository: RecipeRepository): RecipeInteractor {

    private lateinit var translator: Translator

    override suspend fun getAllRecipes(page: Int): Result<RecipeList> = recipeRepository.getAllRecipes(page)
    override suspend fun getRecipeById(id: Long, includeNutrition: Boolean): Result<Recipe> = recipeRepository.getRecipeById(id, includeNutrition)

    override suspend fun translate(text: String): Result<String> = try {
        var newText = ""
        translator.translate(text).addOnSuccessListener { newText = it }.await()
        Result.Success(newText)
    }catch (e: Exception) {
        throw TranslatorException("No se logro traducir.")
    }

    override suspend fun configTranslate(): Result<Unit> = try {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()
        val conditions = DownloadConditions.Builder().requireWifi().build()
        translator = Translation.getClient(options)
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener {  }.await()
        Result.Success(Unit)
    }catch (e: Exception) {
        throw TranslatorException()
    }
}