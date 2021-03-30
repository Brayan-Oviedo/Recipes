package co.recipes.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import co.recipes.R
import co.recipes.core.baseView.BaseActivityWithNavController
import co.recipes.core.utils.getLoggedUser
import co.recipes.data.model.user.User


class HomeActivity : BaseActivityWithNavController() {

    private lateinit var preferences: SharedPreferences
    private lateinit var userLogged: User

    override fun setLayout(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        userLogged = getLoggedUser(preferences)
    }

    override fun setNavHostFragment(): Int = R.id.nav_host_home
}