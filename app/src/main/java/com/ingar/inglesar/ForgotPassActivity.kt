package com.ingar.inglesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ingar.inglesar.databinding.ActivityForgotPassBinding

class ForgotPassActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.fabB.setOnClickListener{
            val signin = Intent(this, SignInActivity::class.java)
            startActivity(signin)
        }
    }
}