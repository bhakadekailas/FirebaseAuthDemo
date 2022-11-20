package com.kb.firebaseauthdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var imageViewGoogle: ImageView
    lateinit var imageViewFacebook: ImageView
    lateinit var auth: FirebaseAuth
    lateinit var googleSinInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSinInClient = GoogleSignIn.getClient(this, gso)
        initViews()
    }

    private fun initViews() {
        imageViewGoogle = findViewById(R.id.imageViewGoogle)
        imageViewGoogle.setOnClickListener {
            val signInIntent = googleSinInClient.signInIntent
            launcher.launch(signInIntent)
        }

        imageViewFacebook = findViewById(R.id.imageViewFacebook)
        imageViewFacebook.setOnClickListener {
            val signInIntent = googleSinInClient.signInIntent
            launcher.launch(signInIntent)
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleResult(task)
            }
        }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                Toast.makeText(this, "Success = " + account.displayName, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                auth.signOut()
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}