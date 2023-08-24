package com.ingar.inglesar

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ingar.inglesar.databinding.ActivityUploadPdfBinding

class UploadPDF : AppCompatActivity() {
    
    private lateinit var binding: ActivityUploadPdfBinding
    private lateinit var pdfReference: StorageReference
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pdfReference = FirebaseStorage.getInstance().getReference("PDFs")

        binding.btnFb.setOnClickListener {
            val intent = Intent()
            intent.setType("pdf/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Seleccione el PDF"), 0)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode== RESULT_OK){
            if (requestCode==0){
                if (data != null) {
                    upload(data.data)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun upload(data: Uri?) {

        var reference: StorageReference = pdfReference.child("Uploads/"+System.currentTimeMillis()+".pdf")
        if (data != null) {
            reference.putFile(data).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot ->
                var uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete)
                    binding.name.text.toString()
                    Toast.makeText(this,  "Archivo Cargado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.showBtn.setOnClickListener {
            var arch: Intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(arch)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getUrl(): String? {
        return getUrl()
    }

}




