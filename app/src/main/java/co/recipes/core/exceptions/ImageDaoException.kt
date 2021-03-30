package co.recipes.core.exceptions

class ImageDaoException(message: String): Exception("$DESCRIPTION, $message") {
    companion object {
        const val DESCRIPTION = "Algo salio mal con la imagen"
    }
}