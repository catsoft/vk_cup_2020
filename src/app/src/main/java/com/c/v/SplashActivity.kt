package com.c.v

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Intent(this, MainActivity::class.java).also { startActivity(it) }

        finish()
    }
}