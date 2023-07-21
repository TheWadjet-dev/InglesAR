package com.ingar.inglesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.ingar.inglesar.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.fabBack.setOnClickListener{
            val init = Intent(this, InitActivity::class.java)
            startActivity(init)
        }
        binding.txtLogIn.setOnClickListener{
            val login = Intent(this, SignUpActivity::class.java)
            startActivity(login)
        }

        binding.txtForgotPass.setOnClickListener{
            val forgot = Intent(this, ForgotPassActivity::class.java)
            startActivity(forgot)
        }

        binding.btnSignIn.setOnClickListener{
            if (binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){

                FirebaseAuth.getInstance().
                signInWithEmailAndPassword(binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        goHome()
                    }else {
                        showAlert()
                    }
                }

            }
        }
    }
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se a producido un error al registrar el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goHome(){
        val home = Intent(this, HomeActivity::class.java)
        startActivity(home)
    }
}