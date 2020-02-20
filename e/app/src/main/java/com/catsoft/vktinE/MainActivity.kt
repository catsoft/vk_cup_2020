package com.catsoft.vktinE

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.catsoft.vktinE.ui.shareContent.ShareContentFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope


class MainActivity : AppCompatActivity() {

    private var _isLogin : Boolean = false
    private var _isInit : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

        if (savedInstanceState == null) {
            tryInit()
        }
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
                VK.login(activity, setOf(VKScope.WALL))
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun tryInit(){
        if (!VK.isLoggedIn()) {
            VK.login(this, setOf(VKScope.WALL, VKScope.PHOTOS))
        } else {
            _isLogin = true
            showDocumentFragment()
        }
    }

    private fun showDocumentFragment() {
        if (_isLogin && !_isInit) {
            supportFragmentManager.beginTransaction().add(R.id.root, ShareContentFragment()).commit()
            _isInit = true
        }
    }
}


