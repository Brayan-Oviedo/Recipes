package co.recipes.core.utils

import android.content.SharedPreferences
import co.recipes.data.model.user.User

fun setLoggedUser(user: User, preferences: SharedPreferences) {
    preferences.edit().putString("user_email", user.email)
        .putString("user_name", user.name)
        .putString("user_imageUrl", user.imageUrl)
        .apply()
}

fun getLoggedUser(preferences: SharedPreferences) : User {
    val email = preferences.getString("user_email", "").toString()
    val name = preferences.getString("email_name", "").toString()
    val imageUrl = preferences.getString("email_imageUrl", "").toString()
    val user: User = User(email)
    user.name = name
    user.imageUrl = imageUrl
    return user
}

fun removeLoggedUser(preferences: SharedPreferences) {
    preferences.edit().clear().apply()
}