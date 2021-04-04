package co.recipes.core.exceptions

class TranslatorException(message: String = "") : Exception("$DESCRIPTION, $message") {

    companion object {
        const val DESCRIPTION = "Algo salio mal con la traduccion."
    }
}