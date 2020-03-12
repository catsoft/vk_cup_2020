package com.c.v

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.c.v.databinding.ActivityMainBinding
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.VKDefaultValidationHandler
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    lateinit var viewBinding: ActivityMainBinding

    private lateinit var navController: NavController

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.getRoot())

        this.setSupportActionBar(viewBinding.toolbar)

        setupVKConfig()

        setupNavController()
    }

    private fun setupNavController() {
        navController = findNavController(R.id.host_fragment)
        navController.setGraph(R.navigation.mobile_navigation)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_features))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupVKConfig() {
        VK.setConfig(
            VKApiConfig(
                context = this,
                appId = resources.getInteger(R.integer.com_vk_sdk_AppId),
                validationHandler = VKDefaultValidationHandler(this),
                version = "5.103"
            )
        )
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        for(fragment in supportFragmentManager.fragments) {
            if (fragment is NavHostFragment) {
                val childFragmentManager = fragment.childFragmentManager
                for (childFragment in childFragmentManager.fragments) {
                    childFragment.onActivityResult(requestCode, resultCode, data)
                }
            } else {
                fragment.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}