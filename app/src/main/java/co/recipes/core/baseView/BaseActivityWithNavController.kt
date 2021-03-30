package co.recipes.core.baseView

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivityWithNavController(): BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(setNavHostFragment()) as NavHostFragment
        navController = navHostFragment.navController
    }

    abstract fun setNavHostFragment() : Int
}