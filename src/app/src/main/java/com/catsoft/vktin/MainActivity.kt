package com.catsoft.vktin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.catsoft.vktin.databinding.ActivityMainBinding
import com.catsoft.vktin.di.SimpleDi
import com.catsoft.vktin.services.WindowsInsetProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiConfig
import com.vk.api.sdk.VKDefaultValidationHandler

class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(viewBinding.getRoot())

        this.setSupportActionBar(viewBinding.toolbar)

        initOffsets()

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

    private fun initOffsets() {
        ViewCompat.setOnApplyWindowInsetsListener(viewBinding.rootCoordinator) { _, i ->
            val s = i.systemWindowInsets
            if (s.top != 0) {
                SimpleDi.Instance.register(
                    WindowsInsetProvider::class.java, WindowsInsetProvider(s)
                )
                viewBinding.rootCoordinator.setPadding(0, s.top, 0, s.bottom)
            }
            i.consumeSystemWindowInsets()
        }
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