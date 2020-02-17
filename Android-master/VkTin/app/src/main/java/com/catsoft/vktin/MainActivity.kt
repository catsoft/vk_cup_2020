package com.catsoft.vktin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        var appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_friends))
        setupActionBarWithNavController(navController, appBarConfiguration)

        if(!VKSdk.isLoggedIn()) {
            VKSdk.login(this, VKScope.WALL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        navController.navigate(R.id.navigation_friends)
    }
}
