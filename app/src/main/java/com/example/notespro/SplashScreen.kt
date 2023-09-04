package com.example.notespro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if(currentUser == null){
                startActivity(Intent(this, LoginScreen::class.java))
            }else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 1000)
    }
}