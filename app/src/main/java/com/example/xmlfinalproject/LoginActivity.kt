package com.example.xmlfinalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.xmlfinalproject.databinding.ActivityLogin2Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLogin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val scale = resources.displayMetrics.density
            val padding = (16 * scale + 0.5f).toInt()
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )
            insets
        }

        auth = Firebase.auth

        binding.tvDontHaveAccount.setOnClickListener {
            navigateToSignUpPage()
        }

        binding.tvForgot.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if(email.isNotBlank())
                resetPassword(email)
            else
                Toast.makeText(this, "Write your email in email field", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank())
                logIn(email, password)
            else if(password.length < 6)
                Toast.makeText(this, "Password length must be 6 at least", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Must fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if(task.isSuccessful)
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show()
            }
    }

    private fun logIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHomePage()
                } else
                    Toast.makeText(this, "Email or Password is wrong", Toast.LENGTH_SHORT).show()
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            if (currentUser.isEmailVerified)
                navigateToHomePage()
        }
    }

    private fun navigateToHomePage() {
        startActivity(Intent(this, CategoryActivity::class.java))
        finish()
    }

    private fun navigateToSignUpPage() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }
}