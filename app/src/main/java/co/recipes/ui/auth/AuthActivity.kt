package co.recipes.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import co.recipes.R
import co.recipes.core.baseView.BaseActivityWithNavController
import co.recipes.databinding.ActivityAuthBinding

class AuthActivity : BaseActivityWithNavController() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(LayoutInflater.from(this))
    }

    override fun setLayout(): Int = R.layout.activity_auth

    override fun setNavHostFragment(): Int = R.id.nav_host
}
