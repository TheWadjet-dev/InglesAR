package com.ingar.inglesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.ingar.inglesar.databinding.ActivityInitBinding

class InitActivity : AppCompatActivity() {

    lateinit var binding: ActivityInitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        initUI()
    }

    private fun initUI() {
        binding.btnSign.setOnClickListener{
            val signin = Intent(this, SignInActivity::class.java)
            startActivity(signin)
        }
    }
}