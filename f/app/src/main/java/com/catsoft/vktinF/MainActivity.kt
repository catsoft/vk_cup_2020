package com.catsoft.vktinF

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.catsoft.vktinF.di.SimpleDi
import com.catsoft.vktinF.services.WindowsInsetProvider
import com.catsoft.vktinF.ui.groupsList.GroupsListFragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var _isLogin: Boolean = false
    private var _isInit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(root_fragment_container!!) { _, i ->
            val s = i.systemWindowInsets
            if (s.top != 0) {
                SimpleDi.Instance.register(WindowsInsetProvider::class.java, WindowsInsetProvider(s))
                root_fragment_container.setPadding(0, s.top, 0 , s.bottom)
            }
            i.consumeSystemWindowInsets()
        }

        tryInit()
    }

    override fun onResume() {
        super.onResume()

        showMainFragment()
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
            showMainFragment()
        }
    }

    private fun showMainFragment() {
        if (_isLogin && !_isInit) {

            this.supportFragmentManager.beginTransaction().add(R.id.root_fragment_container, GroupsListFragment()).commit()

            _isInit = true
        }
    }
}


