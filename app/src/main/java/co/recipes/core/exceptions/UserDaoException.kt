package co.recipes.core.exceptions

class UserDaoException(message: String): Exception("$DESCRIPTION, $message") {
    companion object {
        const val DESCRIPTION = "Algo salio mal con el usuario"
    }
}