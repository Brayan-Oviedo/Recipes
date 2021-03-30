package co.recipes.core.exceptions

class LoginException(message: String = "") : Exception("$DESCRIPTION, $message") {

    companion object {
        const val DESCRIPTION = "Algo salio mal al logearte."
    }
}