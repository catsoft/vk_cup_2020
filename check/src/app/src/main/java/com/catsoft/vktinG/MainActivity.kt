package com.catsoft.vktinG

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.whenCreated
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.catsoft.vktinG.di.SimpleDi
import com.catsoft.vktinG.services.WindowsInsetProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var _isLogin: Boolean = false
    private var _isInit: Boolean = false

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setSupportActionBar(toolbar!!)

        ViewCompat.setOnApplyWindowInsetsListener(root_coordinator!!) { _, i ->
            val s = i.systemWindowInsets
            if (s.top != 0) {
                SimpleDi.Instance.register(WindowsInsetProvider::class.java, WindowsInsetProvider(s))
                root_coordinator.setPadding(0, s.top, 0 , s.bottom)
            }
            i.consumeSystemWindowInsets()
        }

        navController = findNavController(R.id.host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_markets))
        setupActionBarWithNavController(navController, appBarConfiguration)

        tryInit()
    }

    override fun onResume() {
        super.onResume()

        showDocumentFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val activity = this
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                _isLogin = true
            }

            override fun onLoginFailed(errorCode: Int) {
                VK.login(activity, setOf(VKScope.DOCS))
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun tryInit() {
        if (!VK.isLoggedIn()) {
            VK.login(this, setOf(VKScope.GROUPS, VKScope.MARKET, VKScope.WALL))
        } else {
            _isLogin = true
            showDocumentFragment()
        }
    }

    private fun showDocumentFragment() {
        if (_isLogin && !_isInit) {

            val inflater = navController.navInflater
            val graph = inflater.inflate(R.navigation.mobile_navigation)
            navController.graph = graph

            _isInit = true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}


