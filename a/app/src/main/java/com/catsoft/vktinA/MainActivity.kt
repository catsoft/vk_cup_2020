package com.catsoft.vktinA

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.ConfigurationCompat
import com.catsoft.vktinA.di.SimpleDi
import com.catsoft.vktinA.services.CurrentLocaleProvider
import com.catsoft.vktinA.ui.documentList.DocumentListFragment
import com.catsoft.vktinA.vkApi.documents.DocumentsApi
import com.catsoft.vktinA.vkApi.documents.IDocumentsApi
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setSupportActionBar(toolbar!!)

        SimpleDi.Instance.register(IDocumentsApi::class.java, DocumentsApi())
        val locale = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
        SimpleDi.Instance.register(CurrentLocaleProvider::class.java, CurrentLocaleProvider(locale))
    }

    override fun onResume() {
        super.onResume()

        if (!VK.isLoggedIn()) {
            VK.login(this, setOf(VKScope.DOCS))
        } else {
            showDocumentFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val activity = this
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
            }

            override fun onLoginFailed(errorCode: Int) {
                VK.login(activity, setOf(VKScope.DOCS))
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showDocumentFragment() {
        supportFragmentManager.beginTransaction().add(R.id.root, DocumentListFragment()).commit()
    }
}


