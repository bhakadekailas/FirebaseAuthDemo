package com.kb.firebaseauthdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

open class MainActivity : AppCompatActivity() {
    lateinit var buttonSignOut: Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_main)
        buttonSignOut = findViewById(R.id.buttonSignOut)
        buttonSignOut.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}