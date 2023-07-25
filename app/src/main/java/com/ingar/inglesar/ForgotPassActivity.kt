package com.ingar.inglesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ingar.inglesar.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPassBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        initUI()
    }

    private fun initUI() {
        binding.fabB.setOnClickListener{
            val signin = Intent(this, SignInActivity::class.java)
            startActivity(signin)
        }

        binding.btnSend.setOnClickListener{
            val sPassword = binding.etEmail.text.toString()
            mAuth.sendPasswordResetEmail(sPassword)
                .addOnSuccessListener {
                    Toast.makeText(this, "Revisa tu correo electronico", Toast.LENGTH_SHORT).show()
                    val signin = Intent(this, SignInActivity::class.java)
                    startActivity(signin)
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}