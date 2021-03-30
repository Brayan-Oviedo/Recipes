package co.recipes.core.exceptions

class RegisterException(message: String = ""): Exception("$DESCRIPTION, $message") {
    companion object {
        const val DESCRIPTION = "Algo salio mal al registrarte."
    }
}