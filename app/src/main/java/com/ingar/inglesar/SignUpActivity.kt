package com.ingar.inglesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.ingar.inglesar.R
import com.ingar.inglesar.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.fabBut.setOnClickListener{
            val signin = Intent(this, SignInActivity::class.java)
            startActivity(signin)
        }

        binding.btnRegistro.setOnClickListener{
            if (binding.etNombre.text.isNotEmpty() && binding.etApellido.text.isNotEmpty()
                && binding.etCorreo.text.isNotEmpty() && binding.etPass.text.isNotEmpty()
                && binding.etRepeatPass.text.isNotEmpty()){

                FirebaseAuth.getInstance().
                createUserWithEmailAndPassword(binding.etCorreo.text.toString(),
                    binding.etPass.text.toString()).addOnCompleteListener{
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

    private fun showAlertPass() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Las contraseñas no son iguales")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun goHome(){
        val home = Intent(this, HomeActivity::class.java)
        startActivity(home)
    }
}