package com.catsoft.vktinG

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var _isLogin : Boolean = false
    private var _isInit : Boolean = false
    private var _isCreated = false

    lateinit var navController: NavController
    lateinit var navFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _isCreated = true

        this.setSupportActionBar(toolbar!!)

        if (savedInstanceState == null) {
            tryInit()
        }
    }

    override fun onResume() {
        super.onResume()

        showDocumentFragment()

        trySetNavBar()
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

    private fun tryInit(){
        if (!VK.isLoggedIn()) {
            VK.login(this, setOf(VKScope.GROUPS, VKScope.MARKET))
        } else {
            _isLogin = true
            showDocumentFragment()
        }
    }

    private fun showDocumentFragment() {
        if (_isLogin && !_isInit && _isCreated) {

            val hostFragment = NavHostFragment.create(R.navigation.mobile_navigation)
            navFragment = hostFragment
            supportFragmentManager.beginTransaction().add(R.id.root, hostFragment).commit()

            _isInit = true
        }
    }

    private fun trySetNavBar() {
        if (_isInit && _isCreated){

            navController = navFragment.navController
            val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_markets))
            setupActionBarWithNavController(navController, appBarConfiguration)
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


